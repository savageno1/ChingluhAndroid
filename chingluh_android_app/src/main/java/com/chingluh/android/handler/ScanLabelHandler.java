package com.chingluh.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.chingluh.android.R;
import com.chingluh.android.adapter.UfhAdapter;
import com.chingluh.android.base.BaseModel;
import com.chingluh.android.model.Ufh;
import com.chingluh.android.util.MessageUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScanLabelHandler extends Handler{
	private static Activity activity;
	private static UfhAdapter adapter;
	private static List<BaseModel> alModel;
	private static ListView listView;

	public ScanLabelHandler(Activity activity){
		super(activity.getMainLooper());
		this.activity=activity;
		this.listView=(ListView)activity.findViewById(R.id.listView);
		//
		initScanHandler();
	}

	private static void initScanHandler(){
		if(listView.getAdapter()==null){
			if(adapter==null){
				if(alModel==null){
					alModel=new ArrayList<>();
				}
				adapter=new UfhAdapter(activity,alModel);
			}
			listView.setAdapter(adapter);
		}
	}

	@Override
	public void handleMessage(Message message){
		Bundle bundle=message.getData();
		if(MessageUtil.showMessageError(this.activity,Toast.LENGTH_LONG,bundle)){
			return;
		}
		String strWelcomeMsg=null;
		switch((OptType)bundle.get("OptType")){
			case Open:
				if(bundle.getInt("OptRtn")==0){
					strWelcomeMsg=this.activity.getString(R.string.MessageUtil_Message_Ufh_Opened)+"!";
					(activity.findViewById(R.id.buttonScanLabelOpen)).setEnabled(false);
					(activity.findViewById(R.id.buttonScanLabelSet)).setEnabled(true);
					(activity.findViewById(R.id.buttonScanLabelClose)).setEnabled(true);
					(activity.findViewById(R.id.buttonScanLabel6B)).setEnabled(true);
					(activity.findViewById(R.id.buttonScanLabel6C)).setEnabled(true);
				}else{
					strWelcomeMsg=this.activity.getString(R.string.MessageUtil_Message_Ufh_Open_Fail)+"!";
					(activity.findViewById(R.id.buttonScanLabelOpen)).setEnabled(true);
					(activity.findViewById(R.id.buttonScanLabelSet)).setEnabled(false);
					(activity.findViewById(R.id.buttonScanLabelClose)).setEnabled(false);
					(activity.findViewById(R.id.buttonScanLabel6B)).setEnabled(false);
					(activity.findViewById(R.id.buttonScanLabel6C)).setEnabled(false);
				}
				break;
			case Close:
				if(bundle.getInt("OptRtn")==0){
					strWelcomeMsg=this.activity.getString(R.string.MessageUtil_Message_Ufh_Closed)+"!";
					(activity.findViewById(R.id.buttonScanLabelOpen)).setEnabled(true);
					(activity.findViewById(R.id.buttonScanLabelSet)).setEnabled(false);
					(activity.findViewById(R.id.buttonScanLabelClose)).setEnabled(false);
					(activity.findViewById(R.id.buttonScanLabel6B)).setEnabled(false);
					(activity.findViewById(R.id.buttonScanLabel6C)).setEnabled(false);
				}else{
					strWelcomeMsg=this.activity.getString(R.string.MessageUtil_Message_Ufh_Close_Fail)+"!";
					(activity.findViewById(R.id.buttonScanLabelOpen)).setEnabled(false);
					(activity.findViewById(R.id.buttonScanLabelSet)).setEnabled(true);
					(activity.findViewById(R.id.buttonScanLabelClose)).setEnabled(true);
					(activity.findViewById(R.id.buttonScanLabel6B)).setEnabled(true);
					(activity.findViewById(R.id.buttonScanLabel6C)).setEnabled(true);
				}
				break;
			case Scan6B:
			case Scan6C:
				try{
					String[] aStrLabel=bundle.getStringArray("OptRtnStrArr");
					for(String strLabel : aStrLabel){
						if(strLabel.trim().length()==0){
							continue;
						}
						Ufh ufh=new Ufh(strLabel);
						adapter.addItem(ufh);
						int i=adapter.getPosition(ufh);
						listView.smoothScrollToPosition(i);
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			default:
				strWelcomeMsg=null;
		}
		if(strWelcomeMsg!=null){
			MessageUtil.showMessage(this.activity,strWelcomeMsg,Toast.LENGTH_SHORT);
		}
	}

	public enum OptType implements Serializable{
		Open,Read,Set,Close,R6B,R6C,Scan6B,Scan6C
	}
}
