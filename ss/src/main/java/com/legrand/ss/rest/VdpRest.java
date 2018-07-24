package com.legrand.ss.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legrand.ss.context.GlobalContext;
import com.legrand.ss.model.Response;
import com.legrand.ss.model.VDP;

@RestController
@RequestMapping(value = "/vdps")
public class VdpRest {

	private static ObjectMapper jsonMapper = new ObjectMapper();

	@GetMapping(value = "")
	public String getAll() throws JsonProcessingException {
		Map<String, VDP> vdps = GlobalContext.getAllVdps();
		if (null != vdps) {
			List<Map<String, String>> vdpList = new ArrayList<>();
			vdps.forEach((key, value)->{
				Map<String, String> map = new HashMap<String, String>();
				map.put("callNum", value.getCallNum());
				map.put("mac", value.getMac());
				vdpList.add(map);
			});
			Response resp = new Response(true, vdpList);
			return jsonMapper.writeValueAsString(resp);
		} else {
			Response resp = new Response(false, "an error occurred");
			return jsonMapper.writeValueAsString(resp);
		}
	}
	
	@GetMapping(value = "/{callNum}/detail")
	public String getVdpDetail(@PathVariable String callNum) throws JsonProcessingException {
		VDP vdp = GlobalContext.getVDP(callNum);
		if (null != vdp) {
			Response resp = new Response(true, vdp);
			return jsonMapper.writeValueAsString(resp);
		} else {
			Response resp = new Response(false, "not exists");
			return jsonMapper.writeValueAsString(resp);
		}
	}
	
	@GetMapping(value = "/clearAll")
	public String clearAll() throws JsonProcessingException {
		GlobalContext.clearAll();
		Response resp = new Response(true);
		return jsonMapper.writeValueAsString(resp);
	}

	@GetMapping(value = "/save")
	public String add(@RequestParam String callNum, @RequestParam String mac, @RequestParam String siteServerIp, 
			@RequestParam String mcIP, @RequestParam String childMcIP, 
			@RequestParam int alarmDuration, @RequestParam int sceneMode)
			throws JsonProcessingException {
		
		String check = GlobalContext.addVDPinfo(mac, callNum, siteServerIp, mcIP, childMcIP, alarmDuration, sceneMode);
		if (null == check) {
			Response resp = new Response(true);
			return jsonMapper.writeValueAsString(resp);
		}
		
		Response resp = new Response(false, check);
		return jsonMapper.writeValueAsString(resp);
	}

	@GetMapping(value = "/defaultsetting")
	public String getDefaultSetting() throws JsonProcessingException {
		Map<String, Object> settings = GlobalContext.getDefaultSetting();
		Response resp = new Response(true, settings);
		return jsonMapper.writeValueAsString(resp);
	}

	@GetMapping(value = "/saveDefaultsetting")
	public String saveDefaultSetting(@RequestParam String siteServerIp, 
			@RequestParam String mcIP, @RequestParam String childMcIP, 
			@RequestParam int alarmDuration, @RequestParam int sceneMode) throws JsonProcessingException {
		GlobalContext.setDefaultSetting(siteServerIp, mcIP, childMcIP, alarmDuration, sceneMode);
		Response resp = new Response(true);
		return jsonMapper.writeValueAsString(resp);
	}
	
}
