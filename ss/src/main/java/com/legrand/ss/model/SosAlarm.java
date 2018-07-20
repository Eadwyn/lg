package com.legrand.ss.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SosAlarm implements Serializable {

	private static final long serialVersionUID = -2410547429358657716L;
	private String callNum;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", locale = "zh", timezone = "GMT+8")
	private Date time;
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
}
