package com.chenchen.collections.http;

import android.content.Context;

import com.chenchen.collections.http.converter.Base64ConverterFactory;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {
    private static HttpHelper instance;
    private HttpService service;

    private HttpHelper(Context context, String baseUrl){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieManager cookieManager = new CookieManager(new InDiskCookieStore(context), CookiePolicy.ACCEPT_ALL);

        JavaNetCookieJar cookieJar = new JavaNetCookieJar(cookieManager);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .addConverterFactory(Base64ConverterFactory.create())
                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(baseUrl)
                .build();
        service=retrofit.create(HttpService.class);
    }

    public static HttpHelper getInstance(Context context,String baseUrl){
        if(instance==null){
            instance=new HttpHelper(context,baseUrl);
        }
        return instance;
    }

    public HttpService getService() {
        return service;
    }
}
