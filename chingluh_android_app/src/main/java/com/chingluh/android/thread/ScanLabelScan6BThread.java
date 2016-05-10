package com.chingluh.android.thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.chingluh.android.base.BaseThread;
import com.chingluh.android.config.AppConfig;
import com.chingluh.android.handler.ScanLabelHandler;
import com.chingluh.android.util.UfhUtil;

import static com.chingluh.android.handler.ScanLabelHandler.OptType.Close;
import static com.chingluh.android.handler.ScanLabelHandler.OptType.Scan6B;

/**
 * Created by Ray on 2016/05/09.
 */
public class ScanLabelScan6BThread extends BaseThread{
	public ScanLabelScan6BThread(Activity activity){
		super(activity,new ScanLabelHandler(activity));
	}

	@Override
	public void run(){
		Message message=handler.obtainMessage();
		Bundle bundle=new Bundle();
		try{
			int iRtn=UfhUtil.UhfGetData.CloseUhf();
			bundle.putSerializable("OptType",Scan6B);
			bundle.putInt("OptRtn",iRtn);
		}catch(Exception exception){
			bundle.putString(AppConfig.STR_EXCEPTION,exception.getMessage());
		}
		message.setData(bundle);
		this.handler.sendMessage(message);
	}
}
