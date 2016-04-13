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
import com.example.administrator.thehealthy.adapter.ExpandAdapter;
import com.example.administrator.thehealthy.application.AppConfig;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.entity.Summary;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.Aftercare12MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.Aftercare18MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.Aftercare1MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.Aftercare24MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.Aftercare30MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.Aftercare3MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.Aftercare3YearFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.Aftercare4To6YearFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.Aftercare6MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.Aftercare8MonthFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.AftercareFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.AntenatalFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.BodyExamFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.DiabetesAftercareFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.HypertensionAftercareFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.NewbornFamilyVisitFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.OldIdentifyFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.PostpartumVisitFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.PsychiatricAftercareFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.TcmAftercareFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.VaccinationFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.VaccineCardFragment;
import com.example.administrator.thehealthy.tools.MyClickListener;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/11.
 */
public class HealthReFragment extends BaseFragment implements MyClickListener{
    private final String TAG = HealthReFragment.class.getSimpleName();
    private DBTool dbTool;
    private ExpandableListView exListView;
    private ExpandAdapter expandAdapter;
    private List<String> groups = new ArrayList<>();
    private List<List<Summary>> childs = new ArrayList<>();
    private LinearLayout pleaseLoginLinear;
    private TextView nothingText;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_health_repor;
    }

    @Override
    protected void initView() {
//        EventBus.getDefault().register(this);
        nothingText = findView(R.id.text_health_report_nothing);
        pleaseLoginLinear = findView(R.id.linear_pleaseLogin);
//        groups.add("李丽");
//        groups.add("李丽之子");
//        groups.add("李丽侄女");

        dbTool = new DBTool();

        if (dbTool.isLogined()) {
            pleaseLoginLinear.setVisibility(View.GONE);
            final HashMap<String, String> user = dbTool.getUserDetails();


            final StringRequest request = new StringRequest(Request.Method.POST,
                    AppConfig.URL_SUMMARYS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "健康报告界面数据网络通信响应");

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");
                        Log.i(TAG, "------> + error1");

                        if (!error) {
                            Log.i(TAG, "------> + error2");
                            // 得到一级分类的数据加入groups集合中
                            JSONArray member = jsonObject.getJSONArray("member");
                            for (int i = 0; i < member.length(); i++) {
                                groups.add(member.getJSONObject(i).getString("resident"));
                                Log.i(TAG, "------> member.length" + member.getJSONObject(i).getString("resident"));
                                Log.i(TAG, "-------> Groups.size" + groups.size());
                                expandAdapter.addGroups(groups);
                            }

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
//
                                    summary.setRecordId(item.getInt("record_id"));
                                    summary.setTitle(item.getString("title"));
                                    summary.setResident(item.getString("resident"));
                                    summary.setClinic(item.getString("clinic"));
                                    summary.setProvider(item.getString("provider"));
                                    summary.setServiceTime(item.getString("service_time"));
                                    summary.setTypeAlias(item.getString("type_alias"));
                                    summary.setItemAlias(item.getString("item_alias"));
                                    child.add(summary);
                                    dbTool.addSummary(summary);
                                }
                                childs.add(child);
                            }
                            expandAdapter.addChilds(childs);
                        } else {
//                            childs.clear();
                            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/splash_discrip_text_type.ttf");
                            nothingText.setTypeface(typeface);
                            nothingText.setText("还未有相关记录");
                            nothingText.setVisibility(View.VISIBLE);
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
        }


    }

    int group, child;

    @Override
    protected void initData() {
        exListView = findView(R.id.exlistView_health_report);
        expandAdapter = new ExpandAdapter(getActivity(), groups, childs);
        exListView.setAdapter(expandAdapter);
        expandAdapter.setMyClickListener(this);
        exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                group = groupPosition;
                child = childPosition;

                expandAdapter.setPosition(groupPosition, childPosition);
                Log.i("AAA", "--------> group " + groupPosition + " child " + childPosition);
                return true;
            }
        });

    }
