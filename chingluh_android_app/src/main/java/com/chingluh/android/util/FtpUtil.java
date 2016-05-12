package com.chingluh.android.util;

import org.apache.commons.net.ftp.FTPClient;

/**
 * Created by Ray on 2016/05/06.
 */
public class FtpUtil{
	private static FtpUtil ftpUtilInstance=null;
	private FTPClient ftpClient;

	private FtpUtil(){
		ftpClient=new FTPClient();
	}

	public static FtpUtil getInstance(){
		if(ftpUtilInstance==null){
			ftpUtilInstance=new FtpUtil();
		}
		return ftpUtilInstance;
	}
}
