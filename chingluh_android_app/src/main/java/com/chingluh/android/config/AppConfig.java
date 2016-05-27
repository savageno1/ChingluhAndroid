package com.chingluh.android.config;

import android.os.Environment;

public class AppConfig{
	public static final String STR_EXCEPTION="exception";//
	public static final String FtpIpAddress="10.1.1.107";
	public static final int FtpPort=21;
	public static final String FtpUserId="ora";
	public static final String FtpPassword="ora084365";
	public static final String ApkFilePath="androidapk";
	public static final String ApkFileNamePrefix="com.chingluh.android";
	public static final String ApkFileNameSeparator="_v";
	public static final String ApkFileLocalPath=Environment.getExternalStorageDirectory().getPath()+"/chingluh";
	public static final String HttpUtilConnectString="http://172.17.2.21:8089/chingluh-web-server/";
}
