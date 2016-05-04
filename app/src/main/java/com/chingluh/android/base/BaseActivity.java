package com.chingluh.android.base;

import com.chingluh.android.activity.com.LoginActivity;
import com.chingluh.android.app.AppData;
import com.chingluh.android.R;
import com.chingluh.android.util.MessageUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

public class BaseActivity extends Activity {
	private boolean bNeedCheck = true;

	protected void setNeedCheck(boolean bNeedCheck) {
		this.bNeedCheck = bNeedCheck;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//是否需要登录检查
		if (this.bNeedCheck) {
			if (!checkHaveLogin()) {
				return;
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//创建退出对话框
			AlertDialog alertDialogExit = new AlertDialog.Builder(this).create();
			//设置对话框标题
			alertDialogExit.setTitle(getString(R.string.AlertDialog_Exit_Title));
			//设置对话框消息
			alertDialogExit.setMessage(getString(R.string.AlertDialog_Exit_Message));
			//添加按钮并注册监听
			alertDialogExit.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.Button_Positive_Title), this.onClickListenerExit);//确认
			alertDialogExit.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.Button_Negative_Title), this.onClickListenerExit);//取消
			//显示对话框
			alertDialogExit.show();
		}
		return false;
	}

	//按钮监听器
	DialogInterface.OnClickListener onClickListenerExit = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE://确认按钮
				finish();
				break;
			case DialogInterface.BUTTON_NEGATIVE://取消按钮
				break;
			default:
				break;
			}
		}
	};

	private boolean checkHaveLogin() {
		boolean bLogined = AppData.getUserData().getUserId() == null;
		//检查是否登录
		if (bLogined) {
			MessageUtil.showMessage(this, getString(R.string.Toast_NeedLogin_Text), Toast.LENGTH_SHORT);
			//设置跳转登录页面意图
			Intent intentLogin = new Intent(BaseActivity.this, LoginActivity.class);
			//跳转
			startActivity(intentLogin);
			//不关闭当前页面
		}
		return false;
	}
}
