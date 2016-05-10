package com.chingluh.android.data.holder;

import android.view.View;
import android.widget.TextView;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseViewHolder;

/**
 * Created by Ray on 2016/05/10.
 */
public class UfhViewHolder implements BaseViewHolder{
	private TextView textViewCode;
	private TextView textViewQty;

	public UfhViewHolder(View viewRoot){
		this.textViewCode=(TextView)viewRoot.findViewById(R.id.textViewUfhCode);
		this.textViewQty=(TextView)viewRoot.findViewById(R.id.textViewUfhQty);
	}

	public TextView getTextViewCode(){
		return textViewCode;
	}

	public void setTextViewCode(TextView textViewCode){
		this.textViewCode=textViewCode;
	}

	public TextView getTextViewQty(){
		return textViewQty;
	}

	public void setTextViewQty(TextView textViewQty){
		this.textViewQty=textViewQty;
	}
}
