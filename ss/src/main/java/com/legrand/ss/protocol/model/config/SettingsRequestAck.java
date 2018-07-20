package com.legrand.ss.protocol.model.config;

import com.legrand.ss.protocol.model.SubType;

public class SettingsRequestAck extends ConfigAckMessage {

	private static final long serialVersionUID = 2493275126340679776L;
	private SettingsRequestAckData data;

	public SettingsRequestAck() {
		this.subType = SubType.CONFIG_SETTINGS_REQUEST_ACK;
	}

	public SettingsRequestAck(boolean success) {
		this.success = success;
		this.subType = SubType.CONFIG_SETTINGS_REQUEST_ACK;
	}

	public SettingsRequestAck(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.subType = SubType.CONFIG_SETTINGS_REQUEST_ACK;
	}

	public SettingsRequestAckData getData() {
		return data;
	}

	public void setData(SettingsRequestAckData data) {
		this.data = data;
	}
}
