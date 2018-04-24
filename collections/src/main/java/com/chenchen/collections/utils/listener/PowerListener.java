package com.chenchen.collections.utils.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.support.v4.media.MediaBrowserCompat;

/**
 * 监听电源状态
 * 主要通过监听广播获取电源状态
 */

public class PowerListener {
    private PowerBrodcastReceiver powerBrodcastReceiver;
    private Context mContext;
    private PowerManager powerManager;

    private class PowerBrodcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(Intent.ACTION_POWER_CONNECTED.equals(action)){

            }else if(Intent.ACTION_POWER_DISCONNECTED.equals(action)){

            }else if(Intent.ACTION_POWER_USAGE_SUMMARY.equals(action)){

            }
        }
    }

    public void begin(){
        powerManager = (PowerManager) mContext.getApplicationContext().getSystemService(Context.POWER_SERVICE);
        registListener();
    }

    /**
     * 注册电源监听器
     */
    public void registListener(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_POWER_USAGE_SUMMARY);
        mContext.registerReceiver(powerBrodcastReceiver, filter);
    }

    public void unregistListener(){
        mContext.unregisterReceiver(powerBrodcastReceiver);
    }
}
