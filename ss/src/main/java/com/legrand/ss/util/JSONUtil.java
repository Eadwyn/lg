package com.legrand.ss.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONUtil {
	private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static void main(String[] args) {
		String src = "{\"mainType\": \"upload\",\"subType\": \"test\",\"data\": {\"client\": \"1\",\"value\": \"2\"}}";
		String oldValue = getValue(src, "subType");
		String result = setValue(src, "subType", oldValue + "ACK");
		System.out.println(result);
	}

	public static <T> T encode(String jsonString, Class<T> valueType) {
		try {
			return mapper.readValue(jsonString, valueType);
		} catch (Exception e) {
			logger.error("encode JSON string error", e);
			return null;
		}
	}

	public static <T> String decode(T obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("encode JSON object error", e);
			return null;
		}
	}
	
	/**
	 * 获取第一层节点的value值
	 * 
	 * @param jsonString
	 *            JSON字符串
	 * @param key
	 *            第一层节点的key值
	 * @return null - 没有对应的key；否则返回取到的value值
	 * @exception IllegalArgumentException
	 *                非JSON字符串
	 */
	public static String getValue(String jsonString, String key) {
		try {
			JsonNode rootNode = mapper.readTree(jsonString);
			JsonNode value = rootNode.get(key);
			return (null == value) ? null : value.asText();
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format("not a JSON string[%s]", jsonString));
		}
	}
	
	public static String setValue(String jsonString, String key, String newValue) {
		try {
			JsonNode rootNode = mapper.readTree(jsonString);
			((ObjectNode)rootNode).put(key, newValue);
			return rootNode.toString();
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format("not a JSON string[%s]", jsonString));
		}
	}

	/**
	 * 获取第二层节点的value值
	 * 
	 * @param jsonString
	 *            JSON字符串
	 * @param firstNodeKey
	 *            第一层节点的key值
	 * @param secondNodeKey
	 *            第二层节点的key值
	 * @return null - 没有对应的key；否则返回取到的value值
	 * @exception IllegalArgumentException
	 *                非JSON字符串
	 */
	public static String getLevel2Value(String jsonString, String firstNodeKey, String secondNodeKey) {
		JsonNode node;
		try {
			node = mapper.readTree(jsonString);
			JsonNode firstNode = node.get(firstNodeKey);
			if (null == firstNode) {
				return null;
			}

			JsonNode secondNode = firstNode.get(secondNodeKey);
			return (null == secondNode) ? null : secondNode.toString();
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format("not a JSON string[%s]", jsonString));
		}
	}
}
