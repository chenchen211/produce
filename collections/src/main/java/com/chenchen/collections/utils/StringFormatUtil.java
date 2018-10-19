package com.chenchen.collections.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

/**
 * 修改字符串中部分文字的颜色
 */
public class StringFormatUtil {
    private SpannableStringBuilder spBuilder;
    private int start = 0, end = 0;
    private static StringFormatUtil instance;


    public static StringFormatUtil getInstance(){
        if(instance == null){
            instance = new StringFormatUtil();
        }
        return  instance;
    }

    /**
     *
     * @param wholeStr 全部文字
     * @param highlightStr 改变颜色的文字
     * @param color 要改变部分文字的颜色
     */
    public StringFormatUtil fillColor(String wholeStr, String highlightStr, int color){
        if(!TextUtils.isEmpty(wholeStr)&&!TextUtils.isEmpty(highlightStr)){
            if(wholeStr.contains(highlightStr)){
                /*
                 *  返回highlightStr字符串wholeStr字符串中第一次出现处的索引
                 */
                start=wholeStr.indexOf(highlightStr);
                end=start+highlightStr.length();
            }else{
                return null;
            }
        }else{
            return null;
        }
        spBuilder=new SpannableStringBuilder(wholeStr);
        CharacterStyle charaStyle=new ForegroundColorSpan(color);

        spBuilder.setSpan(charaStyle, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringBuilder getResult(){
        return spBuilder;
    }
}
