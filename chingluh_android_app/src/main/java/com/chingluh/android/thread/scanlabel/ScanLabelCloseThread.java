package com.chingluh.android.thread.scanlabel;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.chingluh.android.base.BaseThread;
import com.chingluh.android.config.AppConfig;
import com.chingluh.android.handler.ScanLabelHandler;
import com.chingluh.android.util.MapForSerializable;
import com.chingluh.android.util.UfhUtil;

import java.util.HashMap;
import java.util.Map;

import static com.chingluh.android.handler.ScanLabelHandler.OptType.Close;

/**
 * Created by Ray on 2016/05/09.
 */
public class ScanLabelCloseThread extends BaseThread{
	public ScanLabelCloseThread(Activity activity){
		super(activity,new ScanLabelHandler(activity));
	}

	@Override
	public void run(){
		Message message=handler.obtainMessage();
		Bundle bundle=new Bundle();
		try{
			int iRtn=UfhUtil.UhfGetData.CloseUhf();
			bundle.putSerializable("OptType",Close);
			bundle.putInt("OptRtn",iRtn);
		}catch(Exception exception){
			bundle.putString(AppConfig.STR_EXCEPTION,exception.getMessage());
		}
		message.setData(bundle);
		this.handler.sendMessage(message);
	}
}
