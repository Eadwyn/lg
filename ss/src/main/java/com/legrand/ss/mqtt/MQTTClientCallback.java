package com.legrand.ss.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTClientCallback implements MqttCallback {
	private static final Logger logger = LoggerFactory.getLogger(MQTTClientCallback.class);

	private MQTTClient client;

	public MQTTClientCallback(MQTTClient client) {
		this.client = client;
	}

	public void connectionLost(Throwable cause) {
		logger.error("disconnect from MQTT Server - {}", this.client.getServerUri(), cause);
		this.client.reconnect();
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		this.client.addMessage(message.getPayload());
		logger.info("received - {} - 【topic={}, qos={}, data={}】", this.client.getServerUri(), topic, message.getQos(), new String(message.getPayload(), "utf-8"));

		// if (!"close".equals(new String(message.getPayload(), "utf-8"))) {
		// logger.info("received【topic={}, qos={}, data={}】", topic, message.getQos(),
		// new String(message.getPayload(), "utf-8"));
		// MessageCenter.addMessage(message.getPayload());
		// }
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}
}
