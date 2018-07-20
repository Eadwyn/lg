package com.legrand.ss.protocol;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legrand.ss.context.GlobalContext;

public class DbHandleThread {
	private static final Logger logger = LoggerFactory.getLogger(DbHandleThread.class);	
	private static DbHandleThread instance;
	private static Status STATUS = Status.READY;
	private static ExecutorService executors;
	private static LinkedBlockingQueue<Object> persistDatas = new LinkedBlockingQueue<>();

	public static DbHandleThread getInstance() {
		if (null == instance) {
			synchronized (DbHandleThread.class) {
				if (null == instance) {
					instance = new DbHandleThread();
				}
			}
		}
		return instance;
	}

	public synchronized void start() {
		if (STATUS == Status.RUNNING) {
			logger.warn("DB Handle thread is already running");
			return;
		}
		executors = Executors.newFixedThreadPool(GlobalContext.DB_HANDLE_POOL_SIZE);
		for (int i = 0; i < GlobalContext.DB_HANDLE_POOL_SIZE; i++) {
			executors.submit(() -> {
				while (true) {
					handle();
				}
			});
		}
		STATUS = Status.RUNNING;
	}

	private void handle() {
		Object data = getPersistData();
		if (null == data) {
			return;
		}

		
		// TODO
	}
	


	public static void addPersistData(Object data) {
		try {
			persistDatas.put(data);
		} catch (InterruptedException e) {
			logger.error("an error occurred", e);
		}
	}

	public static Object getPersistData() {
		try {
			return persistDatas.take();
		} catch (InterruptedException e) {
			logger.error("an error occurred", e);
			return null;
		}
	}

	private enum Status {
		READY, RUNNING
	}
}
