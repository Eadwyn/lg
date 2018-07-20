package com.legrand.ss.protocol.model.alarm;

import com.legrand.ss.protocol.model.SubType;

public class Sos extends AlarmMessage {

	private static final long serialVersionUID = -6504981724777926375L;

	private SosData data;

	private Sos() {
		this.subType = SubType.ALARM_SOS;
	}

	public SosData getData() {
		return data;
	}

	public void setData(SosData data) {
		this.data = data;
	}
}
