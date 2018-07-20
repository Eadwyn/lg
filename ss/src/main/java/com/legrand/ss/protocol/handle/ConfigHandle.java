package com.legrand.ss.protocol.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legrand.ss.context.GlobalContext;
import com.legrand.ss.model.VDP;
import com.legrand.ss.protocol.CommService;
import com.legrand.ss.protocol.model.MessageData;
import com.legrand.ss.protocol.model.SubType;
import com.legrand.ss.protocol.model.Type;
import com.legrand.ss.protocol.model.config.SettingsAck;
import com.legrand.ss.protocol.model.config.SettingsAckData;
import com.legrand.ss.protocol.model.config.SettingsData;
import com.legrand.ss.protocol.model.config.SettingsRequestAck;
import com.legrand.ss.protocol.model.config.SettingsRequestAckData;
import com.legrand.ss.protocol.model.config.SettingsRequestData;
import com.legrand.ss.protocol.model.config.VDPInfo;
import com.legrand.ss.protocol.model.config.VDPInfoAck;
import com.legrand.ss.protocol.model.config.VDPInfoAckData;
import com.legrand.ss.protocol.model.config.VDPInfoData;
import com.legrand.ss.util.JSONUtil;

public class ConfigHandle implements MessageHandle {

	private static final Logger logger = LoggerFactory.getLogger(ConfigHandle.class);
	private static final ObjectMapper jsoMapper = new ObjectMapper();

