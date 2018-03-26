package com.chenchen.collections.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 截屏工具
 */

public class SnapShotUtils {

    /**
     * 带状态栏截屏
     * @param activity Activity
     * @return 截图结果
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = ScreenUtils.getScreenWidth(activity);
        int height = ScreenUtils.getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        try {
            saveToPictures(bp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bp;
    }
    /**
     * 不带状态栏截屏
     * @param activity Activity
     * @return 截图结果
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = ScreenUtils.getScreenWidth(activity);
        int height = ScreenUtils.getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        try {
            saveToPictures(bp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bp;
    }

    /**
     * 保存到其他位置
     * @param bmp 要保存的bitmap
     * @param dirName 文件夹名
     * @param fileName 文件名
     * @throws IOException 文件读写异常
     */
    public static void saveToSD(Bitmap bmp, String dirName, String fileName) throws IOException {
        // 判断SD卡是否存在
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)) {
            File dir = new File(dirName);
            // 判断文件夹是否存在，不存在则创建
            if(!dir.exists()){
                boolean mkdir = dir.mkdir();
                if(!mkdir){
                    throw new FileNotFoundException("文件创建失败");
                }
            }
            File file = new File(dirName + fileName);
            // 判断文件是否存在，不存在则创建
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if(!newFile){
                    throw new FileNotFoundException("文件创建失败");
                }
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            // 第一参数是图片格式，第二个是图片质量，第三个是输出流
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            // 用完关闭
            fos.flush();
            fos.close();
        }
    }
    /**
     * 保存到图片文件夹
     * @param bmp 要保存的bitmap
     * @param fileName 新文件名
     * @throws IOException 文件读写异常
     */
    public static void saveToPicturesWithName(Bitmap bmp,String fileName) throws IOException {
        // 判断SD卡是否存在
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)) {
            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),fileName));
            // 第一参数是图片格式，第二个是图片质量，第三个是输出流
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            // 用完关闭
            fos.flush();
            fos.close();
        }
    }

    /**
     * 保存到图片文件夹
     * @param bmp 要保存的bitmap
     * @throws IOException 文件读写异常
     */
    public static void saveToPictures(Bitmap bmp) throws IOException {
        // 判断SD卡是否存在
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)) {
            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),System.currentTimeMillis()+".jpg"));
            // 第一参数是图片格式，第二个是图片质量，第三个是输出流
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            // 用完关闭
            fos.flush();
            fos.close();
        }
    }
}
