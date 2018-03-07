package com.chenchen.collections.http.converter;

import android.util.Base64;
import android.util.Log;

import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2017/5/25.
 */

public class Base64ResponseBodyConverter<T> implements Converter<ResponseBody,T> {
    private static final String TAG = "base64";
    private final TypeAdapter<T> adapter;

    Base64ResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        byte[] bytes = Base64.decode(value.bytes(), Base64.DEFAULT);
        String json = new String(bytes,"UTF-8");
        Log.i(TAG, "convert: "+json);
        return adapter.fromJson(json.trim());
    }
}
