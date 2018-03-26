package com.chenchen.collections.encrypt;

import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/7/24.
 */

public class RSASignature {

    private static final String SIGN_TYPE_RSA = "RSA";

    private static final String SIGN_TYPE_RSA2 = "RSA2";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private static String sign_type="RSA2";

    private static String charset = "UTF-8";

    private static String publicKey=
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu18JuUrY6nY/i6LSZTXT" +
            "+joOXg/lAJUIFRRPGd4L0OsNHu7KugVf7fvZ4fq15dDLyiud8pgdZA/fugfLN5NY" +
            "bBt8yX4mfARksMkyEjZTQC0kBmhqNLNyNW6ze0SWWbmpS6NGBNFJ8wEaeXkaE72q" +
            "eaJBOWIvDtyzXUq+YWCoODY26b53oNaxp0iLAwUIF5bI7kmEJ66nrUty9sJIoObf" +
            "lC8uRmjGMJx1U9vSk7RxKr1AisIFxwC5ZunRgVGPiEPDDVQJ1sC+aZJGOebKo3Uh" +
            "QF21JKAarYCXyc9rE39Bx+UggHbnm/668VwwRkWnnR4+N+wPi7zt2NHwgiu/23T1" +
            "iwIDAQAB";

    private static String privateKey=
            "MIIEogIBAAKCAQEAu18JuUrY6nY/i6LSZTXT+joOXg/lAJUIFRRPGd4L0OsNHu7K" +
            "ugVf7fvZ4fq15dDLyiud8pgdZA/fugfLN5NYbBt8yX4mfARksMkyEjZTQC0kBmhq" +
            "NLNyNW6ze0SWWbmpS6NGBNFJ8wEaeXkaE72qeaJBOWIvDtyzXUq+YWCoODY26b53" +
            "oNaxp0iLAwUIF5bI7kmEJ66nrUty9sJIoObflC8uRmjGMJx1U9vSk7RxKr1AisIF" +
            "xwC5ZunRgVGPiEPDDVQJ1sC+aZJGOebKo3UhQF21JKAarYCXyc9rE39Bx+UggHbn" +
            "m/668VwwRkWnnR4+N+wPi7zt2NHwgiu/23T1iwIDAQABAoIBAFdhS+SYji5RdPMG" +
            "vL2sa63fE0I0gWRTHBDQhs8WaUZFx7msPOihhwoyEHs9c0S4qcEftColXFeEu55C" +
            "8jd5xJut+fTxmrrtRZPYUDyEDzD3nDxMx3LKWLGobZVH+CHh3pzCiO3IOIdV9WW+" +
            "3zVjlzPgQjCjpDR3IkKYj85TyD9l4+G5eZZ/MMh0DxySWht2Lc8pGoZA/eF3FUFa" +
            "pwdIUY3XH2B4O75qnE0uhhV1bhQkrdJh9FpjsJGOrWQW5Ms/m1WhWeb3tROBiog+" +
            "S68kqrn3N0EoUUDTx9T2K+tLipncLutSPyQluAK+e7cM4mwM/6UXj0BpF5MEn2C5" +
            "kesetOkCgYEA8QGxnHBzJ1JFb1YI82QATGMOKZi23yr/T95sSo/S84csHs/ew176" +
            "DlLjzmzvM+dxtPNTXMy6fwAcZlOgDWmaGxU+R8j8u9IemvUmlQqzpksKepDTTdD8" +
            "hAFMOx202ysXLGWUEXyAk6AA1Vhzm2gS62HXT1NlowMkOmAfvQ0KwWUCgYEAxwcj" +
            "tCXnXJnw055uc43zkXs78VWakGS0bkaQidkqBzAtffs0JTj+NQzbJDUDlBOnVNHG" +
            "ImMNFiF838a9Z0PB3d4YsY74jaRjRs6SwtK5X5AiKNeIdth4T8ihMzbrbcd1JssP" +
            "YHoQrzml8wKWm0wgFzBh7P2lgB9tyAvxVgAwZC8CgYB8DNgj4smS8sjkns8qoE17" +
            "A/11MbLnOdWCgcURt+foC6qNDYfm3gsttkKlrPKOr9GaRyigeox/9Emp7d9TKAj3" +
            "ab7N6kkUT/oK3qaGTqTbsoJpRgRNaIWhWJ2pTAgcS5i49Gv7eC8iTVhAeC/BTRd3" +
            "6ruNjCqjdml+Vp3fjEf99QKBgFZ4Epn88cS4mPnH1mLb5FtreAKE148uQXm7rKZH" +
            "NExFMS6Pyfr2BPOVb0wOwExAMa3XKcbc092ulOtAFB/eP0cebAoQfIpFRmCH9Rkx" +
            "phoPq9ektIQ1zieTmf1/Oc/LHWnKRRb8UW1flWq70CUOcM7CVXk6RgIhJXgJQEPF" +
            "90A/AoGAZHjGaV4WhCbAKMi8Rrj8ZstPhe4VliaENYAWx8UJKxfnCm/BmOLwUZXu" +
            "S3fTNsc1maHFFvN1ZezzwMvAg/QBtL5EzrURwUCXCkOpuEsvP7Zo6LINFKHtp346" +
            "SzMoRVj7HNgoEchhek36/o/5YBV3WKo8HHQpDKZ3WCdiQHLBUnY=";

