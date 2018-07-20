package com.legrand.ss.protocol.model.config;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SettingsRequestAckData implements Serializable {

	private static final long serialVersionUID = -6867330986778539986L;
	private String ip;
	private String siteServerIP;
	private String callNum;
	@JsonIgnore
	private String mac;
	private String mcIP;
	private String childMcIP;
	private Integer alarmDuration;
	private Integer sceneMode;
	private String frimwareVersion;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

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

	public String getFrimwareVersion() {
		return frimwareVersion;
	}

	public void setFrimwareVersion(String frimwareVersion) {
		this.frimwareVersion = frimwareVersion;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
}
