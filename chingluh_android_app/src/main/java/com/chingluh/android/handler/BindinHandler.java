package com.chingluh.android.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.chingluh.android.R;
import com.chingluh.android.activity.com.BindinActivity;
import com.chingluh.android.app.AppData;
import com.chingluh.android.util.MessageUtil;

public class BindinHandler extends Handler{
	private Activity activity;

	public BindinHandler(Activity activity){
		super(activity.getMainLooper());
		this.activity=activity;
	}

	@Override
	public void handleMessage(Message message){
		Bundle bundle=message.getData();
		if(MessageUtil.showMessageError(this.activity,Toast.LENGTH_LONG,bundle)){
			AppData.unbind(activity);
			return;
		}
		//提示信息
		String strWelcomeMsg=this.activity.getString(R.string.MessageUtil_Message_Bindin_Welcome)+"  "+AppData.getUserData(activity).getRealName()+"!";
		MessageUtil.showMessage(this.activity,strWelcomeMsg,Toast.LENGTH_SHORT);
		//跳转
		Intent intent=new Intent(this.activity,BindinActivity.class);
		this.activity.startActivity(intent);
		this.activity.finish();
	}
}
