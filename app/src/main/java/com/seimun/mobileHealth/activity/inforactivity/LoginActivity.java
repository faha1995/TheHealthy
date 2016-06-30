package com.seimun.mobileHealth.activity.inforactivity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.seimun.mobileHealth.R;
import com.seimun.mobileHealth.activity.MainActivity;
import com.seimun.mobileHealth.activity.BaseActivity;
import com.seimun.mobileHealth.application.BaseApplication;
import com.seimun.mobileHealth.db.DBTool;
import com.seimun.mobileHealth.entity.AppConfig;
import com.seimun.mobileHealth.volley.VolleySingleton;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/9.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView editCleanImg;
    private EditText mobileEdit, pswEdit;
    private TextView forgetTv;
    private DBTool dbTool;
    private Button loginBtn, registerBtn;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private String phone, psw;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        loginBtn = findView(R.id.btn_login);
        registerBtn = findView(R.id.btn_register);
        mobileEdit = findView(R.id.edit_login_mobile);
        pswEdit = findView(R.id.edit_login_psw);
        forgetTv = findView(R.id.tv_login_forget);
        editCleanImg = findView(R.id.img_login_clean);
        editCleanImg.setOnClickListener(this);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        forgetTv.setOnClickListener(this);
        mobileEdit.addTextChangedListener(new Watcher(mobileEdit));
        pswEdit.addTextChangedListener(new Watcher(pswEdit));

        dbTool = new DBTool();

//        // 若已登录过就直接进入
//        if (dbTool.isLogined()) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_login_clean:
                mobileEdit.setText("");
                pswEdit.setText("");
                break;
            case R.id.btn_login:
                phone = mobileEdit.getText().toString().trim();
                psw = pswEdit.getText().toString().trim();
                if (BaseApplication.isNetwork()) {

                    if (phone.isEmpty() || psw.isEmpty()) {
                        Toast.makeText(this, "手机号或密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("checkLogin", "----->" + phone + "—————>" + psw);
                        checkLogin(phone, psw);
                    }
                } else {
                    Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_register:
                Intent intent = new Intent(this, RegisterActivity2.class);
                overridePendingTransition(R.anim.move_in_from_right, R.anim.no_move);
                startActivityForResult(intent, 2);
                break;
            case R.id.tv_login_forget:
                Intent intent1 = new Intent(this, ForgetActivity.class);
                overridePendingTransition(R.anim.move_in_from_right, R.anim.no_move);
                startActivity(intent1);
                break;
        }
    }

    // 检验是否有该用户
    private void checkLogin(final String phone, final String psw) {

        Log.i(TAG, " -----> 开始与后台通信");
        final String tag_request = "request_login";

        StringRequest request = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "登录响应: " + response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");
                            Log.i(TAG, "------->" + error);

                            if (!error) {
                                Log.i(TAG, "登录操作网络响应返回正确！");
                                dbTool.setLogin(true);

                                String resident_id = jsonObject.getString("resident_id");
                                String phone = jsonObject.getString("mobile");
                                String name = jsonObject.getString("name");
                                String identity = jsonObject.getString("identity");
                                String create_at = jsonObject.getString("created_at");

                                Log.i(TAG, "用户名 ：" + name);
                                dbTool.addUser(name, phone, identity, resident_id, create_at);
                                Log.i(TAG, "添加用户到本地数据库成功~");
                                EventBus.getDefault().post("isNew");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.no_move,R.anim.move_out_from_bottom);
                                finish();
                            } else {
                                String errorMsg = jsonObject.getString("error_msg");
                                Log.i(TAG, "---------->" + errorMsg);
                                Toast.makeText(getApplicationContext(),
                                        "账号或密码错误", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "---> error" + error.toString());

                Toast.makeText(getApplicationContext(),
                        "账号或密码错误", Toast.LENGTH_LONG).show();
            }
        })
                // psot请求需要加请求头信息
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", phone);
                params.put("password", psw);
                Log.i("sss", phone + "-->" + psw);
                return params;
            }
        };
        VolleySingleton.getInstace()._addRequest(request, tag_request);
    }

    // 将注册后的结果返回给LoginActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            mobileEdit.setText(data.getStringExtra("mobile"));
            pswEdit.setText(data.getStringExtra("password"));
        }
    }

    // 通过一个内部类来实现TextWatcher
    // 并进行多个EditText的监听判断
    public class Watcher implements TextWatcher {
        private EditText editId = null;

        public Watcher(EditText editText) {
            editId = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            editCleanImg.setVisibility(View.VISIBLE);
            if (editId == mobileEdit) {
                mobileEdit.setBackgroundResource(R.drawable.edittext_register_change_shape);
            }
            if (editId == pswEdit) {
                pswEdit.setBackgroundResource(R.drawable.edittext_register_change_shape);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {


            if (mobileEdit.getText().length() == 0) {
                mobileEdit.setBackgroundResource(R.drawable.edittext_register_shape);
                editCleanImg.setVisibility(View.INVISIBLE);
            }
            if (pswEdit.getText().length() == 0) {
                pswEdit.setBackgroundResource(R.drawable.edittext_register_shape);
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.no_move, R.anim.move_out_from_bottom);
        EventBus.getDefault().post("forMainActivityBackPressed");
    }


}
