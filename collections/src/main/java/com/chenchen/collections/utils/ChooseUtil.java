package com.chenchen.collections.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * 图片选取工具
 */

public class ChooseUtil {

    private static final String TAG = "chenchen";

    private ChooseUtil(){
        throw new UnsupportedOperationException("不可创建对象");
    }

    /**
     * 从图库选择
     * @param context 当前activity
     * @param requestCode 请求码
     */
    public static void chooseImgFromPick(Activity context,int requestCode){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        context.startActivityForResult(intent, requestCode);
    }
    /**
     * 从相机选择
     * @param context 当前activity
     * @param filePath 相机拍摄要保存的路径
     * @param requestCode 请求码
     */
    public static void chooseImgFromCamera(Activity context,File filePath, int requestCode){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2);// 调用前置摄像头
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", false);// 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filePath));
        context.startActivityForResult(intent,requestCode);
    }
    /**
     * 从相机或相机选择
     * @param context 当前activity
     * @param filePath 相机拍摄要保存的路径
     * @param requestCode 请求码
     */
    public static void chooseImgFromChooser(Activity context,File filePath, int requestCode){
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUrl =Uri.fromFile(filePath);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
        Intent chooser = Intent.createChooser(intent1, "请选择");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});
        context.startActivityForResult(chooser, requestCode);
    }

    /**
     * 选择文件
     * @param context 当前activity
     * @param requestCode 请求码
     */
    public static  void chooseFile(Activity context,int requestCode){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        context.startActivityForResult(intent,requestCode);
    }
}
