package com.example.administrator.thehealthy.fragment.inforFrament;

import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.activity.inforactivity.LoginActivity;
import com.example.administrator.thehealthy.adapter.ServicePlanAdapter;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.entity.AppConfig;
import com.example.administrator.thehealthy.entity.AppData;
import com.example.administrator.thehealthy.entity.PlanEntity;
import com.example.administrator.thehealthy.fragment.BaseFatherFragment;
import com.example.administrator.thehealthy.util.SwipeRefreshLoadingLayout;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/4.
 * 医院诊疗界面
 */
public class ServicePlanFragment extends BaseFatherFragment implements SwipeRefreshLoadingLayout.OnRefreshListener, SwipeRefreshLoadingLayout.OnLoadListener {
    private final String TAG = ServicePlanFragment.class.getSimpleName();
    private DBTool dbTool;
    private ExpandableListView expandableLv;
    private ServicePlanAdapter servicePlanAdapter;
    private LinearLayout plaseLoginLinear;
    private TextView nothingText;
    private SwipeRefreshLoadingLayout swipLayout;
    private List<String> groups = new ArrayList<>();

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_service_plan;
    }

    @Override
    protected void initView() {

        dbTool = new DBTool();
        plaseLoginLinear = findView(R.id.linear_service_plan);
        nothingText = findView(R.id.text_service_plan_nothing);
        expandableLv = findView(R.id.expandable_service_plan);
        servicePlanAdapter = new ServicePlanAdapter(getActivity(), AppData.spGroups, AppData.spChilds);
        initNetWork();
        expandableLv.setAdapter(servicePlanAdapter);

        swipLayout = findView(R.id.swipeRefresh_service_plan);
        swipLayout.setOnRefreshListener(this);
        swipLayout.setOnLoadListener(this);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Subscribe
    public void onEvent(String string) {
        switch (string) {
            case "isNew":
                initNetWork();
                break;
            case "退出当前用户":
                groups.clear();
                AppData.hrChilds.clear();
                servicePlanAdapter.addToPlanGroupsChilds(groups, AppData.spChilds);

                initNetWork();
        }
    }

    private void initNetWork() {
        groups.clear();
        AppData.spChilds.clear();
        if (dbTool.isLogined()) {
            plaseLoginLinear.setVisibility(View.GONE);
            nothingText.setVisibility(View.GONE);
            expandableLv.setVisibility(View.VISIBLE);
            final HashMap<String, String> user = dbTool.getUserDetails();


            final StringRequest request = new StringRequest(Request.Method.POST,
                    AppConfig.URL_PLANS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    swipLayout.setRefreshing(false);

                    Log.i(TAG, "服务计划界面数据网络通信响应");
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");

                        if (!error) {
//
                            // 得到一级分类的数据加入groups集合中
                            JSONArray member = jsonObject.getJSONArray("member");
                            for (int i = 0; i < member.length(); i++) {
                                groups.add(i, member.getJSONObject(i).getString("resident"));
                                Log.i(TAG, "------> member.length" + member.getJSONObject(i).getString("resident"));
                            }


//                             得到一级分类对应的二级分类数据，加入childs集合中
                            JSONArray plans = jsonObject.getJSONArray("plan");
                            for (int i = 0; i < plans.length(); i++) {
                                List<PlanEntity> child = new ArrayList<>();
                                Log.i(TAG, "---------->  summaries.length" + plans.length() + "  " + i);
                                JSONArray array = plans.getJSONArray(i);

                                for (int k = 0; k < array.length(); k++) {
                                    JSONObject item = (JSONObject) array.get(k);
                                    PlanEntity planEntity = new PlanEntity();

                                    planEntity.setStatus(item.getString("status"));
                                    planEntity.setNext_date(item.getString("next_date"));
                                    planEntity.setService_item(item.getString("service_item"));
                                    planEntity.setService_type(item.getString("service_type"));
                                    planEntity.setType_alias(item.getString("type_alias"));
                                    planEntity.setItem_alias(item.getString("item_alias"));

                                    child.add(k, planEntity);
//                                    dbTool.addSummary(planEntity);
                                }
                                AppData.spChilds.add(i, child);
                            }

                            servicePlanAdapter.addToPlanGroupsChilds(groups,AppData.spChilds);

                        } else {
                            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/splash_discrip_text_type.ttf");
                            nothingText.setTypeface(typeface);
                            nothingText.setText("还未有相关记录");
                            nothingText.setVisibility(View.VISIBLE);
                            expandableLv.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("resident_id", user.get("resident_id"));
                    return params;
                }
            };

            VolleySingleton.getInstace().addRequest(request);
        } else {
            plaseLoginLinear.setVisibility(View.VISIBLE);
            plaseLoginLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityIntent(getActivity(), LoginActivity.class);
                }
            });

        }


    }


    @Override
    protected void initData() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onRefresh() {
        initNetWork();
    }

    @Override
    public void onLoad() {
        swipLayout.setLoading(false);
    }
}
