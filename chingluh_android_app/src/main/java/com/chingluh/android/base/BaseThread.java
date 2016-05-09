package com.chingluh.android.base;

import android.app.Activity;
import android.os.Handler;

import com.chingluh.android.handler.LoginHandler;

/**
 * Created by Ray on 2016/05/09.
 */
public abstract class BaseThread implements  Runnable{
	protected Activity activity;
	protected Handler handler;
	public BaseThread(Activity activity,Handler handler){
		this.activity=activity;
		this.handler=handler;
	}
}
