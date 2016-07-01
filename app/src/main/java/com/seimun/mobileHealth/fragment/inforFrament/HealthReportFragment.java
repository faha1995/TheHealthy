package com.seimun.mobileHealth.fragment.inforFrament;

import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.seimun.mobileHealth.R;
import com.seimun.mobileHealth.activity.inforactivity.LoginActivity;
import com.seimun.mobileHealth.adapter.HealthRrportExpandAdapter;
import com.seimun.mobileHealth.application.BaseApplication;
import com.seimun.mobileHealth.db.DBTool;
import com.seimun.mobileHealth.entity.AppConfig;
import com.seimun.mobileHealth.entity.AppData;
import com.seimun.mobileHealth.entity.Summary;
import com.seimun.mobileHealth.fragment.BaseFatherFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.Aftercare12MonthFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.Aftercare18MonthFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.Aftercare1MonthFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.Aftercare24MonthFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.Aftercare30MonthFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.Aftercare3MonthFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.Aftercare3YearFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.Aftercare4To6YearFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.Aftercare6MonthFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.Aftercare8MonthFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.AftercareFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.AntenatalFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.BodyExamFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.DiabetesAftercareFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.HypertensionAftercareFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.NewbornFamilyVisitFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.OldIdentifyFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.OldPeopleLifeAbilityFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.PostpartumManyDaysHealthCheck;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.PostpartumVisitFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.PsychiatricAftercareFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.TcmAftercareFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.VaccinationFragment;
import com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment.VaccineCardFragment;
import com.seimun.mobileHealth.tools.ChangeString;
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
 * Created by Administrator on 2016/4/11.
 * 健康报告界面
 */
