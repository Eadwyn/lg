package com.legrand.ss.net;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MulticastClient {
	private static final Logger logger = LoggerFactory.getLogger(BroadcastClient.class);
	private String host;
	private int port;

	public MulticastClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public boolean send(byte[] data, InetAddress addr) {
		try (MulticastSocket socket = new MulticastSocket()) {
			socket.setInterface(addr);
			InetAddress adds = InetAddress.getByName(host);
			DatagramPacket dp = new DatagramPacket(data, data.length, adds, port);
			socket.send(dp);
			return true;
		} catch (Exception e) {
			logger.error("an error occurred while broadcast message", e);
			return false;
		}
	}

	public boolean send(byte[] data, String interfaceIp) {
		try (MulticastSocket socket = new MulticastSocket()) {
			socket.setInterface(InetAddress.getByName(interfaceIp));
			InetAddress adds = InetAddress.getByName(host);
			DatagramPacket dp = new DatagramPacket(data, data.length, adds, port);
			socket.send(dp);
			return true;
		} catch (Exception e) {
			logger.error("an error occurred while broadcast message", e);
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
		MulticastClient client = new MulticastClient("224.0.0.1", 10001);
		client.send("{ \"type\":\"config\",    \"subType\":\"siteServerIP\"}".getBytes("utf-8"), "10.106.41.198");
	}
}
