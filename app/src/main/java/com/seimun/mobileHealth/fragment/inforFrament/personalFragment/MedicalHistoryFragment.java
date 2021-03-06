package com.seimun.mobileHealth.fragment.inforFrament.personalFragment;

import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.seimun.mobileHealth.R;
import com.seimun.mobileHealth.db.DBTool;
import com.seimun.mobileHealth.entity.AppConfig;
import com.seimun.mobileHealth.fragment.BaseSonFragment;
import com.seimun.mobileHealth.tools.ChangeString;
import com.seimun.mobileHealth.tools.ScrollViewOnTouch;
import com.seimun.mobileHealth.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/7.
 * 既往病史界面
 */
public class MedicalHistoryFragment extends BaseSonFragment {
    private final String TAG = MedicalHistoryFragment.class.getSimpleName();
    private TextView diseaseText, surgeryText, traumaText, bloodText, disabilityText,
            fatherSickText, momSickText, bortherSickText, sonSickText, alergyText,
            geneticText,exposureText;
    private DBTool dbTool;
    private ScrollView scrollViewAfter;
    private ScrollViewOnTouch scrollViewOnTouch = new ScrollViewOnTouch();

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_medicalhistory;
    }

    @Override
    protected void initView() {
        diseaseText = findView(R.id.text_medicalHistory_disease);
        surgeryText = findView(R.id.text_medicalHistory_surgery);
        traumaText = findView(R.id.text_medicalHistory_trauma);
        bloodText = findView(R.id.text_medicalHistory_blood);
        disabilityText = findView(R.id.text_medicalHistory_disability);
        fatherSickText = findView(R.id.text_medicalHistory_sick_father);
        momSickText = findView(R.id.text_medicalHistory_sick_mom);
        bortherSickText = findView(R.id.text_medicalHistory_sick_brother);
        sonSickText = findView(R.id.text_medicalHistory_sick_son);
        alergyText = findView(R.id.text_medicalHistory_allergy);
        geneticText = findView(R.id.text_medicalHistory_genetic);
        scrollViewAfter = findView(R.id.scrollView_medicahistory);
        exposureText = findView(R.id.text_information_exposure);

        scrollViewOnTouch.setScrollView(scrollViewAfter);
        dbTool = new DBTool();
    }

    @Override
    protected void initData() {
        final HashMap<String, String> user = dbTool.getUserDetails();
        Log.i(TAG, "initData()");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.URL_PERSONAL_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "既往病史数据网络通信响应");

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");

                    if (!error) {
                        diseaseText.setText(ChangeString.splitMain(jsonObject.getString("disease_history")));

                        // 手术史
                        if (jsonObject.getString("surgery_history").equals("无")) {
                            surgeryText.setText(jsonObject.getString("surgery_history"));

                        } else if (!jsonObject.getString("surgery_1_name").equals("null") &&
                                !jsonObject.getString("surgery_1_date").equals("null")) {
                            surgeryText.setText(jsonObject.getString("surgery_1_name") + "/"
                                    + jsonObject.getString("surgery_1_date"));
                        }
                        // 外伤史
                        if (jsonObject.getString("injury_history").equals("无")) {
                            traumaText.setText(jsonObject.getString("injury_history"));

                        } else if (!jsonObject.getString("injury_1_name").equals("null") &&
                                !jsonObject.getString("injury_1_date").equals("null")) {
                            traumaText.setText(jsonObject.getString("injury_1_name") + "/"
                                    + jsonObject.getString("injury_1_date"));

                        }
                        // 输血史
                        if (jsonObject.getString("transfusion_history").equals("无")) {
                            bloodText.setText(jsonObject.getString("transfusion_history"));

                        } else if (!jsonObject.getString("transfusion_1_reason").equals("null") &&
                                !jsonObject.getString("transfusion_1_date").equals("null")) {
                            bloodText.setText(jsonObject.getString("transfusion_1_reason") + "/"
                                    + jsonObject.getString("transfusion_1_date"));

                        }

                        disabilityText.setText(ChangeString.splitMain(jsonObject.getString("disability")));
                        fatherSickText.setText(ChangeString.splitMain(jsonObject.getString("family_history_father")));
                        momSickText.setText(ChangeString.splitMain(jsonObject.getString("family_history_mother")));
                        bortherSickText.setText(ChangeString.splitMain(jsonObject.getString("family_history_sibling")));
                        if (jsonObject.getString("family_history_children").length() > 3) {
                            sonSickText.setText(ChangeString.splitMain(jsonObject.getString("family_history_children")));
                        } else {
                            sonSickText.setText(jsonObject.getString("family_history_children"));
                        }
                        alergyText.setText(ChangeString.splitMain(jsonObject.getString("allergy_history")));
                        geneticText.setText(jsonObject.getString("genetic_disease"));
                        exposureText.setText(
                                ChangeString.splitMain(jsonObject.getString("expose_history")));
                    } else if (jsonObject.getString("error_msg").equals("The resident has not fill the personal info table")) {
                        showAlertDialog("请前往卫生院完善个人信息", 2);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "网络不可用",
                        Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("resident_id", user.get("resident_id"));
                return params;
            }
        };
        VolleySingleton.getInstace().addRequest(request);
    }


}
