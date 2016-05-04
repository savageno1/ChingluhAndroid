package com.chingluh.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseModel;
import com.chingluh.android.base.BaseViewHolder;
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
			TextView textViewCompanyId=(TextView)convertView.findViewById(R.id.textViewCompanyId);
			TextView textViewCompanySplit=(TextView)convertView.findViewById(R.id.textViewCompanySplit);
			TextView textViewCompanyName=(TextView)convertView.findViewById(R.id.textViewCompanyName);
			PubCompany pubCompany=(PubCompany)this.getItem(position);
			textViewCompanyId.setText(pubCompany.getCompanyId());
			textViewCompanySplit.setText(":");
			textViewCompanyName.setText(pubCompany.getCompanyName());
		}
		if((convertView!=null)&&(convertView.getTag() instanceof PubCompanyViewHolder)){
			updateView((PubCompanyViewHolder)convertView.getTag(),(PubCompany)getItem(position));
		}
		return convertView;
	}

	public void updateView(BaseViewHolder baseViewHolder,BaseModel baseModel){
		if((baseViewHolder!=null)&(baseModel!=null)){
			PubCompanyViewHolder pubCompanyViewHolder=(PubCompanyViewHolder)baseViewHolder;
			PubCompany pubCompany=(PubCompany)baseModel;
			TextView textViewCompanyId=pubCompanyViewHolder.getTextViewCompanyId();
			TextView textViewCompanySplit=pubCompanyViewHolder.getTextViewCompanySplit();
			TextView textViewCompanyName=pubCompanyViewHolder.getTextViewCompanyName();
			textViewCompanyId.setVisibility(View.VISIBLE);
			textViewCompanyId.setText(pubCompany.getCompanyId());
			textViewCompanySplit.setVisibility(View.VISIBLE);
			textViewCompanySplit.setText(":");
			textViewCompanyName.setVisibility(View.VISIBLE);
			textViewCompanyName.setText(pubCompany.getCompanyName());
		}
	}
}
