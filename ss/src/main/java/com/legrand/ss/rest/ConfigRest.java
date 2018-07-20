package com.legrand.ss.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legrand.ss.model.Response;
import com.legrand.ss.model.VDP;
import com.legrand.ss.service.ConfigService;

@RestController
@RequestMapping(value = "/configs")
public class ConfigRest {
	private static ObjectMapper jsonMapper = new ObjectMapper();
	private final ConfigService configService;

	public ConfigRest(ConfigService configService) {
		this.configService = configService;
	}

	@GetMapping(value = "/{callNum}")
	public String write(@PathVariable String callNum, @RequestParam String siteServerIp, 
			@RequestParam String mcIP, @RequestParam String childMcIP, @RequestParam int alarmDuration,
			@RequestParam int sceneMode) throws JsonProcessingException {

		if (this.configService.write(callNum, siteServerIp, mcIP, childMcIP, alarmDuration, sceneMode)) {
			Response resp = new Response(true);
			return jsonMapper.writeValueAsString(resp);
		}
		Response resp = new Response(false);
		return jsonMapper.writeValueAsString(resp);
	}

	@GetMapping(value = "/{callNum}/read")
	public String read(@PathVariable String callNum) throws JsonProcessingException {

		VDP vdp = this.configService.read(callNum);
		if (null == vdp) {
			Response resp = new Response(false, "time out");
			return jsonMapper.writeValueAsString(resp);
		}
		Response resp = new Response(true, vdp);
		return jsonMapper.writeValueAsString(resp);
	}

}
