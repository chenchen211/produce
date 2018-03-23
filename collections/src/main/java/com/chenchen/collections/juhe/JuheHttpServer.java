package com.chenchen.collections.juhe;

import com.chenchen.collections.juhe.entity.IDCard;
import com.chenchen.collections.juhe.entity.IP;
import com.chenchen.collections.juhe.entity.JuheBase;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 聚合数据APIServer
 */

public interface JuheHttpServer {
    String BASE_URL="http://apis.juhe.cn/";

    @GET("idcard/index?dtype=json")
    Call<JuheBase<IDCard>> checkIDCard(@Query("key") String key, @Query("cardno") String no);

    @GET("ip/ip2addr?dtype=json")
    Call<JuheBase<IP>> checkIP(@Query("key") String key, @Query("ip") String ip);

}
