package com.chenchen.collections.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/7.
 */

public class SPUtils {

	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;

	private static SPUtils instance;

	private SPUtils(Context context, String name) {
		preferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		editor = preferences.edit();
	}

	public static SPUtils getInstance(Context context,String name) {
		if (instance == null) {
			instance = new SPUtils(context,name);
		}
		return instance;
	}
	public void put(String key, Object object){
		if ((object instanceof String)){
			editor.putString(key, (String)object);
		} else if ((object instanceof Integer)){
			editor.putInt(key, ((Integer)object));
		} else if ((object instanceof Boolean)){
			editor.putBoolean(key, ((Boolean)object));
		} else if ((object instanceof Float)){
			editor.putFloat(key, ((Float)object));
		} else if ((object instanceof Long)){
			editor.putLong(key, ((Long)object));
		}else {
			editor.putString(key, object.toString());
		}
		SharedPreferencesCompat.apply(editor);
	}

	public Object get(String key, Object defaultObject){

		if ((defaultObject instanceof String)){
			return preferences.getString(key, (String)defaultObject);
		}if ((defaultObject instanceof Integer)){
			return preferences.getInt(key, ((Integer) defaultObject));
		}if ((defaultObject instanceof Boolean)){
			return preferences.getBoolean(key, ((Boolean)defaultObject));
		}if ((defaultObject instanceof Float)){
			return preferences.getFloat(key, ((Float)defaultObject));
		}if ((defaultObject instanceof Long)){
			return preferences.getLong(key, ((Long)defaultObject));
		}
		return null;
	}

	public void remove(String key){
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	public void clear(){
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

	public boolean contains(String key){
		return preferences.contains(key);
	}

	public Map<String, ?> getAll(Context context){
		return preferences.getAll();
	}

	private static class SharedPreferencesCompat{
		private static final Method sApplyMethod = findApplyMethod();

		private static Method findApplyMethod(){
			try{
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			}catch (NoSuchMethodException localNoSuchMethodException){
				return null;
			}
		}

		public static void apply(SharedPreferences.Editor editor){
			try{
				if (sApplyMethod != null){
					sApplyMethod.invoke(editor);
					return;
				}
			}catch (IllegalArgumentException localIllegalArgumentException) {
			}catch (IllegalAccessException localIllegalAccessException) {
			}catch (InvocationTargetException localInvocationTargetException) {
			}
			editor.commit();
		}
	}
}
