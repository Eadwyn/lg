package com.legrand.ss.protocol.model.config;

import com.legrand.ss.protocol.model.SubType;

public class SettingsRequest extends ConfigMessage {

	private static final long serialVersionUID = 3737700835924567893L;

	private SettingsRequestData data;

	public SettingsRequest() {
		this.subType = SubType.CONFIG_SETTINGS_REQUEST;
	}

	public SettingsRequestData getData() {
		return data;
	}

	public void setData(SettingsRequestData data) {
		this.data = data;
	}
}
