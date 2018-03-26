package com.chenchen.collections.utils;

import com.chenchen.collections.juhe.JuheUtil;
import com.chenchen.collections.juhe.entity.IDCard;

/**
 * 校验身份证号是否合法
 */

public class IDCardValidate {

    public static boolean validate(String no)
    {
        // 对身份证号进行长度等简单判断
        if (no == null || no.length() != 18 || !no.matches("\\d{17}[0-9X]"))
        {
            return false;
        }
        // 1-17位相乘因子数组
        int[] factor = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        // 18位随机码数组
        char[] random = "10X98765432".toCharArray();
        // 计算1-17位与相应因子乘积之和
        int total = 0;
        for (int i = 0; i < 17; i++)
        {
            total += Character.getNumericValue(no.charAt(i)) * factor[i];
        }
        // 判断随机码是否相等
        return random[total % 11] == no.charAt(17);
    }

    public static void validateFromInternet(String no, JuheUtil.OnResultListener<IDCard> listener){
        if(listener == null){
            throw new NullPointerException("listener不能为空");
        }
        // 对身份证号进行长度等简单判断
        if (no == null || no.length() != 18 || !no.matches("\\d{17}[0-9X]")){
            listener.onError("身份证号长度不合法");
        }
        JuheUtil.getInstance().checkIDCard(no,listener);
    }

}
