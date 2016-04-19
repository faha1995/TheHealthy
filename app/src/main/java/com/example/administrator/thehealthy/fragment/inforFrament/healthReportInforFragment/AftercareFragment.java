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
import com.example.administrator.thehealthy.fragment.BaseFatherFragment;
import com.example.administrator.thehealthy.fragment.BaseSonFragment;
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 */
public class AftercareFragment extends BaseSonFragment {
    private final String TAG = AftercareFragment.class.getSimpleName();
    private int record_id;
    private ScrollView scrollViewAfter;
    private ScrollViewOnTouch scrollViewOnTouch = new ScrollViewOnTouch();
    private TextView afterTitle;
    private String titles;
    private LinearLayout linearSbpDanwei;

    public AftercareFragment(String titles) {
        this.titles = titles;
    }

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_aftercare;
    }

    @Override
    protected void initView() {
        afterTitle = findView(R.id.aftercare_title);
        linearSbpDanwei = findView(R.id.linear_aftercareSbpDanwei);
        record_id = getArguments().getInt("record_id", 0);
        scrollViewAfter = findView(R.id.scrollView_aftercare);
        scrollViewOnTouch.setScrollView(scrollViewAfter);
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
                                    // Toast.makeText(getApplicationContext(), detail.getString("visit_date"), Toast.LENGTH_SHORT).show();
                                    afterTitle.setText(titles);
                                    TextView visit_date = findView(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView doctor_signature = findView(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));
                                    TextView gestational_weeks = findView(R.id.gestational_weeks);
                                    gestational_weeks.setText(detail.getString("gestational_weeks"));
                                    TextView complaint = findView(R.id.complaint);
                                    complaint.setText(detail.getString("complaint"));
                                    TextView weight = findView(R.id.weight);
                                    weight.setText(detail.getString("weight"));
                                    TextView sbp = findView(R.id.sbp);
                                    sbp.setText(detail.getString("sbp"));
                                    if (detail.getString("sbp").equals("")) {
                                        linearSbpDanwei.setVisibility(View.INVISIBLE);
                                    }
                                    TextView dbp = findView(R.id.dbp);
                                    dbp.setText(detail.getString("dbp"));
                                    TextView hemoglobin = findView(R.id.hemoglobin);
                                    hemoglobin.setText(detail.getString("hemoglobin"));
                                    TextView urine_protein = findView(R.id.urine_protein);
                                    urine_protein.setText(detail.getString("urine_protein"));
                                    TextView examination_before_parturition_uteri_bottom_height = findView(R.id.examination_before_parturition_uteri_bottom_height);
                                    examination_before_parturition_uteri_bottom_height.setText(detail.getString("examination_before_parturition_uteri_bottom_height"));
                                    TextView examination_before_parturition_abdomen_circumference = findView(R.id.examination_before_parturition_abdomen_circumference);
                                    examination_before_parturition_abdomen_circumference.setText(detail.getString("examination_before_parturition_abdomen_circumference"));
                                    TextView examination_before_parturition_fetus_position = findView(R.id.examination_before_parturition_fetus_position);
                                    examination_before_parturition_fetus_position.setText(detail.getString("examination_before_parturition_fetus_position"));
                                    TextView examination_before_parturition_fetal_heart_rate = findView(R.id.examination_before_parturition_fetal_heart_rate);
                                    examination_before_parturition_fetal_heart_rate.setText(detail.getString("examination_before_parturition_fetal_heart_rate"));
                                    TextView extra_auxiliary_examination = findView(R.id.extra_auxiliary_examination);
                                    extra_auxiliary_examination.setText(detail.getString("extra_auxiliary_examination"));
                                    TextView classification = findView(R.id.classification);
                                    classification.setText(detail.getString("classification"));
                                    TextView classification_abnormal = findView(R.id.classification_abnormal);
                                    classification_abnormal.setText(detail.getString("classification_abnormal"));
                                    TextView guide = findView(R.id.guide);
                                    guide.setText(detail.getString("guide"));
                                    TextView transfer_treatment = findView(R.id.transfer_treatment);
                                    transfer_treatment.setText(detail.getString("transfer_treatment"));
                                    TextView transfer_treatment_reason = findView(R.id.transfer_treatment_reason);
                                    transfer_treatment_reason.setText(detail.getString("transfer_treatment_reason"));
                                    TextView transfer_treatment_institution = findView(R.id.transfer_treatment_institution);
                                    transfer_treatment_institution.setText(detail.getString("transfer_treatment_institution"));
                                    TextView next_visit_date = findView(R.id.next_visit_date);
                                    next_visit_date.setText(detail.getString("next_visit_date"));

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
