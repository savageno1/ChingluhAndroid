package com.chingluh.android.thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.chingluh.android.config.AppConfig;
import com.chingluh.android.handler.InitHandler;
import com.chingluh.android.model.PubCompany;
import com.chingluh.android.util.MapForSerializable;
import com.chingluh.android.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InitThread implements Runnable{
	private Activity activity;
	private InitHandler initHandler;

	public InitThread(Activity activity){
		this.activity=activity;
		this.initHandler=new InitHandler(this.activity);
	}

	@Override
	public void run(){
		Message message=this.initHandler.obtainMessage();
		Bundle bundle=new Bundle();
		try{
			//组参数
			HashMap<String,Object> hmParam=new HashMap<String,Object>();
			hmParam.put("macAddress=",NetworkUtil.getLoaclMacAddress(this.activity));
			//取值
			JSONObject joMsg=new JSONObject("{\"jaCompany\":[{\"companyname\":\"代号001 公司别001\",\"companyid\":\"001\"},{\"companyname\":\"代号002 公司别002\",\"companyid\":\"002\"},{\"companyname\":\"代号003 公司别003\",\"companyid\":\"003\"},{\"companyname\":\"代号004 公司别004\",\"companyid\":\"004\"},{\"companyname\":\"代号005 公司别005\",\"companyid\":\"005\"},{\"companyname\":\"代号006 公司别006\",\"companyid\":\"006\"},{\"companyname\":\"代号007 公司别007\",\"companyid\":\"007\"},{\"companyname\":\"代号008 公司别008\",\"companyid\":\"008\"},{\"companyname\":\"代号009 公司别009\",\"companyid\":\"009\"},{\"companyname\":\"代号010 公司别010\",\"companyid\":\"010\"},{\"companyname\":\"代号011 公司别011\",\"companyid\":\"011\"},{\"companyname\":\"代号012 公司别012\",\"companyid\":\"012\"},{\"companyname\":\"代号013 公司别013\",\"companyid\":\"013\"},{\"companyname\":\"代号015 公司别015\",\"companyid\":\"015\"},{\"companyname\":\"代号014 公司别014\",\"companyid\":\"014\"},{\"companyname\":\"代号016 公司别016\",\"companyid\":\"016\"},{\"companyname\":\"代号017 公司别017\",\"companyid\":\"017\"},{\"companyname\":\"代号018 公司别018\",\"companyid\":\"018\"},{\"companyname\":\"代号019 公司别019\",\"companyid\":\"019\"},{\"companyname\":\"代号020 公司别020\",\"companyid\":\"020\"}]}");
			//			if(AppData.getIpAddressV4().startsWith("192.168.0.")){
			//				joMsg=new JSONObject();
			//				JSONArray jaCompany=new JSONArray();
			//				for(String strCompanyId : new String[]{"001","002","003","004","005","006","007","008","009","010","011","012","013","015","014","016","017","018","019","020"}){
			//					JSONObject joCompany=new JSONObject();
			//					joCompany.put("companyid",strCompanyId);
			//					joCompany.put("companyname","代号"+strCompanyId+" 公司别"+strCompanyId);
			//					jaCompany.put(joCompany);
			//				}
			//				joMsg.put("jaCompany",jaCompany);
			//			}
			//
			Map<String,PubCompany> mapCompany=new HashMap<String,PubCompany>();
			JSONArray jaCompany=joMsg.getJSONArray("jaCompany");
			for(int i=0;i<jaCompany.length();i++){
				JSONObject joCompany=jaCompany.getJSONObject(i);
				PubCompany pubCompany=new PubCompany();
				pubCompany.setCompanyId(joCompany.getString("companyid"));
				pubCompany.setCompanyName(joCompany.getString("companyname"));
				mapCompany.put(pubCompany.getCompanyId(),pubCompany);
			}
			MapForSerializable mapForSerializable=new MapForSerializable();
			mapForSerializable.setMap(mapCompany);
			bundle.putSerializable("mapCompany",mapForSerializable);
		}catch(Exception exception){
			bundle.putString(AppConfig.STR_EXCEPTION,exception.getMessage());
		}
		message.setData(bundle);
		this.initHandler.sendMessage(message);
	}
}
