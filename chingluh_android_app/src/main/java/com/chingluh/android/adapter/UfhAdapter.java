package com.chingluh.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseListAdapter;
import com.chingluh.android.base.BaseModel;
import com.chingluh.android.data.holder.UfhViewHolder;
import com.chingluh.android.model.Ufh;

import java.util.Collections;
import java.util.List;

/**
 * Created by Ray on 2016/05/10.
 */
public class UfhAdapter extends BaseListAdapter{
	public UfhAdapter(Context context,List<BaseModel> alModel){
		super(context,alModel);
	}

	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		if(convertView==null){
			try{
				convertView=layoutInflater.inflate(R.layout.list_item_ufh,parent,false);
				TextView textViewUfhCode=(TextView)convertView.findViewById(R.id.textViewUfhCode);
				TextView textViewUfhQty=(TextView)convertView.findViewById(R.id.textViewUfhQty);
				Ufh ufh=(Ufh)this.getItem(position);
				textViewUfhCode.setText(ufh.getUfhCode());
				textViewUfhQty.setText(String.valueOf(ufh.getUfhQty()));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(convertView.getTag()==null){
			UfhViewHolder ufhViewHolder=new UfhViewHolder(convertView);
			convertView.setTag(ufhViewHolder);
		}
		if(convertView.getTag() instanceof UfhViewHolder){
			UfhViewHolder ufhViewHolder=(UfhViewHolder)convertView.getTag();
			Ufh ufh=(Ufh)getItem(position);
			if((ufhViewHolder!=null)&(ufh!=null)){
				TextView textViewUfhCode=(TextView)convertView.findViewById(R.id.textViewUfhCode);
				TextView textViewUfhQty=(TextView)convertView.findViewById(R.id.textViewUfhQty);
				textViewUfhCode.setText(ufh.getUfhCode());
				textViewUfhQty.setText(String.valueOf(ufh.getUfhQty()));
				textViewUfhCode.setVisibility(View.VISIBLE);
				textViewUfhQty.setVisibility(View.VISIBLE);
			}
		}
		return convertView;
	}

	@Override
	public void addItem(BaseModel model){
		Ufh ufh=(Ufh)model;
		if(!alModel.contains(ufh)){
			alModel.add(ufh);
			Collections.sort(alModel);//model列表排序
		}else{
			int idx=alModel.indexOf(ufh);
			ufh=(Ufh)(alModel.get(idx));
			ufh.addUfhQty(1);
		}
		notifyDataSetChanged();
	}

	@Override
	public void clearItem(){
		alModel.clear();
		notifyDataSetChanged();
	}
}
