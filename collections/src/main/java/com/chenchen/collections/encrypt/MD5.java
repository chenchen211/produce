package com.chenchen.collections.encrypt;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 */

public class MD5 {
    /**
     * 获取字符串的md5值
     * @param string 要加密的字符串
     * @return md5值
     */
    @NonNull
    public static String md5(String string) {
        // MessageDigest专门用于加密的类
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] result = messageDigest.digest(string.getBytes()); // 得到加密后的字符组数

            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                int num = b & 0xff; // 这里的是为了将原本是byte型的数向上提升为int型，从而使得原本的负数转为了正数
                String hex = Integer.toHexString(num); //这里将int型的数直接转换成16进制表示
                //16进制可能是为1的长度，这种情况下，需要在前面补0，
                if (hex.length() == 1) {
                    sb.append(0);
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取16位MD5值
     * @param string 要加密的字符串
     * @return 16位md5值
     */
    public static String miniMd5(String string){
        String s = md5(string);
        if(TextUtils.isEmpty(s)){
            return "";
        }
        return s.substring(8,24);
    }
    /**
     * 获得文件的MD5值
     * @param file 要加密的文件
     * @return 文件的md5值
     */
    @NonNull
    public static String md5(File file) {
        if (file == null || !file.isFile() || !file.exists()) {
            return "";
        }
        FileInputStream in = null;
        String result = "";
        byte buffer[] = new byte[8192];
        int len;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
            byte[] bytes = md5.digest();

            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null!=in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * md5多次加密
     * @param string 要加密的字符串
     * @param times 加密次数
     * @return 32位加密字符串
     */
    @NonNull
    public static String md5(String string, int times) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        String md5 = md5(string);
        for (int i = 0; i < times - 1; i++) {
            md5 = md5(md5);
        }
        return md5(md5);
    }
}