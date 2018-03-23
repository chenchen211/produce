package com.chenchen.collections.juhe;

import com.chenchen.collections.http.HttpService;
import com.chenchen.collections.http.converter.Base64ConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 聚合数据API助手
 */

public class JuheHttpHelper {
    private static JuheHttpHelper instance;
    private JuheHttpServer service;

    private JuheHttpHelper(String baseUrl){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
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

        service=retrofit.create(JuheHttpServer.class);
    }

    public static JuheHttpHelper getInstance(String baseUrl){
        if(instance==null){
            instance=new JuheHttpHelper(baseUrl);
        }
        return instance;
    }
    public static JuheHttpHelper getInstance(){
        return getInstance(JuheHttpServer.BASE_URL);
    }
    public JuheHttpServer getService() {
        return service;
    }
}
