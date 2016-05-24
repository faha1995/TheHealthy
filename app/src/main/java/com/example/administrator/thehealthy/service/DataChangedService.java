package com.example.administrator.thehealthy.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.entity.AppConfig;
import com.example.administrator.thehealthy.entity.AppData;
import com.example.administrator.thehealthy.entity.ServiceToUiEntity;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/3/28.
 */
public class DataChangedService extends Service {
    final String TAG = DataChangedService.class.getSimpleName();
    private ServiceToUiEntity entity = new ServiceToUiEntity();
    private DBTool dbTool = new DBTool();
    ;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 启动一个没有延迟的 重复时间为5分钟/每次的定时器
        timer.schedule(timerTask, 1000, 4 * 1000 * 60);

        Log.i(TAG, "------>" + "serviceOnCreate()");
    }

    private Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    };


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            // 请求健康教育的数据
            StringRequest request = new StringRequest(AppConfig.URL_EDUCATION,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                if (!object.getBoolean("error")) {
                                    int length = object.getInt("length");
                                    // 有新数据提醒显示小绿点
                                    if (AppData.eduCounts < length) {
                                        entity.setName(TAG);
                                        entity.setWhich("tabs0");
                                        entity.setNum(length - AppData.eduCounts);
                                        EventBus.getDefault().post(entity);
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            VolleySingleton.getInstace().addRequest(request);

            // 判断personalFragment已经显现
            if (AppData.isOnResume) {
                final HashMap<String, String> user = dbTool.getUserDetails();
                StringRequest personalRequest = new StringRequest(AppConfig.URL_SUMMARYS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("resident_id", user.get("resident_id"));
                        Log.i(TAG, "----------->" + user.get("resident_id"));
                        return params;
                    }
                };
            }
        }

    };


    // 返回DataChangeService对象
    private class MyBind extends Binder {

        public DataChangedService getService() {
            return DataChangedService.this;
        }

    }
}
