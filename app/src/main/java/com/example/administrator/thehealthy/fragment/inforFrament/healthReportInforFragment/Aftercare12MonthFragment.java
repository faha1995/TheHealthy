package com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
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
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 */
public class Aftercare12MonthFragment extends BaseSonFragment {
    private final String TAG = Aftercare12MonthFragment.class.getSimpleName();
    private ScrollView scrollViewAfter;
    int startX, stopX;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_aftercare12_to30_month;
    }

    @Override
    protected void initView() {
        scrollViewAfter = findView(R.id.scrollView_12_to_30);
        scrollViewAfter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = (int) event.getX();
                    Log.i("startX", "--------->" + startX);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    stopX = (int) event.getX();
                    Log.i("stopX", "--------->" + stopX);
                } else if (stopX - startX > 200) {
                    Log.i("--", "--------->" + (stopX - startX));
                    backBeforFragment();
                }
                return false;
            }
        });

    }

    @Override
    protected void initData() {
        final Integer record_id = getArguments().getInt("record_id", 0);


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
                                    if (!detail.getString("rickets_sign").equals("null")) {
                                        TextView rickets_sign = findView(R.id.rickets_sign);
                                        rickets_sign.setText(detail.getString("rickets_sign"));
                                    }
                                    TextView hearing = findView(R.id.hearing);
                                    hearing.setText(detail.getString("hearing"));
                                    TextView take_vitamin_d = findView(R.id.take_vitamin_d);
                                    take_vitamin_d.setText(detail.getString("take_vitamin_d"));
                                    TextView growth_evaluate = findView(R.id.growth_evaluate);
                                    growth_evaluate.setText(detail.getString("growth_evaluate"));

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
