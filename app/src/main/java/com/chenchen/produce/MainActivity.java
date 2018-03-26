package com.chenchen.produce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chenchen.collections.widget.PasswordInputView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((PasswordInputView) findViewById(R.id.again_paypswd_pet)).getText().toString();
    }
}
