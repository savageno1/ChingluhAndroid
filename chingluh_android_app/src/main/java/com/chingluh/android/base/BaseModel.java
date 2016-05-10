package com.chingluh.android.base;

/**
 * Created by Ray on 2016/05/04.
 */
public abstract class BaseModel implements Comparable{
	private String id;

	public BaseModel(String id){
		this.id=id;
	}

	@Override
	public int compareTo(Object another){
		int iRtn=0;
		BaseModel ufh=(BaseModel)another;
		iRtn=this.id.compareTo(ufh.getId());
		return iRtn;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id=id;
	}

	@Override
	public boolean equals(Object another){
		if(this.id.equals(((BaseModel)another).getId())){
			return true;
		}
		return false;
	}
}
