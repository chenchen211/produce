package com.chenchen.collections.http;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/7/25.
 */

public abstract class HttpResult<T> implements Callback<T> {

    private static final String TAG = "HttpResult";

    public abstract void response(T t);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.i(TAG, "onResponse: "+response.message());
        T t = response.body();
        if(null != t){
            response(t);
        }else{
            onFailure(call,new Exception("无法解析的响应数据"));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.i(TAG, "onFailure:错误信息："+t.getMessage());
    }
}
