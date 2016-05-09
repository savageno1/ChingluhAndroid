/**
 *
 */
package com.chingluh.android.activity.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chingluh.android.R;
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
					new Thread(new LoginThread(LoginActivity.this)).start();
					break;
				case R.id.buttonLogin:
					new Thread(new LoginThread(LoginActivity.this)).start();
					break;
				case R.id.buttonLocal:
					Intent intentLocalMenu=new Intent(LoginActivity.this,LocalActivity.class);
					startActivity(intentLocalMenu);
					break;
			}
		}
	};
	//按钮监听器
	private DialogInterface.OnClickListener onClickListenerExit=new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog,int which){
			switch(which){
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

	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			//创建退出对话框
			AlertDialog alertDialogExit=new AlertDialog.Builder(this).create();
			//设置对话框标题
			alertDialogExit.setTitle(getString(R.string.AlertDialog_Exit_Title));
			//设置对话框消息
			alertDialogExit.setMessage(getString(R.string.AlertDialog_Exit_Application_Message));
			//添加按钮并注册监听
			alertDialogExit.setButton(DialogInterface.BUTTON_POSITIVE,getString(R.string.Button_Positive_Title),this.onClickListenerExit);//确认
			alertDialogExit.setButton(DialogInterface.BUTTON_NEGATIVE,getString(R.string.Button_Negative_Title),this.onClickListenerExit);//取消
			//显示对话框
			alertDialogExit.show();
		}
		return false;
	}
}
