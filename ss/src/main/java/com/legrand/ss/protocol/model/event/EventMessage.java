package com.legrand.ss.protocol.model.event;

import com.legrand.ss.protocol.model.Message;
import com.legrand.ss.protocol.model.Type;

public class EventMessage extends Message {

	private static final long serialVersionUID = 5031876877222520630L;

	public EventMessage() {
		this.type = Type.EVENT;
	}
}
