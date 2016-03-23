package com.example.administrator.thehealthy.activity.inforactivity;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.activity.BaseActivity;
import com.example.administrator.thehealthy.application.AppConfig;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = RegisterActivity.class.getSimpleName();
    private LinearLayout linearRegister;
    private EditText nameEdit, mobileEdit, idEdit, pswEdit;
    private Button registerBtn;
    private DBTool dbTool;
    private Handler handler;

    @Override
    protected int setLayout() {
        return R.layout.activity_register;
    }


    @Override
    protected void initView() {

        nameEdit = findView(R.id.edit_register_name);
        mobileEdit = findView(R.id.edit_register_mobile);
        idEdit = findView(R.id.edit_register_id);
        pswEdit = findView(R.id.edit_register_psw);
        registerBtn = findView(R.id.btn_register_sure);

        registerBtn.setOnClickListener(this);
        nameEdit.addTextChangedListener(new Watcher(nameEdit));
        mobileEdit.addTextChangedListener(new Watcher(mobileEdit));
        idEdit.addTextChangedListener(new Watcher(idEdit));
        pswEdit.addTextChangedListener(new Watcher(pswEdit));
        dbTool = new DBTool();

    }
    String name,mobile,identity,psw;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_sure:

                 name = nameEdit.getText().toString().trim();
                 mobile = mobileEdit.getText().toString().trim();
                 identity = idEdit.getText().toString().trim();
                 psw = pswEdit.getText().toString().trim();
                if (!name.isEmpty() && !mobile.isEmpty() &&
                        !identity.isEmpty() && !psw.isEmpty()) {
                    registerUser(name, mobile, identity, psw);

                } else {
                    Toast.makeText(RegisterActivity.this, "请填入完整信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void registerUser(final String names, final String mobiles, final String identitys, final String psws) {
        String tag_request = "request_register";

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "注册操作网络通信响应: " + response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {

                        String resident_id = jsonObject.getString("resident_id");
                        String name = jsonObject.getString("name");
                        String mobile = jsonObject.getString("mobile");
                        String created_at = jsonObject.getString("created_at");
                        Log.i(TAG, "------>" + name);
                        Log.i(TAG, "------>" + names);
                        if (!name.equals(names)) {

                            Toast.makeText(RegisterActivity.this,
                                    "用户信息不一致", Toast.LENGTH_SHORT).show();
                        } else {
                            dbTool.addUser(name, mobile, identity, resident_id, created_at);
                            Toast.makeText(getApplicationContext(), "恭喜，注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("mobile", mobile);
                            intent.putExtra("password", psw);
                            setResult(1, intent);
                            finish();
                        }
                    } else {
                        String error1 = jsonObject.getString("error_msg");
                        Log.i(TAG, "------->" + error1);
                        if (error1.equals("The mobile has registered")) {
                            Toast.makeText(RegisterActivity.this,
                                    "该身份已被注册", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this,
                                    "身份证信息不存在，请到三河市公共卫生局建档", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "------->" + error.toString());
                Toast.makeText(getApplicationContext(), "您输入的身份信息不正确",
                        Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("identity", identity);
                params.put("mobile", mobile);
                params.put("password", psw);
                Log.i("RegisterActivity", "--------->" + name.toString());
                Log.i("RegisterActivity", "--------->" + mobile.toString());
                Log.i("RegisterActivity", "--------->" + identity.toString());
                Log.i("RegisterActivity", "--------->" + psw.toString());
                return params;
            }
        };
        VolleySingleton.getInstace()._addRequest(request, tag_request);
    }

    // 通过一个内部类来实现TextWatcher
    // 并进行多个EditText的监听判断
    public class Watcher implements TextWatcher {
        private EditText editId = null;

        public Watcher(EditText editText) {
            this.editId = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (editId == nameEdit) {
                nameEdit.setBackgroundResource(R.drawable.edittext_register_change_shape);
            }
            if (editId == mobileEdit) {
                mobileEdit.setBackgroundResource(R.drawable.edittext_register_change_shape);
            }
            if (editId == idEdit) {
                idEdit.setBackgroundResource(R.drawable.edittext_register_change_shape);
            }
            if (editId == pswEdit) {
                pswEdit.setBackgroundResource(R.drawable.edittext_register_change_shape);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (nameEdit.getText().length() == 0) {
                nameEdit.setBackgroundResource(R.drawable.edittext_register_shape);
            }

            if (mobileEdit.getText().length() == 0) {
                mobileEdit.setBackgroundResource(R.drawable.edittext_register_shape);
            }

            if (idEdit.getText().length() == 0) {
                idEdit.setBackgroundResource(R.drawable.edittext_register_shape);
            }

            if (pswEdit.getText().length() == 0) {
                pswEdit.setBackgroundResource(R.drawable.edittext_register_shape);
            }

        }

    }
}