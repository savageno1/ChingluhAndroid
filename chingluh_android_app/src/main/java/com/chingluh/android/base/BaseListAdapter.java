package com.chingluh.android.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Ray on 2016/05/10.
 */
public abstract class BaseListAdapter extends BaseAdapter{
	protected LayoutInflater layoutInflater;
	protected List<BaseModel> alModel;

	public BaseListAdapter(Context context,List<BaseModel> alModel){
		this.layoutInflater=LayoutInflater.from(context);
		this.alModel=alModel;
		//model列表排序
		Collections.sort(alModel);
	}

	@Override
	public int getCount(){
		return alModel.size();
	}

	@Override
	public Object getItem(int position){
		return alModel.get(position);
	}

	@Override
	public long getItemId(int position){
		return position;
	}

	@Override
	public abstract View getView(int position,View convertView,ViewGroup parent);

	public abstract void addItem(BaseModel model);

	public int getPosition(BaseModel model){
		return alModel.indexOf(model);
	}
}
