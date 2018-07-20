package com.legrand.ss.protocol.handle;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legrand.ss.context.GlobalContext;
import com.legrand.ss.model.Unlock;
import com.legrand.ss.protocol.CommService;
import com.legrand.ss.protocol.model.MessageData;
import com.legrand.ss.protocol.model.SubType;
import com.legrand.ss.protocol.model.Type;
import com.legrand.ss.protocol.model.event.UnlockAck;
import com.legrand.ss.protocol.model.event.UnlockData;
import com.legrand.ss.util.JSONUtil;

public class EventHandle implements MessageHandle {

	private static final Logger logger = LoggerFactory.getLogger(EventHandle.class);
	private static final ObjectMapper jsoMapper = new ObjectMapper();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");

	static {
		jsoMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public String getType() {
		return Type.EVENT;
	}

	@Override
	public boolean parse(MessageData message) {
		if (!Type.EVENT.equals(message.getType())) {
			return false;
		}

		switch (message.getSubType()) {
		case SubType.EVENT_UNLOCK:
			return this.unlockHandle(message);
		default:
			return false;
		}
	}

	private boolean unlockHandle(MessageData message) {
		try {
			UnlockData data = JSONUtil.encode(message.getData(), UnlockData.class);
			Unlock unlock = new Unlock();
			unlock.setCallNum(data.getCallNum());
			unlock.setType(data.getType());
			unlock.setTime(sdf.parse(data.getTime()));
			GlobalContext.addUnlock(unlock);
			
			UnlockAck ack = new UnlockAck(true);
			return CommService.sendAck(ack);
		} catch (Exception e) {
			logger.error("error", e);
			UnlockAck ack = new UnlockAck(false, "invalid frame");
			return CommService.sendAck(ack);
		}
	}
}
