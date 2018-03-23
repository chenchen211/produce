package com.chenchen.collections.juhe.entity;

/**
 * Created by Administrator on 2018/3/9.
 */

public class JuheBase<T> {

    /**
     * resultcode : 203
     * reason : 身份证校验位不正确
     * result : {"area":"河南省新乡市原阳县","sex":"男","birthday":"1993年03月20日","verify":"该身份证号校验位不正确"}
     * error_code : 203803
     */

    private String resultcode;
    private String reason;
    /**
     * area : 河南省新乡市原阳县
     * sex : 男
     * birthday : 1993年03月20日
     * verify : 该身份证号校验位不正确
     */

    private T result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
