package com.legrand.ss.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalHostUtil {
	private static final Logger logger = LoggerFactory.getLogger(LocalHostUtil.class);

	public static void main(String[] args) throws Exception {
		// List<InetAddress> lans = getLocalHostAddress();
		// lans.stream().forEach((lan) -> {
		// System.out.println(lan.getHostAddress());
		// });

		getInterface().stream().forEach((obj) -> {
			System.out.println(obj.getHostAddress());
		});

		getMacAddresses().stream().forEach((mac) -> {
			System.out.println(mac);
		});
	}

	/**
	 * 获取本机上所有有效的InetAddress地址
	 * 
	 * @return
	 * @throws Exception
	 */
	private static List<InetAddress> getInterface() {
		List<InetAddress> result = new ArrayList<>();
		Enumeration<NetworkInterface> interfaces;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface iface = (NetworkInterface) interfaces.nextElement();
				Enumeration<InetAddress> inetAddrs = iface.getInetAddresses();
				while (inetAddrs.hasMoreElements()) {
					InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress() && inetAddr.isSiteLocalAddress() && -1 == inetAddr.getHostAddress().indexOf(":")) {
						result.add(inetAddr);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error", e);
		}
		return result;
	}

	public static List<String> getMacAddresses() {
		List<String> result = new ArrayList<>();

		List<InetAddress> addrs = getInterface();
		addrs.forEach((addr) -> {
			try {
				byte[] mac = NetworkInterface.getByInetAddress(addr).getHardwareAddress();
				StringBuffer sb = new StringBuffer("");
				for (int i = 0; i < mac.length; i++) {
					if (i != 0) {
						sb.append("-");
					}
					// 字节转换为整数
					int temp = mac[i] & 0xff;
					String str = Integer.toHexString(temp);
					if (str.length() == 1) {
						sb.append("0" + str);
					} else {
						sb.append(str);
					}
				}

				result.add(sb.toString().toUpperCase());
			} catch (SocketException e) {
			}
		});
		return result;
	}

	public static List<InetAddress> getLocalHostAddress() {
		List<InetAddress> result = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface iface = (NetworkInterface) interfaces.nextElement();
				Enumeration<InetAddress> inetAddrs = iface.getInetAddresses();
				while (inetAddrs.hasMoreElements()) {
					InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
						if (inetAddr.isSiteLocalAddress()) {
							result.add(inetAddr);
						}
					}
				}
			}
			if (result.size() <= 0) {
				result.add(InetAddress.getLocalHost()); // 如果没有发现 non-loopback地址.只能用最次选的方案
			}
		} catch (Exception e) {
		}
		return result;
	}
}
