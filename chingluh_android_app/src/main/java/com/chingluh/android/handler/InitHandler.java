package com.chingluh.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chingluh.android.R;
import com.chingluh.android.adapter.PubCompanyAdapter;
import com.chingluh.android.app.AppData;
import com.chingluh.android.base.BaseModel;
import com.chingluh.android.model.PubCompany;
import com.chingluh.android.util.MapForSerializable;
import com.chingluh.android.util.MessageUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InitHandler extends Handler{
	private Activity activity;
	private Spinner spinner;//登陆公司别
	private PubCompanyAdapter adapter;
	private ListView listView;
	private EditText editTextUserId;//用户名
	private EditText editTextPassword;//密码
	private EditText editTextBindMac;//绑定校验码
	private Button buttonBindin;//绑定按钮
	private Button buttonQrBindin;//扫码绑定按钮
	private Button buttonLogout;//登出按钮
	private Button buttonLocal;//免绑定
	private Button buttonOnline;//需绑定
	private Spinner spinnerCompany;//公司别

	public InitHandler(Activity activity){
		super(activity.getMainLooper());
		this.activity=activity;
		this.spinner=(Spinner)activity.findViewById(R.id.spinnerCompany);
		this.listView=new ListView(activity);
		//
		editTextUserId=(EditText)activity.findViewById(R.id.editTextUserId);
		editTextPassword=(EditText)activity.findViewById(R.id.editTextPassword);
		editTextBindMac=(EditText)activity.findViewById(R.id.editTextBindMac);
		spinnerCompany=(Spinner)activity.findViewById(R.id.spinnerCompany);
		buttonOnline=(Button)activity.findViewById(R.id.buttonOnline);
		buttonQrBindin=(Button)activity.findViewById(R.id.buttonQrBindin);
		buttonBindin=(Button)activity.findViewById(R.id.buttonBindin);
		buttonLogout=(Button)activity.findViewById(R.id.buttonLogout);
		buttonLocal=(Button)activity.findViewById(R.id.buttonLocal);
	}

	@Override
	public void handleMessage(Message message){
		try{
			Bundle bundle=message.getData();
			if(MessageUtil.showMessageError(this.activity,Toast.LENGTH_LONG,bundle)){
				return;
			}
			//组公司别
			Map<String,PubCompany> mapCompany=(Map<String,PubCompany>)((MapForSerializable)bundle.getSerializable("mapCompany")).getMap();
			Iterator<String> iterator=mapCompany.keySet().iterator();
			List<BaseModel> alModel=new ArrayList<BaseModel>();
			while(iterator.hasNext()){
				alModel.add(mapCompany.get(iterator.next()));
			}
			//提供公司别数组给配适器
			if(adapter==null){
				this.adapter=new PubCompanyAdapter(this.activity,this.listView,alModel);
				this.spinner.setAdapter(this.adapter);
			}
			//检核绑定并处理
			boolean bBind=AppData.checkBind(activity);
			editTextUserId.setEnabled(!bBind);
			editTextPassword.setEnabled(!bBind);
			editTextBindMac.setEnabled(!bBind);
			spinnerCompany.setEnabled(!bBind);
			buttonQrBindin.setEnabled(!bBind);
			buttonBindin.setEnabled(!bBind);
			buttonLogout.setEnabled(bBind);
			buttonOnline.setEnabled(bBind);
			if(bBind){
				editTextUserId.setText(AppData.getUserData(activity).getUserId());
				editTextPassword.setText(AppData.getUserData(activity).getPassword());
				editTextBindMac.setText(AppData.getUserData(activity).getBindMac());
			}else{
				editTextUserId.setText("");
				editTextPassword.setText("");
				editTextBindMac.setText("");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
