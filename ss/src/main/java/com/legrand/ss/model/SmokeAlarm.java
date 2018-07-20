package com.legrand.ss.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SmokeAlarm implements Serializable {

	private static final long serialVersionUID = -261228972544305011L;
	private String callNum;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", locale = "zh", timezone = "GMT+8")
	private Date time;
	private int areaNum;
	public String getCallNum() {
		return callNum;
	}
	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(int areaNum) {
		this.areaNum = areaNum;
	}
}
