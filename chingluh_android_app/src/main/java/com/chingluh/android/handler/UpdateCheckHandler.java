package com.chingluh.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.chingluh.android.app.AppData;
import com.chingluh.android.util.MessageUtil;

public class UpdateCheckHandler extends Handler{
	private Activity activity;

	public UpdateCheckHandler(Activity activity){
		super(activity.getMainLooper());
		this.activity=activity;
	}

	@Override
	public void handleMessage(Message message){
		Bundle bundle=message.getData();
		if(MessageUtil.showMessageError(this.activity,Toast.LENGTH_LONG,bundle)){
			AppData.setNullUserData();
			return;
		}
	}
}
