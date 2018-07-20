package com.legrand.ss.protocol.model.alarm;

import java.io.Serializable;

public class SmokeData implements Serializable {

	private static final long serialVersionUID = -6562047127264179190L;
	private String callNum;
	private String time;
	private int areaNum;

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

	public int getAreaNum() {
		return areaNum;
	}

	public void setAreaNum(int areaNum) {
		this.areaNum = areaNum;
	}
}
