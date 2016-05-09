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
				if(bundle.getInt("OptRtn")==0){
					strWelcomeMsg=this.activity.getString(R.string.MessageUtil_Message_Ufh_Opened)+"!";
				}else{
					strWelcomeMsg=this.activity.getString(R.string.MessageUtil_Message_Ufh_Open_Fail)+"!";
				}
				break;
			case Close:
				if(bundle.getInt("OptRtn")==0){
					strWelcomeMsg=this.activity.getString(R.string.MessageUtil_Message_Ufh_Closed)+"!";
				}else{
					strWelcomeMsg=this.activity.getString(R.string.MessageUtil_Message_Ufh_Close_Fail)+"!";
				}
				break;
			default:
				strWelcomeMsg="";
		}
		MessageUtil.showMessage(this.activity,strWelcomeMsg,Toast.LENGTH_SHORT);
	}

	public enum OptType implements Serializable{
		Open,Read,Set,Close,R6B,R6C
	}
}
