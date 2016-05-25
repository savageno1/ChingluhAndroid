/**
 *
 */
package com.chingluh.android.activity.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chingluh.android.R;
import com.chingluh.android.thread.InitThread;
import com.chingluh.android.thread.LoginThread;
import com.chingluh.android.thread.UpdateCheckThread;
import com.chingluh.android.util.MessageUtil;
import com.zbar.lib.CaptureActivity;

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
					Intent intent=new Intent(LoginActivity.this,CaptureActivity.class);
					startActivityForResult(intent,0);
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
		//显示当前版本号
		try{
			PackageManager packageManager=this.getPackageManager();
			PackageInfo packageInfo=packageManager.getPackageInfo(this.getPackageName(),0);
			((TextView)findViewById(R.id.textViewVersion)).setText(getString(R.string.TextView_Version_Text)+packageInfo.versionName);
		}catch(Exception e){
			MessageUtil.showMessage(this,e.getMessage(),Toast.LENGTH_LONG);
		}
		//新线程加载初始化
		new Thread(new InitThread(LoginActivity.this)).start();
		//检查更新
		new Thread(new UpdateCheckThread(LoginActivity.this)).start();
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

	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode,resultCode,data);
		if(resultCode==RESULT_OK){
			Bundle bundle=data.getExtras();
			String scanResult=bundle.getString("result");
			((EditText)findViewById(R.id.editTextUserId)).setText(scanResult);
		}
	}
}
