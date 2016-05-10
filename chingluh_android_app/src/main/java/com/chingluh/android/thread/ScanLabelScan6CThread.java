package com.chingluh.android.thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.chingluh.android.base.BaseThread;
import com.chingluh.android.config.AppConfig;
import com.chingluh.android.handler.ScanLabelHandler;
import com.chingluh.android.util.UfhUtil;

import java.util.Timer;
import java.util.TimerTask;

import static com.chingluh.android.handler.ScanLabelHandler.OptType.Scan6B;
import static com.chingluh.android.handler.ScanLabelHandler.OptType.Scan6C;

/**
 * Created by Ray on 2016/05/09.
 */
public class ScanLabelScan6CThread extends BaseThread{
	public Timer timer;

	public ScanLabelScan6CThread(Activity activity){
		super(activity,new ScanLabelHandler(activity));
	}

	@Override
	public void run(){
		try{
			if(timer==null){
				timer=new Timer();
				timer.schedule(new TimerTask(){
					@Override
					public void run(){
						String[] aStrLabel=UfhUtil.UhfGetData.Scan6C();
						if(aStrLabel==null){
							return;
						}
						for(String strLabel : aStrLabel){
							if(strLabel.trim().length()==0){
								continue;
							}
							Message message=handler.obtainMessage();
							Bundle bundle=new Bundle();
							try{
								bundle.putSerializable("OptType",Scan6C);
								bundle.putString("OptRtnStr",strLabel);
							}catch(Exception exception){
								bundle.putString(AppConfig.STR_EXCEPTION,exception.getMessage());
							}
							message.setData(bundle);
							handler.sendMessage(message);
						}
					}
				},0,100);
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
}
