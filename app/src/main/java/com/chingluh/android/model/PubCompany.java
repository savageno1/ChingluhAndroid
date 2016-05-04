package com.chingluh.android.model;

import com.chingluh.android.base.BaseModel;

public class PubCompany implements BaseModel{
	private String companyId;
	private String companyName;

	public String getCompanyId(){
		return this.companyId;
	}

	public void setCompanyId(String companyId){
		this.companyId=companyId;
	}

	public String getCompanyName(){
		return this.companyName;
	}

	public void setCompanyName(String companyName){
		this.companyName=companyName;
	}

	@Override
	public int compareTo(Object another){
		int iRtn=0;
		PubCompany pubCompany=(PubCompany)another;
		iRtn=this.companyId.compareTo(pubCompany.getCompanyId());
		return iRtn;
	}
}
