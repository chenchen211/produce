package com.chenchen.collections.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by Administrator on 2016/11/7.
 */

public class DensityUtils {

    private DensityUtils()
    {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取手机的屏幕像素密度
     * @param context 上下文
     * @return 屏幕密度
     */
    public static float getDensity(@NonNull Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.density;
    }

    public static DisplayMetrics getDisplayMetrics(@NonNull Context context){
        return context.getResources().getDisplayMetrics();
    }

    public static int dp2px(@NonNull Context context, float dpVal) {
        return (int) TypedValue.applyDimension(1, dpVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(@NonNull Context context, float spVal)
    {
        return (int)TypedValue.applyDimension(2,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static float px2dp(@NonNull Context context, float pxVal)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return pxVal / scale;
    }

    public static float px2sp(@NonNull Context context, float pxVal){
        return pxVal / context.getResources().getDisplayMetrics().scaledDensity;
    }
}