public class HealthReportFragment extends BaseFatherFragment implements SwipeRefreshLoadingLayout.OnRefreshListener, SwipeRefreshLoadingLayout.OnLoadListener {
    private final String TAG = HealthReportFragment.class.getSimpleName();
    private DBTool dbTool;
    private ExpandableListView exListView;
    private HealthRrportExpandAdapter expandAdapter;
    private LinearLayout pleaseLoginLinear, networkLinear;
    private TextView nothingText, networkTv;
    private SwipeRefreshLoadingLayout swipeLayout;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_health_report;
    }

    @Override
    protected void initView() {

        dbTool = new DBTool();
        nothingText = findView(R.id.text_health_report_nothing);
        networkTv = findView(R.id.text_health_report_network);
        networkLinear = findView(R.id.linear_health_report_network);
        pleaseLoginLinear = findView(R.id.linear_pleaseLogin);
        pleaseLoginLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityIntent(getActivity(), LoginActivity.class);
            }
        });
        swipeLayout = findView(R.id.swipeRefresh_health_report);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);

        exListView = findView(R.id.exlistView_health_report);
        expandAdapter = new HealthRrportExpandAdapter(getActivity(), AppData.hrGroups, AppData.hrChilds);
        exListView.setAdapter(expandAdapter);

        initNetWork();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }


    @Override
    public void onRefresh() {
        // 没有网络
        if (!BaseApplication.isNetwork()) {
            // 二级列表显示时
            Log.i(TAG, "onRefresh: " + exListView.getVisibility());
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
                swipeLayout.setRefreshing(false);
            } else {
                swipeLayout.setRefreshing(false);
            }
        } else {
            exListView.setEnabled(false);
            initNetWork();

        }

    }


    @Subscribe
    public void onEvent(String string) {
        switch (string) {
            case "isNew":
                initNetWork();
                break;
            case "退出当前用户":
                AppData.hrGroups.clear();
                AppData.hrChilds.clear();
                expandAdapter.addToReportGroupsChilds(AppData.hrGroups, AppData.hrChilds);
                initNetWork();
        }
    }

    private void initNetWork() {
            AppData.hrGroups.clear();
            AppData.hrChilds.clear();
            if (dbTool.isLogined()) {
            pleaseLoginLinear.setVisibility(View.GONE);
            final HashMap<String, String> user = dbTool.getUserDetails();

            final StringRequest request = new StringRequest(Request.Method.POST,
                    AppConfig.URL_SUMMARYS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    nothingText.setVisibility(View.GONE);
                    exListView.setAlpha(1);
                    swipeLayout.setRefreshing(false);
                    networkLinear.setVisibility(View.GONE);

                    Log.i(TAG, "健康报告界面数据网络通信响应");
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");

                        if (!error) {
                            // 得到一级分类的数据加入groups集合中
                            JSONArray member = jsonObject.getJSONArray("member");
                            //TODO
                            for (int i = 0; i < member.length(); i++) {
                                // 为了多一个隐藏并占位置的group来将底部圆形让出来
//                                if (i == member.length()) {
//                                    AppData.hrGroups.add(i,"aa");
//
//                                } else {
                                AppData.hrGroups.add(i, member.getJSONObject(i).getString("resident"));

//                                }
                                Log.i(TAG, "------> member.length" + member.getJSONObject(i).getString("resident"));
                            Log.i(TAG, "initnetwork : reportGroup --  " + AppData.hrGroups.size());
                            }

//                             得到一级分类对应的二级分类数据，加入childs集合中
                            JSONArray summaries = jsonObject.getJSONArray("summary");

                   for (int i = 0; i < summaries.length(); i++) {
                                List<Summary> child = new ArrayList<>();
                                Log.i(TAG, "---------->  summaries.length" + summaries.length() + "  " + i);
                                JSONArray array = summaries.getJSONArray(i);
                                Log.i(TAG, "---------->  array.length" + array.length() + "  " + i);

                                for (int k = 0; k < array.length() ; k++) {
                                    JSONObject item = (JSONObject) array.get(k);
                                    Summary summary = new Summary();
//                                    if (k != array.length()) {


                                        summary.setRecordId(item.getInt("record_id"));
                                        summary.setTitle(item.getString("title"));
                                        summary.setResident(item.getString("resident"));
                                        summary.setClinic(item.getString("clinic"));
                                        summary.setProvider(item.getString("provider"));
                                        summary.setServiceTime(item.getString("service_time"));
                                        summary.setTypeAlias(item.getString("type_alias"));
                                        summary.setItemAlias(item.getString("item_alias"));

//                                    }
                                    child.add(k, summary);

                                }

                                AppData.hrChilds.add(i, child);
                            }

                            Log.i(TAG, "initnetwork : reportChild -- " + summaries.length());
                            // 为了刷新时 groups 和 childs 同时显示
                            // 同时将两个集合传入adapter中
                            expandAdapter.addToReportGroupsChilds(AppData.hrGroups, AppData.hrChilds);
                            // 如果exListView不可被点击
                            if (!exListView.isEnabled()) {
                                exListView.setEnabled(true);
                            }
                            if (summaries.length() < 1) {
                                Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/splash_discrip_text_type.ttf");
                                nothingText.setTypeface(typeface);
                                nothingText.setText("还未有相关记录");
                                nothingText.setVisibility(View.VISIBLE);
                                exListView.setVisibility(View.GONE);
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "--------->  ErrorListener");
                    Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/splash_discrip_text_type.ttf");
                    networkTv.setTypeface(typeface);
                    networkLinear.setVisibility(View.VISIBLE);

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

            VolleySingleton.getInstace().addRequest(request);
        } else {
            pleaseLoginLinear.setVisibility(View.VISIBLE);

        }


    }

    @Override
    protected void initData() {

        exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (BaseApplication.isNetwork()) {

                    goWhich(AppData.hrChilds.get(groupPosition).get(childPosition).getTypeAlias(),
                            AppData.hrChilds.get(groupPosition).get(childPosition).getItemAlias(),
                            ChangeString.splitForPurpose(AppData.hrChilds.get(groupPosition).get(childPosition).getTitle()),
                            AppData.hrChilds.get(groupPosition).get(childPosition).getRecordId());
                } else {
                    Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });


    }

    // 判断item点击后跳向的界面
    private void goWhich(String type_alias, String item_alias, String title, int record_id) {
        Log.i(TAG, "--------> type_alias--- " + type_alias + " item_alias--- " + item_alias + " record_id--- " + record_id);
        if (type_alias.equals("pregnant") && item_alias.equals("aftercare_1")) {
            goToNextFragmentFromHealthReport(new AntenatalFragment(title), record_id);
        } else if (type_alias.equals("pregnant") && item_alias.equals("postpartum_visit")) {
            goToNextFragmentFromHealthReport(new PostpartumVisitFragment(title), record_id);
        } else if (type_alias.equals("pregnant") && (item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3")
                || item_alias.equals("aftercare_4") || item_alias.equals("aftercare_5"))) {
            goToNextFragmentFromHealthReport(new AftercareFragment(title), record_id);
        } else if (type_alias.equals("pregnant") && item_alias.equals("postpartum_42_day_examination")) {
            goToNextFragmentFromHealthReport(new PostpartumManyDaysHealthCheck(), record_id);
        } else if (type_alias.equals("hypertension") && (item_alias.equals("aftercare_1")
                || item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3")
                || item_alias.equals("aftercare_4"))) {
            goToNextFragmentFromHealthReport(new HypertensionAftercareFragment(title), record_id);
        } else if (type_alias.equals("diabetes") && (item_alias.equals("aftercare_1") || item_alias.equals("aftercare_2")
                || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4"))) {
            goToNextFragmentFromHealthReport(new DiabetesAftercareFragment(title), record_id);
        } else if (item_alias.equals("body_exam_table") || item_alias.equals("physical_examination")) {
            goToNextFragmentFromHealthReport(new BodyExamFragment(title), record_id);

        } else if (type_alias.equals("vaccine") && item_alias.equals("vaccine_card")) {
            goToNextFragmentFromHealthReport(new VaccineCardFragment(title), record_id);

        } else if (type_alias.equals("vaccine") && !item_alias.equals("vaccine_card")) {
            goToNextFragmentFromHealthReport(new VaccinationFragment(title), record_id);
        } else if (type_alias.equals("tcm") && (item_alias.equals("aftercare_6_month") || item_alias.equals("aftercare_12_month")
                || item_alias.equals("aftercare_18_month") || item_alias.equals("aftercare_24_month")
                || item_alias.equals("aftercare_30_month") || item_alias.equals("aftercare_3_year"))) {
            goToNextFragmentFromHealthReport(new TcmAftercareFragment(title), record_id);

        } else if (type_alias.equals("tcm") && item_alias.equals("constitution_identification")) {
            goToNextFragmentFromHealthReport(new OldIdentifyFragment(), record_id);
        } else if (type_alias.equals("psychiatric") && (item_alias.equals("aftercare_1") || item_alias.equals("aftercare_2")
                || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4")
                || item_alias.equals("aftercare_5") || item_alias.equals("aftercare_6")
                || item_alias.equals("aftercare_7") || item_alias.equals("aftercare_8"))) {
            goToNextFragmentFromHealthReport(new PsychiatricAftercareFragment(), record_id);
        } else if (type_alias.equals("child") && item_alias.equals("newborn_family_visit")) {
            goToNextFragmentFromHealthReport(new NewbornFamilyVisitFragment(), record_id);

        } else if (type_alias.equals("child") && item_alias.equals("aftercare_1_month")) {
            goToNextFragmentFromHealthReport(new Aftercare1MonthFragment(), record_id);

        } else if (type_alias.equals("child") && item_alias.equals("aftercare_12_month")) {
            goToNextFragmentFromHealthReport(new Aftercare12MonthFragment(), record_id);

        } else if (type_alias.equals("child") && item_alias.equals("aftercare_18_month")) {
            goToNextFragmentFromHealthReport(new Aftercare18MonthFragment(), record_id);

        } else if (type_alias.equals("child") && item_alias.equals("aftercare_24_month")) {
            goToNextFragmentFromHealthReport(new Aftercare24MonthFragment(), record_id);

        } else if (type_alias.equals("child") && item_alias.equals("aftercare_30_month")) {
            goToNextFragmentFromHealthReport(new Aftercare30MonthFragment(), record_id);

        } else if (type_alias.equals("child") && (item_alias.equals("aftercare_4_year")
                || item_alias.equals("aftercare_5_year") || item_alias.equals("aftercare_6_year"))) {
            goToNextFragmentFromHealthReport(new Aftercare4To6YearFragment(), record_id);

        } else if (type_alias.equals("child") && item_alias.equals("aftercare_3_year")) {
            goToNextFragmentFromHealthReport(new Aftercare3YearFragment(), record_id);

        } else if (type_alias.equals("child") && item_alias.equals("aftercare_3_month")) {
            goToNextFragmentFromHealthReport(new Aftercare3MonthFragment(), record_id);

        } else if (type_alias.equals("child") && item_alias.equals("aftercare_6_month")) {
            goToNextFragmentFromHealthReport(new Aftercare6MonthFragment(), record_id);

        } else if (type_alias.equals("child") && item_alias.equals("aftercare_8_month")) {
            goToNextFragmentFromHealthReport(new Aftercare8MonthFragment(), record_id);
        } else if (type_alias.equals("old") && item_alias.equals("living_selfcare_appraisal")) {
            goToNextFragmentFromHealthReport(new OldPeopleLifeAbilityFragment(), record_id);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onLoad() {
        swipeLayout.setLoading(false);
    }
}
