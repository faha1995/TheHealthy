package com.example.administrator.thehealthy.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.entity.AppConfig;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/3/28.
 */
public class DataChangedService extends Service {
    final String TAG = DataChangedService.class.getSimpleName();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 启动一个没有延迟的 重复时间为5分钟/每次的定时器
        timer.schedule(timerTask, 1000, 5 * 1000 * 60);

        Log.i(TAG, "------>" + "serviceOnCreate()");
    }

    private Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    };


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            StringRequest request = new StringRequest(AppConfig.URL_EDUCATION,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

//        PersonalFragment
    };

    // 返回DataChangeService对象
    private class MyBind extends Binder {

        public DataChangedService getService() {
            return DataChangedService.this;
        }


    }
}
