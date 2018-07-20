package com.legrand.ss.protocol.model.event;

import com.legrand.ss.protocol.model.SubType;
import com.legrand.ss.protocol.model.alarm.AlarmMessage;

public class Unlock extends AlarmMessage {

	private static final long serialVersionUID = 3477031889570067355L;
	private UnlockData data;

	private Unlock() {
		this.subType = SubType.EVENT_UNLOCK;
	}

	public UnlockData getData() {
		return data;
	}

	public void setData(UnlockData data) {
		this.data = data;
	}
}
