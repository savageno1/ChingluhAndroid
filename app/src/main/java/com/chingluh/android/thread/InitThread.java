package com.chingluh.android.thread;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import com.chingluh.android.config.AppConfig;
import com.chingluh.android.handler.InitHandler;
import com.chingluh.android.model.PubCompany;
import com.chingluh.android.util.ConnUtil;
import com.chingluh.android.util.MapForSerializable;
import com.chingluh.android.util.NetworkUtil;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

public class InitThread implements Runnable {
	private Activity activity;
	private InitHandler initHandler;

	public InitThread(Activity activity) {
		this.activity = activity;
		this.initHandler = new InitHandler(activity);
	}

	@Override
	public void run() {
		Message message = this.initHandler.obtainMessage();
		Bundle bundle = new Bundle();
		try {
			//组参数
			HashMap<String, Object> hmParam = new HashMap<String, Object>();
			hmParam.put("macAddress=", NetworkUtil.getLoaclMacAddress(this.activity));
			//取值
			JSONObject joMsg = ConnUtil.getMsg("console/login/" + "android/init?", hmParam).getJSONObject("joMsg");
			//
			Map<String, PubCompany> mapCompany = new HashMap<String, PubCompany>();
			JSONArray jaCompany = joMsg.getJSONArray("jaCompany");
			for (int i = 0; i < jaCompany.length(); i++) {
				JSONObject joCompany = jaCompany.getJSONObject(i);
				PubCompany pubCompany = new PubCompany();
				pubCompany.setCompanyId(joCompany.getString("companyid"));
				pubCompany.setCompanyName(joCompany.getString("companyname"));
				mapCompany.put(pubCompany.getCompanyId(), pubCompany);
			}
			MapForSerializable mapForSerializable = new MapForSerializable();
			mapForSerializable.setMap(mapCompany);
			bundle.putSerializable("mapCompany", mapForSerializable);
		} catch (Exception exception) {
			bundle.putString(AppConfig.STR_EXCEPTION, exception.getMessage());
		}
		message.setData(bundle);
		this.initHandler.sendMessage(message);
	}
}
