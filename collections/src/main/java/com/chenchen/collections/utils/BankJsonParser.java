package com.chenchen.collections.utils;

import android.content.Context;

import com.chenchen.collections.bean.Bank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class BankJsonParser {

    private static BankJsonParser instance;
    private String data;
    private HashMap<String,String> banks = new HashMap<>();
    private HashMap<String,String> temp = new HashMap<>();
    private Set<String> keySet;
    private Collection<String> values;

    private BankJsonParser(Context context){
        BufferedInputStream bis=null;
        ByteArrayOutputStream bos = null;
        try {
            bis=new BufferedInputStream(context.getAssets().open("bankData.json"));
            byte[] b = new byte[1024];
            bos = new ByteArrayOutputStream();
            int len;
            while ((len = bis.read(b)) != -1) {
                bos.write(b,0,len);
            }
            data = bos.toString();
            produceArrays();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static BankJsonParser getInstance(Context context){
        if(instance == null){
            instance = new BankJsonParser(context);
        }
        return instance;
    }

    /**
     * 将json数据生成集合
     */
    private void produceArrays(){
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bankObj = jsonArray.getJSONObject(i);
                banks.put(bankObj.getString("bin"),bankObj.getString("bankName"));
            }
            keySet = banks.keySet();
            values = banks.values();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据银行卡号获取银行卡类别
     * @param no 银行卡号
     * @return 银行卡信息
     */
    public Bank getBanknameByNo(String no){
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if(no.startsWith(next)){
                String name = banks.get(next);
                String[] split = name.split("-");
                int pos = name.indexOf("银行");
                name=name.substring(0,pos+2);

                Bank bank = new Bank();
                bank.setBankName(name.trim());
                bank.setBin(next);
                bank.setCardType(split[2]);
                return bank;
            }
        }
        return null;
    }
}
