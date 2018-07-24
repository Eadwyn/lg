package com.legrand.ss.protocol;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legrand.ss.context.GlobalContext;
import com.legrand.ss.model.VDP;
import com.legrand.ss.protocol.model.Message;
import com.legrand.ss.protocol.model.MessageAck;
import com.legrand.ss.util.JSONUtil;

public class CommService {

	private static final Logger logger = LoggerFactory.getLogger(CommService.class);
	
	private static ConcurrentHashMap<String, Vector<MessageAck>> acks = new ConcurrentHashMap<>();
	public static MessageAck send(String callNum, Message data) {
		String ackKey = callNum + data.getSubType() + "ACK";
		String msg = JSONUtil.decode(data);
		logger.info("publish【{}】", msg);
		if (GlobalContext.mqttClient.publish(msg, GlobalContext.PUBLISH_TOPIC, 1)) {
			acks.put(ackKey, new Vector<MessageAck>());
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, 30); // 最长等待30秒
			long maxTime = cal.getTime().getTime();
			while (new Date().getTime() < maxTime) {				
				synchronized(acks) {
					Vector<MessageAck> ackList = acks.get(ackKey);
					if (null != ackList &&  ackList.size() > 0) {
						MessageAck ack = acks.get(ackKey).remove(0);
						if (ackList.size() == 0) {
							acks.remove(ackKey);
						}
						return ack;
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.error("Error", e);
				}
			}
			acks.remove(ackKey);
		} 
		
		return null;
	}
	
	public static boolean sendAck(MessageAck ack) {
		String topic= GlobalContext.PUBLISH_TOPIC;
		int qos = 1;
		String msg = JSONUtil.decode(ack);
		logger.info("publish【{}】", msg);
		return GlobalContext.mqttClient.publish(msg, topic, qos);
	}
	
	public static void addAck(String callNum, String subType, MessageAck ack) {
		String ackKey = callNum + subType;

		synchronized(acks) {
			if (null != acks.get(ackKey)) {
				acks.get(ackKey).addElement(ack);
			}
		}
	}

	public static boolean isActive(String callNum) {
		VDP vdp = GlobalContext.getVDP(callNum);
		if (null == vdp || !vdp.isActive()) {
			return false;
		}
		return true;
	}
	
	public static void clearAcks() {
		acks.clear();
	}
}
