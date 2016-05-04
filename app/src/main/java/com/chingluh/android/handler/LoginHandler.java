package com.chingluh.android.handler;

import com.chingluh.android.activity.com.MainActivity;
import com.chingluh.android.app.AppData;
import com.chingluh.android.R;
import com.chingluh.android.util.MessageUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class LoginHandler extends Handler {
	private Activity activity;

	public LoginHandler(Activity activity) {
		super(activity.getMainLooper());
		this.activity = activity;
	}

	@Override
	public void handleMessage(Message message) {
		Bundle bundle = message.getData();
		if (MessageUtil.showMessageError(this.activity, Toast.LENGTH_LONG, bundle)) {
			AppData.setNullUserData();
			return;
		}
		//提示信息
		String strWelcomeMsg = this.activity.getString(R.string.MessageUtil_Message_Login_Welcome) + "  " + AppData.getUserData().getRealName() + "!";
		MessageUtil.showMessage(this.activity, strWelcomeMsg, Toast.LENGTH_SHORT);
		//跳转
		Intent intent = new Intent(this.activity, MainActivity.class);
		this.activity.startActivity(intent);
		this.activity.finish();
	}
}
