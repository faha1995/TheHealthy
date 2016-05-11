package com.example.administrator.thehealthy.fragment.inforFrament.personalFragment;

import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.entity.AppConfig;
import com.example.administrator.thehealthy.fragment.BaseSonFragment;
import com.example.administrator.thehealthy.tools.ChangeString;
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/7.
 * 资料信息界面
 */
public class InformationFragment extends BaseSonFragment {
    private final String TAG = InformationFragment.class.getSimpleName();
    private TextView nameText, sexText, birthdayText, idCardText, nationalText,
            mobileText, othersText, educationText, professionText, workPlaceText,
            maritalStatusText, bloodStyleText, paymentMethodText;
    private DBTool dbTool;
    private ScrollView scrollViewAfter;
    private ScrollViewOnTouch scrollViewOnTouch = new ScrollViewOnTouch();
    private String errorMsg = null;
    private FrameLayout frameLayout;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_information;
    }

    @Override
    protected void initView() {
        nameText = findView(R.id.text_information_name);
        sexText = findView(R.id.text_information_sex);
        idCardText = findView(R.id.text_information_idCard);
        birthdayText = findView(R.id.text_information_birthDate);
        nationalText = findView(R.id.text_information_national);
        mobileText = findView(R.id.text_information_mobile);
        othersText = findView(R.id.text_information_others);
        educationText = findView(R.id.text_information_education);
        professionText = findView(R.id.text_information_profession);
        workPlaceText = findView(R.id.text_information_workPlace);
        maritalStatusText = findView(R.id.text_information_maritalStatus);
        bloodStyleText = findView(R.id.text_information_bloodStyle);
        paymentMethodText = findView(R.id.text_information_paymentMethod);
        scrollViewAfter = findView(R.id.scrollView_information);

        scrollViewOnTouch.setScrollView(scrollViewAfter);

        dbTool = new DBTool();
    }

    @Override
    protected void initData() {

        final HashMap<String, String> user = dbTool.getUserDetails();

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.URL_PERSONAL_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "个人资料信息网络通信响应：" + response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");

                    if (!error) {
                        nameText.setText(user.get("name"));
                        sexText.setText(jsonObject.getString("gender"));
                        birthdayText.setText(jsonObject.getString("birthday"));
                        idCardText.setText(jsonObject.getString("identity"));
                        nationalText.setText(jsonObject.getString("nation"));
                        mobileText.setText(jsonObject.getString("phone"));
                        othersText.setText(jsonObject.getString(
                                "contact_name") + ": " + jsonObject.getString("contact_phone"));
                        educationText.setText(jsonObject.getString("education"));
                        professionText.setText(jsonObject.getString("occupation"));
                        workPlaceText.setText(jsonObject.getString("work_company"));
                        maritalStatusText.setText(jsonObject.getString("marriage"));
                        bloodStyleText.setText(jsonObject.getString("blood_type"));
                        paymentMethodText.setText(ChangeString.splitMain(jsonObject.getString("payment_way")));
                    } else {
                        if (jsonObject.getString("error_msg").equals("The resident has not fill the personal info table")) {

                            showAlertDialog("请前往卫生院完善个人信息", 2);
                            Log.i(TAG, "----------> initData()" + errorMsg);
                        }
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
