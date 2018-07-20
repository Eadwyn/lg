package com.legrand.ss.protocol.model.config;

import com.legrand.ss.protocol.model.Message;
import com.legrand.ss.protocol.model.Type;

public abstract class ConfigMessage extends Message {

	private static final long serialVersionUID = -1465780812995034912L;

	public ConfigMessage() {
		this.type = Type.CONFIG;
	}
}
