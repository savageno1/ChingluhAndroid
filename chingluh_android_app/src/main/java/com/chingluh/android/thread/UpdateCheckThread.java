package com.chingluh.android.thread;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseThread;
import com.chingluh.android.config.AppConfig;
import com.chingluh.android.handler.UpdateCheckHandler;
import com.chingluh.android.util.FtpUtil;

import org.apache.commons.net.ftp.FTPFile;

import java.io.File;

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
		FtpUtil ftpUtil=FtpUtil.getInstance();
		try{
			if(!ftpUtil.initFTPSetting(AppConfig.FtpIpAddress,AppConfig.FtpPort,AppConfig.FtpUserId,AppConfig.FtpPassword)){
				throw new Exception(activity.getString(R.string.MessageUtil_Message_Ftp_Connection_Fail));
			}
			if(!ftpUtil.changePath(AppConfig.ApkFilePath)){
				throw new Exception(activity.getString(R.string.MessageUtil_Message_Ftp_Connection_Fail));
			}
			FTPFile[] aFTPFile=ftpUtil.getFiles();
			if(aFTPFile.length==0){
				throw new Exception(activity.getString(R.string.MessageUtil_Message_Apk_Inexist));
			}
			int iFileVersionMax=0;
			String strFileNameMax="";
			long iFIleSizeMax=0;
			for(FTPFile ftpFile : aFTPFile){
				String strFileName=ftpFile.getName();
				if(strFileName.startsWith(AppConfig.ApkFileNamePrefix)){
					String strApkName=strFileName;
					String[] aStrName=strApkName.split(AppConfig.ApkFileNameSeparator);
					if(aStrName.length==3){
						try{
							int iFileVersionCur=Integer.valueOf(aStrName[1]);
							if(iFileVersionCur>iFileVersionMax){
								iFileVersionMax=iFileVersionCur;
								strFileNameMax=strApkName;
								iFIleSizeMax=ftpFile.getSize();
							}
						}catch(Exception exception){
							exception.printStackTrace();
						}
					}
				}
			}
			//检查程序版本号 提示更新
			PackageManager packageManager=activity.getPackageManager();
			PackageInfo packageInfo=packageManager.getPackageInfo(activity.getPackageName(),0);
			int iFileVersionSys=packageInfo.versionCode;
			if(iFileVersionMax>iFileVersionSys){
				bundle.putInt("NEW_VERSION",iFileVersionMax);
				bundle.putString("NEW_VERSION_FILE",strFileNameMax);
				bundle.putLong("NEW_VERSION_FILE_SIZE",iFIleSizeMax);
			}
		}catch(Exception exception){
			bundle.putString(AppConfig.STR_EXCEPTION,exception.getMessage());
		}finally{
			ftpUtil.close();
		}
		message.setData(bundle);
		this.handler.sendMessage(message);
	}
}
