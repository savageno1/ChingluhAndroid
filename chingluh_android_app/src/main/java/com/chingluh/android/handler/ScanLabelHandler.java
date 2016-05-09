package com.chingluh.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.chingluh.android.R;
import com.chingluh.android.util.MessageUtil;

import java.io.Serializable;

public class ScanLabelHandler extends Handler{
	private Activity activity;

	public ScanLabelHandler(Activity activity){
		super(activity.getMainLooper());
		this.activity=activity;
	}

	@Override
	public void handleMessage(Message message){
		Bundle bundle=message.getData();
		if(MessageUtil.showMessageError(this.activity,Toast.LENGTH_LONG,bundle)){
			return;
		}
		String strWelcomeMsg;
		switch((OptType)bundle.get("OptType")){
			case Open:
				//提示信息
				strWelcomeMsg=this.activity.getString(R.string.MessageUtil_Message_Ufh_Opened)+"!";
				break;
			case Close:
				//提示信息
				strWelcomeMsg=this.activity.getString(R.string.MessageUtil_Message_Ufh_Closed)+"!";
				break;
			default:
				strWelcomeMsg="";
		}
		MessageUtil.showMessage(this.activity,strWelcomeMsg,Toast.LENGTH_SHORT);
		//跳转
	}

	public enum OptType implements Serializable{
		Open(0),Close(1);
		private int value;

		OptType(int value){
			this.value=value;
		}

		public int getValue(){
			return value;
		}
	}
}
