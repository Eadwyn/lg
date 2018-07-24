package com.legrand.ss.protocol.model.config;

import java.io.Serializable;

public class SettingsData implements Serializable {

	private static final long serialVersionUID = -1250618067940143945L;
	private String siteServerIP;
	private String callNum;
	private String mcIP;
	private String childMcIP;
	private Integer alarmDuration;
	private Integer sceneMode;

	public String getSiteServerIP() {
		return siteServerIP;
	}

	public void setSiteServerIP(String siteServerIP) {
		this.siteServerIP = siteServerIP;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	public String getMcIP() {
		return mcIP;
	}

	public void setMcIP(String mcIP) {
		this.mcIP = mcIP;
	}

	public String getChildMcIP() {
		return childMcIP;
	}

	public void setChildMcIP(String childMcIP) {
		this.childMcIP = childMcIP;
	}

	public Integer getAlarmDuration() {
		return alarmDuration;
	}

	public void setAlarmDuration(Integer alarmDuration) {
		this.alarmDuration = alarmDuration;
	}

	public Integer getSceneMode() {
		return sceneMode;
	}

	public void setSceneMode(Integer sceneMode) {
		this.sceneMode = sceneMode;
	}
}
