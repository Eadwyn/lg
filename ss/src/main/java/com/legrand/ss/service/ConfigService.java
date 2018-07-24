package com.legrand.ss.service;

import org.springframework.stereotype.Service;

import com.legrand.ss.context.GlobalContext;
import com.legrand.ss.model.VDP;
import com.legrand.ss.protocol.CommService;
import com.legrand.ss.protocol.model.MessageAck;
import com.legrand.ss.protocol.model.config.Settings;
import com.legrand.ss.protocol.model.config.SettingsAck;
import com.legrand.ss.protocol.model.config.SettingsData;
import com.legrand.ss.protocol.model.config.SettingsRequest;
import com.legrand.ss.protocol.model.config.SettingsRequestAck;
import com.legrand.ss.protocol.model.config.SettingsRequestAckData;
import com.legrand.ss.protocol.model.config.SettingsRequestData;

@Service
public class ConfigService {

	public VDP read(String callNum) {
		SettingsRequest request = new SettingsRequest();
		SettingsRequestData data = new SettingsRequestData(callNum);
		request.setData(data);
		
		MessageAck ack = CommService.send(callNum, request);
		if (ack instanceof SettingsRequestAck) {
			SettingsRequestAckData result = ((SettingsRequestAck)ack).getData();
			VDP vdp = GlobalContext.getVDP(callNum);
			if (null == vdp) {
				vdp = new VDP();
				GlobalContext.addVDP(vdp);
			}
			vdp.setAlarmDuration(result.getAlarmDuration());
			vdp.setCallNum(callNum);
			vdp.setChildMcIP(result.getChildMcIP());
			vdp.setMcIP(result.getMcIP());
			vdp.setSceneMode(result.getSceneMode());
			vdp.setSiteServerIP(result.getSiteServerIP());
			vdp.setFrimwareVersion(result.getFrimwareVersion());
			return vdp;
		}
		return null;
	}
	public boolean write(String callNum,  String siteServerIp, 
			 String mcIP,  String childMcIP,  int alarmDuration,
			 int sceneMode) {
		Settings request = new Settings();
		SettingsData data = new SettingsData();
		data.setAlarmDuration(alarmDuration);
		data.setCallNum(callNum);
		data.setChildMcIP(childMcIP);
		data.setMcIP(mcIP);
		data.setSceneMode(sceneMode);
		data.setSiteServerIP(siteServerIp);
		request.setData(data);
		
		MessageAck ack = CommService.send(callNum, request);
		if (ack instanceof SettingsAck) {
			VDP vdp = GlobalContext.getVDP(callNum);
			if (null != vdp) {
				vdp.setAlarmDuration(alarmDuration);
				vdp.setCallNum(callNum);
				vdp.setChildMcIP(childMcIP);
				vdp.setMcIP(mcIP);
				vdp.setSceneMode(sceneMode);
				vdp.setSiteServerIP(siteServerIp);
			}
			return true;
		}
		return false;
	}

}
