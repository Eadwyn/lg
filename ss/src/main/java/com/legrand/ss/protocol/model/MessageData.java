package com.legrand.ss.protocol.model;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageData implements Serializable  {
	private static final long serialVersionUID = 5419246012661069753L;
	private static final ObjectMapper jsonMapper = new ObjectMapper();

	private JsonNode json;
	private String jsonString;
	
	private MessageData(JsonNode json, String jsonString) {
		this.json = json;
		this.jsonString = jsonString;
	}

	public static MessageData build(String jsonString) {
		try {
			JsonNode node = jsonMapper.readTree(jsonString); // ObjectMapper为线程安全类
			return new MessageData(node, jsonString);
		} catch (IOException e) {
			return null;
		}
	}

	public String getType() {
		JsonNode value = this.json.get("type");
		return (null == value) ? null : value.asText().trim();
	}

	public String getSubType() {
		JsonNode value = this.json.get("subType");
		return (null == value) ? null : value.asText().trim();
	}
	
	public String getData() {
		JsonNode value = this.json.get("data");
		return (null == value) ? null : value.toString();
	}
	
	public String getJsonString() {
		return this.jsonString;
	}
}
