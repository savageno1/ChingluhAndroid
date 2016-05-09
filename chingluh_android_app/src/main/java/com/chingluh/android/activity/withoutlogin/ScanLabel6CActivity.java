package com.chingluh.android.activity.withoutlogin;

import android.os.Bundle;
import android.view.View;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseActivityWithoutLogin;

/**
 * Created by Ray on 2016/05/06.
 */
public class ScanLabel6CActivity extends BaseActivityWithoutLogin{
	public View.OnClickListener onClickListener=new View.OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanlabel_6c);
	}
}
