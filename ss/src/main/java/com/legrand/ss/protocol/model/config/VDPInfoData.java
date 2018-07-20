package com.legrand.ss.protocol.model.config;

import java.io.Serializable;

public class VDPInfoData implements Serializable {

	private static final long serialVersionUID = -7977304210471282786L;
	private String callNum;
	private String mac;

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
