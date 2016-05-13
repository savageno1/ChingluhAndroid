/**
 *
 */
package com.chingluh.android.activity.com;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.chingluh.android.R;

/**
 * @author Ray
 *         Logo 页面
 */
public class LogoActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		//float scale=getResources().getDisplayMetrics().density;
		try{
			//
			//线程启动
			new Thread(new Runnable(){
				@Override
				public void run(){
					//延时
					try{
						Thread.sleep(1000);//定时1秒
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					//跳转登录页面
					Intent intentLogin=new Intent(LogoActivity.this,LoginActivity.class);
					startActivity(intentLogin);
					finish();
				}
			}).start();
			//logo渐变
			ImageView imageViewLogo=(ImageView)findViewById(R.id.imageViewLogo);
			ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(imageViewLogo,"alpha",0f,1f);
			objectAnimator.setDuration(300);
			objectAnimator.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
