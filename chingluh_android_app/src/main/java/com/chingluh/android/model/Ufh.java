package com.chingluh.android.model;

import com.chingluh.android.base.BaseModel;

/**
 * Created by Ray on 2016/05/10.
 */
public class Ufh extends BaseModel{
	private String ufhCode;
	private int ufhQty;

	public Ufh(String ufhCode){
		super(ufhCode);
		//
		this.ufhCode=ufhCode;
		ufhQty=0;
	}

	@Override
	public int compareTo(Object another){
		int iRtn=0;
		Ufh ufh=(Ufh)another;
		iRtn=this.ufhCode.compareTo(ufh.getUfhCode());
		return iRtn;
	}

	public String getUfhCode(){
		return ufhCode;
	}

	public int getUfhQty(){
		return ufhQty;
	}

	public void setUfhQty(int ufhQty){
		this.ufhQty=ufhQty;
	}

	public void addUfhQty(int ufhQty){
		this.ufhQty+=ufhQty;
	}

	public void reduceUfhQty(int ufhQty){
		this.ufhQty-=ufhQty;
	}
}
