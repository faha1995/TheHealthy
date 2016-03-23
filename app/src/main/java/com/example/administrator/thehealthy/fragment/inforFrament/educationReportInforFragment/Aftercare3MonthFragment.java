package com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment;

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
import com.example.administrator.thehealthy.application.AppConfig;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 */
public class Aftercare3MonthFragment extends BaseFragment {
    private final String TAG = Aftercare3MonthFragment.class.getSimpleName();
    private ScrollView scrollViewAfter;
    int startX, stopX;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_aftercare1_to8_month;
    }

    @Override
    protected void initView() {
        scrollViewAfter = findView(R.id.scrollView_after1_to_8);
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
                                    TextView rickets_symptom = findView(R.id.rickets_symptom);
                                    rickets_symptom.setText(detail.getString("rickets_symptom"));
                                    TextView navel = findView(R.id.navel);
                                    navel.setText(detail.getString("navel"));
                                    TextView neck_enclosed_mass = findView(R.id.neck_enclosed_mass);
                                    neck_enclosed_mass.setText(detail.getString("neck_enclosed_mass"));

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
