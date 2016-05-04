/**
 *
 */
package com.chingluh.android.activity.com;

import com.chingluh.android.base.BaseActivity;
import com.chingluh.android.R;
import com.chingluh.android.thread.InitThread;
import com.chingluh.android.thread.LoginThread;
import com.chingluh.android.util.MessageUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Ray
 */
public class LoginActivity extends BaseActivity {
	private static final int INT_SCAN_CODE = 1;
	private Button buttonLogin;//登陆按钮
	private Button buttonQrLogin;//扫码登陆按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.setNeedCheck(false);//是否检查登录状态
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//控件获取
		this.buttonLogin = (Button) findViewById(R.id.buttonLogin);
		this.buttonQrLogin = (Button) findViewById(R.id.buttonQrLogin);
		//登陆按钮事件绑定
		this.buttonLogin.setOnClickListener(this.onClickListenerLogin);
		//扫码登陆按钮事件绑定
		this.buttonQrLogin.setOnClickListener(this.onClickListenerQrLogin);
		//显示加载信息
		//MessageUtil.showMessage(LoginActivity.this, getString(R.string.MessageUtil_Message_InitSystemData), Toast.LENGTH_SHORT);
		//新线程加载初始化
		new Thread(new InitThread(LoginActivity.this)).start();
	}

	/**
	 * 登陆按钮事件响应
	 */
	private OnClickListener onClickListenerLogin = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//提示登陆
			//MessageUtil.showMessage(LoginActivity.this, getString(R.string.MessageUtil_Message_CheckingIn), Toast.LENGTH_LONG);
			//登陆线程
			new Thread(new LoginThread(LoginActivity.this)).start();
		}
	};
	/**
	 * 扫码登陆按钮事件响应
	 */
	private OnClickListener onClickListenerQrLogin = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//提示登陆
			//MessageUtil.showMessage(LoginActivity.this, getString(R.string.MessageUtil_Message_CheckingIn), Toast.LENGTH_LONG);
			//登陆线程
//			Intent intent = new Intent(LoginActivity.this, CaptureActivity.class);
//			startActivityForResult(intent, LoginActivity.INT_SCAN_CODE);
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case INT_SCAN_CODE:
				if (resultCode == Activity.RESULT_OK) {
					String result = data.getStringExtra("scan_result");
					MessageUtil.showMessage(LoginActivity.this, result, Toast.LENGTH_LONG);
				} else if (resultCode == Activity.RESULT_CANCELED) {
					MessageUtil.showMessage(LoginActivity.this, getString(R.string.MessageUtil_Message_Wrong_Result), Toast.LENGTH_LONG);
				}
				break;
			default:
				break;
		}
	}
}