	static {
		jsoMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public String getType() {
		return Type.CONFIG;
	}

	@Override
	public boolean parse(MessageData message) {
		if (!Type.CONFIG.equals(message.getType())) {
			return false;
		}

		switch (message.getSubType()) {
		case SubType.CONFIG_VDP_INFO:
			return this.vdpInfoHandle(message);
		case SubType.CONFIG_SETTINGS_ACK:
			return this.settingsAckHandle(message);
		case SubType.CONFIG_SETTINGS_REQUEST_ACK:
			return this.settingsRequestAckHandle(message);
		case SubType.CONFIG_SETTINGS:
			return this.settingsHandle(message);
		case SubType.CONFIG_SETTINGS_REQUEST:
			return this.settingsRequestHandle(message);
		default:
			return false;
		}
	}

	private boolean vdpInfoHandle(MessageData message) {
		String callNum = null;
		String mac = null;
		VDPInfoAck ack = null;
		try {
			VDPInfo info = JSONUtil.encode(message.getJsonString(), VDPInfo.class);
			if (null == info || null == info.getData()) {
				ack = new VDPInfoAck(false, "invalid frame");
				return CommService.sendAck(ack);
			}
			
			VDPInfoData data = info.getData();
			callNum = data.getCallNum();
			mac = data.getMac();
			if (null == mac) {
				ack = new VDPInfoAck(false, "invalid frame", new VDPInfoAckData(callNum, mac));
				return CommService.sendAck(ack);
			}

			if (null != callNum) {
				if (GlobalContext.addVDPinfo(mac, callNum, null, null, null, -1, -1)) {
					ack = new VDPInfoAck(true, new VDPInfoAckData(callNum, mac));
				} else {
					ack = new VDPInfoAck(false, "Call Number or MAC already exists, but not match with the Site server's", new VDPInfoAckData(data.getCallNum(), mac));
				}
			} else {
				String existsCallNum = GlobalContext.getCallNum(mac);
				if (null == existsCallNum) {
					ack = new VDPInfoAck(false, "callNum not exists", new VDPInfoAckData(existsCallNum, mac));
				} else {
					ack = new VDPInfoAck(true, new VDPInfoAckData(existsCallNum, mac));
				}
			}
		} catch (Exception e) {
			ack = new VDPInfoAck(false, "invalid frame", new VDPInfoAckData(callNum, mac));
		}

		return CommService.sendAck(ack);
	}

	private boolean settingsAckHandle(MessageData message) {
		SettingsAck data = JSONUtil.encode(message.getJsonString(), SettingsAck.class);

		VDP vdp = GlobalContext.getVDP(data.getData().getCallNum());
		if (null != vdp) {
			CommService.addAck(data.getData().getCallNum(), message.getSubType(), data);
		} else {
			logger.warn("vdp not exists");
		}
		return true;
	}

	private boolean settingsRequestAckHandle(MessageData message) {
		try {
			SettingsRequestAck ack = JSONUtil.encode(message.getJsonString(), SettingsRequestAck.class);
			SettingsRequestAckData data = ack.getData();

			VDP vdp = GlobalContext.getVDP(data.getCallNum());
			if (null != vdp) {
				vdp.setAlarmDuration(data.getAlarmDuration());
				vdp.setChildMcIP(data.getChildMcIP());
				vdp.setFrimwareVersion(data.getFrimwareVersion());
				vdp.setIp(data.getIp());
				vdp.setCallNum(data.getCallNum());
				// vdp.setMac(data.getMac());
				vdp.setMcIP(data.getMcIP());
				vdp.setSceneMode(data.getSceneMode());
				vdp.setSiteServerIP(data.getSiteServerIP());
				CommService.addAck(vdp.getCallNum(), message.getSubType(), ack);
			} else {
				logger.warn("vdp not exists");
			}

			return true;
		} catch (Exception e) {
			logger.error("error", e);
			return false;
		}
	}

	private boolean settingsHandle(MessageData message) {
		String callNum = "";
		try {
			SettingsData data = JSONUtil.encode(message.getData(), SettingsData.class);
			callNum = data.getCallNum();
			VDP vdp = GlobalContext.getVDP(data.getCallNum());
			if (null != vdp) {
				vdp.setAlarmDuration(data.getAlarmDuration());
				vdp.setChildMcIP(data.getChildMcIP());
				vdp.setCallNum(data.getCallNum());
				vdp.setMcIP(data.getMcIP());
				vdp.setSceneMode(data.getSceneMode());
				vdp.setSiteServerIP(data.getSiteServerIP());

				SettingsAck ack = new SettingsAck(true);
				ack.setData(new SettingsAckData(callNum));
				return CommService.sendAck(ack);
			} else {
				logger.warn("vdp not exists");
				SettingsAck ack = new SettingsAck(false, "vdp not exists");
				ack.setData(new SettingsAckData(callNum));
				return CommService.sendAck(ack);
			}
		} catch (Exception e) {
			logger.error("error", e);
			SettingsAck ack = new SettingsAck(false, "invalid frame");
			ack.setData(new SettingsAckData(callNum));
			return CommService.sendAck(ack);
		}
	}

	private boolean settingsRequestHandle(MessageData message) {
		String callNum = "";
		try {
			SettingsRequestData data = JSONUtil.encode(message.getData(), SettingsRequestData.class);
			callNum = data.getCallNum();
			VDP vdp = GlobalContext.getVDP(data.getCallNum());
			if (null == vdp) {
				logger.warn("vdp not exists");
				SettingsRequestAck ack = new SettingsRequestAck(false, "vdp not exists");
				SettingsRequestAckData adata = new SettingsRequestAckData();
				adata.setCallNum(data.getCallNum());
				ack.setData(adata);
				return CommService.sendAck(ack);
			} else {
				SettingsRequestAck ack = new SettingsRequestAck(true);
				SettingsRequestAckData adata = new SettingsRequestAckData();

				adata.setCallNum(vdp.getCallNum());
				adata.setAlarmDuration(vdp.getAlarmDuration());
				adata.setChildMcIP(vdp.getChildMcIP());
				adata.setMcIP(vdp.getMcIP());
				adata.setSceneMode(vdp.getSceneMode());
				adata.setSiteServerIP(vdp.getSiteServerIP());
				ack.setData(adata);
				return CommService.sendAck(ack);
			}
		} catch (Exception e) {
			logger.error("error", e);
			SettingsRequestAck ack = new SettingsRequestAck(false, "invalid frame");
			SettingsRequestAckData dat = new SettingsRequestAckData();
			dat.setCallNum(callNum);
			ack.setData(dat);
			return CommService.sendAck(ack);
		}
	}
}
