package com.chenchen.collections.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.chenchen.collections.http.HttpHelper;
import com.chenchen.collections.http.HttpService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 用于文件下载
 */
public final class Download {
    private static Download instance;
    private HttpService service;
    private String localPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    private String filePath;
    private String uploadUrl;

    private Download(Context context,String url) {
        if(context == null){
            throw new IllegalArgumentException("Context can not be null");
        }
        if(TextUtils.isEmpty(url)){
            throw new IllegalArgumentException("url can not be null");
        }
        try {
            URI uri = new URI(url);
            uploadUrl=url;
            service = HttpHelper.getInstance(context,"http://"+uri.getHost()+"/").getService(HttpService.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Download实例
     * @param context 上下文对象
     * @param url 要上传的地址
     * @return Download对象
     */
    public static Download getInstance(Context context,String url) {
        if (instance == null) {
            instance = new Download(context,url);
        }
        return instance;
    }

    public void download(@NonNull File file,final OnDownloadListener listener){
        if(TextUtils.isEmpty(uploadUrl)){
            throw new IllegalArgumentException("下载链接为空");
        }
        if(file.exists()){
            throw new IllegalArgumentException("文件已经存在");
        }
        if(listener == null){
            throw new IllegalArgumentException("OnDownloadListener为空");
        }
        filePath = file.getAbsolutePath();
        service.download(uploadUrl).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        write2Disk(response,listener);
                    }
                }).start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void download(@NonNull String name,OnDownloadListener listener){
        File file = new File(localPath + "/" + name);
        download(file,listener);
    }

    public void downloadWithFullPath(@NonNull String path,OnDownloadListener listener){
        download(new File(path),listener);
    }
    private void write2Disk(Response<ResponseBody> response,OnDownloadListener listener) {
        long totalSize = response.body().contentLength();
        long current = 0;
        listener.onStart(totalSize);
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            fos = new FileOutputStream(filePath);
            is = response.body().byteStream();
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len=is.read(buffer)) == -1){
                fos.write(buffer,0,len);
                current += len;
                listener.onProgress(current);
            }
            listener.onFinish(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fos != null){
                    fos.close();
                }
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public interface OnDownloadListener{
        /**
         * 下载开始
         * @param size 文件大小，单位：byte
         */
        void onStart(long size);

        /**
         * 下载进度
         * @param currentLength 下载的大小，单位：byte
         */
        void onProgress(long currentLength);

        /**
         * 下载完成，返回保存地址
         * @param localPath 文件地址
         */
        void onFinish(String localPath);

        /**
         * 下载失败
         * @param msg 返回失败原因
         */
        void onFailure(String msg);
    }
}
