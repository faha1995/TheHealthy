package com.example.administrator.thehealthy.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ListenNetStateService extends Service {
    final String TAG = ListenNetStateService.class.getSimpleName();
        private NetworkInfo infor;
        private ConnectivityManager manager;


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

                manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                infor = manager.getActiveNetworkInfo();

//                Log.i(TAG, "------>" + BaseApplication.getNETCHANGE());
//                if (infor == null && !manager.getBackgroundDataSetting()) {
//                    // 无网状态设置为2
//                    BaseApplication.setNETCHANGE(2);
//                    Log.i(TAG, "------>" + BaseApplication.getNETCHANGE());
//                } else {
//                    // 有网状态设置为1
//                    BaseApplication.setNETCHANGE(1);
//                    Log.i(TAG, "------>" + BaseApplication.getNETCHANGE());
//                }

        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, intentFilter);

        Log.i(TAG, "------>" + "serviceOnCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
