package com.legrand.ss.protocol.model.config;

import com.legrand.ss.protocol.model.SubType;

public class VDPInfo extends ConfigMessage {
	private static final long serialVersionUID = -4132382092236096787L;
	
	private VDPInfoData data;

	private VDPInfo() {
		this.subType = SubType.CONFIG_VDP_INFO;
	}

	public VDPInfoData getData() {
		return data;
	}

	public void setData(VDPInfoData data) {
		this.data = data;
	}
}