//    @Subscribe
//    public void onEvent(List<List<Summary>> childs) {
//        GoWhick(childs.get(group).get(child).getTypeAlias(),
//                childs.get(group).get(child).getItemAlias(),
//                ChangeString.splitForPurpose(childs.get(group).get(child).getTitle()),
//                childs.get(group).get(child).getRecordId());
//        Log.i("AAA","-------> " +childs.get(group).get(child).getTitle());
//    }

    @Override
    public void myOnClickListener(int pos) {

    }

        @Override
    public void myOnClickListener(String type_alias, String item_alias, String title, int record_id) {
//    private void GoWhick(String type_alias, String item_alias, String title, int record_id) {
        if (type_alias.equals("pregnant") && item_alias.equals("aftercare_1")) {
            goToNextFragmentFromPersonal(new AntenatalFragment(title), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("pregnant") && item_alias.equals("postpartum_visit")) {
            goToNextFragmentFromPersonal(new PostpartumVisitFragment(title), record_id);
        } else if (type_alias.equals("pregnant") && (item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3")
                || item_alias.equals("aftercare_4") || item_alias.equals("aftercare_5"))) {
            goToNextFragmentFromPersonal(new AftercareFragment(title), record_id);
        } else if (type_alias.equals("hypertension") && (item_alias.equals("aftercare_1")
                || item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3")
                || item_alias.equals("aftercare_4"))) {
            goToNextFragmentFromPersonal(new HypertensionAftercareFragment(title), record_id);
        } else if (type_alias.equals("diabetes") && (item_alias.equals("aftercare_1") || item_alias.equals("aftercare_2")
                || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4"))) {
            goToNextFragmentFromPersonal(new DiabetesAftercareFragment(title), record_id);
            Log.i(TAG, "------>    OK");
        } else if (item_alias.equals("body_exam_table") || item_alias.equals("physical_examination")) {
            goToNextFragmentFromPersonal(new BodyExamFragment(title), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("vaccine") && item_alias.equals("vaccine_card")) {
            goToNextFragmentFromPersonal(new VaccineCardFragment(title), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("vaccine") && !item_alias.equals("vaccine_card")) {
            goToNextFragmentFromPersonal(new VaccinationFragment(title), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("tcm") && (item_alias.equals("aftercare_6_month") || item_alias.equals("aftercare_12_month")
                || item_alias.equals("aftercare_18_month") || item_alias.equals("aftercare_24_month")
                || item_alias.equals("aftercare_30_month") || item_alias.equals("aftercare_3_year"))) {
            goToNextFragmentFromPersonal(new TcmAftercareFragment(title), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("tcm") && item_alias.equals("constitution_identification")) {
            goToNextFragmentFromPersonal(new OldIdentifyFragment(), record_id);
        } else if (type_alias.equals("psychiatric") && (item_alias.equals("aftercare_1") || item_alias.equals("aftercare_2")
                || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4")
                || item_alias.equals("aftercare_5") || item_alias.equals("aftercare_6")
                || item_alias.equals("aftercare_7") || item_alias.equals("aftercare_8"))) {
            goToNextFragmentFromPersonal(new PsychiatricAftercareFragment(), record_id);
        } else if (type_alias.equals("child") && item_alias.equals("newborn_family_visit")) {
            goToNextFragmentFromPersonal(new NewbornFamilyVisitFragment(), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("child") && item_alias.equals("aftercare_1_month")) {
            goToNextFragmentFromPersonal(new Aftercare1MonthFragment(), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("child") && item_alias.equals("aftercare_12_month")) {
            goToNextFragmentFromPersonal(new Aftercare12MonthFragment(), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("child") && item_alias.equals("aftercare_18_month")) {
            goToNextFragmentFromPersonal(new Aftercare18MonthFragment(), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("child") && item_alias.equals("aftercare_24_month")) {
            goToNextFragmentFromPersonal(new Aftercare24MonthFragment(), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("child") && item_alias.equals("aftercare_30_month")) {
            goToNextFragmentFromPersonal(new Aftercare30MonthFragment(), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("child") && (item_alias.equals("aftercare_4_year")
                || item_alias.equals("aftercare_5_year") || item_alias.equals("aftercare_6_year"))) {
            goToNextFragmentFromPersonal(new Aftercare4To6YearFragment(), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("child") && item_alias.equals("aftercare_3_year")) {
            goToNextFragmentFromPersonal(new Aftercare3YearFragment(), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("child") && item_alias.equals("aftercare_3_month")) {
            goToNextFragmentFromPersonal(new Aftercare3MonthFragment(), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("child") && item_alias.equals("aftercare_6_month")) {
            goToNextFragmentFromPersonal(new Aftercare6MonthFragment(), record_id);
            Log.i(TAG, "------>    OK");
        } else if (type_alias.equals("child") && item_alias.equals("aftercare_8_month")) {
            goToNextFragmentFromPersonal(new Aftercare8MonthFragment(), record_id);
            Log.i(TAG, "------>    OK");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
