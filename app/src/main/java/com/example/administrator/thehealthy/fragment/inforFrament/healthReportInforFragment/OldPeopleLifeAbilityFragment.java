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
 * Created by Administrator on 2016/4/20.
 */
public class OldPeopleLifeAbilityFragment extends BaseSonFragment implements View.OnClickListener {
    private final String TAG = OldPeopleLifeAbilityFragment.class.getSimpleName();
    private TextView eatTv, dressTv, toiletTv, totalTv, activityTv, washTv;
    private ScrollView scrollView;
    private Button unknowBtn, generalBtn, greatBtn;
    private final int SCORE_UNKNOW = 1;
    private final int SCORE_GENERAL = 2;
    private final int SCORE_GREATE = 3;
    private int record_id, evaluation;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_oldpeople_life_ability;
    }

    @Override
    protected void initView() {
        eatTv = findView(R.id.text_score_eat);
        dressTv = findView(R.id.text_score_dress);
        toiletTv = findView(R.id.text_score_toilet);
        totalTv = findView(R.id.text_score_total);
        activityTv = findView(R.id.text_score_activity);
        washTv = findView(R.id.text_score_wash);
        scrollView = findView(R.id.scrollView_oldpeople_life_ability);
        ScrollViewOnTouch.getInstance().setScrollView(scrollView);
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
            final StringRequest detailReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_DETAIL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (!obj.getBoolean("error")) {
                            JSONObject detail = obj.getJSONObject("detail");
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

                            eatTv.setText(detail.getString("eat"));
                            washTv.setText(detail.getString("wash"));
                            dressTv.setText(detail.getString("dress"));
                            toiletTv.setText(detail.getString("toilet"));
                            activityTv.setText(detail.getString("activity"));
                            totalTv.setText(detail.getString("total"));

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
