package com.chingluh.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseModel;
import com.chingluh.android.base.BaseSpinnerAdapter;
import com.chingluh.android.data.holder.PubCompanyViewHolder;
import com.chingluh.android.model.PubCompany;

import java.util.List;

public class PubCompanyAdapter extends BaseSpinnerAdapter{
	public PubCompanyAdapter(Context context,ListView listView,List<BaseModel> alModel){
		super(context,listView,alModel);
	}

	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.spinner_item_company,parent,false);
			TextView textViewCompanyName=(TextView)convertView.findViewById(R.id.textViewCompanyName);
			PubCompany pubCompany=(PubCompany)this.getItem(position);
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
}
