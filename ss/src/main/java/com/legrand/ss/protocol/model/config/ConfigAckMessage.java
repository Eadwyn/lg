package com.legrand.ss.protocol.model.config;

import com.legrand.ss.protocol.model.MessageAck;
import com.legrand.ss.protocol.model.Type;

public abstract class ConfigAckMessage extends MessageAck {

	private static final long serialVersionUID = -8566802039552114935L;

	public ConfigAckMessage() {
		this.type = Type.CONFIG;
	}

	public ConfigAckMessage(boolean success) {
		super(success);
	}

	public ConfigAckMessage(boolean success, String message) {
		super(success, message);
	}
}
