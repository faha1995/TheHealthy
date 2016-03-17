package com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment;

import android.os.Bundle;
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
import com.example.administrator.thehealthy.tools.ChangeString;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 */
public class DiabetesAftercareFragment extends BaseFragment {
    private final String TAG = DiabetesAftercareFragment.class.getSimpleName();
    private DBTool dbTool;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_diabetes_aftercare;
    }

    @Override
    protected void initView() {
        dbTool = new DBTool();
        Log.i(TAG,"---->  initView()");
    }

    @Override
    protected void initData() {
        Log.i(TAG,"---->  initData()");
         Bundle bundle = getArguments();
        final int record_id = bundle.getInt("record_id",0);
        Log.i(TAG,"----> "+ record_id);
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
                                    TextView visit_date = findView(R.id.visit_date);
                                    TextView doctor_signature = findView(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));
                                    Log.i(TAG,"---->"+detail.getString("doctor_signature") );
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView visit_way = findView(R.id.visit_way);
                                    visit_way.setText(detail.getString("visit_way"));
                                    TextView symptom = findView(R.id.symptom);
                                    symptom.setText(ChangeString.splitMainMore(detail.getString("symptom")));
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
                                    TextView sign_acrotarsium_artery_pulse = findView(R.id.sign_acrotarsium_artery_pulse);
                                    sign_acrotarsium_artery_pulse.setText(detail.getString("sign_acrotarsium_artery_pulse"));
                                    TextView sign_extra = findView(R.id.sign_extra);
                                    sign_extra.setText(detail.getString("sign_extra"));
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
                                    TextView life_style_guide_staple = findView(R.id.life_style_guide_staple);
                                    life_style_guide_staple.setText(detail.getString("life_style_guide_staple"));
                                    TextView life_style_guide_staple_next = findView(R.id.life_style_guide_staple_next);
                                    life_style_guide_staple_next.setText(detail.getString("life_style_guide_staple_next"));
                                    TextView life_style_guide_mentality = findView(R.id.life_style_guide_mentality);
                                    life_style_guide_mentality.setText(detail.getString("life_style_guide_mentality"));
                                    TextView life_style_guide_medical_compliance = findView(R.id.life_style_guide_medical_compliance);
                                    life_style_guide_medical_compliance.setText(detail.getString("life_style_guide_medical_compliance"));
                                    TextView auxiliary_examination_fbg_value = findView(R.id.auxiliary_examination_fbg_value);
                                    auxiliary_examination_fbg_value.setText(detail.getString("auxiliary_examination_fbg_value"));
                                    TextView auxiliary_examination_extra_hemoglobin = findView(R.id.auxiliary_examination_extra_hemoglobin);
                                    auxiliary_examination_extra_hemoglobin.setText(detail.getString("auxiliary_examination_extra_hemoglobin"));
                                    TextView auxiliary_examination_extra_examination_date = findView(R.id.auxiliary_examination_extra_examination_date);
                                    auxiliary_examination_extra_examination_date.setText(detail.getString("auxiliary_examination_extra_examination_date"));
                                    TextView take_medicine_compliance = findView(R.id.take_medicine_compliance);
                                    take_medicine_compliance.setText(detail.getString("take_medicine_compliance"));
                                    TextView medicine_untoward_effect = findView(R.id.medicine_untoward_effect);
                                    medicine_untoward_effect.setText(detail.getString("medicine_untoward_effect"));
                                    TextView hypoglycemia_reaction = findView(R.id.hypoglycemia_reaction);
                                    hypoglycemia_reaction.setText(detail.getString("hypoglycemia_reaction"));
                                    TextView take_medicine_insulin = findView(R.id.take_medicine_insulin);
                                    take_medicine_insulin.setText(detail.getString("take_medicine_insulin"));
                                    TextView take_medicine_insulin_volume = findView(R.id.take_medicine_insulin_volume);
                                    take_medicine_insulin_volume.setText(detail.getString("take_medicine_insulin_volume"));
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
