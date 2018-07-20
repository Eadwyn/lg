package com.legrand.ss.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legrand.ss.context.GlobalContext;
import com.legrand.ss.model.Response;
import com.legrand.ss.model.SmokeAlarm;
import com.legrand.ss.model.SosAlarm;

@RestController
@RequestMapping(value = "/alarms")
public class AlarmRest {
	private static ObjectMapper jsonMapper = new ObjectMapper();

	@GetMapping(value = "/soses")
	public String getAllSoses() throws JsonProcessingException {

		List<SosAlarm> datas = GlobalContext.getSoses();
		Response resp = new Response(true, datas);
		return jsonMapper.writeValueAsString(resp);
	}

	@GetMapping(value = "/smokes")
	public String getAllSmokes() throws JsonProcessingException {

		List<SmokeAlarm> datas = GlobalContext.getSmokes();
		Response resp = new Response(true, datas);
		return jsonMapper.writeValueAsString(resp);
	}
}
