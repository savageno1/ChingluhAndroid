/**
 *
 */
package com.chingluh.android.activity.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chingluh.android.R;
import com.chingluh.android.activity.withoutlogin.LocalMenuActivity;
import com.chingluh.android.thread.InitThread;
import com.chingluh.android.thread.LoginThread;

/**
 * @author Ray
 */
public class LoginActivity extends Activity{
	private Button buttonLogin;//登陆按钮
	private Button buttonQrLogin;//扫码登陆按钮
	private Button buttonLocal;//免登陆
	private OnClickListener onClickListener=new OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
				case R.id.buttonQrLogin:
					break;
				case R.id.buttonLogin:
					new Thread(new LoginThread(LoginActivity.this)).start();
					break;
				case R.id.buttonLocal:
					Intent intentLocalMenu=new Intent(LoginActivity.this,LocalMenuActivity.class);
					startActivity(intentLocalMenu);
					break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//
		buttonQrLogin=(Button)findViewById(R.id.buttonQrLogin);
		buttonLogin=(Button)findViewById(R.id.buttonLogin);
		buttonLocal=(Button)findViewById(R.id.buttonLocal);
		buttonQrLogin.setOnClickListener(this.onClickListener);
		buttonLogin.setOnClickListener(this.onClickListener);
		buttonLocal.setOnClickListener(this.onClickListener);
		//新线程加载初始化
		new Thread(new InitThread(LoginActivity.this)).start();
	}
}
