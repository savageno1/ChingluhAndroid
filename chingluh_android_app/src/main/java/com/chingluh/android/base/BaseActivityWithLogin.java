package com.chingluh.android.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.chingluh.android.R;
import com.chingluh.android.activity.com.LoginActivity;
import com.chingluh.android.app.AppData;
import com.chingluh.android.util.MessageUtil;

public class BaseActivityWithLogin extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//登录检查
		if(!checkHaveLogin()){
			return;
		}
	}

	private boolean checkHaveLogin(){
		boolean bLogined=AppData.getUserData().getUserId()!=null;
		//检查是否登录
		if(!bLogined){
			MessageUtil.showMessage(this,getString(R.string.Toast_NeedLogin_Text),Toast.LENGTH_SHORT);
			//设置跳转登录页面意图
			Intent intentLogin=new Intent(BaseActivityWithLogin.this,LoginActivity.class);
			//跳转
			startActivity(intentLogin);
			//关闭当前页面
			finish();
		}
		return false;
	}
}
