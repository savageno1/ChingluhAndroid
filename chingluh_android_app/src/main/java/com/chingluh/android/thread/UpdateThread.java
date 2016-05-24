package com.chingluh.android.thread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;

import com.chingluh.android.R;
import com.chingluh.android.config.AppConfig;
import com.chingluh.android.util.FtpUtil;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ray on 2016/05/17.
 * Check Update
 */
public class UpdateThread implements Runnable{
	private Activity activity;
	private String remoteFileName;

	public UpdateThread(Activity activity,String remoteFileName){
		this.activity=activity;
		this.remoteFileName=remoteFileName;
	}

	@Override
	public void run(){
		FtpUtil ftpUtil=FtpUtil.getInstance();
		//
		final File file=new File(AppConfig.ApkFileLocalPath+"/"+remoteFileName);
		//
		try{
			//
			if(!ftpUtil.initFTPSetting(AppConfig.FtpIpAddress,AppConfig.FtpPort,AppConfig.FtpUserId,AppConfig.FtpPassword)){
				throw new Exception(activity.getString(R.string.MessageUtil_Message_Ftp_Connection_Fail));
			}
			if(!ftpUtil.download(AppConfig.ApkFilePath,remoteFileName,AppConfig.ApkFileLocalPath,remoteFileName)){
				throw new Exception(activity.getString(R.string.MessageUtil_Message_Ftp_Download_Fail));
			}
			//
			Intent intent=new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
			activity.startActivity(intent);
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			ftpUtil.close();
		}
	}
}