    public static String getSign_type() {
        return sign_type;
    }

    public static void setSign_type(String sign_type) {
        RSASignature.sign_type = sign_type;
    }

    public static String getCharset() {
        return charset;
    }

    public static void setCharset(String charset) {
        RSASignature.charset = charset;
    }

    public static String getPublicKey() {
        return publicKey;
    }

    public static void setPublicKey(String publicKey) {
        RSASignature.publicKey = publicKey;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public static void setPrivateKey(String privateKey) {
        RSASignature.privateKey = privateKey;
    }

    /**
     * RSA/RSA2 生成签名
     *
     * @param map 包含签名参数
     * @return 签名结果
     * @throws Exception
     */
    public static String sign(Map<String,String> map){
        String content = getSignContent(map);
        System.out.println("请求参数生成的字符串为:" + content);
        return sign(content);
    }
    /**
     * RSA/RSA2 生成签名
     *
     * @param content 代签名字符串
     * @return 签名结果
     */
    private static String sign(String content){
        PrivateKey priKey;
        java.security.Signature signature;
        try {
            if (SIGN_TYPE_RSA.equals(sign_type)) {
                priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));
                signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            } else if (SIGN_TYPE_RSA2.equals(sign_type)) {
                priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));
                signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
            } else {
                throw new Exception("不是支持的签名类型 : : signType=" + sign_type);
            }
            signature.initSign(priKey);
            if (TextUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            String sign = new String(Base64.encode(signed, Base64.DEFAULT));
            System.out.println("签名结果:" + sign);
            return sign;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 验签方法
     */
    public static boolean vertify(Map<String,String> map){
        String sign = map.remove("sign");
        String content = getSignContent(map);
        return vertify(content,sign);
    }

    private static boolean vertify(String content,String sign){
        try {
            java.security.Signature signature;
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            if (SIGN_TYPE_RSA.equals(sign_type)) {
                signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            } else if (SIGN_TYPE_RSA2.equals(sign_type)) {
                signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
            } else {
                throw new Exception("不是支持的签名类型 : signType=" + sign_type);
            }
            signature.initVerify(pubKey);

            if (TextUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decode(sign.getBytes(),Base64.DEFAULT));
        }catch (Exception e){
            return false;
        }
    }
    private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins){
        if (ins == null || TextUtils.isEmpty(algorithm)) {
            return null;
        }

        try {
            KeyFactory keyFactory =  KeyFactory.getInstance(algorithm,"BC");
            byte[] encodedKey = readText(ins).getBytes();
            encodedKey = Base64.decode(encodedKey,Base64.DEFAULT);

            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm,"BC");

            StringWriter writer = new StringWriter();
            io(new InputStreamReader(ins), writer, -1);

            byte[] encodedKey = writer.toString().getBytes();

            encodedKey = Base64.decode(encodedKey,Base64.DEFAULT);

            return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把参数合成成字符串
     *
     * @param map 代签名的参数
     * @return 代签名字符串
     */
    private static String getSignContent(Map<String, String> map) {
        StringBuilder content = new StringBuilder();
        Map<String, String> sortMap = sortMapByKey(map);

        Set<Map.Entry<String, String>> entrySet = sortMap.entrySet();
        for (Map.Entry<String,String> entry : entrySet) {
            if(!entry.getKey().equals("sign") && !entry.getKey().equals("sign_type") && !TextUtils.isEmpty(entry.getValue())){
                content.append( entry.getKey());
                content.append("=");
                content.append(entry.getValue());
                content.append("&");
            }
        }
        content.deleteCharAt(content.length()-1);
        return content.toString();
    }

    private static String readText(InputStream ins) throws IOException {
        Reader reader = new InputStreamReader(ins);
        StringWriter writer = new StringWriter();

        io(reader, writer, -1);
        return writer.toString();
    }

    private static void io(Reader in, Writer out, int bufferSize) throws IOException {
        if (bufferSize == -1) {
            bufferSize = DEFAULT_BUFFER_SIZE >> 1;
        }

        char[] buffer = new char[bufferSize];
        int amount;

        while ((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }
    }
    /**
     * 使用 Map按key进行排序
     * @param map 要排序的字符串
     * @return 返回类型为TreeMap
     */
    private static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, String> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        sortMap.putAll(map);

        return sortMap;
    }
}
