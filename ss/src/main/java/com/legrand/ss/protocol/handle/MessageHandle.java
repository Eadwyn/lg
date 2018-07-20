package com.legrand.ss.protocol.handle;

import com.legrand.ss.protocol.model.MessageData;

public interface MessageHandle {

	public abstract String getType();
	
	public abstract boolean parse(MessageData message);
}
