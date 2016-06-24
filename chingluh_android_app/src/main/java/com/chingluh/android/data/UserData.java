package com.chingluh.android.data;

public class UserData{
	private String userId;
	private String password;
	private String realName;
	private String ipAddress;
	private String bindMac;

	public String getUserId(){
		return this.userId;
	}

	public void setUserId(String userId){
		this.userId=userId;
	}

	public String getPassword(){
		return this.password;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public String getIpAddress(){
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress){
		this.ipAddress=ipAddress;
	}

	public String getRealName(){
		return this.realName;
	}

	public void setRealName(String realName){
		this.realName=realName;
	}

	public String getBindMac(){
		return bindMac;
	}

	public void setBindMac(String bindMac){
		this.bindMac=bindMac;
	}
}
