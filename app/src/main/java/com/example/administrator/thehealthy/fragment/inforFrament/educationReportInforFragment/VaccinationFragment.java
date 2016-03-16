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
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 * type_alias.equals("vaccine") && !item_alias.equals("vaccine_card")
 * 的界面
 */
public class VaccinationFragment extends BaseFragment {
    private static final String TAG = VaccinationFragment.class.getSimpleName();

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_vaccination;
    }

    @Override
    protected void initView() {

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
                                    TextView visit_date = findView(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView vaccine = findView(R.id.vaccine);
                                    vaccine.setText(detail.getString("vaccine"));
                                    TextView vaccinate_position = findView(R.id.vaccinate_position);
                                    vaccinate_position.setText(detail.getString("vaccinate_position"));
                                    TextView doctor_signature = findView(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));
                                    TextView batch_number = findView(R.id.batch_number);
                                    batch_number.setText(detail.getString("batch_number"));
                                    TextView remarks = findView(R.id.remarks);
                                    remarks.setText(detail.getString("remarks"));
                                    TextView next_vaccinate_date = findView(R.id.next_vaccinate_date);
                                    next_vaccinate_date.setText(detail.getString("next_vaccinate_date"));


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
