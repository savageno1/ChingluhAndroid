package com.chingluh.android.thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseThread;
import com.chingluh.android.config.AppConfig;
import com.chingluh.android.handler.UpdateCheckHandler;
import com.chingluh.android.util.FtpUtil;

/**
 * Created by Ray on 2016/05/17.
 * Check Update
 */
public class UpdateCheckThread extends BaseThread{
	public UpdateCheckThread(Activity activity){
		super(activity,new UpdateCheckHandler(activity));
	}

	@Override
	public void run(){
		Message message=this.handler.obtainMessage();
		Bundle bundle=new Bundle();
		try{
			FtpUtil ftpUtil=FtpUtil.getInstance();
			if(!ftpUtil.initFTPSetting(AppConfig.FtpIpAddress,AppConfig.FtpPort,AppConfig.FtpUserId,AppConfig.FtpPassword)){
				throw new Exception(activity.getString(R.string.MessageUtil_Message_FtpConnectionFail));
			}
		}catch(Exception exception){
			bundle.putString(AppConfig.STR_EXCEPTION,exception.getMessage());
		}
		message.setData(bundle);
		this.handler.sendMessage(message);
	}
}
