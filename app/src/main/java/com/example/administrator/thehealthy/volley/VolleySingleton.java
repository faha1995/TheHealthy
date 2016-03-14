package com.example.administrator.thehealthy.volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.administrator.thehealthy.application.BaseApplication;

/**
 * Created by Administrator on 2016/3/8.
 */
public class VolleySingleton {
    private RequestQueue queue;

    public VolleySingleton() {
        queue = getQueue();
    }

    // 初始化RequestQueue
    private  RequestQueue getQueue(){
        if (queue == null) {
            queue = Volley.newRequestQueue(BaseApplication.getContext());
        }
        return queue;
    }

    private static final class singletonHolder{
        private static final VolleySingleton instance = new VolleySingleton();
    }

    public static VolleySingleton getInstace(){
        return singletonHolder.instance;
    }

    public static final String TAG = VolleySingleton.class.getSimpleName();

    public <T> void addRequest(Request<T> request){
        request.setTag(TAG);
        getQueue().add(request);
    }

    public <T> void _addRequest(Request<T> request, Object tag) {
        request.setTag(tag);
        getQueue().add(request);
    }




}
