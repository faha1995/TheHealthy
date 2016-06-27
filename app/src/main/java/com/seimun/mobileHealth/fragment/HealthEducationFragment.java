package com.seimun.mobileHealth.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.seimun.mobileHealth.activity.inforactivity.EducateWebViewActivity;
import com.seimun.mobileHealth.adapter.HealthEducationAdapter;
import com.seimun.mobileHealth.application.BaseApplication;
import com.seimun.mobileHealth.db.DBTool;
import com.seimun.mobileHealth.entity.AppConfig;
import com.seimun.mobileHealth.entity.AppData;
import com.seimun.mobileHealth.entity.HealthEduEntity;
import com.seimun.mobileHealth.util.RefreshableView;
import com.seimun.mobileHealth.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/3.
 * 健康教育界面
 */
public class HealthEducationFragment extends BaseFatherFragment implements AdapterView.OnItemClickListener {
    private final String TAG = HealthEducationFragment.class.getSimpleName();
    private ListView educationLv;
    private HealthEducationAdapter educationAdapter;
    private RefreshableView refreshableView;
    private DBTool dbTool;
    private List<HealthEduEntity> eduEntityList;
    private final int DISAPPEAR = 1;
    private int item_id;
    private SharedPreferences sharedPreference;
    private final String THE_FIRST_RUN = "firstRun";


    @Override
    protected int setLayoutView() {
        return R.layout.fragment_health_education;
    }


    @Override
    protected void initView() {
        // 清理之前eduEntityList中的数据
        AppData.eduEntityList.clear();
        dbTool = new DBTool();
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


        eduEntityList = dbTool.queryHealthData();
        sharedPreference = getActivity().getSharedPreferences(THE_FIRST_RUN, Context.MODE_PRIVATE);
    }


    @Override
    protected void initData() {
        Log.i(TAG, "----------->  eduEntityList.size()  " + eduEntityList.size());
        if (!BaseApplication.isNetwork() && eduEntityList != null && eduEntityList.size() != 0) {
            educationAdapter.addData(eduEntityList);
            AppData.eduCounts = eduEntityList.size();
        } else {
            initNetWork();
        }


    }


    private void initNetWork() {
        AppData.eduEntityList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.URL_EDUCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (!object.getBoolean("error")) {
                        int length = object.getInt("length");
                        // 获得数据库中已有的item_id
                        boolean hasItem = false;
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

                            dbTool.deleteHealthEduData();

                            // 如果第一次进入APP 就将所有数据加入到数据库
//                            if (sharedPreference.getBoolean("first", false)) {
                                dbTool.saveHealthEduData(AppData.eduEntityList);
//                            } else {
                                // 获得数据库中保存健康教育
                                // 数据中的所有item_id集合
                                // 并且判断当前解析数据中是否有新的
                                // 若有则加入数据库中
//                                List<Integer> itemList = dbTool.itemsFromSummary();
//                                if (itemList != null) {
//                                    for (int item = 0; item < itemList.size(); item++) {
//                                        if (obj.getInt("item_id") == itemList.get(item)) {
//                                            hasItem = true;
//                                        }
//                                    }
//                                    if (hasItem = false) {
                                        dbTool.saveNewHealthEduData(eduEntity);
//                                    }
//                                }
//                            }

                        }
                        AppData.eduCounts = AppData.eduEntityList.size();
                        Log.i(TAG, " initNetwork : " + AppData.eduCounts);
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
                Toast.makeText(getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
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
                BaseApplication.getInstance().getHandler().sendEmptyMessage(DISAPPEAR);

                try {
                    JSONObject object = new JSONObject(response);
                    if (!object.getBoolean("error")) {
                        int length = object.getInt("length");
                        Log.i(TAG, "----------->  newCounts  " + AppData.eduCounts);
                        if (length > AppData.eduCounts) {
                            for (int i = 0; i < length - AppData.eduCounts; i++) {
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

                                dbTool.saveRefreshHealthEduData(eduEntity);
                                Log.i(TAG, "--------> length > newCounts");
                            }
                            AppData.eduCounts = length;
                        } else {
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

                                dbTool.deleteHealthEduData();
                                dbTool.saveHealthEduData(AppData.eduEntityList);
                                Log.i(TAG, "--------> length < newCounts");
                            }
                            AppData.eduCounts = length;
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
                Toast.makeText(getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
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

    int pos;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (BaseApplication.isNetwork()) {
            // 获得点击的item的item_id 并且改变数据库中其对应的create_by属性
//            List<Integer> itemList = dbTool.itemsFromSummary();
//            item_id = AppData.eduEntityList.get(position).getItem_id();
//            for (int i = 0; i < itemList.size(); i++) {
//                if (item_id == itemList.get(i)) {
//                    // 改变数据库中对应的create_by属性
//                    dbTool.changeItem(item_id);
//                }
//            }
            activityIntent(this, new EducateWebViewActivity(), position);
//            TextView titleTv,descripTv,timeTv,dateTv;
//            titleTv = view.findViewById(R.id.)
        } else {
            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
        }
    }


}
