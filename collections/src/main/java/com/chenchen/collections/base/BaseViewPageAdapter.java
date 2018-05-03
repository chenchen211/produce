package com.chenchen.collections.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

public class BaseViewPageAdapter extends FragmentPagerAdapter {
    private String[] titleArray;
    private List<Fragment> fragments;

    public BaseViewPageAdapter(FragmentManager fm, String[] titleArray, List<Fragment> fragments) {
        super(fm);
        this.titleArray = titleArray;
        this.fragments=fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
    @Override
    public int getCount() {
        return fragments.size();
    }

}
