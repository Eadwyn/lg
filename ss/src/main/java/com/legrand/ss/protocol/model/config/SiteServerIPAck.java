package com.legrand.ss.protocol.model.config;

import com.legrand.ss.protocol.model.SubType;

public class SiteServerIPAck extends ConfigAckMessage {

	private static final long serialVersionUID = -8924978558350368602L;
	private SiteServerIPAckData data;

	public SiteServerIPAck(SiteServerIPAckData data) {
		this.data = data;
		this.subType = SubType.CONFIG_SITESERVER_IP_ACK;
	}

	public SiteServerIPAck(boolean success, SiteServerIPAckData data) {
		this.success = success;
		this.data = data;
		this.subType = SubType.CONFIG_SITESERVER_IP_ACK;
	}

	public SiteServerIPAck(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.subType = SubType.CONFIG_SITESERVER_IP_ACK;
	}

	public SiteServerIPAck(boolean success, String message, SiteServerIPAckData data) {
		this.success = success;
		this.message = message;
		this.data = data;
		this.subType = SubType.CONFIG_SITESERVER_IP_ACK;
	}

	public SiteServerIPAckData getData() {
		return data;
	}
}
