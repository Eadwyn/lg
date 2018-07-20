package com.legrand.ss.net;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legrand.ss.context.GlobalContext;
import com.legrand.ss.protocol.model.MessageData;
import com.legrand.ss.protocol.model.SubType;
import com.legrand.ss.protocol.model.Type;
import com.legrand.ss.protocol.model.config.SiteServerIPAck;
import com.legrand.ss.protocol.model.config.SiteServerIPAckData;
import com.legrand.ss.util.JSONUtil;


public class MulticastServer {
	private static final Logger logger = LoggerFactory.getLogger(MulticastServer.class);
	private static final int PACKET_SIZE = 1024;

	private String interfaceIp;
	private String host;
	private int port;
	private Status status = Status.READY;

	public MulticastServer(String interfaceIp, String host, int port) {
		this.interfaceIp = interfaceIp;
		this.host = host;
		this.port = port;
	}

	public void start() {
		synchronized(status) {
			if (status == Status.RUNNING) {
				logger.warn("Multicast Server is running!");
				return;
			}
		}

		logger.info("start Multicast Server【{}-{}-{}】...", interfaceIp, host, port);
		DatagramPacket packet = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
		try (MulticastSocket server = new MulticastSocket(this.port)) {
			server.setInterface(InetAddress.getByName(this.interfaceIp));
			server.joinGroup(InetAddress.getByName(this.host));
			server.setSoTimeout(5000);
			logger.info("Multicast Server started【{}-{}-{}】", interfaceIp, host, port);
			synchronized(status) {
				if (status == Status.RUNNING) {
					logger.warn("Multicast Server is running!");
					return;
				}
				status = Status.RUNNING;
			}
			while (true && status == Status.RUNNING) {
				try {
					server.receive(packet);
					byte[] buf = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
					logger.info(new String(buf));
					parse(packet.getAddress(), buf);
//					MessageCenter.addMessage(buf);
				} catch(SocketTimeoutException e) {
					// receive time out, no need handle
				} catch (Exception e) {
					logger.error("an error occurred while receiving", e);
				}
			}
			logger.info("Multicast Server Stopped【{}-{}-{}】", interfaceIp, host, port);
		} catch (Exception e) {
			logger.error("Multicast Server start Failed!", e);
		}
	}
	
	public void stop() {
		logger.info("stop Multicast Server【{}-{}-{}】...", interfaceIp, host, port);
		synchronized(status) {
			status = Status.READY;
		}
	}

	private enum Status {
		READY, RUNNING
	}
	
	
	
	
	
	
	
	

	
	private void parse(InetAddress host, byte[] buf) {
		try {
			String message = new String(buf, "utf-8");
			MessageData msg = MessageData.build(message);
			if (Type.CONFIG.equals(msg.getType()) && SubType.CONFIG_SITESERVER_IP.equals(msg.getSubType())) {
				siteServerIPHandle(host);
			}
		} catch (Exception e) {
			logger.error("an error occurred while sending Site Server IP", e);		
		}
	}
	
	private void siteServerIPHandle(InetAddress host) {
		SiteServerIPAck ack = new SiteServerIPAck(true, new SiteServerIPAckData(GlobalContext.localHostIP));
		String msg = JSONUtil.decode(ack);
		if (UdpClient.send(host, GlobalContext.UDP_CLIENT_PORT, msg)) {
			logger.info("Site Server IP sent,【{}】", new String(msg));
		} else {
			logger.error("an error occurred while sending Site Server IP");				
		}
	}
	
	
	public static void main(String[] args) {
		new MulticastServer("10.106.41.198", "224.0.0.1", 10001).start();
	}
}
