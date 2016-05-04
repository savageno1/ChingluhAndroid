package com.chingluh.android.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.chingluh.android.adapter.CompanyAdapter;
import com.chingluh.android.R;
import com.chingluh.android.model.PubCompany;
import com.chingluh.android.util.MapForSerializable;
import com.chingluh.android.util.MessageUtil;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class InitHandler extends Handler {
	private Activity activity;
	private Spinner spinnerCompany;//登陆公司别
	private CompanyAdapter companyAdapter;
	private ListView listView;

	public InitHandler(Activity activity) {
		super(activity.getMainLooper());
		this.activity = activity;
		this.spinnerCompany = (Spinner) activity.findViewById(R.id.spinnerCompany);
		this.listView = new ListView(activity);
	}

	@Override
	public void handleMessage(Message message) {
		Bundle bundle = message.getData();
		if (MessageUtil.showMessageError(this.activity, Toast.LENGTH_LONG, bundle)) {
			return;
		}
		//组公司别
		Map<String, PubCompany> mapCompany = (Map<String, PubCompany>) ((MapForSerializable) bundle.getSerializable("mapCompany")).getMap();
		Iterator<String> iterator = mapCompany.keySet().iterator();
		List<PubCompany> alPubCompany = new ArrayList<PubCompany>();
		while (iterator.hasNext()) {
			alPubCompany.add(mapCompany.get(iterator.next()));
		}
		//提供公司别数组给配适器
		this.companyAdapter = new CompanyAdapter(this.activity, this.listView, alPubCompany);
		this.spinnerCompany.setAdapter(this.companyAdapter);
	}
}
