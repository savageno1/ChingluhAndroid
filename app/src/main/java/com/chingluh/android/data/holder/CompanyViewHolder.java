package com.chingluh.android.data.holder;

import com.chingluh.android.R;
import android.view.View;
import android.widget.TextView;

public class CompanyViewHolder {
	private TextView textViewCompanyId;
	private TextView textViewCompanySplit;
	private TextView textViewCompanyName;

	public CompanyViewHolder(View viewRoot) {
		this.textViewCompanyId = (TextView) viewRoot.findViewById(R.id.textViewCompanyId);
		this.textViewCompanySplit = (TextView) viewRoot.findViewById(R.id.textViewCompanySplit);
		this.textViewCompanyName = (TextView) viewRoot.findViewById(R.id.textViewCompanyName);
	}

	public TextView getTextViewCompanyId() {
		return this.textViewCompanyId;
	}

	public void setTextViewCompanyId(TextView textViewCompanyId) {
		this.textViewCompanyId = textViewCompanyId;
	}

	public TextView getTextViewCompanySplit() {
		return this.textViewCompanySplit;
	}

	public void setTextViewCompanySplit(TextView textViewCompanySplit) {
		this.textViewCompanySplit = textViewCompanySplit;
	}

	public TextView getTextViewCompanyName() {
		return this.textViewCompanyName;
	}

	public void setTextViewCompanyName(TextView textViewCompanyName) {
		this.textViewCompanyName = textViewCompanyName;
	}
}
