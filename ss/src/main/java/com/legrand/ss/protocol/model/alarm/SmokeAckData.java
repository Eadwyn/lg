package com.legrand.ss.protocol.model.alarm;

import java.io.Serializable;

public class SmokeAckData implements Serializable {

	private static final long serialVersionUID = -3842874669253263117L;
	private String callNum;

	public SmokeAckData() {
	}

	public SmokeAckData(String callNum) {
		this.callNum = callNum;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}
}
