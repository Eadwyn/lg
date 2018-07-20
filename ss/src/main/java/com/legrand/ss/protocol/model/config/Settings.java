package com.legrand.ss.protocol.model.config;

import com.legrand.ss.protocol.model.SubType;

public class Settings extends ConfigMessage {

	private static final long serialVersionUID = 3702737041766230261L;
	private SettingsData data;

	public Settings() {
		this.subType = SubType.CONFIG_SETTINGS;
	}

	public Settings(SettingsData data) {
		this.subType = SubType.CONFIG_SETTINGS;
		this.data = data;
	}

	public SettingsData getData() {
		return data;
	}

	public void setData(SettingsData data) {
		this.data = data;
	}
}
