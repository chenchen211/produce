package com.chenchen.collections.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/11/7.
 */

public class KeyBoardUtils {

    public static void openKeybord(EditText mEditText, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager)mContext .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, 2);
        imm.toggleSoftInput(2, 1);
    }

    public static void closeKeybord(EditText mEditText, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
