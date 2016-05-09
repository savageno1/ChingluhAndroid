package com.chingluh.android.activity.withoutlogin;

import android.os.Bundle;
import android.view.View;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseActivityWithoutLogin;
import com.chingluh.android.thread.ScanLabelCloseThread;
import com.chingluh.android.thread.ScanLabelOpenThread;

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
				case R.id.buttonScanLabelClose:
					new Thread(new ScanLabelCloseThread(ScanLabelActivity.this)).start();
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanlabel);
		//
		findViewById(R.id.buttonScanLabelOpen).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabelRead).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabelSet).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabelClose).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabel6B).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabel6C).setOnClickListener(onClickListener);
	}
}
