package com.legrand.ss.protocol.model.event;

import java.io.Serializable;

public class UnlockAckData implements Serializable {

	private static final long serialVersionUID = -8298785737970567976L;
	private String callNum;

	public UnlockAckData() {
	}

	public UnlockAckData(String callNum) {
		this.callNum = callNum;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}
}
