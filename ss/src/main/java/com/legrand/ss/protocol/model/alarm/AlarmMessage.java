package com.legrand.ss.protocol.model.alarm;

import com.legrand.ss.protocol.model.Message;
import com.legrand.ss.protocol.model.Type;

public abstract class AlarmMessage extends Message {

	private static final long serialVersionUID = 4293757744161690881L;

	public AlarmMessage() {
		this.type = Type.ALARM;
	}
}
