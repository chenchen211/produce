package com.chenchen.collections.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * SD卡工具
 */

public class SDCardUtils {
    private SDCardUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals( "mounted");
    }
    /**
     * 获取外部SD卡根路径
     * @return 路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            long availableBlocks = stat.getAvailableBlocks() - 4L;

            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0L;
    }

    public static long getFreeBytes(String filePath) {
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = stat.getAvailableBlocks() - 4L;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取根路径
     * @return 根路径
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }
}
