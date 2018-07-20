package com.legrand.ss.protocol.model;

public class SubType {

	// ------------------------------------------------------------------------
	// type = "config"
	// ------------------------------------------------------------------------
	public static final String CONFIG_SITESERVER_IP = "siteServerIP";
	public static final String CONFIG_SITESERVER_IP_ACK = "siteServerIPACK";
	public static final String CONFIG_VDP_INFO = "VDPInfo";
	public static final String CONFIG_VDP_INFO_ACK = "VDPInfoACK";
	public static final String CONFIG_SETTINGS = "settings";
	public static final String CONFIG_SETTINGS_ACK = "settingsACK";
	public static final String CONFIG_SETTINGS_REQUEST = "settingsRequest";
	public static final String CONFIG_SETTINGS_REQUEST_ACK = "settingsRequestACK";

	// ------------------------------------------------------------------------
	// type = "alarm"
	// ------------------------------------------------------------------------
	public static final String ALARM_SOS = "SOS";
	public static final String ALARM_SOS_ACK = "SOSACK";
	public static final String ALARM_SMOKE = "smoke";
	public static final String ALARM_SMOKE_ACK = "smokeACK";

	// ------------------------------------------------------------------------
	// type = "event"
	// ------------------------------------------------------------------------
	public static final String EVENT_UNLOCK = "unlock";
	public static final String EVENT_UNLOCK_ACK = "unlockACK";
}
