package com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.entity.AppConfig;
import com.example.administrator.thehealthy.fragment.BaseSonFragment;
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/20.
 */
public class OldPeopleLifeAbilityFragment extends BaseSonFragment {
    private final String TAG = OldPeopleLifeAbilityFragment.class.getSimpleName();
    private TextView eatTv, dressTv, toiletTv, totalTv, activityTv, washTv;
    private LinearLayout linearLayout;

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
        linearLayout = findView(R.id.linear_oldPeopleAbility);
        ScrollViewOnTouch.getInstance().setViewFinishTouchFromFragment(linearLayout);
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
}
