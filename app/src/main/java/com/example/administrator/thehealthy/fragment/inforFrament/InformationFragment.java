package com.example.administrator.thehealthy.fragment.inforFrament;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.application.AppConfig;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.tools.ChangeString;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/7.
 * 资料信息界面
 */
public class InformationFragment extends BaseFragment {
    private final String TAG = InformationFragment.class.getSimpleName();
    private TextView nameText, sexText, birthdayText, idCardText, nationalText,
            mobileText, othersText, educationText, professionText, workPlaceText,
            maritalStatusText, bloodStyleText, paymentMethodText;
    private DBTool dbTool;
    private ScrollView scrollViewAfter;
    int startX,stopX;

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
        scrollViewAfter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = (int) event.getX();
                    Log.i("startX","--------->"+ startX);
                }

                else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    stopX = (int) event.getX();
                    Log.i("stopX","--------->"+ stopX);
                }

                else if (stopX - startX >200){
                    Log.i("--","--------->"+ (stopX - startX));
                    backBeforFragment();
                }
                return false;
            }
        });
        dbTool = new DBTool();
        Log.i(TAG, "-------->" + "inforBac"+getActivity().getSupportFragmentManager().getBackStackEntryCount());
    }

    @Override
    protected void initData() {

        final HashMap<String, String> user = dbTool.getUserDetails();

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.URL_PERSONAL_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG,"个人资料信息网络通信响应："+ response.toString());

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
                                "contact_name")+": "+ jsonObject.getString("contact_phone"));
                        educationText.setText(jsonObject.getString("education"));
                        professionText.setText(jsonObject.getString("occupation"));
                        workPlaceText.setText(jsonObject.getString("work_company"));
                        maritalStatusText.setText(jsonObject.getString("marriage"));
                        bloodStyleText.setText(jsonObject.getString("blood_type"));
                        paymentMethodText.setText(ChangeString.splitMain(jsonObject.getString("payment_way")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                  HashMap<String, String> params = new HashMap<>();
                  params.put("resident_id",user.get("resident_id"));
                return params;
            }
        };
        VolleySingleton.getInstace().addRequest(request);
    }

}
