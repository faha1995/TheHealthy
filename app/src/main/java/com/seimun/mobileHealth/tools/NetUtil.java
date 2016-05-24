package com.seimun.mobileHealth.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/3/28.
 * 判断网络的工具类
 */
public class NetUtil {
    public static  final int NETWORK_NONE = 0;
    public static final int NETWORK_WIFI = 1;
    public static final int NETWORK_MOBILE = 2;

    public static int getNetworkState(Context context){
        ConnectivityManager connManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // WIFI
        NetworkInfo.State state = connManager.
                getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return NETWORK_WIFI;
        }

        //3G
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return NETWORK_MOBILE;
        }
        return NETWORK_NONE;
    }
}
