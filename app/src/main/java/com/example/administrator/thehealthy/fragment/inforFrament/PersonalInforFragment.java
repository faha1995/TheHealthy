package com.example.administrator.thehealthy.fragment.inforFrament;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.activity.inforactivity.LoginActivity;
import com.example.administrator.thehealthy.application.AppConfig;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/4.
 * TabLayout个人界面
 */
public class PersonalInforFragment extends BaseFragment implements View.OnClickListener {
    private final String TAG = PersonalInforFragment.class.getSimpleName();

    private ImageView editImg;
    private LinearLayout inforLinearFirst, inforLinearSecond, inforLinearThird;
    private Button exitBtn;
    private TextView nameText, mobileText;
    private DBTool dbTool;
    private static HashMap<String, String> user = null;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_personal_infor;
    }

    @Override
    protected void initView() {
        inforLinearFirst = findView(R.id.linear_personalInfor_first);
        inforLinearSecond = findView(R.id.linear_personalInfor_second);
        inforLinearThird = findView(R.id.linear_personalInfor_third);
        exitBtn = findView(R.id.btn_personalInfor_exit);
        nameText = findView(R.id.text_personalInfor_name);
        mobileText = findView(R.id.text_personalInfor_mobile);

        inforLinearFirst.setOnClickListener(this);
        inforLinearSecond.setOnClickListener(this);
        inforLinearThird.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        dbTool = new DBTool();

        Log.i(TAG, "-------->" + "initView()");
    }


    @Override
    protected void initData() {
        Log.i(TAG, "-------->" + "initData()");

        if (dbTool.isLogined()) {
            Log.i(TAG, "-------->" + "isLogined()");
            Log.i(TAG, "-----> NAME :" + user.get("name"));

            user = dbTool.getUserDetails();
            StringRequest request = new StringRequest(Request.Method.POST,
                    AppConfig.URL_PERSONAL_INFO, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "个人基本信息操作网络通信响应: " + response.toString());

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");

                        if (!error) {
                            nameText.setText(user.get("name"));
                            mobileText.setText(user.get("mobile"));
                        } else {
                            Toast.makeText(getContext(), jsonObject.getString("error_msg") + "",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("resident_id", user.get("resident_id"));
                    return params;
                }
            };
            VolleySingleton.getInstace().addRequest(request);
        }

        Log.i(TAG, "-------->" + "initDataEnd()");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 资料信息
            case R.id.linear_personalInfor_first:
                //没有登录的判断
                if (!dbTool.isLogined()) {
                    Log.i(TAG, "-------->" + "onCLickfirstLinear()");
                    logoutUser();
                    Log.i(TAG, "-------->" + "afterLogoutUser()");
                    // 跳转到登录界面
                    activityIntent(getActivity(), LoginActivity.class);
//                    Intent intent = new Intent(getActivity(),LoginActivity.class);
//                    startActivity(intent);

                    getActivity().finish();
                } else {
                    goToNextFragmentFromPersonal(new InformationFragment());
                }
                break;
            // 生活习惯
            case R.id.linear_personalInfor_second:
                //没有登录的判断
                if (!dbTool.isLogined()) {
                    logoutUser();
                    // 跳转到登录界面
                    activityIntent(getActivity(), LoginActivity.class);
                    getActivity().finish();
                } else {
                    goToNextFragmentFromPersonal(new HabitFragment());
                }
                break;
            // 既往病史
            case R.id.linear_personalInfor_third:
                //没有登录的判断
                if (!dbTool.isLogined()) {
                    logoutUser();
                    // 跳转到登录界面
                    activityIntent(getActivity(), LoginActivity.class);
                    getActivity().finish();
                } else {
                    goToNextFragmentFromPersonal(new MedicalHistoryFragment());
                }
                break;
            // 退出
            case R.id.btn_personalInfor_exit:
                activityIntent(getActivity(), LoginActivity.class);
                break;
        }
    }

    // 一系列删除数据库的方法
    private void logoutUser() {
        dbTool.setLogin(false);
        dbTool.deleteUser();
        dbTool.deleteSummary();
    }

}
