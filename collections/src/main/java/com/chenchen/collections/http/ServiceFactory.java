package com.chenchen.collections.http;

import android.content.Context;
import android.support.annotation.IdRes;

import com.chenchen.collections.http.converter.Base64ConverterFactory;
import com.chenchen.collections.utils.SSLFactory;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/23.
 *
 */

public class ServiceFactory {
    private static Retrofit retrofit;
    private static OkHttpClient client;

    public ServiceFactory(){
        throw new IllegalStateException("ServiceFactory 不能创建对象");
    }

    private static void create(Context context,String baseUrl,int[] certificates,String[] hosts){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        CookieManager cookieManager = new CookieManager(new InDiskCookieStore(context), CookiePolicy.ACCEPT_ALL);
        JavaNetCookieJar cookieJar = new JavaNetCookieJar(cookieManager);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(certificates != null && certificates.length > 0) {
            SSLSocketFactory factory = SSLFactory.getSSLSocketFactory(context, certificates);
            if(factory != null){
                builder.sslSocketFactory(factory);
            }
        }
        if(hosts != null && hosts.length>0)
            builder.hostnameVerifier(SSLFactory.getHostnameVerifier(hosts));
        client = builder
                .cookieJar(cookieJar)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .addConverterFactory(Base64ConverterFactory.create())
                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(baseUrl)
                .build();
    }

    private static HttpService getService(Context context,String baseUrl){
        create(context,baseUrl,null,null);
        return retrofit.create(HttpService.class);
    }
    private static HttpService getService(Context context,String baseUrl,int[] c,String[] h){
        create(context,baseUrl,c,h);
        return retrofit.create(HttpService.class);
    }
    /**
     * 创建http网络请求Service
     * @param context 上下文
     * @param baseUrl baseurl
     * @return 网络请求Service
     */
    public static HttpService createService(Context context,String baseUrl){
        return getService(context,baseUrl);
    }

    /**
     * 创建https网络请求Service
     * @param context 上下文
     * @param baseUrl baseurl
     * @param c 证书，这里将cer文件放在资源目录中，使用资源id获取
     * @param h host名，这里的名称不能带https://
     * @return 网络请求Service
     */
    public static HttpService createService(Context context, String baseUrl, @IdRes int[] c, String[] h){
        return getService(context,baseUrl,c,h);
    }
}
