package com.legrand.ss.protocol.model;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 2744675357531701293L;
	protected String type;
	protected String subType;

	public String getType() {
		return this.type;
	}

	public String getSubType() {
		return subType;
	}
}