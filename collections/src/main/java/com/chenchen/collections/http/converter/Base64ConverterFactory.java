package com.chenchen.collections.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/5/25.
 * 自定义的base64转化器
 */

public class Base64ConverterFactory extends Converter.Factory {

    public static Base64ConverterFactory create() {
        return create(new Gson());
    }

    public static Base64ConverterFactory create(Gson gson) {
        return new Base64ConverterFactory(gson);
    }

    private final Gson gson;

    private Base64ConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new Base64ResponseBodyConverter<>(adapter);
    }
}
