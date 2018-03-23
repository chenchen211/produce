package com.chenchen.collections.http;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/7/24.
 * Api接口
 */

public interface HttpService {

    @GET
    Call<ResponseBody> doGet(@Url String url);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> doPost(@Url String url, @FieldMap Map<String, String> map);

    /**
     * 上传图片
     * @param partList  MultipartBody.Part
     * @return 上传结果
     */
    @Multipart
    @POST
    Call<ResponseBody> uploadWithRoot(@Url String url,@Part List<MultipartBody.Part> partList);

    @Multipart
    @POST("{path}")
    Call<ResponseBody> upload(@Path("path") String path, @Part List<MultipartBody.Part> parts);
    /**
     * 图片验证码
     * @param url 验证码地址
     * @return 图形数据流
     */
    @GET()
    Call<ResponseBody> imgcode(@Url String url);
}
