package com.legrand.ss.protocol.model.event;

import com.legrand.ss.protocol.model.MessageAck;
import com.legrand.ss.protocol.model.Type;

public class EventAckMessage extends MessageAck {

	private static final long serialVersionUID = 4871680916405754434L;

	public EventAckMessage() {
		this.type = Type.EVENT;
	}

	public EventAckMessage(boolean success) {
		super(success);
	}

	public EventAckMessage(boolean success, String message) {
		super(success, message);
	}
}
