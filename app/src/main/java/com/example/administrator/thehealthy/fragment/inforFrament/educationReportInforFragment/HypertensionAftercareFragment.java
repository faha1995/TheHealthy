package com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.application.AppConfig;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 */
public class HypertensionAftercareFragment extends BaseFragment {
    private final String TAG = HypertensionAftercareFragment.class.getSimpleName();
    private DBTool dbTool;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_hypertension_aftercare;
    }

    @Override
    protected void initView() {
        dbTool = new DBTool();

    }

    @Override
    protected void initData() {

        final Integer record_id = getArguments().getInt("record_id", 0);

        if (record_id != 0) {
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
                                    TextView visit_date = findView(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView visit_way = findView(R.id.visit_way);
                                    visit_way.setText(detail.getString("visit_way"));
                                    TextView doctor_signature = findView(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));
                                    TextView symptom = findView(R.id.symptom);
                                    symptom.setText(detail.getString("symptom"));
                                    TextView visit_classification = findView(R.id.visit_classification);
                                    visit_classification.setText(detail.getString("visit_classification"));
                                    TextView transfer_treatment_reason = findView(R.id.transfer_treatment_reason);
                                    transfer_treatment_reason.setText(detail.getString("transfer_treatment_reason"));
                                    TextView transfer_treatment_institution = findView(R.id.transfer_treatment_institution);
                                    transfer_treatment_institution.setText(detail.getString("transfer_treatment_institution"));
                                    TextView next_visit_date = findView(R.id.next_visit_date);
                                    next_visit_date.setText(detail.getString("next_visit_date"));
                                    TextView sign_sbp = findView(R.id.sign_sbp);
                                    sign_sbp.setText(detail.getString("sign_sbp"));
                                    TextView sign_dbp = findView(R.id.sign_dbp);
                                    sign_dbp.setText(detail.getString("sign_dbp"));
                                    TextView sign_weight = findView(R.id.sign_weight);
                                    sign_weight.setText(detail.getString("sign_weight"));
                                    TextView sign_weight_next = findView(R.id.sign_weight_next);
                                    sign_weight_next.setText(detail.getString("sign_weight_next"));
                                    TextView sign_bmi = findView(R.id.sign_bmi);
                                    sign_bmi.setText(detail.getString("sign_bmi"));
                                    TextView sign_bmi_next = findView(R.id.sign_bmi_next);
                                    sign_bmi_next.setText(detail.getString("sign_bmi_next"));
                                    TextView sign_heart_rhythm = findView(R.id.sign_heart_rhythm);
                                    sign_heart_rhythm.setText(detail.getString("sign_heart_rhythm"));
                                    TextView life_style_guide_smoke = findView(R.id.life_style_guide_smoke);
                                    life_style_guide_smoke.setText(detail.getString("life_style_guide_smoke"));
                                    TextView life_style_guide_smoke_next = findView(R.id.life_style_guide_smoke_next);
                                    life_style_guide_smoke_next.setText(detail.getString("life_style_guide_smoke_next"));
                                    TextView life_style_guide_liquor = findView(R.id.life_style_guide_liquor);
                                    life_style_guide_liquor.setText(detail.getString("life_style_guide_liquor"));
                                    TextView life_style_guide_liquor_next = findView(R.id.life_style_guide_liquor_next);
                                    life_style_guide_liquor_next.setText(detail.getString("life_style_guide_liquor_next"));
                                    TextView life_style_guide_sport1 = findView(R.id.life_style_guide_sport1);
                                    life_style_guide_sport1.setText(detail.getString("life_style_guide_sport1"));
                                    TextView life_style_guide_sport2 = findView(R.id.life_style_guide_sport2);
                                    life_style_guide_sport2.setText(detail.getString("life_style_guide_sport2"));
                                    TextView life_style_guide_sport3 = findView(R.id.life_style_guide_sport3);
                                    life_style_guide_sport3.setText(detail.getString("life_style_guide_sport3"));
                                    TextView life_style_guide_sport4 = findView(R.id.life_style_guide_sport4);
                                    life_style_guide_sport4.setText(detail.getString("life_style_guide_sport4"));
                                    TextView life_style_guide_salt = findView(R.id.life_style_guide_salt);
                                    life_style_guide_salt.setText(detail.getString("life_style_guide_salt"));
                                    TextView life_style_guide_salt_next = findView(R.id.life_style_guide_salt_next);
                                    life_style_guide_salt_next.setText(detail.getString("life_style_guide_salt_next"));
                                    TextView life_style_guide_mentality = findView(R.id.life_style_guide_mentality);
                                    life_style_guide_mentality.setText(detail.getString("life_style_guide_mentality"));
                                    TextView life_style_guide_medical_compliance = findView(R.id.life_style_guide_medical_compliance);
                                    life_style_guide_medical_compliance.setText(detail.getString("life_style_guide_medical_compliance"));
                                    TextView auxiliary_examination = findView(R.id.auxiliary_examination);
                                    auxiliary_examination.setText(detail.getString("auxiliary_examination"));
                                    TextView take_medicine_compliance = findView(R.id.take_medicine_compliance);
                                    take_medicine_compliance.setText(detail.getString("take_medicine_compliance"));
                                    TextView medicine_untoward_effect = findView(R.id.medicine_untoward_effect);
                                    medicine_untoward_effect.setText(detail.getString("medicine_untoward_effect"));
                                    if (!detail.getString("take_medicine_1").equals("null")) {
                                        TextView take_medicine_1 = findView(R.id.take_medicine_1);
                                        take_medicine_1.setText(detail.getString("take_medicine_1"));
                                    }
                                    if (!detail.getString("take_medicine_1_day").equals("null")) {
                                        TextView take_medicine_1_day = findView(R.id.take_medicine_1_day);
                                        take_medicine_1_day.setText(detail.getString("take_medicine_1_day"));
                                    }
                                    if (!detail.getString("take_medicine_1_time").equals("null")) {
                                        TextView take_medicine_1_time = findView(R.id.take_medicine_1_time);
                                        take_medicine_1_time.setText(detail.getString("take_medicine_1_time"));
                                    }
                                    if (!detail.getString("take_medicine_2").equals("null")) {
                                        TextView take_medicine_2 = findView(R.id.take_medicine_2);
                                        take_medicine_2.setText(detail.getString("take_medicine_2"));
                                    }
                                    if (!detail.getString("take_medicine_2_day").equals("null")) {
                                        TextView take_medicine_2_day = findView(R.id.take_medicine_2_day);
                                        take_medicine_2_day.setText(detail.getString("take_medicine_2_day"));
                                    }
                                    if (!detail.getString("take_medicine_2_time").equals("null")) {
                                        TextView take_medicine_2_time = findView(R.id.take_medicine_2_time);
                                        take_medicine_2_time.setText(detail.getString("take_medicine_2_time"));
                                    }
                                    if (!detail.getString("take_medicine_3").equals("null")) {
                                        TextView take_medicine_3 = findView(R.id.take_medicine_3);
                                        take_medicine_3.setText(detail.getString("take_medicine_3"));
                                    }
                                    if (!detail.getString("take_medicine_3_day").equals("null")) {
                                        TextView take_medicine_3_day = findView(R.id.take_medicine_3_day);
                                        take_medicine_3_day.setText(detail.getString("take_medicine_3_day"));
                                    }
                                    if (!detail.getString("take_medicine_3_time").equals("null")) {
                                        TextView take_medicine_3_time = findView(R.id.take_medicine_3_time);
                                        take_medicine_3_time.setText(detail.getString("take_medicine_3_time"));
                                    }
                                    if (!detail.getString("take_medicine_qt").equals("null")) {
                                        TextView take_medicine_qt = findView(R.id.take_medicine_qt);
                                        take_medicine_qt.setText(detail.getString("take_medicine_qt"));
                                    }
                                    if (!detail.getString("take_medicine_qt_day").equals("null")) {
                                        TextView take_medicine_qt_day = findView(R.id.take_medicine_qt_day);
                                        take_medicine_qt_day.setText(detail.getString("take_medicine_qt_day"));
                                    }
                                    if (!detail.getString("take_medicine_qt_time").equals("null")) {
                                        TextView take_medicine_qt_time = findView(R.id.take_medicine_qt_time);
                                        take_medicine_qt_time.setText(detail.getString("take_medicine_qt_time"));
                                    }


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
            }
            ) {
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
