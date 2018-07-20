package com.legrand.ss.protocol.model.config;

import com.legrand.ss.protocol.model.SubType;

public class SiteServerIP extends ConfigMessage {
	private static final long serialVersionUID = -2113647318846351047L;

	private VDPInfoData data;

	private SiteServerIP() {
		this.subType = SubType.CONFIG_SITESERVER_IP;
	}

	public VDPInfoData getData() {
		return data;
	}

	public void setData(VDPInfoData data) {
		this.data = data;
	}
}
