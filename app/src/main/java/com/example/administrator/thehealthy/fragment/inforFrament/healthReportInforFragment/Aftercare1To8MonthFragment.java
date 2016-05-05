package com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment;

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
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.entity.AndroidToServerEntity;
import com.example.administrator.thehealthy.entity.AppConfig;
import com.example.administrator.thehealthy.fragment.BaseSonFragment;
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
public class Aftercare1To8MonthFragment extends BaseSonFragment implements View.OnClickListener {
    private final String TAG = Aftercare1To8MonthFragment.class.getSimpleName();
    private ScrollView scrollViewAfter;
    private ScrollViewOnTouch scrollViewOnTouch = new ScrollViewOnTouch();
    private Button unknowBtn, generalBtn, greatBtn;
    private final int SCORE_UNKNOW = 1;
    private final int SCORE_GENERAL = 2;
    private final int SCORE_GREATE = 3;
    private int record_id, evaluation;


    @Override
    protected int setLayoutView() {
        return R.layout.fragment_aftercare1_to8_month;
    }

    @Override
    protected void initView() {
        scrollViewAfter = findView(R.id.scrollView_after1_to_8);
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
                                    TextView doctor_signature = findView(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));
                                    TextView two_visit_disease = findView(R.id.two_visit_disease);
                                    two_visit_disease.setText(detail.getString("two_visit_disease"));
                                    TextView growth_evaluate = findView(R.id.growth_evaluate);
                                    growth_evaluate.setText(detail.getString("growth_evaluate"));
                                    TextView transfer_treatment_suggestion = findView(R.id.transfer_treatment_suggestion);
                                    transfer_treatment_suggestion.setText(detail.getString("transfer_treatment_suggestion"));
                                    TextView guide = findView(R.id.guide);
                                    guide.setText(detail.getString("guide"));
                                    TextView next_visit_date = findView(R.id.next_visit_date);
                                    next_visit_date.setText(detail.getString("next_visit_date"));
                                    TextView weight = findView(R.id.weight);
                                    weight.setText(detail.getString("weight"));
                                    TextView weight_grade = findView(R.id.weight_grade);
                                    weight_grade.setText(detail.getString("weight_grade"));
                                    TextView height = findView(R.id.height);
                                    height.setText(detail.getString("height"));
                                    TextView height_grade = findView(R.id.height_grade);
                                    height_grade.setText(detail.getString("height_grade"));
                                    TextView head_circumference = findView(R.id.head_circumference);
                                    head_circumference.setText(detail.getString("head_circumference"));
                                    TextView outdoor_activities = findView(R.id.outdoor_activities);
                                    outdoor_activities.setText(detail.getString("outdoor_activities"));
                                    TextView take_vitamin_d = findView(R.id.take_vitamin_d);
                                    take_vitamin_d.setText(detail.getString("take_vitamin_d"));
                                    TextView complexion = findView(R.id.complexion);
                                    complexion.setText(detail.getString("complexion"));
                                    TextView skin = findView(R.id.skin);
                                    skin.setText(detail.getString("skin"));
                                    TextView bregma = findView(R.id.bregma);
                                    bregma.setText(detail.getString("bregma"));
                                    if (!detail.getString("bregma_length").equals("null")) {
                                        TextView bregma_length = findView(R.id.bregma_length);
                                        bregma_length.setText(detail.getString("bregma_length"));
                                    }
                                    if (!detail.getString("bregma_width").equals("null")) {
                                        TextView bregma_width = findView(R.id.bregma_width);
                                        bregma_width.setText(detail.getString("bregma_width"));
                                    }

                                    TextView eye_appearance = findView(R.id.eye_appearance);
                                    eye_appearance.setText(detail.getString("eye_appearance"));
                                    TextView ear_appearance = findView(R.id.ear_appearance);
                                    ear_appearance.setText(detail.getString("ear_appearance"));
                                    TextView oral_cavity = findView(R.id.oral_cavity);
                                    oral_cavity.setText(detail.getString("oral_cavity"));
                                    TextView heart_lung = findView(R.id.heart_lung);
                                    heart_lung.setText(detail.getString("heart_lung"));
                                    TextView abdomen = findView(R.id.abdomen);
                                    abdomen.setText(detail.getString("abdomen"));
                                    TextView all_fours = findView(R.id.all_fours);
                                    all_fours.setText(detail.getString("all_fours"));
                                    TextView anus_externalia = findView(R.id.anus_externalia);
                                    anus_externalia.setText(detail.getString("anus_externalia"));
                                    TextView hemoglobin_value = findView(R.id.hemoglobin_value);
                                    hemoglobin_value.setText(detail.getString("hemoglobin_value"));
                                    TextView rickets_sign = findView(R.id.rickets_sign);
                                    rickets_sign.setText(detail.getString("rickets_sign"));
                                    TextView transfer_treatment_suggestion_reason = findView(R.id.transfer_treatment_suggestion_reason);
                                    transfer_treatment_suggestion_reason.setText(detail.getString("transfer_treatment_suggestion_reason"));
                                    TextView transfer_treatment_suggestion_institution = findView(R.id.transfer_treatment_suggestion_institution);
                                    transfer_treatment_suggestion_institution.setText(detail.getString("transfer_treatment_suggestion_institution"));


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
                    setButtonEnabled(unknowBtn, generalBtn, greatBtn)
                    ;
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
