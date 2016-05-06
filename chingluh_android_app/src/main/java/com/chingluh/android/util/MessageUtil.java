package com.chingluh.android.util;

import com.chingluh.android.config.AppConfig;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MessageUtil {
	public static void showMessage(Activity activity, String message, int duration) {
		//创建消息框
		Toast toastNeedLogin = Toast.makeText(activity, message, duration);
		//设置消息框信息
		toastNeedLogin.setText(message);
		//设置消息框显示时间
		toastNeedLogin.setDuration(duration);
		//显示消息框
		toastNeedLogin.show();
	}

	public static void showMessageError(Activity activity, int duration, Exception exception) {
		//显示消息
		showMessage(activity, exception.getMessage(), duration);
		//显示错误信息
		exception.printStackTrace();
	}

	public static boolean showMessageError(Activity activity, int duration, Bundle bundle) {
		if (bundle.containsKey(AppConfig.STR_EXCEPTION)) {
			//String[] aStrException = bundle.getString(AppConfig.STR_EXCEPTION).split("\\n");
			//			StringBuilder sbException = new StringBuilder();
			//			for (String strException : aStrException) {
			//				sbException.append("\\n").append(sbException);
			//			}
			//			String strExceptionString = sbException.toString().substring(3);
			String strExceptionString = bundle.getString(AppConfig.STR_EXCEPTION).replace("\\n", "\n");
			//显示消息
			showMessage(activity, strExceptionString, duration);
			//Log消息
			Log.d("I", strExceptionString);
			return true;
		}
		return false;
	}
}
