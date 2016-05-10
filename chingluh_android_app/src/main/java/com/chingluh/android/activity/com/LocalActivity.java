package com.chingluh.android.activity.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chingluh.android.R;
import com.chingluh.android.activity.withoutlogin.scanlabel.ScanLabelActivity;

/**
 * Created by Ray on 2016/05/06.
 */
public class LocalActivity extends Activity{
	private View.OnClickListener onClickListener=new View.OnClickListener(){
		@Override
		public void onClick(View view){
			switch(view.getId()){
				case R.id.buttonScanLabel:
					Intent intentScanLabel=new Intent(LocalActivity.this,ScanLabelActivity.class);
					startActivity(intentScanLabel);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local);
		//
		Button buttonScanLabel=(Button)findViewById(R.id.buttonScanLabel);
		buttonScanLabel.setOnClickListener(onClickListener);
	}
}
