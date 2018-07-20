package com.legrand.ss.protocol.model.config;

import com.legrand.ss.protocol.model.SubType;

public class SettingsAck extends ConfigAckMessage {

	private static final long serialVersionUID = 8768452967875076664L;
	private SettingsAckData data;

	public SettingsAck() {
		this.subType = SubType.CONFIG_SETTINGS_ACK;
	}

	public SettingsAck(boolean success) {
		this.success = success;
		this.subType = SubType.CONFIG_SETTINGS_ACK;
	}

	public SettingsAck(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.subType = SubType.CONFIG_SETTINGS_ACK;
	}

	public SettingsAckData getData() {
		return data;
	}

	public void setData(SettingsAckData data) {
		this.data = data;
	}
}
