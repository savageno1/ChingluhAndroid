package com.chingluh.android.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.chingluh.android.R;
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
		Integer iNewVersion=bundle.getInt("NEW_VERSION");
		if(iNewVersion!=null){
			MessageUtil.showMessage(this.activity,activity.getString(R.string.MessageUtil_Message_Apk_New),Toast.LENGTH_LONG);
			//创建退出对话框
			AlertDialog alertDialogExit=new AlertDialog.Builder(activity).create();
			//设置对话框标题
			alertDialogExit.setTitle(activity.getString(R.string.AlertDialog_ApkNew_Title));
			//设置对话框消息
			alertDialogExit.setMessage(activity.getString(R.string.AlertDialog_ApkNew_Application_Message));
			//添加按钮并注册监听
			alertDialogExit.setButton(DialogInterface.BUTTON_POSITIVE,activity.getString(R.string.Button_Positive_Title),this.onClickListenerExit);//确认
			alertDialogExit.setButton(DialogInterface.BUTTON_NEGATIVE,activity.getString(R.string.Button_Negative_Title),this.onClickListenerExit);//取消
			//显示对话框
			alertDialogExit.show();
		}
	}
	//按钮监听器
	private DialogInterface.OnClickListener onClickListenerExit=new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog,int which){
			switch(which){
				case DialogInterface.BUTTON_POSITIVE://确认按钮
					break;
				case DialogInterface.BUTTON_NEGATIVE://取消按钮
					break;
				default:
					break;
			}
		}
	};
}
