package com.legrand.ss.protocol.model.config;

import java.io.Serializable;

public class SettingsAckData implements Serializable {

	private static final long serialVersionUID = -7250251785410480321L;
	private String callNum;
	
	public SettingsAckData() {}
	public SettingsAckData(String callNum) {
		this.callNum = callNum;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}
}