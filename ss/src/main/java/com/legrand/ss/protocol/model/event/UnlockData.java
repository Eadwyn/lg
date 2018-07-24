package com.legrand.ss.protocol.model.event;

import java.io.Serializable;

public class UnlockData implements Serializable {

	private static final long serialVersionUID = 1386853664330847821L;
	private String callNum;
	private String time;
	private Integer type;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
