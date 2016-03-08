package com.example.administrator.thehealthy.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/3.
 * 保存全局变量
 */
public class BaseApplication extends Application {
    private static Context context;
    private static ArrayList<Activity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
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
    public static void addActivity(Activity activity){
        if (activityList == null) {
            activityList = new ArrayList<>();
        } else {
            activityList.add(activity);
        }
    }

    /*
      移除activity
     */
    public static void removeActivity(Activity activity){
        if (activityList != null) {
           activityList.remove(activity);
        }
    }

    /*
      销毁集合中所有的activity
     */
    public static void finishAllActivity(){
        for (Activity activity: activityList){
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }


}
