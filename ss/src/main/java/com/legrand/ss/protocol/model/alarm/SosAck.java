package com.legrand.ss.protocol.model.alarm;

import com.legrand.ss.protocol.model.SubType;

public class SosAck extends AlarmAckMessage {

	private static final long serialVersionUID = -5942201387488608062L;
	private SosAckData data;

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

	public SosAckData getData() {
		return data;
	}

	public void setData(SosAckData data) {
		this.data = data;
	}
}
