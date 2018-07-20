package com.legrand.ss.model;

import java.io.Serializable;

public class VDP implements Serializable {

	private static final long serialVersionUID = 1644185958482133762L;
	private String ip;
	private String mac;
	private String callNum;
	private String siteServerIP;
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

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	public String getSiteServerIP() {
		return siteServerIP;
	}

	public void setSiteServerIP(String siteServerIP) {
		this.siteServerIP = siteServerIP;
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

	public void setAlarmDuration(int alarmDuration) {
		this.alarmDuration = alarmDuration;
	}

	public Integer getSceneMode() {
		return sceneMode;
	}

	public void setSceneMode(int sceneMode) {
		this.sceneMode = sceneMode;
	}

	public String getFrimwareVersion() {
		return frimwareVersion;
	}

	public void setFrimwareVersion(String frimwareVersion) {
		this.frimwareVersion = frimwareVersion;
	}

}
