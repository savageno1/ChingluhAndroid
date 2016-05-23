package com.chingluh.android.util;

import com.chingluh.android.app.AppData;
import com.chingluh.android.config.AppConfig;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.net.SocketException;

/**
 * Created by Ray on 2016/05/06.
 */
public class FtpUtil{
	private static FtpUtil ftpUtilInstance=null;
	private FTPClient ftpClient;
	private String url;
	private int port;
	private String userName;
	private String userPassword;

	private FtpUtil(){
		ftpClient=new FTPClient();
	}

	public static FtpUtil getInstance(){
		if(ftpUtilInstance==null){
			ftpUtilInstance=new FtpUtil();
		}
		return ftpUtilInstance;
	}

	public boolean initFTPSetting(String url,int port,String userName,String userPassword){
		this.url=url;
		this.port=port;
		this.userName=userName;
		this.userPassword=userPassword;
		int reply;
		try{
			ftpClient.connect(url,port);
			ftpClient.enterLocalPassiveMode();
			ftpClient.login(userName,userPassword);
			reply=ftpClient.getReplyCode();
			if(!(FTPReply.isPositiveCompletion(reply))){
				ftpClient.disconnect();
				return false;
			}
			return true;
		}catch(SocketException exception){
			exception.printStackTrace();
			return false;
		}catch(IOException exception){
			exception.printStackTrace();
			return false;
		}
	}

	public boolean changePath(String path){
		try{
			if(!ftpClient.changeWorkingDirectory(path)){
				return false;
			}
			return true;
		}catch(Exception exception){
			exception.printStackTrace();
			return false;
		}
	}

	public FTPFile[] getFiles(){
		FTPFile[] aFTPFile={};
		try{
			aFTPFile=ftpClient.listFiles();
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return aFTPFile;
	}

	public String[] getFileNames(){
		String[] aFileName={};
		try{
			aFileName=ftpClient.listNames();
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return aFileName;
	}
}
