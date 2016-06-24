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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chingluh.android.R;
import com.chingluh.android.activity.withlogin.MainActivity;
import com.chingluh.android.app.AppData;
import com.chingluh.android.thread.InitThread;
import com.chingluh.android.thread.BindinThread;
import com.chingluh.android.thread.UpdateCheckThread;
import com.chingluh.android.util.MessageUtil;
import com.zbar.lib.CaptureActivity;

/**
 * @author Ray
 */
public class BindinActivity extends Activity{
	private EditText editTextUserId;//用户名
	private EditText editTextPassword;//密码
	private EditText editTextBindMac;//绑定校验码
	private Button buttonBindin;//绑定按钮
	private Button buttonQrBindin;//扫码绑定按钮
	private Button buttonLogout;//登出按钮
	private Button buttonLocal;//免绑定
	private Button buttonOnline;//需绑定
	private Spinner spinnerCompany;//公司别
	private OnClickListener onClickListener=new OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
				case R.id.buttonBindin:
					new Thread(new BindinThread(BindinActivity.this)).start();
					break;
				case R.id.buttonQrBindin:
					if(editTextUserId.getText().toString().trim().equals("")){
						MessageUtil.showMessage(BindinActivity.this,getString(R.string.MessageUtil_Message_Please_Input_Userid),Toast.LENGTH_LONG);
						break;
					}
					if(editTextPassword.getText().toString().trim().equals("")){
						MessageUtil.showMessage(BindinActivity.this,getString(R.string.MessageUtil_Message_Please_Input_Password),Toast.LENGTH_LONG);
						break;
					}
					Intent intent=new Intent(BindinActivity.this,CaptureActivity.class);
					startActivityForResult(intent,0);
					break;
				case R.id.buttonLogout:
					AppData.unbind(BindinActivity.this);
					onCreate(null);
					break;
				case R.id.buttonOnline:
					Intent intentOnlineMenu=new Intent(BindinActivity.this,MainActivity.class);
					startActivity(intentOnlineMenu);
					break;
				case R.id.buttonLocal:
					Intent intentLocalMenu=new Intent(BindinActivity.this,LocalActivity.class);
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
		//新线程加载初始化
		new Thread(new InitThread(BindinActivity.this)).start();
		//
		editTextUserId=(EditText)findViewById(R.id.editTextUserId);
		editTextPassword=(EditText)findViewById(R.id.editTextPassword);
		editTextBindMac=(EditText)findViewById(R.id.editTextBindMac);
		spinnerCompany=(Spinner)findViewById(R.id.spinnerCompany);
		buttonOnline=(Button)findViewById(R.id.buttonOnline);
		buttonQrBindin=(Button)findViewById(R.id.buttonQrBindin);
		buttonBindin=(Button)findViewById(R.id.buttonBindin);
		buttonLogout=(Button)findViewById(R.id.buttonLogout);
		buttonLocal=(Button)findViewById(R.id.buttonLocal);
		buttonQrBindin.setOnClickListener(this.onClickListener);
		buttonBindin.setOnClickListener(this.onClickListener);
		buttonLogout.setOnClickListener(this.onClickListener);
		buttonLocal.setOnClickListener(this.onClickListener);
		buttonOnline.setOnClickListener(this.onClickListener);
		//显示当前版本号
		try{
			PackageManager packageManager=this.getPackageManager();
			PackageInfo packageInfo=packageManager.getPackageInfo(this.getPackageName(),0);
			((TextView)findViewById(R.id.textViewVersion)).setText(getString(R.string.TextView_Version_Text)+packageInfo.versionName);
		}catch(Exception e){
			MessageUtil.showMessage(this,e.getMessage(),Toast.LENGTH_LONG);
		}
		//检查更新
		new Thread(new UpdateCheckThread(BindinActivity.this)).start();
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