package com.chingluh.android.model;

import com.chingluh.android.base.BaseModel;

public class PubCompany extends BaseModel{
	private String companyId;
	private String companyName;

	public PubCompany(String companyId){
		//必须实现父类的Id赋值
		super(companyId);
		//
		this.companyId=companyId;
	}

	public String getCompanyId(){
		return this.companyId;
	}

	public String getCompanyName(){
		return this.companyName;
	}

	public void setCompanyName(String companyName){
		this.companyName=companyName;
	}
}
