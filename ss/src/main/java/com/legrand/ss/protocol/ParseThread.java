package com.legrand.ss.protocol;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legrand.ss.context.GlobalContext;
import com.legrand.ss.protocol.handle.AlarmHandle;
import com.legrand.ss.protocol.handle.ConfigHandle;
import com.legrand.ss.protocol.handle.EventHandle;
import com.legrand.ss.protocol.handle.MessageHandle;
import com.legrand.ss.protocol.model.MessageData;

public class ParseThread {
	private static final Logger logger = LoggerFactory.getLogger(ParseThread.class);
	private static ParseThread instance;
	private static Status STATUS = Status.READY;
	private static ExecutorService executors;

	private static List<MessageHandle> handleList = new ArrayList<>();

	private ParseThread() {
		handleList.add(new ConfigHandle());
		handleList.add(new AlarmHandle());
		handleList.add(new EventHandle());
	}

	public static ParseThread getInstance() {
		if (null == instance) {
			synchronized (ParseThread.class) {
				if (null == instance) {
					instance = new ParseThread();
				}
			}
		}
		return instance;
	}

	public synchronized void start() {
		if (STATUS == Status.RUNNING) {
			logger.warn("Parse thread is already running");
			return;
		}
		executors = Executors.newFixedThreadPool(GlobalContext.PARSE_POOL_SIZE);
		for (int i = 0; i < GlobalContext.PARSE_POOL_SIZE; i++) {
			executors.submit(() -> {
				while (true) {
					parse();
				}
			});
		}
		STATUS = Status.RUNNING;
	}

	private void parse() {
		byte[] data = GlobalContext.mqttClient.getMessage();
		if (null == data) {
			return;
		}

		String message = "";
		try {
			message = new String(data, "utf-8");
			MessageData msg = MessageData.build(message);
			if (null == msg) {
				logger.error("frame incorrect, not a JSON string!【{}】", message);
			}
			doParse(msg);
		} catch (UnsupportedEncodingException e) {
			logger.error("frame incorrect!【{}】", new String(data), e);
		} catch (Exception e) {
			logger.error("an error occurred while parsing message【】", message, e);
		}
	}

	private void doParse(MessageData message) {
		boolean success = false;
		for (MessageHandle handle : handleList) {
			if (handle.getType().equals(message.getType())) {
				if (handle.parse(message)) {
					success = true;
					logger.info("parse success");
				} else {
					logger.warn("parse failed");
				}
			}
		}
		if (!success) {
			logger.warn("unkonwn frame【{}】", message.getData());
		}
	}

	private enum Status {
		READY, RUNNING
	}
}
