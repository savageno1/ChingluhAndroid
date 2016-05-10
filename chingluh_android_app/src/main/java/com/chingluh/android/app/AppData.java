package com.chingluh.android.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.chingluh.android.data.UserData;
import com.chingluh.android.util.NetworkUtil;

/**
 * 安卓用公共资料
 *
 * @author Ray
 */
public class AppData{
	/**
	 * 用户信息
	 */
	private static UserData USER_DATA;
	/**
	 * IP地址
	 */
	private static String STR_IP_ADDRESS_V4;

	/**
	 * 用户信息
	 *
	 * @return
	 */
	public static UserData getUserData(){
		if(AppData.USER_DATA==null){
			AppData.USER_DATA=new UserData();
		}
		return AppData.USER_DATA;
	}

	public static void setNullUserData(){
		AppData.USER_DATA=null;
	}

	public static String getIpAddressV4(){
		if(AppData.STR_IP_ADDRESS_V4==null){
			AppData.STR_IP_ADDRESS_V4=NetworkUtil.getIPAddress(true);
		}
		return AppData.STR_IP_ADDRESS_V4;
	}

	public static boolean inDebug(Context context){
		try{
			ApplicationInfo applicationInfo=context.getApplicationInfo();
			return (applicationInfo.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
		}catch(Exception e){
			return false;
		}
	}
}
