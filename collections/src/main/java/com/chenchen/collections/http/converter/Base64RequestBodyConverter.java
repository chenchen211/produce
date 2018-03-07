package com.chenchen.collections.http.converter;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2017/5/25.
 */

public class Base64RequestBodyConverter<T> implements Converter<T, RequestBody> {
    @Override
    public RequestBody convert(T value) throws IOException {
        return null;
    }
}
