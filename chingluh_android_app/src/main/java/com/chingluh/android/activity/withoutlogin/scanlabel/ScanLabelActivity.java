package com.chingluh.android.activity.withoutlogin.scanlabel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.chingluh.android.R;
import com.chingluh.android.adapter.UfhAdapter;
import com.chingluh.android.base.BaseActivityWithoutBindin;
import com.chingluh.android.thread.scanlabel.ScanLabelCloseThread;
import com.chingluh.android.thread.scanlabel.ScanLabelOpenThread;
import com.chingluh.android.thread.scanlabel.ScanLabelScanThread;

/**
 * Created by Ray on 2016/05/06.
 */
public class ScanLabelActivity extends BaseActivityWithoutBindin{
	private boolean bScaning=false;
	private Thread scanLabelScanThread;
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
					if(scanLabelScanThread!=null){
						ScanLabelScanThread.Off();
						scanLabelScanThread=null;
					}
					new Thread(new ScanLabelCloseThread(ScanLabelActivity.this)).start();
					clear();
					break;
				case R.id.buttonScanLabelClear:
					clear();
					break;
				case R.id.buttonScanLabel6C:
					if(bScaning){
						ScanLabelScanThread.Off();
						scanLabelScanThread=null;
					}else{
						ScanLabelScanThread.On();
						scanLabelScanThread=new Thread(new ScanLabelScanThread(ScanLabelActivity.this,(Button)v));
						scanLabelScanThread.start();
					}
					bScaning=!bScaning;
					if(bScaning){
						((Button)findViewById(R.id.buttonScanLabel6C)).setText(getString(R.string.Button_Scanlabel_Stop_Text));
						//						findViewById(R.id.buttonScanLabelSet).setEnabled(false);
					}else{
						((Button)findViewById(R.id.buttonScanLabel6C)).setText(getString(R.string.Button_Scanlabel_6C_Text));
						//						findViewById(R.id.buttonScanLabelSet).setEnabled(true);
					}
					break;
				case R.id.buttonScanLabelViewSet:
					if(ScanLabelScanThread.getViewSet()){
						ScanLabelScanThread.ViewOff();
						((Button)findViewById(R.id.buttonScanLabelViewSet)).setText(R.string.Button_Scanlabel_ViewSet_Off);
						clear();
					}else{
						ScanLabelScanThread.ViewOn();
						((Button)findViewById(R.id.buttonScanLabelViewSet)).setText(R.string.Button_Scanlabel_ViewSet_On);
					}
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanlabel);
		//
		findViewById(R.id.buttonScanLabelOpen).setEnabled(true);
		findViewById(R.id.buttonScanLabelSet).setEnabled(false);
		findViewById(R.id.buttonScanLabelClose).setEnabled(false);
		findViewById(R.id.buttonScanLabelClear).setEnabled(true);
		findViewById(R.id.buttonScanLabel6C).setEnabled(false);
		findViewById(R.id.buttonScanLabelViewSet).setEnabled(true);
		//
		findViewById(R.id.buttonScanLabelOpen).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabelSet).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabelClose).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabelClear).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabel6C).setOnClickListener(onClickListener);
		findViewById(R.id.buttonScanLabelViewSet).setOnClickListener(onClickListener);
		//
		if(ScanLabelScanThread.getViewSet()){
			((Button)findViewById(R.id.buttonScanLabelViewSet)).setText(R.string.Button_Scanlabel_ViewSet_On);
		}else{
			((Button)findViewById(R.id.buttonScanLabelViewSet)).setText(R.string.Button_Scanlabel_ViewSet_Off);
		}
	}

	@Override
	protected void onPause(){
		if(scanLabelScanThread!=null){
			ScanLabelScanThread.Off();
			scanLabelScanThread=null;
		}
		new Thread(new ScanLabelCloseThread(ScanLabelActivity.this)).start();
		super.onPause();
	}

	private void clear(){
		Adapter adapter=((ListView)findViewById(R.id.listView)).getAdapter();
		if(adapter!=null){
			UfhAdapter ufhAdapter=(UfhAdapter)((ListView)findViewById(R.id.listView)).getAdapter();
			ufhAdapter.clearItem();
		}
		((TextView)findViewById(R.id.textViewScanLabelUnlockedQty)).setText("0");
		((TextView)findViewById(R.id.textViewScanLabelLockedQty)).setText("0");
	}
}
