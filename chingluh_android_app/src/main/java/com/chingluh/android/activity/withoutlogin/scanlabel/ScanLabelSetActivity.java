package com.chingluh.android.activity.withoutlogin.scanlabel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
		Spinner spinnerDMin=(Spinner)findViewById(R.id.spinner_ScanLabel_DMin);
		ArrayAdapter<CharSequence> arrayAdapterDMin=ArrayAdapter.createFromResource(this,R.array.Spinner_Items_Frequencies,android.R.layout.simple_spinner_item);
		arrayAdapterDMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerDMin.setAdapter(arrayAdapterDMin);
		spinnerDMin.setSelection(0,true);
		//
		Spinner spinnerDMax=(Spinner)findViewById(R.id.spinner_ScanLabel_DMax);
		ArrayAdapter<CharSequence> arrayAdapterDMax=ArrayAdapter.createFromResource(this,R.array.Spinner_Items_Frequencies,android.R.layout.simple_spinner_item);
		arrayAdapterDMax.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerDMax.setAdapter(arrayAdapterDMax);
		spinnerDMax.setSelection(arrayAdapterDMax.getCount()-1,true);
		//
		Spinner spinnerPower=(Spinner)findViewById(R.id.spinner_ScanLabel_Power);
		ArrayAdapter<CharSequence> arrayAdapterPower=ArrayAdapter.createFromResource(this,R.array.Spinner_Items_Powers,android.R.layout.simple_spinner_item);
		arrayAdapterPower.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPower.setAdapter(arrayAdapterPower);
		spinnerPower.setSelection(arrayAdapterPower.getCount()-1,true);
		//
		findViewById(R.id.buttonScanLabelWrite).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabelBack).setOnClickListener(onClickListener);
	}
}
