package com.chenchen.collections.encrypt;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 各种字符串加密方法
 */

public class StringEncrypt {

    public static String MD5_32(String strSrc)
    {
        MessageDigest md = null;
        String strDes = null;
        String encName = "MD5";
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String MD5_16(String strSrc)
    {
        MessageDigest md = null;
        String strDes = null;
        String encName = "MD5";
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
            strDes = strDes.substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String SHA256(String strSrc)
    {
        MessageDigest md = null;
        String strDes = null;
        String encName = "SHA-256";
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String SHA1(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        String encName = "SHA-1";
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String Hmac_MD5(String data, String key) {
        byte[] keyBytes = key.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacMD5");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacMD5");
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (InvalidKeyException e) {
            return null;
        }
        byte[] rawHmac = mac.doFinal(data.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : rawHmac) {
            sb.append(byteToHexString(b));
        }
        return sb.toString();
    }

    public static String Hmac_SHA1(String data, String key) {
        byte[] keyBytes = key.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (InvalidKeyException e) {
            return null;
        }
        byte[] rawHmac = mac.doFinal(data.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : rawHmac) {
            sb.append(byteToHexString(b));
        }
        return sb.toString();
    }

    public static String Hmac_SHA256(String data, String key) {
        byte[] keyBytes = key.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (InvalidKeyException e) {
            return null;
        }
        byte[] rawHmac = mac.doFinal(data.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : rawHmac) {
            sb.append(byteToHexString(b));
        }
        return sb.toString();
    }

    public static String Hmac_SHA384(String data, String key)
    {
        byte[] keyBytes = key.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA384");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA384");
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (InvalidKeyException e) {
            return null;
        }
        byte[] rawHmac = mac.doFinal(data.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : rawHmac) {
            sb.append(byteToHexString(b));
        }
        return sb.toString();
    }

    public static String Hmac_SHA512(String data, String key) {
        byte[] keyBytes = key.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA512");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA512");
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (InvalidKeyException e) {
            return null;
        }
        byte[] rawHmac = mac.doFinal(data.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : rawHmac) {
            sb.append(byteToHexString(b));
        }
        return sb.toString();
    }

    private static String byteToHexString(byte ib) {
        char[] Digit = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4 & 0xF)];
        ob[1] = Digit[(ib & 0xF)];
        String s = new String(ob);
        return s;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = Integer.toHexString(bts[i] & 0xFF);
            if (tmp.length() == 1) {
                des = des + "0";
            }
            des = des + tmp;
        }
        return des;
    }
}
