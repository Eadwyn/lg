package com.legrand.ss.protocol.model.alarm;

import com.legrand.ss.protocol.model.SubType;

public class SmokeAck extends AlarmAckMessage {

	private static final long serialVersionUID = 6610128317064412300L;
	
	private SmokeAckData data;

	public SmokeAck() {
		this.subType = SubType.ALARM_SMOKE_ACK;
	}

	public SmokeAck(boolean success) {
		this.success = success;
		this.subType = SubType.ALARM_SMOKE_ACK;
	}

	public SmokeAck(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.subType = SubType.ALARM_SMOKE_ACK;
	}

	public SmokeAckData getData() {
		return data;
	}

	public void setData(SmokeAckData data) {
		this.data = data;
	}
}
