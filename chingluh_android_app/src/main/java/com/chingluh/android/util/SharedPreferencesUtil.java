package com.chingluh.android.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Ray on 2016/06/23.
 */
public class SharedPreferencesUtil{
	private final static String SETTING="Setting";

	private static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences(SETTING,Context.MODE_PRIVATE);
	}

	private static SharedPreferences.Editor getEditor(Context context){
		return getSharedPreferences(context).edit();
	}

	public static void putValue(Context context,String key,int value){
		SharedPreferences.Editor editor=getEditor(context);
		editor.putInt(key,value);
		editor.commit();
	}

	public static void putValue(Context context,String key,boolean value){
		SharedPreferences.Editor editor=getEditor(context);
		editor.putBoolean(key,value);
		editor.commit();
	}

	public static void putValue(Context context,String key,float value){
		SharedPreferences.Editor editor=getEditor(context);
		editor.putFloat(key,value);
		editor.commit();
	}

	public static void putValue(Context context,String key,String value){
		SharedPreferences.Editor editor=getEditor(context);
		editor.putString(key,value);
		editor.commit();
	}

	public static void putValue(Context context,String key,Set<String> value){
		SharedPreferences.Editor editor=getEditor(context);
		editor.putStringSet(key,value);
		editor.commit();
	}

	public static int getValue(Context context,String key,int defValue){
		return getSharedPreferences(context).getInt(key,defValue);
	}

	public static boolean getValue(Context context,String key,boolean defValue){
		return getSharedPreferences(context).getBoolean(key,defValue);
	}

	public static float getValue(Context context,String key,float defValue){
		return getSharedPreferences(context).getFloat(key,defValue);
	}

	public static String getValue(Context context,String key,String defValue){
		return getSharedPreferences(context).getString(key,defValue);
	}

	public static Set<String> getValue(Context context,String key,Set<String> defValue){
		return getSharedPreferences(context).getStringSet(key,defValue);
	}
}
