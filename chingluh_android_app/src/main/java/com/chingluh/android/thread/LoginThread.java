package com.chingluh.android.thread;

import java.util.HashMap;

import org.json.JSONObject;

import com.chingluh.android.app.AppData;
import com.chingluh.android.R;
import com.chingluh.android.base.BaseThread;
import com.chingluh.android.config.AppConfig;
import com.chingluh.android.handler.LoginHandler;
import com.chingluh.android.model.PubCompany;
import com.chingluh.android.util.HttpUtil;
import com.chingluh.android.util.NetworkUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.widget.EditText;
import android.widget.Spinner;

public class LoginThread extends BaseThread{
	//
	private EditText editTextUserId;//用户名
	private EditText editTextPassword;//密码
	private Spinner spinnerCompany;//公司别

	public LoginThread(Activity activity){
		super(activity,new LoginHandler(activity));
		//
		this.editTextUserId=(EditText)activity.findViewById(R.id.editTextUserId);
		this.editTextPassword=(EditText)activity.findViewById(R.id.editTextPassword);
		this.spinnerCompany=(Spinner)activity.findViewById(R.id.spinnerCompany);
	}

	@Override
	public void run(){
		Message message=this.handler.obtainMessage();
		Bundle bundle=new Bundle();
		try{
			//组参数
			HashMap<String,Object> hmParam=new HashMap<String,Object>();
			hmParam.put("userId",this.editTextUserId.getText().toString());
			hmParam.put("password",this.editTextPassword.getText().toString());
			hmParam.put("companyId",((PubCompany)this.spinnerCompany.getSelectedItem()).getCompanyId());
			hmParam.put("macAddress",NetworkUtil.getLoaclMacAddress(activity));
			hmParam.put("lang","CHS");
			//
			JSONObject joMsg=HttpUtil.getMsg("console/login/"+"android/checkIn?",hmParam).getJSONObject("joMsg");
			//
			if(joMsg.has("rtnMsg")){
				throw new Exception(this.activity.getString(R.string.MessageUtil_Message_CheckInFail)+"\n"+joMsg.getString("rtnMsg"));
			}
			JSONObject joPubUser=joMsg.getJSONObject("joPubUser");
			//
			AppData.getUserData().setUserId(joPubUser.getString("user_id"));
			AppData.getUserData().setPassword(this.editTextPassword.getText().toString());
			AppData.getUserData().setRealName(joPubUser.getString("realname"));
		}catch(Exception exception){
			bundle.putString(AppConfig.STR_EXCEPTION,exception.getMessage());
		}
		message.setData(bundle);
		this.handler.sendMessage(message);
	}
}
