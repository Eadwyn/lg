package com.legrand.ss.protocol.model.alarm;

import com.legrand.ss.protocol.model.MessageAck;
import com.legrand.ss.protocol.model.Type;

public abstract class AlarmAckMessage extends MessageAck {

	private static final long serialVersionUID = 8553652003204505423L;

	public AlarmAckMessage() {
		this.type = Type.ALARM;
	}

	public AlarmAckMessage(boolean success) {
		super(success);
	}

	public AlarmAckMessage(boolean success, String message) {
		super(success, message);
	}
}
