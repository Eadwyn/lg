package com.legrand.ss.protocol.model.alarm;

import com.legrand.ss.protocol.model.SubType;

public class SosAck extends AlarmAckMessage {

	private static final long serialVersionUID = -5942201387488608062L;

	public SosAck() {
		this.subType = SubType.ALARM_SOS_ACK;
	}

	public SosAck(boolean success) {
		this.success = success;
		this.subType = SubType.ALARM_SOS_ACK;
	}

	public SosAck(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.subType = SubType.ALARM_SOS_ACK;
	}
}
