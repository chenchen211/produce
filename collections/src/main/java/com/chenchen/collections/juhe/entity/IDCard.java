package com.chenchen.collections.juhe.entity;

/**
 * 聚合数据身份证校验
 */

public class IDCard{
    private String birthday;
    private String sex;
    private String area;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "IDCard{" +
                " birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
