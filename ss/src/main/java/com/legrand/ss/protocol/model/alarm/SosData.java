package com.legrand.ss.protocol.model.alarm;

import java.io.Serializable;

public class SosData implements Serializable {

	private static final long serialVersionUID = 949508780001137535L;
	private String callNum;
	private String time;

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
