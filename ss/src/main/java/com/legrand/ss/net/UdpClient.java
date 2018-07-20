package com.legrand.ss.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
	   
    private String host;
    private int port;
    
    public UdpClient(String host, int port) {
    	this.host = host;
    	this.port = port;
    }
    
    public boolean send(byte[] buf) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress host = InetAddress.getByName(this.host);
			DatagramPacket dp = new DatagramPacket(buf, buf.length, host, port);
			socket.send(dp);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * 以UTF-8编码发送
     * @param message
     * @return
     */
    public boolean send(String message) {
    	try {
			return send(message.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			return false;
		}
    }

    public static boolean send(InetAddress host, int port, String message) {
    	try {
			return new UdpClient(host.getHostAddress(), port).send(message.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			return false;
		}
    }
    
    public static void main(String[] args) {
    }
}
