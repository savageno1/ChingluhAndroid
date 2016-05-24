package com.chingluh.android.util;

import android.app.Activity;
import android.app.ProgressDialog;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

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

	public boolean downloadWithProgressDialog(Activity activity,long lRemoteFileSize,String remotePath,String remoteFileName,String localPath,String localFileName){
		final long lFileSize=lRemoteFileSize;
		final File fileLocal=new File(localPath+"/"+localFileName);
		final ProgressDialog progressDialog=new ProgressDialog(activity);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(true);
		progressDialog.setIndeterminate(false);
		progressDialog.show();
		new Timer().schedule(new TimerTask(){
			@Override
			public void run(){
				if(fileLocal.length()>=lFileSize){
					progressDialog.cancel();
					cancel();
				}
				progressDialog.setProgress((int)(fileLocal.length()*100/lFileSize));
			}
		},0,500);
		return download(remotePath,remoteFileName,localPath,localFileName);
	}

	public boolean download(String remotePath,String remoteFileName,String localPath,String localFileName){
		try{
			//创建本地目录
			File fileLocalPath=new File(localPath);
			if(!fileLocalPath.exists()){
				fileLocalPath.mkdir();
			}
			//
			if(remotePath!=null&&!changePath(remotePath)){
				return false;
			}
			//
			File fileLocalFile=new File(localPath+"/"+localFileName);
			//
			fileLocalFile.delete();
			//
			OutputStream outputStream=new FileOutputStream(fileLocalFile);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.retrieveFile(remoteFileName,outputStream);
			outputStream.close();
		}catch(Exception exception){
			exception.printStackTrace();
			return false;
		}
		return true;
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

	@Deprecated
	public long getRemoteFileSize(String remotePath,String remoteFileName){
		long lRtn=0;
		try{
			if(remotePath!=null){
				if(!ftpClient.printWorkingDirectory().equals(remotePath)){
					if(!changePath(remotePath)){
						throw new Exception("");
					}
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return lRtn;
	}

	public void close(){
		try{
			ftpClient.logout();
			ftpClient.disconnect();
		}catch(IOException exception){
			exception.printStackTrace();
		}
	}
}
