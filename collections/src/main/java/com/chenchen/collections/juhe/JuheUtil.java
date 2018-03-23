package com.chenchen.collections.juhe;

import com.chenchen.collections.juhe.entity.IDCard;
import com.chenchen.collections.juhe.entity.IP;
import com.chenchen.collections.juhe.entity.JuheBase;
import com.chenchen.collections.http.HttpResult;

/**
 * Created by Administrator on 2018/3/9.
 */

public class JuheUtil {
    public static final String KEY_IDCARD = "78e2733ca4d50445e2b46647dc834415";
    public static final String KEY_IP  = "f5a2f206596cfc2a51376a2086a99e78";
    private JuheHttpServer server;//HTTP请求server

    private static JuheUtil instance;

    private JuheUtil() {
        server = JuheHttpHelper.getInstance().getService();
    }
    public static JuheUtil getInstance() {
        if (instance == null) {
            instance = new JuheUtil();
        }
        return instance;
    }
    public void checkIDCard(String no, final OnResultListener<IDCard> listener){
        server.checkIDCard(KEY_IDCARD,no).enqueue(new HttpResult<JuheBase<IDCard>>() {
            @Override
            public void response(JuheBase<IDCard> idCardJuheBase) {
                if("200".equals(idCardJuheBase.getResultcode())){
                    listener.onSuccess(idCardJuheBase.getResult());
                }else{
                    listener.onError(idCardJuheBase.getReason());
                }
            }
        });
    }
    public void checkIP(String ip,final OnResultListener<IP> listener){
        server.checkIP(KEY_IP,ip).enqueue(new HttpResult<JuheBase<IP>>() {
            @Override
            public void response(JuheBase<IP> idCardJuheBase) {
                if("200".equals(idCardJuheBase.getResultcode())){
                    listener.onSuccess(idCardJuheBase.getResult());
                }else{
                    listener.onError(idCardJuheBase.getReason());
                }
            }
        });
    }
    public interface OnResultListener<T>{
        void onSuccess(T t);
        void onError(String msg);
    }
}
