package com.chenchen.collections.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * Created by Liang_Lu on 2017/9/29.
 * Fragment基类
 */

public class BaseFragment extends Fragment {

    protected Context mContext;
    private View mView;
    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    /**
     * 不使用Databinding设置布局
     *
     * @param resId  布局layout
     */
    public View setContentView(ViewGroup container, @LayoutRes int resId) {
        if (mView == null) {
            mView = LayoutInflater.from(getActivity()).inflate(resId, container, false);
            ButterKnife.bind(this, mView);
            initView();
        }
        return mView;
    }

    public void initView() {

    }

    /**
     * activity跳转（无参数）
     *
     * @param className
     */
    public void startActivity(Class<?> className) {
        Intent intent = new Intent(mContext, className);
        startActivity(intent);
    }

    /**
     * activity跳转（有参数）
     *
     * @param className
     */
    public void startActivity(Class<?> className, Bundle bundle) {
        Intent intent = new Intent(mContext, className);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
