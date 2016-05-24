package com.chingluh.android.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.chingluh.android.R;
import com.chingluh.android.app.AppData;
import com.chingluh.android.config.AppConfig;
import com.chingluh.android.thread.UpdateThread;
import com.chingluh.android.util.MessageUtil;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateCheckHandler extends Handler{
	private Activity activity;
	private String strFileName;
	private Long lFileSize;
	//按钮监听器
	private DialogInterface.OnClickListener onClickListenerExit=new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog,int which){
			switch(which){
				case DialogInterface.BUTTON_POSITIVE://确认按钮
					//下载提示框
					final File file=new File(AppConfig.ApkFileLocalPath+"/"+strFileName);
					final ProgressDialog progressDialog=new ProgressDialog(activity);
					progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					progressDialog.setTitle(R.string.ProgressDialog_Ftp_Download_Title);
					progressDialog.setMessage(activity.getString(R.string.ProgressDialog_Ftp_Download_Title));
					progressDialog.setCancelable(true);
					progressDialog.setIndeterminate(false);
					progressDialog.show();
					new Timer().schedule(new TimerTask(){
						@Override
						public void run(){
							if(file.length()>=lFileSize){
								progressDialog.cancel();
								cancel();
							}
							if(!progressDialog.isShowing()){
								cancel();
							}
							progressDialog.setProgress((int)(file.length()*100/lFileSize));
						}
					},0,100);
					//更新程式
					new Thread(new UpdateThread(activity,strFileName)).start();
					break;
				case DialogInterface.BUTTON_NEGATIVE://取消按钮
					break;
				default:
					break;
			}
		}
	};

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
		int iNewVersion=bundle.getInt("NEW_VERSION");
		strFileName=bundle.getString("NEW_VERSION_FILE");
		lFileSize=bundle.getLong("NEW_VERSION_FILE_SIZE");
		if(iNewVersion>0){
			//创建退出对话框
			AlertDialog alertDialogExit=new AlertDialog.Builder(activity).create();
			//设置对话框标题
			alertDialogExit.setTitle(activity.getString(R.string.AlertDialog_ApkNew_Title)+String.valueOf(iNewVersion));
			//设置对话框消息
			alertDialogExit.setMessage(activity.getString(R.string.AlertDialog_ApkNew_Application_Message));
			//添加按钮并注册监听
			alertDialogExit.setButton(DialogInterface.BUTTON_POSITIVE,activity.getString(R.string.Button_Positive_Title),this.onClickListenerExit);//确认
			alertDialogExit.setButton(DialogInterface.BUTTON_NEGATIVE,activity.getString(R.string.Button_Negative_Title),this.onClickListenerExit);//取消
			//显示对话框
			alertDialogExit.show();
		}
	}
}
