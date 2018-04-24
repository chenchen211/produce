package com.chenchen.collections.xframe.base;

import android.app.Application;

import com.chenchen.collections.xframe.XFrame;


public class XApplication extends Application {
    private static XApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        XFrame.init(this);
    }


    public static XApplication getInstance() {
        return instance;
    }


}
