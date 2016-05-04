package com.chingluh.android.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;
import com.chingluh.android.app.AppData;

public class ConnUtil {
	private static Map<String, URL> HM_URL;
	private static String STR_BASE_URL_172 = "http://10.2.2.2:80/chingluh-web-server/";
	private static String STR_BASE_URL_192 = "http://192.168.0.253:8080/chingluh-web-server/";

	/**
	 * 取连接
	 * @param componentName
	 * @return
	 * @throws Exception
	 */
	private static HttpURLConnection getConnection(String componentName) throws Exception {
		if (ConnUtil.HM_URL == null) {
			ConnUtil.HM_URL = new HashMap<String, URL>();
		}
		if (!ConnUtil.HM_URL.containsKey(componentName)) {
			if (!AppData.getIpAddressV4().startsWith("192.168.0.")) {
				ConnUtil.HM_URL.put(componentName, new URL(ConnUtil.STR_BASE_URL_172 + componentName));
			} else {
				ConnUtil.HM_URL.put(componentName, new URL(ConnUtil.STR_BASE_URL_192 + componentName));
			}
		}
		URL url = ConnUtil.HM_URL.get(componentName);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		//		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Charset", "utf-8");
		connection.connect();
		return connection;
	}

	/**
	 * 取数据
	 * @param componentName
	 * @param hmParam
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	public static JSONObject getMsg(String componentName, HashMap<String, Object> hmParam) throws Exception {
		JSONObject joRtn = new JSONObject();
		//组参数数组
		StringBuilder sbParameter = new StringBuilder("paramVersion=0");
		Iterator<String> iterator = hmParam.keySet().iterator();
		while (iterator.hasNext()) {
			String strKey = iterator.next();
			sbParameter.append("&").append(strKey).append("=").append(URLEncoder.encode(hmParam.get(strKey).toString(), "utf-8"));
		}
		//取资料
		HttpURLConnection connection = getConnection(componentName);
		DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
		//要事先编码
		dataOutputStream.write(sbParameter.toString().getBytes());
		dataOutputStream.flush();
		//
		int i = connection.getResponseCode();
		//接收资料
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder sbRtn = new StringBuilder();
		String strReadLine = null;
		while ((strReadLine = bufferedReader.readLine()) != null) {
			sbRtn.append(strReadLine);
		}
		bufferedReader.close();
		connection.disconnect();
		//返回资料
		joRtn.put("joMsg", new JSONObject(sbRtn.toString()));
		return joRtn;
	}
}
