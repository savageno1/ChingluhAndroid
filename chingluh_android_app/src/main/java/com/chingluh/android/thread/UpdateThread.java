package com.chingluh.android.thread;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by Ray on 2016/05/17.
 * Check Update
 */
public class UpdateThread implements Runnable{
	private Activity activity;
	private File file;

	public UpdateThread(Activity activity,File file){
		this.activity=activity;
		this.file=file;
	}

	@Override
	public void run(){
		Intent intent=new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
		activity.startActivity(intent);
	}
}
