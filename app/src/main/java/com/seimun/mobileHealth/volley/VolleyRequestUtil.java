package com.seimun.mobileHealth.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.seimun.mobileHealth.application.BaseApplication;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/21.
 */
public class VolleyRequestUtil {

    public static StringRequest stringRequest;
    public Context context;

    /**
     * 获取GET请求内容
     * 参数：
     * @param context 当前上下文
     * @param url   请求的url地址
     * @param tag   当前请求的标签
     * @param volleyListenerInterface   接口回调
     */
    public static void RequestGet(Context context, String url, String tag, VolleyListenerInterface volleyListenerInterface){
        // 清除请求队列中的tag标记请求
        BaseApplication.getRequestQueue().cancelAll(tag);
        // 创建当前的请求，获取字符串内容
        stringRequest = new StringRequest(Request.Method.GET,
                url, volleyListenerInterface.responseListener(),
                volleyListenerInterface.errorListener());
        // 为当前请求添加标记
        stringRequest.setTag(tag);
        // 将当前请求添加到请求队列中
        BaseApplication.getRequestQueue().add(stringRequest);
        // 重启当前请求队列
        BaseApplication.getRequestQueue().start();
    }

    /**
     * 获取POST请求内容（请求的代码为Map）
     * @param context   当前上下文
     * @param url   请求的url地址
     * @param tag   当前请求的标签
     * @param params    POST请求参数
     * @param volleyListenerInterface   接口回调
     */
    public static void RequestPost(Context context, String url, String tag, final Map<String,String> params, VolleyListenerInterface volleyListenerInterface){
        // 清除请求队列中的tag标记请求
        BaseApplication.getRequestQueue().cancelAll(tag);
        // 创建当前的POST请求，并将请求内容写入Map中
        stringRequest = new StringRequest(Request.Method.POST,
                url, volleyListenerInterface.responseListener(),
                volleyListenerInterface.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        // 为当前请求添加标记
        stringRequest.setTag(tag);
        // 将当前请求添加到请求队列中
        BaseApplication.getRequestQueue().add(stringRequest);
        // 重启当前请求队列
        BaseApplication.getRequestQueue().start();
    }
}
