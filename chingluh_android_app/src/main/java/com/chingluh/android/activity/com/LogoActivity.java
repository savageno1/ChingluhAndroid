/**
 *
 */
package com.chingluh.android.activity.com;

import android.animation.AnimatorSet;
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
	private static final int INT_WAIT_TIME=1500;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		try{
			//动画
			//线程启动
			new Thread(new Runnable(){
				@Override
				public void run(){
					//延时
					try{
						Thread.sleep(INT_WAIT_TIME);//定时1秒
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					//跳转登录页面
					Intent intentLogin=new Intent(LogoActivity.this,BindinActivity.class);
					startActivity(intentLogin);
					finish();
				}
			}).start();
			//logo渐变
			ImageView imageViewLogo=(ImageView)findViewById(R.id.imageViewLogo);
			ObjectAnimator objectAnimatorAlpha=ObjectAnimator.ofFloat(imageViewLogo,"alpha",0f,1f);
			ObjectAnimator objectAnimatorScaleX=ObjectAnimator.ofFloat(imageViewLogo,"scaleX",0.97f,1f);
			ObjectAnimator objectAnimatorScaleY=ObjectAnimator.ofFloat(imageViewLogo,"scaleY",0.97f,1f);
			AnimatorSet animatorSet=new AnimatorSet();
			animatorSet.play(objectAnimatorScaleX).with(objectAnimatorScaleY).with(objectAnimatorAlpha);
			animatorSet.setDuration(INT_WAIT_TIME);
			animatorSet.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
