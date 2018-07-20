package com.legrand.ss.rest;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legrand.ss.model.Response;
import com.legrand.ss.util.LocalHostUtil;

@RestController
@RequestMapping(value = "/localhost")
public class LocalHostRest {
	private static ObjectMapper jsonMapper = new ObjectMapper();

	@GetMapping(value = "/ips")
	public String getIps() throws JsonProcessingException {
		List<InetAddress> addresses = LocalHostUtil.getLocalHostAddress();
		List<String> ips = new ArrayList<String>();
		addresses.stream().forEach((addr) -> {
			ips.add(addr.getHostAddress());
		});
		Response resp = new Response(true, ips);
		return jsonMapper.writeValueAsString(resp);
	}

}
