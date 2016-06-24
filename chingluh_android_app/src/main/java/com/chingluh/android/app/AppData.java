package com.chingluh.android.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.chingluh.android.data.UserData;
import com.chingluh.android.util.NetworkUtil;
import com.chingluh.android.util.SharedPreferencesUtil;

/**
 * 应用程序用公共资料
 *
 * @author Ray
 */
public class AppData{
	public static final String SP_USER_ID="UserId";
	public static final String SP_PASSWORD="Password";
	public static final String SP_BIND_MAC="BindMac";
	/**
	 * 用户信息
	 */
	private static UserData USER_DATA;
	/**
	 * IP地址
	 */
	private static String STR_IP_ADDRESS_V4;

	/**
	 * 确认绑定状态
	 *
	 * @return
	 */
	public static boolean checkBind(Activity activity){
		//未绑定
		if(getUserData(activity).getBindMac()==null||getUserData(activity).getBindMac().equals("")){
			return false;
		}
		return true;
	}

	/**
	 * 用户信息
	 *
	 * @return
	 */
	public static UserData getUserData(Activity activity){
		if(AppData.USER_DATA==null){
			AppData.USER_DATA=new UserData();
		}
		USER_DATA.setUserId(SharedPreferencesUtil.getValue(activity,SP_USER_ID,""));
		USER_DATA.setPassword(SharedPreferencesUtil.getValue(activity,SP_PASSWORD,""));
		USER_DATA.setBindMac(SharedPreferencesUtil.getValue(activity,SP_BIND_MAC,""));
		return AppData.USER_DATA;
	}

	/**
	 * 取消绑定
	 *
	 * @param activity
	 */
	public static void unbind(Activity activity){
		AppData.USER_DATA=null;
		SharedPreferencesUtil.putValue(activity,SP_USER_ID,"");
		SharedPreferencesUtil.putValue(activity,SP_PASSWORD,"");
		SharedPreferencesUtil.putValue(activity,SP_BIND_MAC,"");
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
