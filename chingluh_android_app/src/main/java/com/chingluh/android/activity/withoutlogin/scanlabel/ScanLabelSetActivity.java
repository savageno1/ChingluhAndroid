package com.chingluh.android.activity.withoutlogin.scanlabel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.chingluh.android.R;
import com.chingluh.android.thread.ScanLabelOpenThread;

/**
 * Created by Ray on 2016/05/06.
 */
public class ScanLabelSetActivity extends Activity{
	public View.OnClickListener onClickListener=new View.OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
				case R.id.buttonScanLabelWrite:
					new Thread(new ScanLabelOpenThread(ScanLabelSetActivity.this)).start();
					break;
				case R.id.buttonScanLabelBack:
					finish();
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanlabel_set);
		//
		findViewById(R.id.buttonScanLabelWrite).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabelBack).setOnClickListener(onClickListener);
	}
}
