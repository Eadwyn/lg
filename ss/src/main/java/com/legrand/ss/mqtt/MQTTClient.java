package com.legrand.ss.mqtt;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTClient {
	private static final Logger logger = LoggerFactory.getLogger(MQTTClient.class);
	private static LinkedBlockingQueue<byte[]> messages = new LinkedBlockingQueue<>();

	private ConnectType type;
	private String host;
	private int port;
	private String clientId;
	private String username;
	private String password;
	private String uri;

	private int connectionTimeout = 30;
	private int keepAliveInterval = 30;

	private static MQTTClient instance;
	private MqttClient client;

	private MQTTClient(String host, int port, ConnectType type, String username, String password, String clientId) {
		this.type = type;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.clientId = clientId;

		uri = String.format("%s://%s:%d", this.type.getCode(), this.host, this.port);
	}

	/**
	 * 创建一个 mqtt客户端
	 * 
	 * @param host
	 *            服务端IP地址
	 * @param port
	 *            服务端端口号
	 * @param type
	 *            mqtt的连接方式
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param clientId
	 *            客户端标识
	 * @return
	 */
	public static MQTTClient newInstance(String host, int port, ConnectType type, String username, String password, String clientId) {
		if (null == instance) {
			synchronized (MQTTClient.class) {
				if (null == instance) {
					instance = new MQTTClient(host, port, type, username, password, clientId);
				}
			}
		}
		return instance;
	}

	/**
	 * 报建一个指定host和port的，连接方式为tcp的匿名mqtt客户端
	 * 
	 * @param host
	 *            服务端IP地址
	 * @param port
	 *            服务端端口号
	 * @return
	 */
	public static MQTTClient newInstance(String host, int port) {
		if (null == instance) {
			synchronized (MQTTClient.class) {
				if (null == instance) {
					instance = new MQTTClient(host, port, ConnectType.TCP, null, null, String.valueOf(UUID.randomUUID()));
				}
			}
		}
		return instance;
	}

	public synchronized boolean connect() {
		try {
			logger.info("begin connect to MQTT Server - {}...", this.uri);
			if (null == this.client) {
				this.client = new MqttClient(this.uri, this.clientId, new MemoryPersistence());
				this.client.setCallback(new MQTTClientCallback(this));
			}

			IMqttToken token = this.client.connectWithResult(this.getOption());
			if (token.isComplete()) {
				logger.info("connect to MQTT Server successed - {}", this.uri);
				return true;
			} else {
				logger.info("connect to MQTT Server failed - {}", this.uri);
				return false;
			}
		} catch (Exception e) {
			logger.error("connect to MQTT Server failed - {}", this.uri, e);
			return false;
		}
	}

	public boolean disconnect() {
		try {
			logger.info("begin stop MQTT client - {}", this.uri);
			this.client.disconnect();
			logger.info("stop MQTT client successed - {}", this.uri);
			return true;
		} catch (MqttException e) {
			logger.error("stop MQTT client failed - {}", this.uri, e);
			return false;
		}
	}

	public boolean reconnect() {
		try {
			logger.info("begin reconnect MQTT client - {}", this.uri);
			this.client.reconnect();
			logger.info("reconnect MQTT client successed - {}", this.uri);
			return true;
		} catch (MqttException e) {
			logger.error("an error occurred while reconnect MQTT client failed - {}", this.uri, e);
			return false;
		}
	}

	public boolean subscribe(String topic, int qos) {
		try {
			this.client.subscribe(topic, qos);
			return true;
		} catch (MqttException e) {
			logger.error("an error occurred while subscribe a topic【topic={}, qos={}】", topic, qos, e);
			return false;
		}
	}

	public boolean publish(String message, String topic, int qos) {
		try {
			MqttMessage msg = new MqttMessage(message.getBytes("utf-8"));
			msg.setQos(qos);
			this.client.publish(topic, msg);
			return true;
		} catch (Exception e) {
			logger.error("an error occurred while publish message【message={}, topic={}, qos={}】", message, topic, qos, e);
			return false;
		}
	}

	public void addMessage(byte[] data) {
		try {
			messages.put(data);
		} catch (InterruptedException e) {
			logger.error("an error occurred while add a message", e);
		}
	}

	public byte[] getMessage() {
		try {
			return messages.take();
		} catch (InterruptedException e) {
			logger.error("an error occurred while get a message", e);
			return null;
		}
	}

	public String getServerUri() {
		return this.client.getServerURI();
	}

	private MqttConnectOptions getOption() {
		MqttConnectOptions options = new MqttConnectOptions(); // MQTT的连接设置
		options.setCleanSession(true); // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
		if (null != this.username) {
			options.setUserName(this.username);// 设置连接的用户名
		}
		if (null != this.password) {
			options.setPassword(this.password.toCharArray()); // 设置连接的密码
		}
		options.setConnectionTimeout(connectionTimeout);// 设置超时时间 单位为秒
		options.setKeepAliveInterval(keepAliveInterval);// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
		// MqttTopic topic = client.getTopic(TOPIC);
		// options.setWill(topic, "close".getBytes(), 0, true);//
		// setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
		return options;
	}

	/**
	 * 获取 连接超时时间
	 * 
	 * @return 连接超时时间，单位：秒
	 */
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * 设置连接超时时间
	 * 
	 * @param connectionTimeout
	 *            连接超时时间，单位：秒
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * 获取心跳间隔
	 * 
	 * @return 心跳间隔。单位：秒
	 */
	public int getKeepAliveInterval() {
		return keepAliveInterval;
	}

	/**
	 * 设置心跳间隔
	 * 
	 * @param keepAliveInterval
	 *            心跳间隔。单位：秒
	 */
	public void setKeepAliveInterval(int keepAliveInterval) {
		this.keepAliveInterval = keepAliveInterval;
	}

	/**
	 * 连接类型
	 *
	 * @author Eadwyn
	 * @version 1.0.0
	 * @date 2018年7月19日 下午2:08:30
	 * @link
	 * @see
	 */
	public static enum ConnectType {
		TCP("tcp"), SSL("ssl");
		ConnectType(String code) {
			this.code = code;
		};

		private String code;

		public String getCode() {
			return this.code;
		}
	}

	public static void main(String[] args) {
		MQTTClient cli = MQTTClient.newInstance("127.0.0.1", 1883);
		cli.connect();
		cli.subscribe("topic/test", 1);
		cli.publish("waerfgsd", "topic/testp", 1);
	}

}
