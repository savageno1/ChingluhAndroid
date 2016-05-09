package com.chingluh.android.activity.withoutlogin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseActivityWithoutLogin;

/**
 * Created by Ray on 2016/05/06.
 */
public class ScanLabelActivity extends BaseActivityWithoutLogin{
	public View.OnClickListener onClickListener=new View.OnClickListener(){
		@Override
		public void onClick(View v){
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanlabel_set);
	}
}
