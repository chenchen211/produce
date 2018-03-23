package com.chenchen.produce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.chenchen.collections.http.HttpHelper;
import com.chenchen.collections.http.HttpResult;
import com.chenchen.collections.http.HttpService;
import com.chenchen.collections.http.ServiceFactory;
import com.chenchen.collections.util.Upload;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "imigmaofsdljdfosj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpService service = ServiceFactory.createService(this, "https://www.nanfriends.com/",new int[]{R.raw.nanfriends_cn},new String[]{"nanfriends.cn"});
        try {
            new Upload("demo.php","images[]").setService(service).upload("", new HttpResult<ResponseBody>() {
                @Override
                public void response(ResponseBody responseBody) {
                    try {
                        Log.i(TAG, "response: "+responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
