package com.legrand.ss.protocol.model.alarm;

import java.io.Serializable;

public class SosAckData implements Serializable {

	private static final long serialVersionUID = 5601989068802746118L;
	private String callNum;

	public SosAckData() {
	}

	public SosAckData(String callNum) {
		this.callNum = callNum;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}
}
