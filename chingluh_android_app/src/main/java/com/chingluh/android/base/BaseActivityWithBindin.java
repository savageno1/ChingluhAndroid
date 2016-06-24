package com.chingluh.android.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.chingluh.android.R;
import com.chingluh.android.activity.com.BindinActivity;
import com.chingluh.android.app.AppData;
import com.chingluh.android.util.MessageUtil;

public class BaseActivityWithBindin extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//登录检查
		if(!checkHaveBindin()){
			return;
		}
	}

	private boolean checkHaveBindin(){
		boolean bBindined=AppData.getUserData().getUserId()!=null;
		//检查是否登录
		if(!bBindined){
			MessageUtil.showMessage(this,getString(R.string.Toast_NeedBindin_Text),Toast.LENGTH_SHORT);
			//设置跳转登录页面意图
			Intent intentBindin=new Intent(BaseActivityWithBindin.this,BindinActivity.class);
			//跳转
			startActivity(intentBindin);
			//关闭当前页面
			finish();
		}
		return false;
	}
}
