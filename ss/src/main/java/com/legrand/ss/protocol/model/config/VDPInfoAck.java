package com.legrand.ss.protocol.model.config;

import com.legrand.ss.protocol.model.SubType;

public class VDPInfoAck extends ConfigAckMessage {

	private static final long serialVersionUID = 7325097960630930781L;

	private VDPInfoAckData data;

	public VDPInfoAck() {
		this.subType = SubType.CONFIG_VDP_INFO_ACK;
	}

	public VDPInfoAck(VDPInfoAckData data) {
		this.data = data;
		this.subType = SubType.CONFIG_VDP_INFO_ACK;
	}

	public VDPInfoAck(boolean success, VDPInfoAckData data) {
		this.success = success;
		this.data = data;
		this.subType = SubType.CONFIG_VDP_INFO_ACK;
	}

	public VDPInfoAck(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.subType = SubType.CONFIG_VDP_INFO_ACK;
	}

	public VDPInfoAck(boolean success, String message, VDPInfoAckData data) {
		this.success = success;
		this.message = message;
		this.data = data;
		this.subType = SubType.CONFIG_VDP_INFO_ACK;
	}

	public VDPInfoAckData getData() {
		return data;
	}

	public void setData(VDPInfoAckData data) {
		this.data = data;
	}
}
