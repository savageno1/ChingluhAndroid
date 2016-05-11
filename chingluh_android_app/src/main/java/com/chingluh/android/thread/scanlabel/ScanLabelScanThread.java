package com.chingluh.android.thread.scanlabel;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.widget.Button;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseThread;
import com.chingluh.android.config.AppConfig;
import com.chingluh.android.handler.ScanLabelHandler;
import com.chingluh.android.util.UfhUtil;

import java.util.Timer;
import java.util.TimerTask;

import static com.chingluh.android.handler.ScanLabelHandler.OptType.Scan6C;

/**
 * Created by Ray on 2016/05/09.
 */
public class ScanLabelScanThread extends BaseThread{
	public static Timer timer;
	private static boolean bCancel=false;
	private static Button button;

	public ScanLabelScanThread(Activity activity,Button button){
		super(activity,new ScanLabelHandler(activity));
		ScanLabelScanThread.button=button;
	}

	public static void On(){
		bCancel=false;
	}

	public static void Off(){
		bCancel=true;
	}

	@Override
	public void run(){
		try{
			if(timer==null){//开始感应
				UfhUtil.Set_sound(true);
				UfhUtil.SoundFlag=false;
				//
				timer=new Timer();
				timer.schedule(new TimerTask(){
					@Override
					public void run(){
						if(bCancel){
							Cancel();
						}
						String[] aStrLabel=null;
						switch(button.getId()){
							case R.id.buttonScanLabel6B:
								aStrLabel=UfhUtil.UhfGetData.Scan6B();
								break;
							case R.id.buttonScanLabel6C:
								aStrLabel=UfhUtil.UhfGetData.Scan6C();
								break;
						}
						if(aStrLabel==null){
							return;
						}
						Message message=handler.obtainMessage();
						Bundle bundle=new Bundle();
						try{
							bundle.putSerializable("OptType",Scan6C);
							bundle.putStringArray("OptRtnStrArr",aStrLabel);
						}catch(Exception exception){
							bundle.putString(AppConfig.STR_EXCEPTION,exception.getMessage());
						}
						message.setData(bundle);
						handler.sendMessage(message);
					}
				},0,100);
			}else{//停止感应
				Cancel();
			}
		}catch(Exception exception){
			Cancel();
			exception.printStackTrace();
		}
	}

	public void Cancel(){
		UfhUtil.Set_sound(false);
		if(timer!=null){
			timer.cancel();
			timer=null;
		}
	}
}
