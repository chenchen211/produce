package com.chenchen.collections.juhe.entity;

/**
 * Created by Administrator on 2018/3/9.
 */

public class IP{

    /**
     * area : 天津市滨海新区
     * location : 腾讯云华北数据中心
     */

    private String area;
    private String location;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "IP{" +
                "area='" + area + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
