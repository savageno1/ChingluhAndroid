package com.chingluh.android.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class BaseSpinnerAdapter extends BaseAdapter implements OnScrollListener{
	protected LayoutInflater layoutInflater;
	protected int scrollState=OnScrollListener.SCROLL_STATE_IDLE;
	protected List<BaseModel> alModel;
	protected List<Runnable> alRunnable=new ArrayList<Runnable>();
	private ListView listView;

	public BaseSpinnerAdapter(Context context,ListView listView,List<BaseModel> alModel){
		this.layoutInflater=LayoutInflater.from(context);
		this.alModel=alModel;
		this.listView=listView;
		this.listView.setOnScrollListener(this);
		//model列表排序
		Collections.sort(alModel);
	}

	@Override
	public int getCount(){
		return this.alModel.size();
	}

	@Override
	public Object getItem(int position){
		return this.alModel.get(position);
	}

	@Override
	public long getItemId(int position){
		return position;
	}

	@Override
	public abstract View getView(int position,View convertView,ViewGroup parent);

	@Override
	public void onScrollStateChanged(AbsListView view,int scrollState){
		this.scrollState=scrollState;
		if(scrollState==OnScrollListener.SCROLL_STATE_IDLE){
			//滚动停止
			synchronized(this.alRunnable){
				final Iterator<Runnable> iterator=this.alRunnable.iterator();
				while(iterator.hasNext()){
					iterator.next().run();
					iterator.remove();
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView arg0,int arg1,int arg2,int arg3){
		// TODO Auto-generated method stub
	}
}
