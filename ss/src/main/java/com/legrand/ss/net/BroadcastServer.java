package com.legrand.ss.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BroadcastServer {
	private static final Logger logger = LoggerFactory.getLogger(BroadcastServer.class);
	private static final int PACKET_SIZE = 1024;

	private int port;
	private Status status = Status.READY;

	public BroadcastServer(int port) {
		this.port = port;
	}

	public synchronized void start() {
		if (status == Status.RUNNING) {
			logger.warn("Broadcast Server is running!");
			return;
		}

		logger.info("start Broadcast Server...");
		DatagramPacket packet = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
		try (DatagramSocket server = new DatagramSocket(this.port)) {
			logger.info("Broadcast Server started!");
			status = Status.RUNNING;
			while (true) {
				try {
					server.receive(packet);
//					MessageCenter.addMessage(Arrays.copyOfRange(packet.getData(), 0, packet.getLength()));
				} catch (Exception e) {
					logger.error("an error occurred while receiving", e);
				}
			}
		} catch (SocketException e) {
			logger.error("Broadcast Server start Failed!", e);
		}
	}

	private enum Status {
		READY, RUNNING
	}
}
