package com.example.administrator.thehealthy.fragment;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.activity.inforactivity.EducateWebViewActivity;
import com.example.administrator.thehealthy.adapter.HealthEducationAdapter;
import com.example.administrator.thehealthy.application.BaseApplication;
import com.example.administrator.thehealthy.entity.AppConfig;
import com.example.administrator.thehealthy.entity.AppData;
import com.example.administrator.thehealthy.entity.HealthEduEntity;
import com.example.administrator.thehealthy.util.RefreshableView;
import com.example.administrator.thehealthy.util.SwipeRefreshLoadingLayout;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/3.
 * 健康教育界面
 */
public class HealthEducationFragment extends BaseFatherFragment implements AdapterView.OnItemClickListener {
    private final String TAG = HealthEducationFragment.class.getSimpleName();
    private ListView educationLv;
    private HealthEducationAdapter educationAdapter;
    private SwipeRefreshLoadingLayout swipeRefresh;
    private RefreshableView refreshableView;
    private int newCounts;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_health_education;
    }

    @Override
    protected void initView() {
        // 清理之前eduEntityList中的数据
        AppData.eduEntityList.clear();
//        frameLayout = findView(R.id.fragment_healthEducation);
//        frameLayout.setOnTouchListener(this);
//        swipeRefresh = findView(R.id.swipeRefreshLoadingLayout);
//        swipeRefresh.setOnRefreshListener(this);
//        swipeRefresh.setOnLoadListener(this);
        refreshableView = findView(R.id.refreshView_education);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(1000);
                    initRefreshData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }

        }, 0);

        educationLv = findView(R.id.listView_health_education);
        educationLv.setOnItemClickListener(this);
        educationAdapter = new HealthEducationAdapter(getActivity());

        educationLv.setAdapter(educationAdapter);

//        NetBroadcastReceiver.mListeners.add(this);

    }


    @Override
    protected void initData() {
        if (BaseApplication.isNetwork()) {
            initNetWork();
        } else {
            Toast.makeText(getActivity(), "当前无网络", Toast.LENGTH_SHORT).show();
        }
    }


    private void initNetWork() {
        AppData.eduEntityList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.URL_EDUCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                swipeRefresh.setRefreshing(false);

                try {
                    JSONObject object = new JSONObject(response);
                    if (!object.getBoolean("error")) {
                        int length = object.getInt("length");

                        for (int i = 0; i < length; i++) {
                            Log.i(TAG, "-------->" + object.getInt("length"));
                            JSONObject obj = (JSONObject) object.getJSONArray("list").get(i);
                            HealthEduEntity eduEntity = new HealthEduEntity();
                            eduEntity.setItem_id(obj.getInt("item_id"));
                            eduEntity.setTitle(obj.getString("title"));
                            eduEntity.setDescription(obj.getString("description"));
                            eduEntity.setCreate_at(obj.getString("create_at"));
                            eduEntity.setCreate_by(obj.getString("create_by"));
                            eduEntity.setContent_url(obj.getString("content_url"));
                            eduEntity.setImage_url(obj.getString("image_url"));
                            AppData.eduEntityList.add(eduEntity);
                            educationAdapter.addData(AppData.eduEntityList);
                        }
                        newCounts = AppData.eduEntityList.size();
                    } else {
                        String errorMsg = object.getString("error_msg");
                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "网络无应答,请连接网络重新进入", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        VolleySingleton.getInstace().addRequest(stringRequest);

    }


    private void initRefreshData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.URL_EDUCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                swipeRefresh.setRefreshing(false);

                try {
                    JSONObject object = new JSONObject(response);
                    if (!object.getBoolean("error")) {
                        int length = object.getInt("length");

                        if (length > newCounts) {
                            for (int i = 0; i < length - newCounts; i++) {
                                // 如果发布的教育咨询有更新
                                Log.i(TAG, "-------->" + object.getInt("length"));
                                JSONObject obj = (JSONObject) object.getJSONArray("list").get(i);
                                HealthEduEntity eduEntity = new HealthEduEntity();
                                eduEntity.setItem_id(obj.getInt("item_id"));
                                eduEntity.setTitle(obj.getString("title"));
                                eduEntity.setDescription(obj.getString("description"));
                                eduEntity.setCreate_at(obj.getString("create_at"));
                                eduEntity.setCreate_by(obj.getString("create_by"));
                                eduEntity.setContent_url(obj.getString("content_url"));
                                eduEntity.setImage_url(obj.getString("image_url"));
                                educationAdapter.addRefreshData(eduEntity);
                            }
                            newCounts = length;
                        } else if (length < newCounts) {
                            // 如果发布的教育咨询有删除
                            AppData.eduEntityList.clear();
                            for (int i = 0; i < length; i++) {
                                Log.i(TAG, "-------->" + object.getInt("length"));
                                JSONObject obj = (JSONObject) object.getJSONArray("list").get(i);
                                HealthEduEntity eduEntity = new HealthEduEntity();
                                eduEntity.setItem_id(obj.getInt("item_id"));
                                eduEntity.setTitle(obj.getString("title"));
                                eduEntity.setDescription(obj.getString("description"));
                                eduEntity.setCreate_at(obj.getString("create_at"));
                                eduEntity.setCreate_by(obj.getString("create_by"));
                                eduEntity.setContent_url(obj.getString("content_url"));
                                eduEntity.setImage_url(obj.getString("image_url"));
                                AppData.eduEntityList.add(eduEntity);
                                educationAdapter.addData(AppData.eduEntityList);
                            }
                            newCounts = length;
                        }
                    } else {
                        String errorMsg = object.getString("error_msg");
                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "网络无应答,请连接网络重新进入", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        VolleySingleton.getInstace().addRequest(stringRequest);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        activityIntent(this, new EducateWebViewActivity(), position);
    }
}
