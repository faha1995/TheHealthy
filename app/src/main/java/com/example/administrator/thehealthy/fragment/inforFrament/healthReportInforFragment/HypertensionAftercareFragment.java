package com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.administrator.thehealthy.entity.AndroidToServerEntity;
import com.example.administrator.thehealthy.entity.AppConfig;
import com.example.administrator.thehealthy.fragment.BaseSonFragment;
import com.example.administrator.thehealthy.tools.ChangeString;
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 */
public class HypertensionAftercareFragment extends BaseSonFragment implements View.OnClickListener {
    private final String TAG = HypertensionAftercareFragment.class.getSimpleName();
    private ScrollView scrollViewAfter;
    private ScrollViewOnTouch scrollViewOnTouch = new ScrollViewOnTouch();
    private TextView hypeTitle,signRhythm,smokeDanwei,liquorDanwei,saltDanwei;
    private LinearLayout linearSbpDanwei,linearSignWeightDanwei,linearSignbmiDanwei,
    linearSportNowDanwei,linearSportSuggestDanwei,linearMedicineFirstDanwei,
    linearMedicineSecondDanwei,linearMedicineThirdDanwei,linearMedicineOthersDanwei;
    private String titles;
    private Button unknowBtn, generalBtn, greatBtn;
    private final int SCORE_UNKNOW = 1;
    private final int SCORE_GENERAL = 2;
    private final int SCORE_GREATE = 3;
    private int record_id, evaluation;

