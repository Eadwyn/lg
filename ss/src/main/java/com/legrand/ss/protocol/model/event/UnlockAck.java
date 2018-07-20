package com.legrand.ss.protocol.model.event;

import com.legrand.ss.protocol.model.SubType;

public class UnlockAck extends EventAckMessage {

	private static final long serialVersionUID = 6464191646909889349L;

	private UnlockAckData data;
	private UnlockAck() {
		this.subType = SubType.EVENT_UNLOCK_ACK;
	}

	public UnlockAck(boolean success) {
		this.success = success;
		this.subType = SubType.EVENT_UNLOCK_ACK;
	}

	public UnlockAck(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.subType = SubType.EVENT_UNLOCK_ACK;
	}

	public UnlockAckData getData() {
		return data;
	}

	public void setData(UnlockAckData data) {
		this.data = data;
	}
}
