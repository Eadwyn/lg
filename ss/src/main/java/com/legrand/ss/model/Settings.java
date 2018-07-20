package com.legrand.ss.model;

import java.io.Serializable;

public class Settings implements Serializable {

	private static final long serialVersionUID = -8294010544367119154L;
	private long id;
	private String siteServerIp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSiteServerIp() {
		return siteServerIp;
	}

	public void setSiteServerIp(String siteServerIp) {
		this.siteServerIp = siteServerIp;
	}
}
