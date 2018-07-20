package com.legrand.ss.protocol.model;

import java.io.Serializable;

public class MessageAck implements Serializable {

	private static final long serialVersionUID = 8201544558365686726L;
	protected String type;
	protected String subType;
	protected boolean success;
	protected String message;

	protected MessageAck() {
	}

	protected MessageAck(boolean success) {
		this.success = success;
	}

	protected MessageAck(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public String getType() {
		return this.type;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubType() {
		return this.subType;
	}
}
