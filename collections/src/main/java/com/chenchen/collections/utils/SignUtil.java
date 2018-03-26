package com.chenchen.collections.utils;

import android.text.TextUtils;

import com.chenchen.collections.encrypt.MD5;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/**
 * MD5签名工具
 */

public class SignUtil {
	private String key;
	private static SignUtil instance;
	
	private SignUtil(){
		
	}
	public static SignUtil getInstance(){
		if(instance==null){
			instance = new SignUtil();
		}
		return instance;
	}
	
	public SignUtil setKey(String key) {
		this.key = key;
		return instance;
	}
	
    private String createSignString(Map<String,String> para){
        String signStr="";
        para=sortMapByKey(para);
        for (Map.Entry<String,String> entry : para.entrySet()) {
            if(!TextUtils.isEmpty(entry.getValue())){
                signStr += entry.getKey()+"="+entry.getValue()+"&";
            }
        }
        signStr += "key="+key;
        return signStr;
    }

    public String sign(Map<String,String> para){
        if(key == null || TextUtils.isEmpty(key)){
            throw new NullPointerException("key不能为空");
        }
        String signString = createSignString(para);
        return MD5.encode(signString).toUpperCase();
    }
    /**
     * 使用 Map按key进行排序
     * @param map 要排序的参数
     * @return 排序后的参数
     */
    private Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, String> sortMap = new TreeMap<>(new MapComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    private class MapComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }
}
