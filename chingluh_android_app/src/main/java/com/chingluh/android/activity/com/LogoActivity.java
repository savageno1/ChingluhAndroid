/**
 *
 */
package com.chingluh.android.activity.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.chingluh.android.R;

/**
 * @author Ray
 *         Logo 页面
 */
public class LogoActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		//线程启动
		new Thread(new Runnable(){
			@Override
			public void run(){
				//延时
				try{
					Thread.sleep(500);//定时1秒
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				//跳转登录页面
				Intent intentLogin=new Intent(LogoActivity.this,LoginActivity.class);
				startActivity(intentLogin);
				finish();
			}
		}).start();
	}
}
