/**
 *
 */
package com.chingluh.android.activity.com;

import com.chingluh.android.base.BaseActivity;
import com.chingluh.android.R;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author Ray
 *         Logo 页面
 */
public class LogoActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.setNeedCheck(false);//是否检查登录状态
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		//线程启动
		new Thread(new Runnable() {
			@Override
			public void run() {
				//延时
				try {
					Thread.sleep(1000);//定时1秒
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//跳转登录页面
				Intent intentLogin = new Intent(LogoActivity.this, LoginActivity.class);
				startActivity(intentLogin);
				//				Intent intentMain = new Intent(LogoActivity.this, MainActivity.class);
				//				startActivity(intentMain);
				//				Intent intentRfid = new Intent(LogoActivity.this, RfidActivity.class);
				//				startActivity(intentRfid);
				finish();
			}
		}).start();
	}
}
