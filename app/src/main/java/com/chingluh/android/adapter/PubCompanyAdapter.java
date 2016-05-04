package com.chingluh.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseModel;
import com.chingluh.android.data.holder.PubCompanyViewHolder;
import com.chingluh.android.model.PubCompany;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PubCompanyAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
	//	public PubCompanyAdapter(Context context,ListView listView,List<BaseModel> alModel){
	//		super(context,listView,alModel);
	//	}
	private LayoutInflater layoutInflater;
	private int scrollState=AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
	private List<Runnable> alRunnable=new ArrayList<Runnable>();
	private List<BaseModel> alModel;
	private ListView listView;

	public PubCompanyAdapter(Context context,ListView listView,List<BaseModel> alModel){
		this.layoutInflater=LayoutInflater.from(context);
		this.alModel=alModel;
		this.listView=listView;
		this.listView.setOnScrollListener(this);
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
	public View getView(int position,View convertView,ViewGroup parent){
//		Log.d("getView","position:"+position+" convertView:"+convertView.getTag().getClass()+" parent:"+parent.getTag().getClass());
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.spinner_item_company,parent,false);
			TextView textViewCompanyId=(TextView)convertView.findViewById(R.id.textViewCompanyId);
			TextView textViewCompanyName=(TextView)convertView.findViewById(R.id.textViewCompanyName);
			PubCompany pubCompany=(PubCompany)this.getItem(position);
			textViewCompanyId.setText(pubCompany.getCompanyId());
			textViewCompanyName.setText(pubCompany.getCompanyName());
		}
		if(convertView.getTag()==null){
			PubCompanyViewHolder pubCompanyViewHolder=new PubCompanyViewHolder(convertView);
			convertView.setTag(pubCompanyViewHolder);
		}
		if(convertView.getTag() instanceof PubCompanyViewHolder){
			PubCompanyViewHolder pubCompanyViewHolder=(PubCompanyViewHolder)convertView.getTag();
			PubCompany pubCompany=(PubCompany)getItem(position);
			if((pubCompanyViewHolder!=null)&(pubCompany!=null)){
				TextView textViewCompanyName=pubCompanyViewHolder.getTextViewCompanyName();
				textViewCompanyName.setVisibility(View.VISIBLE);
				textViewCompanyName.setText(pubCompany.getCompanyName());
			}
		}
		return convertView;
	}

	@Override
	public void onScrollStateChanged(AbsListView view,int scrollState){
		this.scrollState=scrollState;
		if(scrollState==AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
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
