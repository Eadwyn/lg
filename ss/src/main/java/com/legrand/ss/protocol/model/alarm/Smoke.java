package com.legrand.ss.protocol.model.alarm;

import com.legrand.ss.protocol.model.SubType;

public class Smoke extends AlarmMessage {

	private static final long serialVersionUID = -2537958456679682801L;
	private SmokeData data;

	private Smoke() {
		this.subType = SubType.ALARM_SMOKE;
	}

	public SmokeData getData() {
		return data;
	}

	public void setData(SmokeData data) {
		this.data = data;
	}
}
