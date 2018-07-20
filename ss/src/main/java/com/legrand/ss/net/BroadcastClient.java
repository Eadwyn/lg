package com.legrand.ss.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BroadcastClient {
	private static final Logger logger = LoggerFactory.getLogger(BroadcastClient.class);
	private String host;
	private int port;

	public BroadcastClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public boolean send(byte[] data) {
		try (DatagramSocket socket = new DatagramSocket()) {
			InetAddress adds = InetAddress.getByName(host);
			DatagramPacket dp = new DatagramPacket(data, data.length, adds, port);
			socket.send(dp);
			return true;
		} catch (IOException e) {
			logger.error("an error occurred while broadcast message", e);
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
		BroadcastClient client = new BroadcastClient("224.0.0.1", 10001);
		client.send("{ \"type\":\"config\",    \"subType\":\"siteServerIP\"}".getBytes("utf-8"));
	}
}
