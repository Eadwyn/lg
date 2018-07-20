package com.legrand.ss.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legrand.ss.context.GlobalContext;
import com.legrand.ss.model.Response;
import com.legrand.ss.model.Unlock;

@RestController
@RequestMapping(value = "/events")
public class EventRest {
	private static ObjectMapper jsonMapper = new ObjectMapper();

	@GetMapping(value = "/unlocks")
	public String getAllUnlocks() throws JsonProcessingException {
		List<Unlock> datas = GlobalContext.getUnlocks();
		Response resp = new Response(true, datas);
		return jsonMapper.writeValueAsString(resp);
	}

}
