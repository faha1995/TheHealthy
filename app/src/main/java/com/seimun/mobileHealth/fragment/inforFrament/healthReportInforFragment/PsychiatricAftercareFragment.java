package com.seimun.mobileHealth.fragment.inforFrament.healthReportInforFragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.seimun.mobileHealth.entity.AndroidToServerEntity;
import com.seimun.mobileHealth.entity.AppConfig;
import com.seimun.mobileHealth.fragment.BaseSonFragment;
import com.seimun.mobileHealth.tools.ScrollViewOnTouch;
import com.seimun.mobileHealth.volley.VolleySingleton;
import com.example.administrator.thehealthy.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 */
public class PsychiatricAftercareFragment extends BaseSonFragment implements View.OnClickListener {
    private final String TAG = PsychiatricAftercareFragment.class.getSimpleName();
    private ScrollView scrollViewAfter;
    private ScrollViewOnTouch scrollViewOnTouch = new ScrollViewOnTouch();
    private Button unknowBtn, generalBtn, greatBtn;
    private final int SCORE_UNKNOW = 1;
    private final int SCORE_GENERAL = 2;
    private final int SCORE_GREATE = 3;
    private int record_id, evaluation;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_psychiatric_aftercare;
    }

    @Override
    protected void initView() {
        scrollViewAfter = findView(R.id.scrollView_psychiatric);
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

                                    TextView visit_date = findView(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView dangerousness = findView(R.id.dangerousness);
                                    dangerousness.setText(detail.getString("dangerousness"));
                                    TextView insight = findView(R.id.insight);
                                    insight.setText(detail.getString("insight"));
                                    TextView sleep_situation = findView(R.id.sleep_situation);
                                    sleep_situation.setText(detail.getString("sleep_situation"));
                                    TextView diet_situation = findView(R.id.diet_situation);
                                    diet_situation.setText(detail.getString("diet_situation"));
                                    TextView society_function_housework = findView(R.id.society_function_housework);
                                    society_function_housework.setText(detail.getString("society_function_housework"));
                                    TextView society_function_individual_life_care = findView(R.id.society_function_individual_life_care);
                                    society_function_individual_life_care.setText(detail.getString("society_function_individual_life_care"));
                                    TextView society_function_productive_work = findView(R.id.society_function_productive_work);
                                    society_function_productive_work.setText(detail.getString("society_function_productive_work"));
                                    TextView society_function_learn_ability = findView(R.id.society_function_learn_ability);
                                    society_function_learn_ability.setText(detail.getString("society_function_learn_ability"));
                                    TextView society_function_social_interpersonal = findView(R.id.society_function_social_interpersonal);
                                    society_function_social_interpersonal.setText(detail.getString("society_function_social_interpersonal"));
                                    TextView disease_family_society_effect_mild_disturbance = findView(R.id.disease_family_society_effect_mild_disturbance);
                                    disease_family_society_effect_mild_disturbance.setText(detail.getString("disease_family_society_effect_mild_disturbance"));
                                    TextView disease_family_society_effect_disturbance = findView(R.id.disease_family_society_effect_disturbance);
                                    disease_family_society_effect_disturbance.setText(detail.getString("disease_family_society_effect_disturbance"));
                                    TextView disease_family_society_effect_accident = findView(R.id.disease_family_society_effect_accident);
                                    disease_family_society_effect_accident.setText(detail.getString("disease_family_society_effect_accident"));
                                    TextView disease_family_society_effect_autolesion = findView(R.id.disease_family_society_effect_autolesion);
                                    disease_family_society_effect_autolesion.setText(detail.getString("disease_family_society_effect_autolesion"));
                                    TextView disease_family_society_effect_attempted_suicide = findView(R.id.disease_family_society_effect_attempted_suicide);
                                    disease_family_society_effect_attempted_suicide.setText(detail.getString("disease_family_society_effect_attempted_suicide"));
                                    TextView lock_situation = findView(R.id.lock_situation);
                                    lock_situation.setText(detail.getString("lock_situation"));
                                    TextView hospitalized_situation = findView(R.id.hospitalized_situation);
                                    hospitalized_situation.setText(detail.getString("hospitalized_situation"));
                                    if (!detail.getString("last_hospitalized_date").equals("null")) {
                                        TextView last_hospitalized_date = findView(R.id.last_hospitalized_date);
                                        last_hospitalized_date.setText(detail.getString("last_hospitalized_date"));
                                    }
                                    TextView laboratory_examination = findView(R.id.laboratory_examination);
                                    laboratory_examination.setText(detail.getString("laboratory_examination"));
                                    TextView medicine_untoward_effect = findView(R.id.medicine_untoward_effect);
                                    medicine_untoward_effect.setText(detail.getString("medicine_untoward_effect"));
                                    TextView medicine_untoward_effect_yes = findView(R.id.medicine_untoward_effect_yes);
                                    medicine_untoward_effect_yes.setText(detail.getString("medicine_untoward_effect_yes"));
                                    TextView take_medicine_compliance = findView(R.id.take_medicine_compliance);
                                    take_medicine_compliance.setText(detail.getString("take_medicine_compliance"));
                                    TextView treatment_effect = findView(R.id.treatment_effect);
                                    treatment_effect.setText(detail.getString("treatment_effect"));
                                    if (!detail.getString("take_medicine_1").equals("null")) {
                                        TextView take_medicine_1 = findView(R.id.take_medicine_1);
                                        take_medicine_1.setText(detail.getString("take_medicine_1"));
                                    }
                                    if (!detail.getString("take_medicine_1_per").equals("null")) {
                                        TextView take_medicine_1_per = findView(R.id.take_medicine_1_per);
                                        take_medicine_1_per.setText(detail.getString("take_medicine_1_per"));
                                    }
                                    if (!detail.getString("take_medicine_1_time").equals("null")) {
                                        TextView take_medicine_1_time = findView(R.id.take_medicine_1_time);
                                        take_medicine_1_time.setText(detail.getString("take_medicine_1_time"));
                                    }
                                    if (!detail.getString("take_medicine_1_mg").equals("null")) {
                                        TextView take_medicine_1_mg = findView(R.id.take_medicine_1_mg);
                                        take_medicine_1_mg.setText(detail.getString("take_medicine_1_mg"));
                                    }
                                    if (!detail.getString("take_medicine_2").equals("null")) {
                                        TextView take_medicine_2 = findView(R.id.take_medicine_2);
                                        take_medicine_2.setText(detail.getString("take_medicine_2"));
                                    }
                                    if (!detail.getString("take_medicine_2_per").equals("null")) {
                                        TextView take_medicine_2_per = findView(R.id.take_medicine_2_per);
                                        take_medicine_2_per.setText(detail.getString("take_medicine_2_per"));
                                    }
                                    if (!detail.getString("take_medicine_2_time").equals("null")) {
                                        TextView take_medicine_2_time = findView(R.id.take_medicine_2_time);
                                        take_medicine_2_time.setText(detail.getString("take_medicine_2_time"));
                                    }
                                    if (!detail.getString("take_medicine_2_mg").equals("null")) {
                                        TextView take_medicine_2_mg = findView(R.id.take_medicine_2_mg);
                                        take_medicine_2_mg.setText(detail.getString("take_medicine_2_mg"));
                                    }
                                    if (!detail.getString("take_medicine_3").equals("null")) {
                                        TextView take_medicine_3 = findView(R.id.take_medicine_3);
                                        take_medicine_3.setText(detail.getString("take_medicine_3"));
                                    }
                                    if (!detail.getString("take_medicine_3_per").equals("null")) {
                                        TextView take_medicine_3_per = findView(R.id.take_medicine_3_per);
                                        take_medicine_3_per.setText(detail.getString("take_medicine_3_per"));
                                    }
                                    if (!detail.getString("take_medicine_3_time").equals("null")) {
                                        TextView take_medicine_3_time = findView(R.id.take_medicine_3_time);
                                        take_medicine_3_time.setText(detail.getString("take_medicine_3_time"));
                                    }
                                    if (!detail.getString("take_medicine_3_mg").equals("null")) {
                                        TextView take_medicine_3_mg = findView(R.id.take_medicine_3_mg);
                                        take_medicine_3_mg.setText(detail.getString("take_medicine_3_mg"));
                                    }
                                    TextView recovery_measure = findView(R.id.recovery_measure);
                                    recovery_measure.setText(detail.getString("recovery_measure"));
                                    TextView recovery_measure_extra = findView(R.id.recovery_measure_extra);
                                    recovery_measure_extra.setText(detail.getString("recovery_measure_extra"));
                                    TextView visit_classification = findView(R.id.visit_classification);
                                    visit_classification.setText(detail.getString("visit_classification"));
                                    TextView transfer_treatment = findView(R.id.transfer_treatment);
                                    transfer_treatment.setText(detail.getString("transfer_treatment"));
                                    TextView transfer_treatment_reason = findView(R.id.transfer_treatment_reason);
                                    transfer_treatment_reason.setText(detail.getString("transfer_treatment_reason"));
                                    TextView transfer_treatment_institution = findView(R.id.transfer_treatment_institution);
                                    transfer_treatment_institution.setText(detail.getString("transfer_treatment_institution"));
                                    TextView doctor_signature = findView(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));
                                    TextView now_symptom = findView(R.id.now_symptom);
                                    now_symptom.setText(detail.getString("now_symptom"));
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
