package com.legrand.ss.protocol.model.config;

import java.io.Serializable;

public class SiteServerIPAckData implements Serializable {

	private static final long serialVersionUID = 1705990453650795908L;
	private String ip;
	
	public SiteServerIPAckData() {
		
	}
	
	public SiteServerIPAckData(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
