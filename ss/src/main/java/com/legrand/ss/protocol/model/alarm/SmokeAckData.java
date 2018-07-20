package com.legrand.ss.protocol.model.alarm;

public class SmokeAckData extends AlarmAckMessage {

	private static final long serialVersionUID = 346671438393225019L;
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
