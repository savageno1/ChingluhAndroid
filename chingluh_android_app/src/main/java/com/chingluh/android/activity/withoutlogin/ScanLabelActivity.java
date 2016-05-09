package com.chingluh.android.activity.withoutlogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseActivityWithoutLogin;

/**
 * Created by Ray on 2016/05/09.
 */
public class ScanLabelActivity extends BaseActivityWithoutLogin{
	private TabHost tabHostScanlabel;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanlabel);
		//
		tabHostScanlabel=(TabHost)findViewById(R.id.tabHostScanlabel);
		Intent intentSet=new Intent(this,ScanLabelSetActivity.class);
		Intent intent6B=new Intent(this,ScanLabel6BActivity.class);
		Intent intent6C=new Intent(this,ScanLabel6CActivity.class);
		//
		TabHost.TabSpec tabSpecSet=tabHostScanlabel.newTabSpec(getString(R.string.TabHost_Set_Label)).setIndicator(getString(R.string.TabHost_Set_Label)).setContent(intentSet);
		TabHost.TabSpec tabSpec6B=tabHostScanlabel.newTabSpec(getString(R.string.TabHost_6B_Label)).setIndicator(getString(R.string.TabHost_6B_Label)).setContent(intent6B);
		TabHost.TabSpec tabSpec6C=tabHostScanlabel.newTabSpec(getString(R.string.TabHost_6C_Label)).setIndicator(getString(R.string.TabHost_6C_Label)).setContent(intent6C);
		//
		tabHostScanlabel.addTab(tabSpecSet);
		tabHostScanlabel.addTab(tabSpec6B);
		tabHostScanlabel.addTab(tabSpec6C);
		//
		tabHostScanlabel.setCurrentTab(0);
	}
}
