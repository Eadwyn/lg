package com.legrand.ss.protocol.model.config;

import java.io.Serializable;

public class SettingsRequestData implements Serializable {

	private static final long serialVersionUID = 5638856522561016125L;
	private String callNum;
	
	public SettingsRequestData() {}
	public SettingsRequestData(String callNum) {
		this.callNum = callNum;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

}
