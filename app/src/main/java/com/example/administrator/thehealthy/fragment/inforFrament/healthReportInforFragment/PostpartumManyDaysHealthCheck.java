package com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.entity.AppConfig;
import com.example.administrator.thehealthy.fragment.BaseSonFragment;
import com.example.administrator.thehealthy.tools.ChangeString;
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/20.
 */
public class PostpartumManyDaysHealthCheck extends BaseSonFragment {
    private final String TAG = PostpartumManyDaysHealthCheck.class.getSimpleName();
    private TextView visitDateTv, visitDoctorTv, healthStuationTv, mentalitySituationTv,
            sbpTv, dbpTv, breastTv, lochiaTv, uterusTv, woundTv, extraTv, classificationTv,
            guideTv, disposeTv;
    private LinearLayout linearSbpDanwei;
    private int record_id;
    private ScrollView scrollView;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_postpartum_manydays_healthcheck;
    }

    @Override
    protected void initView() {
        record_id = getArguments().getInt("record_id", 0);

        linearSbpDanwei = findView(R.id.linear_manyDays_postPartum_sbpDanwei);
        visitDateTv = findView(R.id.visit_date);
        visitDoctorTv = findView(R.id.doctor_signature);
        healthStuationTv = findView(R.id.manyDaysCheck_general_health_situation);
        mentalitySituationTv = findView(R.id.manyDaysCheck_general_mentality_situation);
        sbpTv = findView(R.id.manyDaysCheck_sbp);
        dbpTv = findView(R.id.manyDaysCheck_dbp);
        breastTv = findView(R.id.manyDaysCheck_breast);
        lochiaTv = findView(R.id.manyDaysCheck_lochia);
        uterusTv = findView(R.id.manyDaysCheck_uterus);
        woundTv = findView(R.id.manyDaysCheck_wound);
        extraTv = findView(R.id.manyDaysCheck_extra);
        classificationTv = findView(R.id.manyDaysCheck_classification);
        guideTv = findView(R.id.manyDaysCheck_guide);
        disposeTv = findView(R.id.manyDaysCheck_dispose);
        scrollView = findView(R.id.scrollView_manyDays_postpartum);
        ScrollViewOnTouch.getInstance().setScrollView(scrollView);


    }

    @Override
    protected void initData() {
        if (record_id != 0) {
            Log.e(TAG, "开始从后台获取详情");
            final StringRequest detailReq = new StringRequest(
                    Request.Method.POST,
                    AppConfig.URL_DETAIL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (!obj.getBoolean("error")) {
                                    JSONObject detail = obj.getJSONObject("detail");

                                    visitDateTv.setText(detail.getString("visit_date"));
                                    visitDoctorTv.setText(detail.getString("doctor_signature"));
                                    healthStuationTv.setText(detail.getString("general_health_situation"));
                                    mentalitySituationTv.setText(detail.getString("general_mentality_situation"));
                                    if (detail.getInt("sbp") < 1) {
                                        linearSbpDanwei.setVisibility(View.INVISIBLE);
                                    } else {
                                        sbpTv.setText(detail.getInt("sbp")+"");
                                        dbpTv.setText(detail.getInt("dbp")+"");
                                    }

                                    breastTv.setText(detail.getString("breast"));
                                    lochiaTv.setText(detail.getString("lochia"));
                                    uterusTv.setText(detail.getString("uterus"));
                                    woundTv.setText(detail.getString("wound"));
                                    extraTv.setText(detail.getString("extra"));
                                    classificationTv.setText(detail.getString("classification"));
                                    disposeTv.setText(detail.getString("dispose"));

                                    JSONArray array = detail.getJSONArray("guide");
                                    String guide = "";
                                    if (array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {
                                            guide += ChangeString.splitOut((String) array.get(i))+"  ";

                                        }
                                    }
                                    guideTv.setText(guide);

                                } else {
                                    String errorMsg = obj.getString("error_msg");
                                    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("record_id", Integer.toString(record_id));
                    Log.e(TAG, "to get record_id: " + record_id);
                    return params;
                }
            };
            VolleySingleton.getInstace().addRequest(detailReq);
        } else {
            Toast.makeText(getActivity(), "没有获得记录ID", Toast.LENGTH_LONG).show();
        }

    }
}
