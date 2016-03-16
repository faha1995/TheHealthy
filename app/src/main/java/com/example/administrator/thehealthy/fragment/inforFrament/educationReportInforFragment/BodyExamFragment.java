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
public class BodyExamFragment extends BaseFragment {
    private final String TAG = BodyExamFragment.class.getSimpleName();
    private DBTool dbTool;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_body_exam;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        final Integer record_id = getArguments().getInt("record_id", 0);


        if (record_id != 0) {
            Log.e(TAG, "开始从后台获取详情");
            final StringRequest detailReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_DETAIL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (!obj.getBoolean("error")) {
                            JSONObject detail = obj.getJSONObject("detail");
                            // Toast.makeText(getApplicationContext(), detail.getString("visit_date"), Toast.LENGTH_SHORT).show();
                            TextView doctor = findView(R.id.doctor);
                            doctor.setText(detail.getString("doctor"));
                            TextView body_temperature = findView(R.id.body_temperature);
                            body_temperature.setText(detail.getString("body_temperature"));
                            TextView pulse = findView(R.id.pulse);
                            pulse.setText(detail.getString("pulse"));
                            TextView breath_frequency = findView(R.id.breath_frequency);
                            breath_frequency.setText(detail.getString("breath_frequency"));
                            TextView blood_pressure_left_sbp = findView(R.id.blood_pressure_left_sbp);
                            blood_pressure_left_sbp.setText(detail.getString("blood_pressure_left_sbp"));
                            TextView blood_pressure_left_dbp = findView(R.id.blood_pressure_left_dbp);
                            blood_pressure_left_dbp.setText(detail.getString("blood_pressure_left_dbp"));
                            TextView blood_pressure_right_sbp = findView(R.id.blood_pressure_right_sbp);
                            blood_pressure_right_sbp.setText(detail.getString("blood_pressure_right_sbp"));
                            TextView blood_pressure_right_dbp = findView(R.id.blood_pressure_right_dbp);
                            blood_pressure_right_dbp.setText(detail.getString("blood_pressure_right_dbp"));
                            TextView height = findView(R.id.height);
                            height.setText(detail.getString("height"));
                            TextView weight = findView(R.id.weight);
                            weight.setText(detail.getString("weight"));
                            TextView waistline = findView(R.id.waistline);
                            waistline.setText(detail.getString("waistline"));
                            TextView body_mass_index = findView(R.id.body_mass_index);
                            body_mass_index.setText(detail.getString("body_mass_index"));
                            TextView mouth_lip = findView(R.id.mouth_lip);
                            mouth_lip.setText(detail.getString("mouth_lip"));
                            TextView mouth_tooth = findView(R.id.mouth_tooth);
                            mouth_tooth.setText(detail.getString("mouth_tooth"));
                            if (!detail.getString("mouth_tooth_missing_upleft").equals("null")) {
                                TextView mouth_tooth_missing_upleft = findView(R.id.mouth_tooth_missing_upleft);
                                mouth_tooth_missing_upleft.setText(detail.getString("mouth_tooth_missing_upleft"));
                            }
                            if (!detail.getString("mouth_tooth_missing_bottomleft").equals("null")) {
                                TextView mouth_tooth_missing_bottomleft = findView(R.id.mouth_tooth_missing_bottomleft);
                                mouth_tooth_missing_bottomleft.setText(detail.getString("mouth_tooth_missing_bottomleft"));
                            }
                            if (!detail.getString("mouth_tooth_missing_upright").equals("null")) {
                                TextView mouth_tooth_missing_upright = findView(R.id.mouth_tooth_missing_upright);
                                mouth_tooth_missing_upright.setText(detail.getString("mouth_tooth_missing_upright"));
                            }
                            if (!detail.getString("mouth_tooth_missing_bottomright").equals("null")) {
                                TextView mouth_tooth_missing_bottomright = findView(R.id.mouth_tooth_missing_bottomright);
                                mouth_tooth_missing_bottomright.setText(detail.getString("mouth_tooth_missing_bottomright"));
                            }
                            if (!detail.getString("mouth_tooth_decayed_upleft").equals("null")) {
                                TextView mouth_tooth_decayed_upleft = findView(R.id.mouth_tooth_decayed_upleft);
                                mouth_tooth_decayed_upleft.setText(detail.getString("mouth_tooth_decayed_upleft"));
                            }
                            if (!detail.getString("mouth_tooth_decayed_bottomleft").equals("null")) {
                                TextView mouth_tooth_decayed_bottomleft = findView(R.id.mouth_tooth_decayed_bottomleft);
                                mouth_tooth_decayed_bottomleft.setText(detail.getString("mouth_tooth_decayed_bottomleft"));
                            }
                            if (!detail.getString("mouth_tooth_decayed_upright").equals("null")) {
                                TextView mouth_tooth_decayed_upright = findView(R.id.mouth_tooth_decayed_upright);
                                mouth_tooth_decayed_upright.setText(detail.getString("mouth_tooth_decayed_upright"));
                            }
                            if (!detail.getString("mouth_tooth_decayed_bottomright").equals("null")) {
                                TextView mouth_tooth_decayed_bottomright = findView(R.id.mouth_tooth_decayed_bottomright);
                                mouth_tooth_decayed_bottomright.setText(detail.getString("mouth_tooth_decayed_bottomright"));
                            }
                            if (!detail.getString("mouth_tooth_denture_upleft").equals("null")) {
                                TextView mouth_tooth_denture_upleft = findView(R.id.mouth_tooth_denture_upleft);
                                mouth_tooth_denture_upleft.setText(detail.getString("mouth_tooth_denture_upleft"));
                            }
                            if (!detail.getString("mouth_tooth_denture_bottomleft").equals("null")) {
                                TextView mouth_tooth_denture_bottomleft = findView(R.id.mouth_tooth_denture_bottomleft);
                                mouth_tooth_denture_bottomleft.setText(detail.getString("mouth_tooth_denture_bottomleft"));
                            }
                            if (!detail.getString("mouth_tooth_denture_upright").equals("null")) {
                                TextView mouth_tooth_denture_upright = findView(R.id.mouth_tooth_denture_upright);
                                mouth_tooth_denture_upright.setText(detail.getString("mouth_tooth_denture_upright"));
                            }
                            if (!detail.getString("mouth_tooth_denture_bottomright").equals("null")) {
                                TextView mouth_tooth_denture_bottomright = findView(R.id.mouth_tooth_denture_bottomright);
                                mouth_tooth_denture_bottomright.setText(detail.getString("mouth_tooth_denture_bottomright"));
                            }
                            TextView mouth_throat = findView(R.id.mouth_throat);
                            mouth_throat.setText(detail.getString("mouth_throat"));
                            TextView eyesight_left = findView(R.id.eyesight_left);
                            eyesight_left.setText(detail.getString("eyesight_left"));
                            TextView eyesight_right = findView(R.id.eyesight_right);
                            eyesight_right.setText(detail.getString("eyesight_right"));
                            TextView eyesight_left_rectified = findView(R.id.eyesight_left_rectified);
                            eyesight_left_rectified.setText(detail.getString("eyesight_left_rectified"));
                            TextView eyesight_right_rectified = findView(R.id.eyesight_right_rectified);
                            eyesight_right_rectified.setText(detail.getString("eyesight_right_rectified"));
                            TextView hearing = findView(R.id.hearing);
                            hearing.setText(detail.getString("hearing"));
                            TextView movement_function = findView(R.id.movement_function);
                            movement_function.setText(detail.getString("movement_function"));
                            TextView skin = findView(R.id.skin);
                            skin.setText(detail.getString("skin"));
                            TextView skin_extra = findView(R.id.skin_extra);
                            skin_extra.setText(detail.getString("skin_extra"));
                            TextView lymph_node = findView(R.id.lymph_node);
                            lymph_node.setText(detail.getString("lymph_node"));
                            TextView lymph_node_extra = findView(R.id.lymph_node_extra);
                            lymph_node_extra.setText(detail.getString("lymph_node_extra"));
                            TextView lung_barrel_chested = findView(R.id.lung_barrel_chested);
                            lung_barrel_chested.setText(detail.getString("lung_barrel_chested"));
                            TextView lung_breath_sound = findView(R.id.lung_breath_sound);
                            TextView lung_breath_sound_extra = findView(R.id.lung_breath_sound_extra);
                            lung_breath_sound_extra.setText(detail.getString("lung_breath_sound_extra"));
                            lung_breath_sound.setText(detail.getString("lung_breath_sound"));
                            TextView lung_rale = findView(R.id.lung_rale);
                            lung_rale.setText(detail.getString("lung_rale"));
                            TextView lung_rale_extra = findView(R.id.lung_rale_extra);
                            lung_rale_extra.setText(detail.getString("lung_rale_extra"));
                            TextView heart_rate = findView(R.id.heart_rate);
                            heart_rate.setText(detail.getString("heart_rate"));
                            TextView heart_rhythm = findView(R.id.heart_rhythm);
                            heart_rhythm.setText(detail.getString("heart_rhythm"));
                            TextView heart_noise = findView(R.id.heart_noise);
                            heart_noise.setText(detail.getString("heart_noise"));
                            TextView heart_noise_extra = findView(R.id.heart_noise_extra);
                            heart_noise_extra.setText(detail.getString("heart_noise_extra"));
                            TextView stomach_tenderness = findView(R.id.stomach_tenderness);
                            stomach_tenderness.setText(detail.getString("stomach_tenderness"));
                            TextView stomach_tenderness_extra = findView(R.id.stomach_tenderness_extra);
                            stomach_tenderness_extra.setText(detail.getString("stomach_tenderness_extra"));
                            TextView stomach_enclosed_mass = findView(R.id.stomach_enclosed_mass);
                            stomach_enclosed_mass.setText(detail.getString("stomach_enclosed_mass"));
                            TextView stomach_enclosed_mass_extra = findView(R.id.stomach_enclosed_mass_extra);
                            stomach_enclosed_mass_extra.setText(detail.getString("stomach_enclosed_mass_extra"));
                            TextView stomach_hepatomegaly = findView(R.id.stomach_hepatomegaly);
                            stomach_hepatomegaly.setText(detail.getString("stomach_hepatomegaly"));
                            TextView stomach_hepatomegaly_extra = findView(R.id.stomach_hepatomegaly_extra);
                            stomach_hepatomegaly_extra.setText(detail.getString("stomach_hepatomegaly_extra"));
                            TextView stomach_slenauxe = findView(R.id.stomach_slenauxe);
                            stomach_slenauxe.setText(detail.getString("stomach_slenauxe"));
                            TextView stomach_slenauxe_extra = findView(R.id.stomach_slenauxe_extra);
                            stomach_slenauxe_extra.setText(detail.getString("stomach_slenauxe_extra"));
                            TextView stomach_shifting_dullness = findView(R.id.stomach_shifting_dullness);
                            stomach_shifting_dullness.setText(detail.getString("stomach_shifting_dullness"));
                            TextView stomach_shifting_dullness_extra = findView(R.id.stomach_shifting_dullness_extra);
                            stomach_shifting_dullness_extra.setText(detail.getString("stomach_shifting_dullness_extra"));
                            if (!detail.getString("hemoglobin").equals("null")) {
                                TextView hemoglobin = findView(R.id.hemoglobin);
                                hemoglobin.setText(detail.getString("hemoglobin"));
                            }
                            if (!detail.getString("leucocyte").equals("null")) {
                                TextView leucocyte = findView(R.id.leucocyte);
                                leucocyte.setText(detail.getString("leucocyte"));
                            }
                            if (!detail.getString("blood_platelets").equals("null")) {
                                TextView blood_platelets = findView(R.id.blood_platelets);
                                blood_platelets.setText(detail.getString("blood_platelets"));
                            }
                            if (!detail.getString("blood_routine_test_extra").equals("null")) {
                                TextView blood_routine_test_extra = findView(R.id.blood_routine_test_extra);
                                blood_routine_test_extra.setText(detail.getString("blood_routine_test_extra"));
                            }
                            if (!detail.getString("urine_protein").equals("urine_protein")) {
                                TextView urine_protein = findView(R.id.urine_protein);
                                urine_protein.setText(detail.getString("urine_protein"));
                            }
                            if (!detail.getString("urine_glucose").equals("null")) {
                                TextView urine_glucose = findView(R.id.urine_glucose);
                                urine_glucose.setText(detail.getString("urine_glucose"));
                            }
                            if (!detail.getString("ketone_bodies").equals("null")) {
                                TextView ketone_bodies = findView(R.id.ketone_bodies);
                                ketone_bodies.setText(detail.getString("ketone_bodies"));
                            }
                            if (!detail.getString("occult_blood").equals("null")) {
                                TextView occult_blood = findView(R.id.occult_blood);
                                occult_blood.setText(detail.getString("occult_blood"));
                            }
                            if (!detail.getString("routine_urine_test_extra").equals("null")) {
                                TextView routine_urine_test_extra = findView(R.id.routine_urine_test_extra);
                                routine_urine_test_extra.setText(detail.getString("routine_urine_test_extra"));
                            }
                            if (!detail.getString("blood_glucose_mmol").equals("null")) {
                                TextView blood_glucose_mmol = findView(R.id.blood_glucose_mmol);
                                blood_glucose_mmol.setText(detail.getString("blood_glucose_mmol"));
                            }
                            if (!detail.getString("blood_glucose_mg").equals("null")) {
                                TextView blood_glucose_mg = findView(R.id.blood_glucose_mg);
                                blood_glucose_mg.setText(detail.getString("blood_glucose_mg"));
                            }
                            if (!detail.getString("electr_gram").equals("null")) {
                                TextView electr_gram = findView(R.id.electr_gram);
                                electr_gram.setText(detail.getString("electr_gram"));
                            }
                            if (!detail.getString("electr_gram_abnormal").equals("null")) {
                                TextView electr_gram_abnormal = findView(R.id.electr_gram_abnormal);
                                electr_gram_abnormal.setText(detail.getString("electr_gram_abnormal"));
                            }
                            if (!detail.getString("alt").equals("null")) {
                                TextView alt = findView(R.id.alt);
                                alt.setText(detail.getString("alt"));
                            }
                            if (!detail.getString("ast").equals("null")) {
                                TextView ast = findView(R.id.ast);
                                ast.setText(detail.getString("ast"));
                            }
                            if (!detail.getString("tbil").equals("null")) {
                                TextView tbil = findView(R.id.tbil);
                                tbil.setText(detail.getString("tbil"));
                            }
                            if (!detail.getString("scr").equals("null")) {
                                TextView scr = findView(R.id.scr);
                                scr.setText(detail.getString("scr"));
                            }
                            if (!detail.getString("bun").equals("null")) {
                                TextView bun = findView(R.id.bun);
                                bun.setText(detail.getString("bun"));
                            }
                            if (!detail.getString("tc").equals("null")) {
                                TextView tc = findView(R.id.tc);
                                tc.setText(detail.getString("tc"));
                            }
                            if (!detail.getString("tg").equals("null")) {
                                TextView tg = findView(R.id.tg);
                                tg.setText(detail.getString("tg"));
                            }
                            if (!detail.getString("ldl_c").equals("null")) {
                                TextView ldl_c = findView(R.id.ldl_c);
                                ldl_c.setText(detail.getString("ldl_c"));
                            }
                            if (!detail.getString("hdl_c").equals("null")) {
                                TextView hdl_c = findView(R.id.hdl_c);
                                hdl_c.setText(detail.getString("hdl_c"));
                            }
                            if (!detail.getString("b_ultrasonic").equals("null")) {
                                TextView b_ultrasonic = findView(R.id.b_ultrasonic);
                                b_ultrasonic.setText(detail.getString("b_ultrasonic"));
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
