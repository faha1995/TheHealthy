package com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment;

import android.util.Log;
import android.widget.ScrollView;
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
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/15.
 * type_alias.equals("pregnant") && item_alias.equals("aftercare_1"）
 * 产前界面
 */
public class AntenatalFragment extends BaseFragment {
    private static final String TAG = AntenatalFragment.class.getSimpleName();
    private DBTool dbTool;
    private int record_id;
    private ScrollView scrollViewAfter;
    private ScrollViewOnTouch scrollViewOnTouch = new ScrollViewOnTouch();

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_antenatal;
    }

    @Override
    protected void initView() {
        dbTool = new DBTool();
        record_id = getArguments().getInt("record_id", 0);
        scrollViewAfter = findView(R.id.scrollView_antenatal);
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
                                    TextView visit_date = findView(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView weeks = findView(R.id.weeks);
                                    weeks.setText(detail.getString("weeks"));
                                    TextView age = findView(R.id.age);
                                    age.setText(detail.getString("age"));
                                    TextView husband_name = findView(R.id.husband_name);
                                    husband_name.setText(detail.getString("husband_name"));
                                    TextView husband_age = findView(R.id.husband_age);
                                    husband_age.setText(detail.getString("husband_age"));
                                    TextView husband_phone = findView(R.id.husband_phone);
                                    husband_phone.setText(detail.getString("husband_phone"));
                                    TextView pregnant_times = findView(R.id.pregnant_times);
                                    pregnant_times.setText(detail.getString("pregnant_times"));
                                    TextView natural_production = findView(R.id.natural_production);
                                    natural_production.setText(detail.getString("natural_production"));
                                    TextView surgery_production = findView(R.id.surgery_production);
                                    surgery_production.setText(detail.getString("surgery_production"));
                                    TextView last_menstruation = findView(R.id.last_menstruation);
                                    last_menstruation.setText(detail.getString("last_menstruation"));
                                    TextView due_date = findView(R.id.due_date);
                                    due_date.setText(detail.getString("due_date"));
                                    TextView disease_history = findView(R.id.disease_history);
                                    disease_history.setText(detail.getString("disease_history"));
                                    TextView family_history = findView(R.id.family_history);
                                    family_history.setText(detail.getString("family_history"));
                                    TextView personal_history = findView(R.id.personal_history);
                                    personal_history.setText(detail.getString("family_history"));
                                    TextView gynaecology_surgery_history = findView(R.id.gynaecology_surgery_history);
                                    gynaecology_surgery_history.setText(detail.getString("gynaecology_surgery_history"));
                                    TextView miscarriage = findView(R.id.miscarriage);
                                    miscarriage.setText(detail.getString("miscarriage"));
                                    TextView dead_fetus = findView(R.id.dead_fetus);
                                    dead_fetus.setText(detail.getString("dead_fetus"));
                                    TextView still_birth = findView(R.id.still_birth);
                                    still_birth.setText(detail.getString("still_birth"));
                                    TextView newnatal_death = findView(R.id.newnatal_death);
                                    newnatal_death.setText(detail.getString("newnatal_death"));
                                    TextView birth_defect = findView(R.id.birth_defect);
                                    birth_defect.setText(detail.getString("birth_defect"));
                                    TextView height = findView(R.id.height);
                                    height.setText(detail.getString("height"));
                                    TextView weight = findView(R.id.weight);
                                    weight.setText(detail.getString("weight"));
                                    TextView bmi = findView(R.id.bmi);
                                    bmi.setText(detail.getString("bmi"));
                                    TextView sbp = findView(R.id.sbp);
                                    sbp.setText(detail.getString("sbp"));
                                    TextView dbp = findView(R.id.dbp);
                                    dbp.setText(detail.getString("dbp"));
                                    TextView ausculate_heart = findView(R.id.ausculate_heart);
                                    ausculate_heart.setText(detail.getString("ausculate_heart"));
                                    TextView ausculate_heart_abnormal = findView(R.id.ausculate_heart_abnormal);
                                    ausculate_heart_abnormal.setText(detail.getString("ausculate_heart_abnormal"));
                                    TextView ausculate_lung = findView(R.id.ausculate_lung);
                                    ausculate_lung.setText(detail.getString("ausculate_lung"));
                                    TextView ausculate_lung_abnormal = findView(R.id.ausculate_lung_abnormal);
                                    ausculate_lung_abnormal.setText(detail.getString("ausculate_lung_abnormal"));
                                    TextView vulva = findView(R.id.vulva);
                                    vulva.setText(detail.getString("vulva"));
                                    TextView vulva_abnormal = findView(R.id.vulva_abnormal);
                                    vulva_abnormal.setText(detail.getString("vulva_abnormal"));
                                    TextView vagina = findView(R.id.vagina);
                                    vagina.setText(detail.getString("vagina"));
                                    TextView vagina_abnormal = findView(R.id.vagina_abnormal);
                                    vagina_abnormal.setText(detail.getString("vagina_abnormal"));
                                    TextView cervix = findView(R.id.cervix);
                                    cervix.setText(detail.getString("cervix"));
                                    TextView uteri = findView(R.id.uteri);
                                    uteri.setText(detail.getString("uteri"));
                                    TextView uteri_abnormal = findView(R.id.uteri_abnormal);
                                    uteri_abnormal.setText(detail.getString("uteri_abnormal"));
                                    TextView accessory = findView(R.id.accessory);
                                    accessory.setText(detail.getString("accessory"));
                                    TextView accessory_abnormal = findView(R.id.accessory_abnormal);
                                    accessory_abnormal.setText(detail.getString("accessory_abnormal"));
                                    if (!detail.getString("hemoglobin").equals("null")) {
                                        TextView hemoglobin = findView(R.id.hemoglobin);
                                        hemoglobin.setText(detail.getString("hemoglobin"));
                                    }
                                    if (!detail.getString("leukocyte").equals("null")) {
                                        TextView leukocyte = findView(R.id.leukocyte);
                                        leukocyte.setText(detail.getString("leukocyte"));
                                    }
                                    if (!detail.getString("thrombocyte").equals("null")) {
                                        TextView thrombocyte = findView(R.id.thrombocyte);
                                        thrombocyte.setText(detail.getString("thrombocyte"));
                                    }
                                    if (!detail.getString("urine_protein").equals("null")) {
                                        TextView urine_protein = findView(R.id.urine_protein);
                                        urine_protein.setText(detail.getString("urine_protein"));
                                    }
                                    if (!detail.getString("urine_glucose").equals("null")) {
                                        TextView urine_glucose = findView(R.id.urine_glucose);
                                        urine_glucose.setText(detail.getString("urine_glucose"));
                                    }
                                    if (!detail.getString("urine_ket").equals("null")) {
                                        TextView urine_ket = findView(R.id.urine_ket);
                                        urine_ket.setText(detail.getString("urine_ket"));
                                    }
                                    if (!detail.getString("urine_ery").equals("null")) {
                                        TextView urine_ery = findView(R.id.urine_ery);
                                        urine_ery.setText(detail.getString("urine_ery"));
                                    }
                                    if (!detail.getString("blood_type_abo").equals("null")) {
                                        TextView blood_type_abo = findView(R.id.blood_type_abo);
                                        blood_type_abo.setText(detail.getString("blood_type_abo"));
                                    }
                                    if (!detail.getString("blood_type_rh").equals("null")) {
                                        TextView blood_type_rh = findView(R.id.blood_type_rh);
                                        blood_type_rh.setText(detail.getString("blood_type_rh"));
                                    }
                                    if (!detail.getString("blood_glucose").equals("null")) {
                                        TextView blood_glucose = findView(R.id.blood_glucose);
                                        blood_glucose.setText(detail.getString("blood_glucose"));
                                    }
                                    if (!detail.getString("sgpt").equals("null")) {
                                        TextView sgpt = findView(R.id.sgpt);
                                        sgpt.setText(detail.getString("sgpt"));
                                    }
                                    if (!detail.getString("sgot").equals("null")) {
                                        TextView sgot = findView(R.id.sgot);
                                        sgot.setText(detail.getString("sgot"));
                                    }
                                    if (!detail.getString("albumin").equals("null")) {
                                        TextView albumin = findView(R.id.albumin);
                                        albumin.setText(detail.getString("albumin"));
                                    }
                                    if (!detail.getString("tbil").equals("null")) {
                                        TextView tbil = findView(R.id.tbil);
                                        tbil.setText(detail.getString("tbil"));
                                    }
                                    if (!detail.getString("dbil").equals("null")) {
                                        TextView dbil = findView(R.id.dbil);
                                        dbil.setText(detail.getString("dbil"));
                                    }
                                    if (!detail.getString("scr").equals("null")) {
                                        TextView scr = findView(R.id.scr);
                                        scr.setText(detail.getString("scr"));
                                    }
                                    if (!detail.getString("bun").equals("null")) {
                                        TextView bun = findView(R.id.bun);
                                        bun.setText(detail.getString("bun"));
                                    }

                                    if (!detail.getString("surface_antigen").equals("null")) {
                                        TextView surface_antigen = findView(R.id.surface_antigen);
                                        surface_antigen.setText(detail.getString("surface_antigen"));
                                    }

                                    if (!detail.getString("surface_antibody").equals("null")) {
                                        TextView surface_antibody = findView(R.id.surface_antibody);
                                        surface_antibody.setText(detail.getString("surface_antibody"));
                                    }
                                    if (!detail.getString("e_antigen").equals("null")) {
                                        TextView e_antigen = findView(R.id.e_antigen);
                                        e_antigen.setText(detail.getString("e_antigen"));
                                    }
                                    if (!detail.getString("e_antibody").equals("null")) {
                                        TextView e_antibody = findView(R.id.e_antibody);
                                        e_antibody.setText(detail.getString("e_antibody"));
                                    }
                                    if (!detail.getString("core_antibody").equals("null")) {
                                        TextView core_antibody = findView(R.id.core_antibody);
                                        core_antibody.setText(detail.getString("core_antibody"));
                                    }
                                    if (!detail.getString("total_evaluation").equals("null")) {
                                        TextView total_evaluation = findView(R.id.total_evaluation);
                                        total_evaluation.setText(detail.getString("total_evaluation"));
                                    }
                                    if (!detail.getString("total_evaluation_abnormal").equals("null")) {
                                        TextView total_evaluation_abnormal = findView(R.id.total_evaluation_abnormal);
                                        total_evaluation_abnormal.setText(detail.getString("total_evaluation_abnormal"));
                                    }
                                    TextView guide = findView(R.id.guide);
                                    guide.setText(detail.getString("guide"));
                                    if (!detail.getString("transfer").equals("null")) {
                                        TextView transfer = findView(R.id.transfer);
                                        transfer.setText(detail.getString("transfer"));
                                    }
                                    if (!detail.getString("transfer_reason").equals("null")) {
                                        TextView transfer_reason = findView(R.id.transfer_reason);
                                        transfer_reason.setText(detail.getString("transfer_reason"));
                                    }
                                    if (!detail.getString("transfer_hospital").equals("null")) {
                                        TextView transfer_hospital = findView(R.id.transfer_hospital);
                                        transfer_hospital.setText(detail.getString("transfer_hospital"));
                                    }
                                    TextView next_visit_date = findView(R.id.next_visit_date);
                                    next_visit_date.setText(detail.getString("next_visit_date"));
                                    TextView doctor_signature = findView(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));


                                } else {
                                    String errorMsg = obj.getString("error_msg");
                                    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
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
