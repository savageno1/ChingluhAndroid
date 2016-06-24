package com.chingluh.android.thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.chingluh.android.R;
import com.chingluh.android.app.AppData;
import com.chingluh.android.config.AppConfig;
import com.chingluh.android.handler.InitHandler;
import com.chingluh.android.model.PubCompany;
import com.chingluh.android.util.HttpUtil;
import com.chingluh.android.util.MapForSerializable;
import com.chingluh.android.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InitThread implements Runnable{
	private static String STR_DEFAULT_JOMSG;
	private Activity activity;
	private InitHandler initHandler;

	public InitThread(Activity activity){
		this.activity=activity;
		this.initHandler=new InitHandler(this.activity);
		//
		if(STR_DEFAULT_JOMSG==null){
			STR_DEFAULT_JOMSG="{\"jaCompany\":[{\"companyname\":\"代号001 公司别001\",\"companyid\":\"001\"},{\"companyname\":\"代号002 公司别002\",\"companyid\":\"002\"},{\"companyname\":\"代号003 公司别003\",\"companyid\":\"003\"},{\"companyname\":\"代号004 公司别004\",\"companyid\":\"004\"},{\"companyname\":\"代号005 公司别005\",\"companyid\":\"005\"},{\"companyname\":\"代号006 公司别006\",\"companyid\":\"006\"},{\"companyname\":\"代号007 公司别007\",\"companyid\":\"007\"},{\"companyname\":\"代号008 公司别008\",\"companyid\":\"008\"},{\"companyname\":\"代号009 公司别009\",\"companyid\":\"009\"},{\"companyname\":\"代号010 公司别010\",\"companyid\":\"010\"},{\"companyname\":\"代号011 公司别011\",\"companyid\":\"011\"},{\"companyname\":\"代号012 公司别012\",\"companyid\":\"012\"},{\"companyname\":\"代号013 公司别013\",\"companyid\":\"013\"},{\"companyname\":\"代号015 公司别015\",\"companyid\":\"015\"},{\"companyname\":\"代号014 公司别014\",\"companyid\":\"014\"},{\"companyname\":\"代号016 公司别016\",\"companyid\":\"016\"},{\"companyname\":\"代号017 公司别017\",\"companyid\":\"017\"},{\"companyname\":\"代号018 公司别018\",\"companyid\":\"018\"},{\"companyname\":\"代号019 公司别019\",\"companyid\":\"019\"},{\"companyname\":\"代号020 公司别020\",\"companyid\":\"020\"}]}";
		}
	}

	@Override
	public void run(){
		Message message=this.initHandler.obtainMessage();
		Bundle bundle=new Bundle();
		try{
			//组参数
			HashMap<String,Object> hmParam=new HashMap<String,Object>();
			hmParam.put("uesrId=",AppData.getUserData(activity).getUserId());
			hmParam.put("macAddress=",NetworkUtil.getLoaclMacAddress(this.activity));
			//取默认列表值
			JSONObject joMsg=null;
			try{
				joMsg=HttpUtil.getMsg("console/login/"+"android/init?",hmParam).getJSONObject("joMsg");
			}catch(Exception e){
				joMsg=new JSONObject(STR_DEFAULT_JOMSG);
			}
			Map<String,PubCompany> mapCompany=new HashMap<String,PubCompany>();
			JSONArray jaCompany=joMsg.getJSONArray("jaCompany");
			for(int i=0;i<jaCompany.length();i++){
				JSONObject joCompany=jaCompany.getJSONObject(i);
				PubCompany pubCompany=new PubCompany(joCompany.getString("companyid"));
				pubCompany.setCompanyName(joCompany.getString("companyname"));
				mapCompany.put(pubCompany.getCompanyId(),pubCompany);
			}
			MapForSerializable mapForSerializable=new MapForSerializable();
			mapForSerializable.setMap(mapCompany);
			bundle.putSerializable("mapCompany",mapForSerializable);
			//检核绑定状态变更
			try{
				joMsg=HttpUtil.getMsg("console/login/"+"android/checkBin?",hmParam).getJSONObject("joMsg");
			}catch(Exception e){
				joMsg=new JSONObject().put("rtnMsg",this.activity.getString(R.string.MessageUtil_Message_CheckInFail));
			}
			if(joMsg.has("rtnMsg")){
				AppData.unbind(activity);
			}
		}catch(Exception exception){
			bundle.putString(AppConfig.STR_EXCEPTION,exception.getMessage());
		}
		message.setData(bundle);
		this.initHandler.sendMessage(message);
	}
}
