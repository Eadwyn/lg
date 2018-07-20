package com.legrand.ss.listener;

import java.net.InetAddress;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.legrand.ss.context.GlobalContext;
import com.legrand.ss.mqtt.MQTTClient;
import com.legrand.ss.protocol.ParseThread;
import com.legrand.ss.util.LocalHostUtil;

@WebListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		initLocalHost();
		GlobalContext.restartMulticastServer();		

//		GlobalContext.multicastClient = new MulticastClient(GlobalContext.MULTICAST_CLIENT_ADDRESS, GlobalContext.MULTICAST_CLIENT_PORT);

		initMqttClient();
		ParseThread.getInstance().start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
	private void initLocalHost() {
		List<InetAddress> ips = LocalHostUtil.getLocalHostAddress();
		if (ips.size() > 0) {
			GlobalContext.setSiteServerIp(ips.get(0).getHostAddress());
		}
	}
	
	private void initMqttClient() {
		GlobalContext.mqttClient = MQTTClient.newInstance("127.0.0.1", 1883);
		GlobalContext.mqttClient.connect();
		GlobalContext.mqttClient.subscribe(GlobalContext.SUBSCRIBE_TOPIC, 1);
	}
}
