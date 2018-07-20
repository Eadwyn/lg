package com.legrand.ss.protocol.handle;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legrand.ss.context.GlobalContext;
import com.legrand.ss.model.SmokeAlarm;
import com.legrand.ss.model.SosAlarm;
import com.legrand.ss.protocol.CommService;
import com.legrand.ss.protocol.model.MessageData;
import com.legrand.ss.protocol.model.SubType;
import com.legrand.ss.protocol.model.Type;
import com.legrand.ss.protocol.model.alarm.SmokeAck;
import com.legrand.ss.protocol.model.alarm.SmokeData;
import com.legrand.ss.protocol.model.alarm.SosAck;
import com.legrand.ss.protocol.model.alarm.SosData;
import com.legrand.ss.util.JSONUtil;

public class AlarmHandle implements MessageHandle {

	private static final Logger logger = LoggerFactory.getLogger(AlarmHandle.class);
	private static final ObjectMapper jsoMapper = new ObjectMapper();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");

	static {
		jsoMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public String getType() {
		return Type.ALARM;
	}

	@Override
	public boolean parse(MessageData message) {
		if (!Type.ALARM.equals(message.getType())) {
			return false;
		}

		switch (message.getSubType()) {
		case SubType.ALARM_SMOKE:
			return this.smokeHandle(message);
		case SubType.ALARM_SOS:
			return this.sosHandle(message);
		default:
			return false;
		}
	}

	private boolean smokeHandle(MessageData message) {
		try {
			SmokeData data = JSONUtil.encode(message.getData(), SmokeData.class);
			SmokeAlarm smoke = new SmokeAlarm();
			smoke.setAreaNum(data.getAreaNum());
			smoke.setCallNum(data.getCallNum());
			smoke.setTime(sdf.parse(data.getTime()));
			GlobalContext.addSmoke(smoke);

			SmokeAck ack = new SmokeAck(true);
			return CommService.sendAck(ack);
		} catch (Exception e) {
			logger.error("error", e);
			SmokeAck ack = new SmokeAck(false, "invalid frame");
			return CommService.sendAck(ack);
		}
	}

	private boolean sosHandle(MessageData message) {
		try {
			SosData data = JSONUtil.encode(message.getData(), SosData.class);
			SosAlarm sos = new SosAlarm();
			sos.setCallNum(data.getCallNum());
			sos.setTime(sdf.parse(data.getTime()));
			GlobalContext.addSos(sos);

			SosAck ack = new SosAck(true);
			return CommService.sendAck(ack);
		} catch (Exception e) {
			logger.error("error", e);
			SosAck ack = new SosAck(false, "invalid frame");
			return CommService.sendAck(ack);
		}
	}
}
