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
			switch(v.getId()){
				case R.id.buttonScanLabelOpen:/*打开*/
					break;
				case R.id.buttonScanLabelSet:/*设置*/
					break;
				case R.id.buttonScanLabelClose:/*关闭*/
					break;
				case R.id.buttonScanLabelRead:/*读取*/
					break;
				case R.id.buttonScanLabelStop:/*停止*/
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanlabel);
		//抓取按钮
		Button buttonScanLabelOpen=(Button)findViewById(R.id.buttonScanLabelOpen);/*打开*/
		Button buttonScanLabelSet=(Button)findViewById(R.id.buttonScanLabelSet);
		Button buttonScanLabelClose=(Button)findViewById(R.id.buttonScanLabelClose);/*关闭*/
		Button buttonScanLabelRead=(Button)findViewById(R.id.buttonScanLabelRead);/*读取*/
		Button buttonScanLabelStop=(Button)findViewById(R.id.buttonScanLabelStop);/*停止*/
		//按钮设置监听
		buttonScanLabelOpen.setOnClickListener(onClickListener);/*打开*/
		buttonScanLabelSet.setOnClickListener(onClickListener);/*设置*/
		buttonScanLabelClose.setOnClickListener(onClickListener);/*关闭*/
		buttonScanLabelRead.setOnClickListener(onClickListener);/*读取*/
		buttonScanLabelStop.setOnClickListener(onClickListener);/*停止*/
	}
}
