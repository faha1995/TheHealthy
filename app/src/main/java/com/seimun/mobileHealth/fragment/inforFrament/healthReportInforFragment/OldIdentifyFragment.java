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
import com.seimun.mobileHealth.tools.ChangeString;
import com.seimun.mobileHealth.tools.ScrollViewOnTouch;
import com.seimun.mobileHealth.volley.VolleySingleton;
import com.example.administrator.thehealthy.R;
import com.seimun.mobileHealth.entity.AppConfig;
import com.seimun.mobileHealth.fragment.BaseSonFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 * type_alias.equals("tcm") && item_alias.equals("constitution_identification")
 */
public class OldIdentifyFragment extends BaseSonFragment implements View.OnClickListener {
    private final String TAG = OldIdentifyFragment.class.getSimpleName();
    private ScrollView scrollViewAfter;
    private ScrollViewOnTouch scrollViewOnTouch = new ScrollViewOnTouch();
    private Button unknowBtn, generalBtn, greatBtn;
    private final int SCORE_UNKNOW = 1;
    private final int SCORE_GENERAL = 2;
    private final int SCORE_GREATE = 3;
    private int record_id, evaluation;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_old_identify;
    }

    @Override
    protected void initView() {
        scrollViewAfter = findView(R.id.scrollView_old);
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

                                    TextView fill_table_date = findView(R.id.fill_table_date);
                                    fill_table_date.setText(detail.getString("fill_table_date"));
                                    TextView points_pinghe = findView(R.id.points_pinghe);
                                    points_pinghe.setText(detail.getString("points_pinghe"));
                                    if (!detail.getString("yes_trend_pinghe").equals("null")) {
                                        TextView yes_trend_pinghe = findView(R.id.yes_trend_pinghe);
                                        yes_trend_pinghe.setText(detail.getString("yes_trend_pinghe"));
                                    }
                                    TextView health_care_guide_pinghe = findView(R.id.health_care_guide_pinghe);
                                    health_care_guide_pinghe.setText(ChangeString.splitMain(detail.getString("health_care_guide_pinghe")));
                                    TextView health_care_guide_extra_pinghe = findView(R.id.health_care_guide_extra_pinghe);
                                    health_care_guide_extra_pinghe.setText(detail.getString("health_care_guide_extra_pinghe"));
                                    TextView points_qixu = findView(R.id.points_qixu);
                                    points_qixu.setText(detail.getString("points_qixu"));
                                    if (!detail.getString("yes_trend_qixu").equals("null")) {
                                        TextView yes_trend_qixu = findView(R.id.yes_trend_qixu);
                                        yes_trend_qixu.setText(detail.getString("yes_trend_qixu"));
                                    }
                                    TextView health_care_guide_qixu = findView(R.id.health_care_guide_qixu);
                                    health_care_guide_qixu.setText(ChangeString.splitMain(detail.getString("health_care_guide_qixu")));
                                    TextView health_care_guide_extra_qixu = findView(R.id.health_care_guide_extra_qixu);
                                    health_care_guide_extra_qixu.setText(detail.getString("health_care_guide_extra_qixu"));
                                    TextView points_yangxu = findView(R.id.points_yangxu);
                                    points_yangxu.setText(detail.getString("points_yangxu"));

                                    if (!detail.getString("yes_trend_yangxu").equals("null")) {
                                        TextView yes_trend_yangxu = findView(R.id.yes_trend_yangxu);
                                        yes_trend_yangxu.setText(detail.getString("yes_trend_yangxu"));
                                    }
                                    TextView health_care_guide_yangxu = findView(R.id.health_care_guide_yangxu);
                                    health_care_guide_yangxu.setText(ChangeString.splitMain(detail.getString("health_care_guide_yangxu")));
                                    TextView health_care_guide_extra_yangxu = findView(R.id.health_care_guide_extra_yangxu);
                                    health_care_guide_extra_yangxu.setText(detail.getString("health_care_guide_extra_yangxu"));
                                    TextView points_yinxu = findView(R.id.points_yinxu);
                                    points_yinxu.setText(detail.getString("points_yinxu"));
                                    if (!detail.getString("yes_trend_yinxu").equals("null")) {
                                        TextView yes_trend_yinxu = findView(R.id.yes_trend_yinxu);
                                        yes_trend_yinxu.setText(detail.getString("yes_trend_yinxu"));
                                    }
                                    TextView health_care_guide_yinxu = findView(R.id.health_care_guide_yinxu);
                                    health_care_guide_yinxu.setText(ChangeString.splitMain(detail.getString("health_care_guide_yinxu")));
                                    TextView health_care_guide_extra_yinxu = findView(R.id.health_care_guide_extra_yinxu);
                                    health_care_guide_extra_yinxu.setText(detail.getString("health_care_guide_extra_yinxu"));
                                    TextView points_tanshi = findView(R.id.points_tanshi);
                                    points_tanshi.setText(detail.getString("points_tanshi"));
                                    if (!detail.getString("yes_trend_tanshi").equals("null")) {
                                        TextView yes_trend_tanshi = findView(R.id.yes_trend_tanshi);
                                        yes_trend_tanshi.setText(detail.getString("yes_trend_tanshi"));
                                    }
                                    TextView health_care_guide_tanshi = findView(R.id.health_care_guide_tanshi);
                                    health_care_guide_tanshi.setText(ChangeString.splitMain(detail.getString("health_care_guide_tanshi")));
                                    TextView health_care_guide_extra_tanshi = findView(R.id.health_care_guide_extra_tanshi);
                                    health_care_guide_extra_tanshi.setText(detail.getString("health_care_guide_extra_tanshi"));
                                    TextView points_shire = findView(R.id.points_shire);
                                    points_shire.setText(detail.getString("points_shire"));
                                    if (!detail.getString("yes_trend_shire").equals("null")) {
                                        TextView yes_trend_shire = findView(R.id.yes_trend_shire);
                                        yes_trend_shire.setText(detail.getString("yes_trend_shire"));
                                    }
                                    TextView health_care_guide_shire = findView(R.id.health_care_guide_shire);
                                    health_care_guide_shire.setText(ChangeString.splitMain(detail.getString("health_care_guide_shire")));
                                    TextView health_care_guide_extra_shire = findView(R.id.health_care_guide_extra_shire);
                                    health_care_guide_extra_shire.setText(detail.getString("health_care_guide_extra_shire"));
                                    TextView points_xueyu = findView(R.id.points_xueyu);
                                    points_xueyu.setText(detail.getString("points_xueyu"));
                                    if (!detail.getString("yes_trend_xueyu").equals("null")) {
                                        TextView yes_trend_xueyu = findView(R.id.yes_trend_xueyu);
                                        yes_trend_xueyu.setText(detail.getString("yes_trend_xueyu"));
                                    }
                                    TextView health_care_guide_xueyu = findView(R.id.health_care_guide_xueyu);
                                    health_care_guide_xueyu.setText(ChangeString.splitMain(detail.getString("health_care_guide_xueyu")));
                                    TextView health_care_guide_extra_xueyu = findView(R.id.health_care_guide_extra_xueyu);
                                    health_care_guide_extra_xueyu.setText(detail.getString("health_care_guide_extra_xueyu"));
                                    TextView points_qiyu = findView(R.id.points_qiyu);
                                    points_qiyu.setText(detail.getString("points_qiyu"));
                                    if (!detail.getString("yes_trend_qiyu").equals("null")) {
                                        TextView yes_trend_qiyu = findView(R.id.yes_trend_qiyu);
                                        yes_trend_qiyu.setText(detail.getString("yes_trend_qiyu"));
                                    }
                                    TextView health_care_guide_qiyu = findView(R.id.health_care_guide_qiyu);
                                    health_care_guide_qiyu.setText(ChangeString.splitMain(detail.getString("health_care_guide_qiyu")));
                                    TextView health_care_guide_extra_qiyu = findView(R.id.health_care_guide_extra_qiyu);
                                    health_care_guide_extra_qiyu.setText(detail.getString("health_care_guide_extra_qiyu"));
                                    TextView points_tebing = findView(R.id.points_tebing);
                                    points_tebing.setText(detail.getString("points_tebing"));
                                    if (!detail.getString("yes_trend_tebing").equals("null")) {
                                        TextView yes_trend_tebing = findView(R.id.yes_trend_tebing);
                                        yes_trend_tebing.setText(detail.getString("yes_trend_tebing"));
                                    }
                                    TextView health_care_guide_tebing = findView(R.id.health_care_guide_tebing);
                                    health_care_guide_tebing.setText(ChangeString.splitMain(detail.getString("health_care_guide_tebing")));
                                    TextView health_care_guide_extra_tebing = findView(R.id.health_care_guide_extra_tebing);
                                    health_care_guide_extra_tebing.setText(detail.getString("health_care_guide_extra_tebing"));
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
