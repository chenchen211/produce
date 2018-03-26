package com.chenchen.collections.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.util.ArrayList;

/**
 * 本地分享工具
 */

public class ShareUtil {
    /**
     * 图文分析
     * @param context 上下文
     * @param text 要分享的文字
     */
    public void shareText(Activity context, String text){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
    /**
     * 图文分析
     * @param context 上下文
     * @param uri 要分享的图片Uri
     */
    public void shareImg(Activity context, Uri uri){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(intent, "分享到"));
    }
    /**
     * 图文分析
     * @param context 上下文
     * @param uris 要分享的图片Uri
     */
    public void shareImgs(Activity context, ArrayList<Uri> uris){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,uris);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    /**
     * 图文分析
     * @param context 上下文
     * @param text 要分享的文字
     * @param uri 要分享的图片Uri
     */
    public void shareTextAndImg(Activity context, String text,Uri uri){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("*/*");
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
