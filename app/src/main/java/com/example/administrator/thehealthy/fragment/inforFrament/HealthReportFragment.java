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
import com.example.administrator.thehealthy.adapter.HealthRrportExpandAdapter;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.entity.AppConfig;
import com.example.administrator.thehealthy.entity.AppData;
import com.example.administrator.thehealthy.entity.Summary;
import com.example.administrator.thehealthy.fragment.BaseFatherFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.Aftercare12MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.Aftercare18MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.Aftercare1MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.Aftercare24MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.Aftercare30MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.Aftercare3MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.Aftercare3YearFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.Aftercare4To6YearFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.Aftercare6MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.Aftercare8MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.AftercareFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.AntenatalFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.BodyExamFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.DiabetesAftercareFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.HypertensionAftercareFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.NewbornFamilyVisitFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.OldIdentifyFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.OldPeopleLifeAbilityFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.PostpartumManyDaysHealthCheck;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.PostpartumVisitFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.PsychiatricAftercareFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.TcmAftercareFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.VaccinationFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment.VaccineCardFragment;
import com.example.administrator.thehealthy.tools.ChangeString;
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
 * Created by Administrator on 2016/4/11.
 * 健康报告界面
 */
public class HealthReportFragment extends BaseFatherFragment implements SwipeRefreshLoadingLayout.OnRefreshListener, SwipeRefreshLoadingLayout.OnLoadListener {
    private final String TAG = HealthReportFragment.class.getSimpleName();
    private DBTool dbTool;
    private ExpandableListView exListView;
    private HealthRrportExpandAdapter expandAdapter;
    private LinearLayout pleaseLoginLinear;
    private TextView nothingText;
    private SwipeRefreshLoadingLayout swipeLoading;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_health_report;
    }

    @Override
    protected void initView() {

        nothingText = findView(R.id.text_health_report_nothing);
        pleaseLoginLinear = findView(R.id.linear_pleaseLogin);
        pleaseLoginLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityIntent(getActivity(), LoginActivity.class);
            }
        });

        dbTool = new DBTool();
        exListView = findView(R.id.exlistView_health_report);
        expandAdapter = new HealthRrportExpandAdapter(getActivity(), AppData.hrGroups, AppData.hrChilds);
        initNetWork();
        exListView.setAdapter(expandAdapter);
        swipeLoading = findView(R.id.swipeRefresh_health_report);
        swipeLoading.setOnRefreshListener(this);
        swipeLoading.setOnLoadListener(this);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onRefresh() {

        initNetWork();

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
            nothingText.setVisibility(View.GONE);
            exListView.setVisibility(View.VISIBLE);
            final HashMap<String, String> user = dbTool.getUserDetails();


            final StringRequest request = new StringRequest(Request.Method.POST,
                    AppConfig.URL_SUMMARYS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    swipeLoading.setRefreshing(false);

                    Log.i(TAG, "健康报告界面数据网络通信响应");
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");

                        if (!error) {
//
                            // 得到一级分类的数据加入groups集合中
                            JSONArray member = jsonObject.getJSONArray("member");
                            for (int i = 0; i < member.length(); i++) {
//                                groups.add(i, member.getJSONObject(i).getString("resident"));
                                AppData.hrGroups.add(i, member.getJSONObject(i).getString("resident"));
                                Log.i(TAG, "------> member.length" + member.getJSONObject(i).getString("resident"));
                            }
//                            expandAdapter.addGroups(AppData.hrGroups);


//                             得到一级分类对应的二级分类数据，加入childs集合中
                            JSONArray summaries = jsonObject.getJSONArray("summary");
                            for (int i = 0; i < summaries.length(); i++) {
                                List<Summary> child = new ArrayList<>();
                                Log.i(TAG, "---------->  summaries.length" + summaries.length() + "  " + i);
                                JSONArray array = summaries.getJSONArray(i);
                                Log.i(TAG, "---------->  array.length" + array.length() + "  " + i);

                                for (int k = 0; k < array.length(); k++) {
                                    JSONObject item = (JSONObject) array.get(k);
                                    Summary summary = new Summary();

                                    summary.setRecordId(item.getInt("record_id"));
                                    summary.setTitle(item.getString("title"));
                                    summary.setResident(item.getString("resident"));
                                    summary.setClinic(item.getString("clinic"));
                                    summary.setProvider(item.getString("provider"));
                                    summary.setServiceTime(item.getString("service_time"));
                                    summary.setTypeAlias(item.getString("type_alias"));
                                    summary.setItemAlias(item.getString("item_alias"));
                                    child.add(k, summary);
                                    dbTool.addSummary(summary);
                                }
                                AppData.hrChilds.add(i, child);
                            }

//                            expandAdapter.addChilds(AppData.hrChilds);
                            // 为了刷新时 groups 和 childs 同时显示
                            // 同时将两个集合传入adapter中
                            expandAdapter.addToReportGroupsChilds(AppData.hrGroups, AppData.hrChilds);

                        } else {
                            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/splash_discrip_text_type.ttf");
                            nothingText.setTypeface(typeface);
                            nothingText.setText("还未有相关记录");
                            nothingText.setVisibility(View.VISIBLE);
                            exListView.setVisibility(View.GONE);
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

                goWhich(AppData.hrChilds.get(groupPosition).get(childPosition).getTypeAlias(),
                        AppData.hrChilds.get(groupPosition).get(childPosition).getItemAlias(),
                        ChangeString.splitForPurpose(AppData.hrChilds.get(groupPosition).get(childPosition).getTitle()),
                        AppData.hrChilds.get(groupPosition).get(childPosition).getRecordId());


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
        swipeLoading.setLoading(false);
    }
}
