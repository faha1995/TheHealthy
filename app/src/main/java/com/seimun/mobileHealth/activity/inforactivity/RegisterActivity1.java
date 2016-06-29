package com.seimun.mobileHealth.activity.inforactivity;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.seimun.mobileHealth.R;
import com.seimun.mobileHealth.activity.BaseActivity;
import com.seimun.mobileHealth.entity.AppConfig;
import com.seimun.mobileHealth.tools.MobValidateCode;
import com.seimun.mobileHealth.tools.PublicTools;
import com.seimun.mobileHealth.volley.VolleyListenerInterface;
import com.seimun.mobileHealth.volley.VolleyRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/21.
 */
public class RegisterActivity1 extends BaseActivity implements View.OnClickListener {

    private final String TAG = RegisterActivity1.class.getSimpleName();
    private EditText mobileEdit, validateCodeEdit, pswEdit, repswEdit, nameEdit, idNumberEdit;
    private Button getValidateCodeBtn, registerBtn;
    String mobile, validateCode, psw, repsw, name, idNumber;
    private CountDownTimer timer;
    private MobValidateCode mob;
    private boolean ValidateCodeSubmitFlag = false;

    @Override
    protected int setLayout() {
        return R.layout.activity_register1;
    }

    @Override
    protected void initView() {
        //手机号、验证码、获取验证码
        mobileEdit = findView(R.id.edit_register_mobile);
        validateCodeEdit = findView(R.id.edit_register_validateCode);
        getValidateCodeBtn = findView(R.id.button_register_getValidateCode);
        //密码、重复密码
        pswEdit = findView(R.id.edit_register_psw);
        repswEdit = findView(R.id.edit_register_repsw);
        //姓名、身份证号
        nameEdit = findView(R.id.edit_register_name);
        idNumberEdit = findView(R.id.edit_register_idnumber);
        //注册
        registerBtn = findView(R.id.btn_register_sure);

        //监听
        getValidateCodeBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_register_getValidateCode:
                getValidateCode();
                break;
            case R.id.btn_register_sure:
                register();
                break;
            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        //判断网络连接
        if (!PublicTools.isConnect(RegisterActivity1.this)) {
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        mobile = mobileEdit.getText().toString().trim();
        validateCode = validateCodeEdit.getText().toString().trim();
        psw = pswEdit.getText().toString().trim();
        repsw = repswEdit.getText().toString().trim();
        name = nameEdit.getText().toString().trim();
        idNumber = idNumberEdit.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            PublicTools.Animshake(mobileEdit);
            Toast.makeText(RegisterActivity1.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!PublicTools.isMobile(mobile)) {
            PublicTools.Animshake(mobileEdit);
            Toast.makeText(RegisterActivity1.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(validateCode)) {
            PublicTools.Animshake(validateCodeEdit);
            Toast.makeText(RegisterActivity1.this, "手机验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(psw)) {
            PublicTools.Animshake(pswEdit);
            Toast.makeText(RegisterActivity1.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(repsw)) {
            PublicTools.Animshake(repswEdit);
            Toast.makeText(RegisterActivity1.this, "重复密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!repsw.equals(psw)) {
            PublicTools.Animshake(pswEdit);
            PublicTools.Animshake(repswEdit);
            Toast.makeText(RegisterActivity1.this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(name)) {
            PublicTools.Animshake(nameEdit);
            Toast.makeText(RegisterActivity1.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(idNumber)) {
            PublicTools.Animshake(idNumberEdit);
            Toast.makeText(RegisterActivity1.this, "身份证不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!PublicTools.isIDCard(idNumber)) {
            PublicTools.Animshake(idNumberEdit);
            Toast.makeText(RegisterActivity1.this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
            return;
        }


        mob.setOnMyListener(new MobValidateCode.OnMyListener() {

            @Override
            public void myListener(Boolean flag) {
                Log.i(TAG, "ValidateCodeSubmitFlag = " + flag);

                if (!flag) {
                    Toast.makeText(RegisterActivity1.this, "提交验证码失败,请重新获取验证码", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "ValidateCodeSubmitFlag = else" + "提交验证码失败,请重新获取验证码");
                    return;
                } else {
                    //注册
                    String tag_request = "request_register";
                    String url = AppConfig.URL_REGISTER;
                    Map<String, String> params = new HashMap<>();
                    params.put("mobile", mobile);
                    params.put("password", psw);
                    params.put("name", name);
                    params.put("identity", idNumber);

                    VolleyRequestUtil.RequestPost(RegisterActivity1.this, url, tag_request, params, new VolleyListenerInterface(RegisterActivity1.this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
                        @Override
                        public void onMySuccess(String result) {

                            Log.i(TAG, result.toString());


                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                boolean error = jsonObject.getBoolean("error");
                                if (!error) {
                                    Toast.makeText(RegisterActivity1.this, "注册成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    String error1 = jsonObject.getString("error_msg");
                                    Toast.makeText(RegisterActivity1.this, error1, Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onMyError(VolleyError error) {
                            Toast.makeText(RegisterActivity1.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //

                }

            }
        });

        //提交验证码，检测输入验证码是否与收到的一致
        mob.submitVaildateCode(mobile, validateCode);

        /*if(!ValidateCodeSubmitFlag){
            Toast.makeText(RegisterActivity1.this, "提交验证码失败", Toast.LENGTH_SHORT).show();
            return;
        } else{
            ValidateCodeSubmitFlag = false;
            Log.i(TAG, "ValidateCodeSubmitFlag = else" + ValidateCodeSubmitFlag);
        }*/


    }


    /**
     * 获取验证码
     */
    private void getValidateCode() {

        mobile = mobileEdit.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            PublicTools.Animshake(mobileEdit);
            Toast.makeText(RegisterActivity1.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!PublicTools.isMobile(mobile)) {
            PublicTools.Animshake(mobileEdit);
            Toast.makeText(RegisterActivity1.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        //获取手机验证码
        mob = new MobValidateCode();
        mob.getVaildateCode(mobile);


        getValidateCodeBtn.setEnabled(false);
        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                getValidateCodeBtn.setText("发送中" + (millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                getValidateCodeBtn.setEnabled(true);
                getValidateCodeBtn.setText("获取验证码");
            }
        };

        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // SMSSDK.unregisterEventHandler(EventHandler);
    }


}
