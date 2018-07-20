package com.legrand.ss.protocol.model.config;

import java.io.Serializable;

public class VDPInfoAckData implements Serializable {

	private static final long serialVersionUID = 7303486694172910930L;
	private String callNum;
	private String mac;

	public VDPInfoAckData() {
	}

	public VDPInfoAckData(String callNum, String mac) {
		this.callNum = callNum;
		this.mac = mac;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

}