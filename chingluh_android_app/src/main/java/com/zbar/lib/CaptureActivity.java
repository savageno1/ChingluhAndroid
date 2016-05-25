package com.zbar.lib;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chingluh.android.R;
import com.zbar.lib.camera.CameraManager;
import com.zbar.lib.decode.CaptureActivityHandler;
import com.zbar.lib.decode.InactivityTimer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 浣滆�: 闄堟稕(1076559197@qq.com)
 * <p>
 * 鏃堕棿: 2014骞�鏈�鏃�涓嬪崍12:25:31
 * <p>
 * 鐗堟湰: V_1.0.0
 * <p>
 * 鎻忚堪: 鎵弿鐣岄潰
 */
public class CaptureActivity extends Activity implements Callback{
	private static final float BEEP_VOLUME=0.50f;
	private static final long VIBRATE_DURATION=200L;
	private final OnCompletionListener beepListener=new OnCompletionListener(){
		public void onCompletion(MediaPlayer mediaPlayer){
			mediaPlayer.seekTo(0);
		}
	};
	boolean flag=true;
	private CaptureActivityHandler handler;
	private boolean hasSurface;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private boolean vibrate;
	private int x=0;
	private int y=0;
	private int cropWidth=0;
	private int cropHeight=0;
	private RelativeLayout mContainer=null;
	private RelativeLayout mCropLayout=null;
	private boolean isNeedCapture=false;

	public boolean isNeedCapture(){
		return isNeedCapture;
	}

	public void setNeedCapture(boolean isNeedCapture){
		this.isNeedCapture=isNeedCapture;
	}

	public int getX(){
		return x;
	}

	public void setX(int x){
		this.x=x;
	}

	public int getY(){
		return y;
	}

	public void setY(int y){
		this.y=y;
	}

	public int getCropWidth(){
		return cropWidth;
	}

	public void setCropWidth(int cropWidth){
		this.cropWidth=cropWidth;
	}

	public int getCropHeight(){
		return cropHeight;
	}

	public void setCropHeight(int cropHeight){
		this.cropHeight=cropHeight;
	}

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qr_scan);
		// 鍒濆鍖�CameraManager
		CameraManager.init(getApplication());
		hasSurface=false;
		inactivityTimer=new InactivityTimer(this);
		mContainer=(RelativeLayout)findViewById(R.id.capture_containter);
		mCropLayout=(RelativeLayout)findViewById(R.id.capture_crop_layout);
		ImageView mQrLineView=(ImageView)findViewById(R.id.capture_scan_line);
		TranslateAnimation mAnimation=new TranslateAnimation(TranslateAnimation.ABSOLUTE,0f,TranslateAnimation.ABSOLUTE,0f,TranslateAnimation.RELATIVE_TO_PARENT,0f,TranslateAnimation.RELATIVE_TO_PARENT,0.9f);
		mAnimation.setDuration(1500);
		mAnimation.setRepeatCount(-1);
		mAnimation.setRepeatMode(Animation.REVERSE);
		mAnimation.setInterpolator(new LinearInterpolator());
		mQrLineView.setAnimation(mAnimation);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume(){
		super.onResume();
		SurfaceView surfaceView=(SurfaceView)findViewById(R.id.capture_preview);
		SurfaceHolder surfaceHolder=surfaceView.getHolder();
		if(hasSurface){
			initCamera(surfaceHolder);
		}else{
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		playBeep=true;
		AudioManager audioService=(AudioManager)getSystemService(AUDIO_SERVICE);
		if(audioService.getRingerMode()!=AudioManager.RINGER_MODE_NORMAL){
			playBeep=false;
		}
		initBeepSound();
		vibrate=true;
	}

	@Override
	protected void onPause(){
		super.onPause();
		if(handler!=null){
			handler.quitSynchronously();
			handler=null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy(){
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder){
		try{
			CameraManager.get().openDriver(surfaceHolder);
			Point point=CameraManager.get().getCameraResolution();
			int width=point.y;
			int height=point.x;
			int x=mCropLayout.getLeft()*width/mContainer.getWidth();
			int y=mCropLayout.getTop()*height/mContainer.getHeight();
			int cropWidth=mCropLayout.getWidth()*width/mContainer.getWidth();
			int cropHeight=mCropLayout.getHeight()*height/mContainer.getHeight();
			setX(x);
			setY(y);
			setCropWidth(cropWidth);
			setCropHeight(cropHeight);
			// 璁剧疆鏄惁闇�鎴浘
			setNeedCapture(true);
		}catch(IOException ioe){
			return;
		}catch(RuntimeException e){
			return;
		}
		if(handler==null){
			handler=new CaptureActivityHandler(CaptureActivity.this);
		}
	}

	private void initBeepSound(){
		if(playBeep&&mediaPlayer==null){
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer=new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);
			AssetFileDescriptor file=getResources().openRawResourceFd(R.raw.beep);
			try{
				mediaPlayer.setDataSource(file.getFileDescriptor(),file.getStartOffset(),file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME,BEEP_VOLUME);
				mediaPlayer.prepare();
			}catch(IOException e){
				mediaPlayer=null;
			}
		}
	}

	protected void light(){
		if(flag==true){
			flag=false;
			// 寮�棯鍏夌伅
			CameraManager.get().openLight();
		}else{
			flag=true;
			// 鍏抽棯鍏夌伅
			CameraManager.get().offLight();
		}
	}

	public void handleDecode(String result){
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		byte[] b;
		try{
			b=result.getBytes("SJIS");
			int i=0;
		}catch(UnsupportedEncodingException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		for (int i = 0; i < b.length; i++) {
		////			if(b[])
		//		}
		//		try {
		//			result=new String(,"GBK2312");
		//		} catch (UnsupportedEncodingException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
		//handler.sendEmptyMessage(R.id.restart_preview);
		if(!result.equals("")){
			Intent resultIntent=new Intent();
			Bundle bundle=new Bundle();
			bundle.putString("result",result);
			resultIntent.putExtras(bundle);
			this.setResult(RESULT_OK,resultIntent);
		}else{
			Toast.makeText(CaptureActivity.this,"Scan failed!",Toast.LENGTH_SHORT).show();
		}
		CaptureActivity.this.finish();
	}

	private void playBeepSoundAndVibrate(){
		if(playBeep&&mediaPlayer!=null){
			mediaPlayer.start();
		}
		if(vibrate){
			Vibrator vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder){
		if(!hasSurface){
			hasSurface=true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder){
		hasSurface=false;
	}

	public Handler getHandler(){
		return handler;
	}
}