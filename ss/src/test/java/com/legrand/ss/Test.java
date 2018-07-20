package com.legrand.ss;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.legrand.ss.protocol.model.config.Settings;
import com.legrand.ss.protocol.model.config.SiteServerIPAckData;

public class Test {

	public static void main(String[] args) throws Exception {
		// List<InetAddress> lans = LocalHostUtil.getLocalHostAddress();
		// lans.stream().forEach((lan) -> {
		// System.out.println(lan.getHostAddress());
		// });

		// SiteServerIPAckData data = new SiteServerIPAckData();
		// data.setIp("127.0.0.1");
		//// SiteServerIPAck obj = new SiteServerIPAck("true", "dfdv");
		//// obj.setSuccess(String.valueOf(true));
		//
		// ObjectMapper mapper = new ObjectMapper();
		// mapper.enable(SerializationFeature.INDENT_OUTPUT);
		//// System.out.println(mapper.writeValueAsString(obj));

		test();
	}

	private static void test() throws Exception {
		String text = "{\"type\":\"config\",\"typwsdce\":\"config\",\"data\":{\"ip\":\"192.168.1.5\",\"siteServerIP\":\"192.168.1.3\",\"callNum\":\"01010602\",\"mcIP\":\"192.168.1.10\",\"childMcIP\":\"192.168.1.222\",\"alarmDuration\":60,\"sceneMode\":1}}";

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		Settings settings = mapper.readValue(text, Settings.class);

		System.out.println(mapper.writeValueAsString(settings));

	}
}
