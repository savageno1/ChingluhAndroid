package com.chingluh.android.activity.withoutlogin.scanlabel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseActivityWithoutLogin;
import com.chingluh.android.thread.ScanLabelCloseThread;
import com.chingluh.android.thread.ScanLabelOpenThread;
import com.chingluh.android.thread.ScanLabelScan6BThread;
import com.chingluh.android.thread.ScanLabelScan6CThread;

/**
 * Created by Ray on 2016/05/06.
 */
public class ScanLabelActivity extends BaseActivityWithoutLogin{
	public View.OnClickListener onClickListener=new View.OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
				case R.id.buttonScanLabelOpen:
					new Thread(new ScanLabelOpenThread(ScanLabelActivity.this)).start();
					break;
				case R.id.buttonScanLabelSet:
					Intent intentSacnLabelSet=new Intent(ScanLabelActivity.this,ScanLabelSetActivity.class);
					startActivity(intentSacnLabelSet);
					break;
				case R.id.buttonScanLabelClose:
					new Thread(new ScanLabelCloseThread(ScanLabelActivity.this)).start();
					break;
				case R.id.buttonScanLabel6B:
					new Thread(new ScanLabelScan6BThread(ScanLabelActivity.this)).start();
					break;
				case R.id.buttonScanLabel6C:
					new Thread(new ScanLabelScan6CThread(ScanLabelActivity.this)).start();
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanlabel);
		//
		//		findViewById(R.id.buttonScanLabelOpen).setEnabled(true);
		//		findViewById(R.id.buttonScanLabelRead).setEnabled(false);
		//		findViewById(R.id.buttonScanLabelSet).setEnabled(false);
		//		findViewById(R.id.buttonScanLabelClose).setEnabled(false);
		//		findViewById(R.id.buttonScanLabel6B).setEnabled(false);
		//		findViewById(R.id.buttonScanLabel6C).setEnabled(false);
		//
		findViewById(R.id.buttonScanLabelOpen).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabelSet).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabelClose).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabel6B).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabel6C).setOnClickListener(onClickListener);
	}
}
