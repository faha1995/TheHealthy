package com.example.administrator.thehealthy.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.administrator.thehealthy.application.BaseApplication;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/28.
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    private final String TAG = NetBroadcastReceiver.class.getSimpleName();
    public static ArrayList<netEventHandler> mListeners =
            new ArrayList<netEventHandler>();
    private static String NET_CHANGE_ACTIVON = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager mg = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mg.getActiveNetworkInfo();

        boolean isNetwork = info != null && info.isConnectedOrConnecting();
        if (isNetwork) {
            BaseApplication.setmNetWorkState(1);
        }
//        Log.i(TAG, "----------->" + "onReceive()");
//        if (intent.getAction().equals(NET_CHANGE_ACTIVON)) {
//            Log.i(TAG,"----------->"+ "onReceive()---NET_CHANGE_ACTIVON");
//            BaseApplication.mNetWorkState = NetUtil.getNetworkState(context);
//            Log.i(TAG,"----------->"+ BaseApplication.mNetWorkState);
//            // 通知接口完成加载

            for (netEventHandler handler : mListeners) {
                handler.netState();
            }
//            }
//        }


    }
        public static abstract interface netEventHandler {
            public abstract void netState();
        }
}

