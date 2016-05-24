package com.seimun.mobileHealth.fragment.inforFrament;

import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.seimun.mobileHealth.activity.inforactivity.LoginActivity;
import com.seimun.mobileHealth.db.DBTool;
import com.seimun.mobileHealth.entity.PlanEntity;
import com.example.administrator.thehealthy.R;
import com.seimun.mobileHealth.adapter.ServicePlanAdapter;
import com.seimun.mobileHealth.application.BaseApplication;
import com.seimun.mobileHealth.entity.AppConfig;
import com.seimun.mobileHealth.entity.AppData;
import com.seimun.mobileHealth.fragment.BaseFatherFragment;
import com.seimun.mobileHealth.util.SwipeRefreshLoadingLayout;
import com.seimun.mobileHealth.volley.VolleySingleton;

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
    private ExpandableListView exListView;
    private ServicePlanAdapter servicePlanAdapter;
    private LinearLayout pleaseLoginLinear,networkLinear;
    private TextView nothingText,networkTv;
    private SwipeRefreshLoadingLayout swipLayout;
    private List<String> groups = new ArrayList<>();

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_service_plan;
    }

    @Override
    protected void initView() {

        dbTool = new DBTool();
        pleaseLoginLinear = findView(R.id.linear_service_plan);
        networkLinear = findView(R.id.linear_service_plan_network);
        networkTv = findView(R.id.text_service_plan_network);
        nothingText = findView(R.id.text_service_plan_nothing);
        exListView = findView(R.id.expandable_service_plan);
        servicePlanAdapter = new ServicePlanAdapter(getActivity(), AppData.spGroups, AppData.spChilds);
        exListView.setAdapter(servicePlanAdapter);
        initNetWork();

        swipLayout = findView(R.id.swipeRefresh_service_plan);
        swipLayout.setOnRefreshListener(this);
        swipLayout.setOnLoadListener(this);

//        if (!BaseApplication.isNetwork()) {
//            networkLinear.setVisibility(View.VISIBLE);
//            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/splash_discrip_type.ttf");
//            networkTv.setTypeface(typeface);
//        }
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
            pleaseLoginLinear.setVisibility(View.GONE);
           
            final HashMap<String, String> user = dbTool.getUserDetails();


            final StringRequest request = new StringRequest(Request.Method.POST,
                    AppConfig.URL_PLANS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    nothingText.setVisibility(View.GONE);
                    exListView.setAlpha(1);
                    networkLinear.setVisibility(View.GONE);
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

                            Log.i(TAG, "initnetwork : plantGroup --  " + member.length());
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
                            Log.i(TAG, "initnetwork : reportChild --  " + member.length());
                            servicePlanAdapter.addToPlanGroupsChilds(groups,AppData.spChilds);

                            // 如果exListView不可被点击
                            if (!exListView.isEnabled()) {
                                exListView.setEnabled(true);
                            }
                            if (plans.length() < 1) {
                                Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/splash_discrip_text_type.ttf");
                                nothingText.setTypeface(typeface);
                                nothingText.setText("还未有相关记录");
                                nothingText.setVisibility(View.VISIBLE);
                                exListView.setVisibility(View.GONE);
                            }
                        } 
//                        else {
//                            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/splash_discrip_text_type.ttf");
//                            nothingText.setTypeface(typeface);
//                            nothingText.setText("还未有相关记录");
//                            nothingText.setVisibility(View.VISIBLE);
//                            exListView.setVisibility(View.GONE);
//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/splash_discrip_text_type.ttf");
                    networkTv.setTypeface(typeface);
                    networkLinear.setVisibility(View.VISIBLE);
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
            pleaseLoginLinear.setVisibility(View.VISIBLE);
            pleaseLoginLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityIntent(getActivity(), LoginActivity.class);
                    Log.i(TAG, "onClick: lalalaaal+");
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
        // 没有网络
        if (!BaseApplication.isNetwork()) {
            // 二级列表显示时
            if (exListView.getVisibility() == View.VISIBLE) {

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.dialog_icon_alpha);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        exListView.setAlpha(0);
                        networkLinear.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                exListView.setAnimation(animation);
                swipLayout.setRefreshing(false);
            } else {
                swipLayout.setRefreshing(false);
            }
        } else {
            initNetWork();
        }

    }

    @Override
    public void onLoad() {
        swipLayout.setLoading(false);
    }
}
