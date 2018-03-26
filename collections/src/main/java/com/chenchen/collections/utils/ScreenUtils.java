package com.chenchen.collections.utils;

import android.content.Context;

/**
 * 屏幕尺寸获取工具
 */

public class ScreenUtils {
	private ScreenUtils()
	{
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static int getScreenWidth(Context context) {
		return DensityUtils.getDisplayMetrics(context).widthPixels;
	}

	public static int getScreenHeight(Context context) {
		return DensityUtils.getDisplayMetrics(context).heightPixels;
	}

	/**
	 * 获取状态栏高度
	 * @param context 上下文
	 * @return 状态栏高度
	 */
	public static int getStatusHeight(Context context) {
		int statusHeight = -1;
		try {
			Class clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

}

