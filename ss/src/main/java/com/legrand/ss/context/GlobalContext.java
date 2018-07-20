package com.legrand.ss.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.legrand.ss.model.SmokeAlarm;
import com.legrand.ss.model.SosAlarm;
import com.legrand.ss.model.Unlock;
import com.legrand.ss.model.VDP;
import com.legrand.ss.mqtt.MQTTClient;
import com.legrand.ss.net.MulticastServer;
import com.legrand.ss.protocol.CommService;
import com.legrand.ss.util.CloneUtil;

public class GlobalContext {

	// public static BroadcastServer broadcastServer;
	// public static final int BROADCAST_SERVER_PORT = 10001;
	// public static final int BROADCAST_CLIENT_PORT = 10002;
	// public static BroadcastClient broadcastClient;

	public static MulticastServer multicastServer;
	public static final String MULTICAST_SERVER_ADDRESS = "224.0.0.1";
	public static final int MULTICAST_SERVER_PORT = 10001;

	// public static final String MULTICAST_CLIENT_ADDRESS = "224.0.0.1";
	// public static final int MULTICAST_CLIENT_PORT = 10000;
	// public static MulticastClient multicastClient;

	public static MQTTClient mqttClient;

	public static final int UDP_CLIENT_PORT = 10000;

	public static String localHostIP = "10.106.41.198";

	public static final int PARSE_POOL_SIZE = 10;
	public static final int DB_HANDLE_POOL_SIZE = 3;

	public static final String SUBSCRIBE_TOPIC = "topic/vdp2ss";
	public static final String PUBLISH_TOPIC = "topic/ss2vdp";

	private static final ConcurrentHashMap<String, Object> defaultSetting = new ConcurrentHashMap<>();

	/**
	 * 保存所有的VDp信息
	 */
	private static final ConcurrentHashMap<String, VDP> VDPS = new ConcurrentHashMap<>();

	private static final Vector<SosAlarm> soses = new Vector<>();
	private static final Vector<SmokeAlarm> smokes = new Vector<>();
	private static final Vector<Unlock> unlocks = new Vector<>();

	static {
		defaultSetting.put("siteServerIP", localHostIP);
		defaultSetting.put("mcIP", "192.168.1.3");
		defaultSetting.put("childMcIP", "192.168.20.6");
		defaultSetting.put("alarmDuration", 60);
		defaultSetting.put("sceneMode", 0);
	}

	public static void setSiteServerIp(String ip) {
		localHostIP = ip;
		defaultSetting.put("siteServerIP", localHostIP);
	}

	public static void setDefaultSetting(String siteServerIp, String mcIP, String childMcIP, int alarmDuration, int sceneMode) {
		localHostIP = siteServerIp;
		defaultSetting.put("siteServerIP", siteServerIp);
		defaultSetting.put("mcIP", mcIP);
		defaultSetting.put("childMcIP", childMcIP);
		if (-1 == alarmDuration) {
			defaultSetting.put("alarmDuration", null);
		} else {
			defaultSetting.put("alarmDuration", alarmDuration);
		}
		if (-1 == sceneMode) {
			defaultSetting.put("sceneMode", null);
		} else {
			defaultSetting.put("sceneMode", sceneMode);
		}
		restartMulticastServer();
	}

	///////////////////////////////////////////////////////////////////////////////
	public static Map<String, Object> getDefaultSetting() {
		Map<String, Object> obj = CloneUtil.deepClone(defaultSetting);
		return null == obj ? new HashMap<String, Object>() : obj;
	}

	///////////////////////////////////////////////////////////////////////////////
	public static VDP getVDP(String callNum) {
		return VDPS.get(callNum);
	}

	public static void addVDP(VDP vdp) {
		VDPS.put(vdp.getCallNum(), vdp);
	}

	public static String getCallNum(String mac) {
		Map<String, String> map = new HashMap<>();
		VDPS.forEach((callNum, vdp) -> {
			if (vdp.getMac().equals(mac)) {
				map.put(mac, callNum);
				return;
			}
		});
		return map.get(mac);
	}

	public static boolean addVDPinfo(String mac, String callNum, String siteServerIp, String mcIP, String childMcIP, int alarmDuration, int sceneMode) {
		String existsCallNum = getCallNum(mac);
		if (null != existsCallNum && !existsCallNum.equals(callNum)) {
			return false;
		}

		VDP existsVDP = VDPS.get(callNum);
		if (null != existsVDP && !mac.equals(existsVDP.getMac())) {
			return false;
		}

		VDP vdp = new VDP();
		vdp.setCallNum(callNum);
		vdp.setMac(mac);
		vdp.setSiteServerIP(siteServerIp);
		vdp.setMcIP(mcIP);
		vdp.setChildMcIP(childMcIP);
		if (-1 != alarmDuration) {
			vdp.setAlarmDuration(alarmDuration);
		}
		if (-1 != sceneMode) {
			vdp.setSceneMode(sceneMode);
		}
		VDPS.put(callNum, vdp);
		return true;
	}

	public static ConcurrentHashMap<String, VDP> getAllVdps() {
		ConcurrentHashMap<String, VDP> obj = CloneUtil.deepClone(VDPS);
		return null == obj ? new ConcurrentHashMap<String, VDP>() : obj;
	}

	///////////////////////////////////////////////////////////////////////////////
	public static void addSos(SosAlarm sos) {
		soses.add(sos);
	}

	public static List<SosAlarm> getSoses() {
		List<SosAlarm> obj = CloneUtil.deepClone(soses);
		return null == obj ? Collections.emptyList() : obj;
	}

	public synchronized static void clearAll() {
		VDPS.clear();
		soses.clear();
		smokes.clear();
		unlocks.clear();
		CommService.clearAcks();
	}

	///////////////////////////////////////////////////////////////////////////////
	public static void addSmoke(SmokeAlarm smoke) {
		smokes.add(smoke);
	}

	public static List<SmokeAlarm> getSmokes() {
		List<SmokeAlarm> obj = CloneUtil.deepClone(smokes);
		return null == obj ? Collections.emptyList() : obj;
	}

	///////////////////////////////////////////////////////////////////////////////
	public static void addUnlock(Unlock unlock) {
		unlocks.add(unlock);
	}

	public static List<Unlock> getUnlocks() {
		List<Unlock> obj = CloneUtil.deepClone(unlocks);
		return null == obj ? Collections.emptyList() : obj;
	}
	///////////////////////////////////////////////////////////////////////////////

	public static void restartMulticastServer() {
		if (null != multicastServer) {
			multicastServer.stop();
		}
		multicastServer = new MulticastServer(localHostIP, MULTICAST_SERVER_ADDRESS, MULTICAST_SERVER_PORT);
		new Thread(new Runnable() {
			public void run() {
				GlobalContext.multicastServer.start();
			}
		}).start();
	}
}