    public HypertensionAftercareFragment(String titles) {
        this.titles = titles;
    }

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_hypertension_aftercare;
    }

    @Override
    protected void initView() {
        hypeTitle = findView(R.id.hypertension_afterCare_title);
        signRhythm = findView(R.id.hypertension_sign_heart_rhythmDanwei);
        linearSbpDanwei = findView(R.id.linear_hypertension_sbpDanwei);
        linearSignWeightDanwei = findView(R.id.linear_hypertension_sign_weightDanwei);
        linearSignbmiDanwei = findView(R.id.linear_hypertension_sign_bmiDanwei);
        smokeDanwei = findView(R.id.life_style_guide_smokeDanwei);
        liquorDanwei = findView(R.id.life_style_guide_liquorDanwei);
        saltDanwei = findView(R.id.life_style_guide_saltDanwei);
        linearSportNowDanwei = findView(R.id.linear_diabetes_sport_now);
        linearSportSuggestDanwei = findView(R.id.linear_diabetes_sport_suggest);
        linearMedicineFirstDanwei = findView(R.id.linear_medicineFirstDanwei);
        linearMedicineSecondDanwei = findView(R.id.linear_medicineSecondDanwei);
        linearMedicineThirdDanwei = findView(R.id.linear_medicineThirdDanwei);
        linearMedicineOthersDanwei = findView(R.id.linear_medicineOthersDanwei);

        scrollViewAfter = findView(R.id.scrollView_hypertension);
        scrollViewOnTouch.setScrollView(scrollViewAfter);
        unknowBtn = findView(R.id.btn_unKnow);
        generalBtn = findView(R.id.btn_general);
        greatBtn = findView(R.id.btn_great);
        unknowBtn.setOnClickListener(this);
        generalBtn.setOnClickListener(this);
        greatBtn.setOnClickListener(this);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void initData() {

         record_id = getArguments().getInt("record_id", 0);

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

                                    evaluation = detail.getInt("evaluation");
                                    Log.i(TAG, "---------> " + evaluation);
// 该界面已评价
                                    if (evaluation > 0) {
                                        setButtonEnabled(unknowBtn, generalBtn, greatBtn);
                                        switch (evaluation) {
                                            case 1:
                                                unknowBtn.setBackgroundResource(R.drawable.button_shape);
                                                break;
                                            case 2:
                                                generalBtn.setBackgroundResource(R.drawable.button_shape);
                                                break;
                                            case 3:
                                                greatBtn.setBackgroundResource(R.drawable.button_shape);
                                        }
                                    }

                                    hypeTitle.setText(titles);
                                    TextView visit_date = findView(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView visit_way = findView(R.id.visit_way);
                                    visit_way.setText(detail.getString("visit_way"));
                                    TextView doctor_signature = findView(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));
                                    TextView symptom = findView(R.id.symptom);
                                    if (detail.getString("symptom").length() > 3) {
                                        symptom.setText(ChangeString.splitMainMore(detail.getString("symptom")));
                                    }
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
                                    if (detail.getString("sign_sbp").equals("")) {
                                        linearSbpDanwei.setVisibility(View.INVISIBLE);
                                    }
                                    TextView sign_dbp = findView(R.id.sign_dbp);
                                    sign_dbp.setText(detail.getString("sign_dbp"));
                                    TextView sign_weight = findView(R.id.sign_weight);
                                    sign_weight.setText(detail.getString("sign_weight"));
                                    if (detail.getString("sign_weight").equals("")) {
                                        linearSignWeightDanwei.setVisibility(View.INVISIBLE);
                                    }
                                    TextView sign_weight_next = findView(R.id.sign_weight_next);
                                    sign_weight_next.setText(detail.getString("sign_weight_next"));
                                    TextView sign_bmi = findView(R.id.sign_bmi);
                                    sign_bmi.setText(detail.getString("sign_bmi"));
                                    if (detail.getString("sign_bmi").equals("")) {
                                        linearSignbmiDanwei.setVisibility(View.INVISIBLE);
                                    }
                                    TextView sign_bmi_next = findView(R.id.sign_bmi_next);
                                    sign_bmi_next.setText(detail.getString("sign_bmi_next"));
                                    TextView sign_heart_rhythm = findView(R.id.sign_heart_rhythm);
                                    sign_heart_rhythm.setText(detail.getString("sign_heart_rhythm"));
                                    if (detail.getString("sign_heart_rhythm").equals("")) {
                                        signRhythm.setVisibility(View.INVISIBLE);
                                    }
                                    TextView life_style_guide_smoke = findView(R.id.life_style_guide_smoke);
                                    life_style_guide_smoke.setText(detail.getString("life_style_guide_smoke"));
                                    if (detail.getString("life_style_guide_smoke").equals("")) {
                                        smokeDanwei.setVisibility(View.INVISIBLE);
                                    }
                                    TextView life_style_guide_smoke_next = findView(R.id.life_style_guide_smoke_next);
                                    life_style_guide_smoke_next.setText(detail.getString("life_style_guide_smoke_next"));
                                    TextView life_style_guide_liquor = findView(R.id.life_style_guide_liquor);
                                    life_style_guide_liquor.setText(detail.getString("life_style_guide_liquor"));
                                    if (detail.getString("life_style_guide_liquor").equals("")) {
                                        liquorDanwei.setVisibility(View.INVISIBLE);
                                    }
                                    TextView life_style_guide_liquor_next = findView(R.id.life_style_guide_liquor_next);
                                    life_style_guide_liquor_next.setText(detail.getString("life_style_guide_liquor_next"));
                                    TextView life_style_guide_sport1 = findView(R.id.life_style_guide_sport1);
                                    life_style_guide_sport1.setText(detail.getString("life_style_guide_sport1"));
                                    if (detail.getString("life_style_guide_sport1").equals("")) {
                                        linearSportNowDanwei.setVisibility(View.INVISIBLE);
                                    }
                                    TextView life_style_guide_sport2 = findView(R.id.life_style_guide_sport2);
                                    life_style_guide_sport2.setText(detail.getString("life_style_guide_sport2"));
                                    TextView life_style_guide_sport3 = findView(R.id.life_style_guide_sport3);
                                    life_style_guide_sport3.setText(detail.getString("life_style_guide_sport3"));
                                    if (detail.getString("life_style_guide_sport3").equals("")) {
                                        linearSportSuggestDanwei.setVisibility(View.INVISIBLE);
                                    }
                                    TextView life_style_guide_sport4 = findView(R.id.life_style_guide_sport4);
                                    life_style_guide_sport4.setText(detail.getString("life_style_guide_sport4"));
                                    TextView life_style_guide_salt = findView(R.id.life_style_guide_salt);
                                    life_style_guide_salt.setText(detail.getString("life_style_guide_salt"));
                                    if (detail.getString("life_style_guide_salt").equals("")) {
                                        saltDanwei.setVisibility(View.INVISIBLE);
                                    }
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
                                    } else {
                                        linearMedicineFirstDanwei.setVisibility(View.INVISIBLE);
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
                                    } else {
                                        linearMedicineSecondDanwei.setVisibility(View.INVISIBLE);
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
                                    } else {
                                        linearMedicineThirdDanwei.setVisibility(View.INVISIBLE);
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
                                    } else {
                                        linearMedicineOthersDanwei.setVisibility(View.INVISIBLE);
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


    @Override
    public void onClick(View v) {
        if (evaluation == 0) {
            switch (v.getId()) {
                case R.id.btn_unKnow:
                    //判断客户端与服务器交互后是否成功
                    androidToServer(record_id, SCORE_UNKNOW, AppConfig.URL_EVALUATE,TAG);
                    break;
                case R.id.btn_general:
                    androidToServer(record_id, SCORE_GENERAL, AppConfig.URL_EVALUATE,TAG);
                    break;
                case R.id.btn_great:
                    androidToServer(record_id, SCORE_GREATE, AppConfig.URL_EVALUATE,TAG);
                    break;
            }

        }

    }

    @Subscribe
    public void onEvent(AndroidToServerEntity entity) {

        if (entity.getString().equals(TAG)) {
            switch (entity.getScore()) {
                case 1:
                    unknowBtn.setBackgroundResource(R.drawable.button_shape);
                    setButtonEnabled(unknowBtn, generalBtn, greatBtn);
                    break;
                case 2:
                    generalBtn.setBackgroundResource(R.drawable.button_shape);
                    setButtonEnabled(unknowBtn, generalBtn, greatBtn);
                    break;
                case 3:
                    greatBtn.setBackgroundResource(R.drawable.button_shape);
                    setButtonEnabled(unknowBtn, generalBtn, greatBtn);
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
