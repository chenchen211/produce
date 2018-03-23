package com.chenchen.collections.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 地址数据解析类
 */
public class Data4Address {
    private List<String > province = new ArrayList<>();
    private String address;
    private HashMap<String,List<String>> city = new HashMap<>();
    private HashMap<String,List<String>> county = new HashMap<>();
    private static Data4Address instance;

    private Data4Address() {
    }

    private Data4Address(Context context) {
        BufferedInputStream bis=null;
        ByteArrayOutputStream bos = null;
        try {
            bis=new BufferedInputStream(context.getAssets().open("adjson.json"));
            byte[] b = new byte[1024];
            bos = new ByteArrayOutputStream();
            int len;
            while ((len = bis.read(b)) != -1) {
                bos.write(b,0,len);
            }
            address = bos.toString();
            JSONObject obj = new JSONObject(address);
            JSONArray array = obj.getJSONArray("address");
            for (int i = 0; i < array.length(); i++) {
                getProvince(array,i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bos.close();
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Data4Address getInstance(Context context) {
        if (instance == null) {
            instance = new Data4Address(context);
        }
        return instance;
    }

    private void getProvince(JSONArray array, int i) throws JSONException {
        JSONObject proObj = array.getJSONObject(i);
        province.add(proObj.getString("name"));
        JSONArray arr = proObj.getJSONArray("children");//得到市级
        List<String> listCity = new ArrayList<>();
        if(arr.length()>0) {
            for (int j = 0; j < arr.length(); j++) {
                String city = arr.getJSONObject(j).getString("name");
                listCity.add(city);
                List<String> countyList = new ArrayList<>();
                JSONArray countyArr = arr.getJSONObject(j).getJSONArray("children");//得到区级
                if (countyArr.length() > 0) {
                    for (int k = 0; k < countyArr.length(); k++) {
                        JSONObject countyObj = countyArr.getJSONObject(k);
                        String county = countyObj.getString("name");
                        countyList.add(county);
                    }
                    this.county.put(city, countyList);
                }
            }
            this.city.put(province.get(i),listCity);
        }
    }

    public List<String > getProvince() {
        return province;
    }

    public List<String> getCityByProvice(String province) {
        return city.get(province);
    }

    public List<String> getCountyByCity(String city) {
        return county.get(city);
    }
}
