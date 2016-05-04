package com.chingluh.android.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetworkUtil {
	private static WifiManager wifiManager;
	private static WifiInfo wifiInfo;

	private static WifiManager getWIfiManager(Activity activity) {
		if (NetworkUtil.wifiManager == null) {
			NetworkUtil.wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
		}
		return NetworkUtil.wifiManager;
	}

	public static WifiInfo getWifiInfo(Activity activity) {
		if (NetworkUtil.wifiInfo == null) {
			NetworkUtil.wifiInfo = getWIfiManager(activity).getConnectionInfo();
		}
		return NetworkUtil.wifiInfo;
	}

	public static String getLoaclMacAddress(Activity activity) {
		return getWifiInfo(activity).getMacAddress();
	}

	public static String getIPAddress(boolean useIPv4) {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress()) {
						String sAddr = addr.getHostAddress();
						//boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
						boolean isIPv4 = sAddr.indexOf(':') < 0;
						if (useIPv4) {
							if (isIPv4) {
								return sAddr;
							}
						} else {
							if (!isIPv4) {
								int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
								return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
							}
						}
					}
				}
			}
		} catch (Exception ex) {
		} // for now eat exceptions
		return "";
	}
}
