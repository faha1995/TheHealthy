package com.example.administrator.thehealthy.tools;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.administrator.thehealthy.entity.AppConfig;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/4/27.
 */
public class HttpUtils {
private static final String TAG = HttpUtils.class.getSimpleName();

    //　向后端发送消息
    public static boolean setEvaluateToHttp(int record_id, String evaluate) {
final boolean httpOk;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("record_id",record_id);
            jsonObject.put("score", evaluate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AppConfig.URL_DETAIL,
                    jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.i(TAG, "----------> Response.Listener<>() " + response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError) {
                        Log.e("Volley", "TimeoutError");
                    }
                    if (error instanceof ServerError) {

                        Log.i(TAG, "----------> Response.ErrorListener<>() -- SerVerError");
                    }
                }
            });
//                    {
//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> headers = new HashMap<String, String>();
//                        headers.put("Accept", "application/json");
//                        headers.put("Content-Type", "application/x-www-form-urldecoded");
//                        return headers;
//                    }
//
//                    }
            VolleySingleton.getInstace().addRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        VolleySingleton.queueStart();

        return false;
    }
}
