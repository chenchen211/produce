package com.chenchen.collections.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chenchen.collections.http.HttpHelper;
import com.chenchen.collections.http.HttpResult;
import com.chenchen.collections.http.HttpService;
import com.chenchen.collections.http.ProgressRequestBody;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by 陈陈 on 2018/1/29.
 * 使用retrofit上传文件
 */

public class Upload {
    private HttpService service;
    private String paramName;//后台接收图片流的参数名
    private String url;
    private static Upload instance;
    private ProgressRequestBody.OnUploadListener listener;
    public static Upload getInstance(@NonNull Context context ,String url) {
        if (instance == null) {
            instance = new Upload(context,url);
        }
        return instance;
    }

    private Upload(@NonNull Context context, @NonNull String uploadUrl){
        try {
            URI uri = new URI(uploadUrl);
            this.url=uploadUrl;
            service = HttpHelper.getInstance(context,"http://"+uri.getHost()+"/").getService(HttpService.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Upload setParamName(String paramName) {
        this.paramName = paramName;
        return  this;
    }

    public Upload setListener(ProgressRequestBody.OnUploadListener listener) {
        this.listener = listener;
        return  this;
    }

    /**
     * 上传文件：
     *      只能上传单个文件，可带其他参数数据
     * @param filePath 文件路径
     * @param response 回调
     * @throws Exception 文件不存在
     */
    public void uploadWithParams(@NonNull String filePath,@Nullable Map<String,String> params,HttpResult<ResponseBody> response) throws Exception {
        ArrayList<String> files = new ArrayList<>();
        files.add(filePath);
        uploadMultiple(files,params,response);
    }

    /**
     * 不带参数的上传文件
     * @param filePath 文件地址
     * @param response 回调
     * @throws IOException 文件不存在
     */
    public void upload(@NonNull String filePath,HttpResult<ResponseBody> response) throws Exception{
        uploadWithParams(filePath,null,response);
    }
    /**
     *
     * @param files 文件地址集合
     * @param params 附加参数
     */
    public void uploadMultiple(@NonNull List<String> files,@Nullable Map<String,String> params,HttpResult<ResponseBody> response) throws Exception{
        if(null==url || TextUtils.isEmpty(url)){
            throw new IllegalArgumentException("上传地址不能为空");
        }
        if(null==paramName || TextUtils.isEmpty(paramName)){
            throw new IllegalArgumentException("未设置图片上传参数，请使用setParamName设置");
        }
        if(files.isEmpty()){
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型;
        new Builder(builder).addStrData(params).addFileData(files);
        List<MultipartBody.Part> parts = builder.build().parts();
        service.upload(url,parts).enqueue(response);
    }

    private class Builder{

        private MultipartBody.Builder builder;

        public Builder(MultipartBody.Builder builder){
            this.builder = builder;
        }
        /**
         * 添加参数
         * @param params 参数
         */
        Builder addStrData(Map<String,String> params){
            if(null == params) return this;
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                builder.addFormDataPart(next.getKey(),next.getValue());
            }
            return this;
        }

        /**
         * 添加多张图片的数据流
         * @param files 文件路径集合
         */
        Builder addFileData(List<String> files) throws IOException{
            if(files.isEmpty()) return this;
            for (String filePath : files) {
                addFileData(filePath);
            }
            return this;
        }

        /**
         * 添加单张图片的数据流
         * @param filePath 单个文件路径
         */
        Builder addFileData(String filePath) throws IOException {
            File file = new File(filePath);
            if(!file.exists()){
                throw new IOException("文件"+filePath+"不存在");
            }
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart(paramName, file.getName(),imageBody);
            return this;
        }
    }
    private class ProgressBuilder{

        private MultipartBody.Builder builder;

        public ProgressBuilder(MultipartBody.Builder builder){
            this.builder = builder;
        }
        /**
         * 添加参数
         * @param params 参数
         */
        ProgressBuilder addStrData(Map<String,String> params){
            if(null == params) return this;
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                builder.addFormDataPart(next.getKey(),next.getValue());
            }
            return this;
        }

        /**
         * 添加多张图片的数据流
         * @param files 文件路径集合
         */
        ProgressBuilder addFileData(List<String> files) throws IOException{
            if(files.isEmpty()) return this;
            for (String filePath : files) {
                addFileData(filePath);
            }
            return this;
        }

        /**
         * 添加单张图片的数据流
         * @param filePath 单个文件路径
         */
        ProgressBuilder addFileData(String filePath) throws IOException {
            File file = new File(filePath);
            if(!file.exists()){
                throw new IOException("文件"+filePath+"不存在");
            }
            if(listener == null){
                throw new IllegalArgumentException("listener can not be null");
            }
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart(paramName, file.getName(),new ProgressRequestBody(imageBody,listener));
            return this;
        }
    }

}
