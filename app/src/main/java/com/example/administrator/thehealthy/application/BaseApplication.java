package com.example.administrator.thehealthy.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/3.
 * 保存全局变量
 */
public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    private static Context context;
    private static ArrayList<Activity> activityList;
    public static int mNetWorkState;

    public static int getmNetWorkState() {
        return mNetWorkState;
    }

    public static void setmNetWorkState(int mNetWorkState) {
        BaseApplication.mNetWorkState = mNetWorkState;
    }

    public static synchronized BaseApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mApplication = this;
//        initData();

//        if (isNetwork()) {
//            Toast.makeText(BaseApplication.this, "有网络", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(BaseApplication.this, "无网络", Toast.LENGTH_SHORT).show();
//        }
        Log.i("BaseAppplication", "------------>" + "BaseApplication");
//        Intent intent = new Intent(this, ListenNetStateService.class);
//        startService(intent);
    }

//    private void initData() {
//        mNetWorkState = NetUtil.getNetworkState(this);
//    }

    public static boolean isNetwork() {
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || !manager.getBackgroundDataSetting()) {
            return false;
        }
        return true;

    }

    /*
     该方法提供了Application的Context
      */
    public static Context getContext() {
        return context;
    }

    /*
     将activity 加入到ArrayList中
     */
    public static void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new ArrayList<>();
        } else {
            activityList.add(activity);
        }
    }

    /*
      移除activity
     */
    public static void removeActivity(Activity activity) {
        if (activityList != null) {
            activityList.remove(activity);
        }
    }

    /*
      销毁集合中所有的activity
     */
    public static void finishAllActivity() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }


}
