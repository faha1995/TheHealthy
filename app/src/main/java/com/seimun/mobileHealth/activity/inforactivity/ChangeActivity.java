package com.seimun.mobileHealth.activity.inforactivity;

import android.os.CountDownTimer;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.seimun.mobileHealth.R;
import com.seimun.mobileHealth.activity.BaseActivity;
import com.seimun.mobileHealth.db.DBTool;
import com.seimun.mobileHealth.entity.AppConfig;
import com.seimun.mobileHealth.tools.PublicTools;
import com.seimun.mobileHealth.volley.VolleyListenerInterface;
import com.seimun.mobileHealth.volley.VolleyRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dell on 2016/6/29.
 */
public class ChangeActivity extends BaseActivity implements View.OnClickListener{

    private final String TAG = ChangeActivity.class.getSimpleName();
    private EditText  validateCodeEdit, pswEdit;
    private TextView mobileTv;
    private Button getValidateCodeBtn, seePswBtn, changeBtn;
    private String mobile, validateCode, psw;
    private String verificationCode;
    private CountDownTimer timer;
    private boolean isHidden=true;
    private DBTool dbTool;


    @Override
    protected int setLayout() {
        return R.layout.activity_change;
    }

    @Override
    protected void initView() {
        //手机号、验证码、获取验证码
        mobileTv = findView(R.id.edit_change_mobile);
        validateCodeEdit = findView(R.id.edit_change_validateCode);
        getValidateCodeBtn = findView(R.id.button_change_getValidateCode);
        pswEdit = findView(R.id.edit_change_psw);

        //看密码、注册
        seePswBtn = findView(R.id.btn_change_see_psw);
        changeBtn = findView(R.id.btn_change_sure);

        //监听
        getValidateCodeBtn.setOnClickListener(this);
        changeBtn.setOnClickListener(this);
        seePswBtn.setOnClickListener(this);
        dbTool = new DBTool();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "-------->  onResume()");


        if (dbTool.isLogined()) {
            final HashMap<String, String> user = dbTool.getUserDetails();
            Log.i(TAG, "-------->" + "isLogined()");


            mobileTv.setText(user.get("mobile"));

            Log.i(TAG, "-----> mobile :" + user.get("mobile"));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_change_getValidateCode:
                getValidateCode();
                break;
            case R.id.btn_change_see_psw:
                seePsw();
                break;
            case R.id.btn_change_sure:
                change();
                break;
            default:
                break;
        }
    }

    /**
     * 查看密码
     */
    private void seePsw() {
        if (isHidden) {
            //设置EditText文本为可见的 @mipmap/show_password
            seePswBtn.setBackgroundResource(R.mipmap.hide_password);
            pswEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //设置EditText文本为隐藏的
            seePswBtn.setBackgroundResource(R.mipmap.show_pass);
            pswEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        isHidden = !isHidden;
        pswEdit.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = pswEdit.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }


    /**
     * 获取验证码
     */
    private void getValidateCode() {
        //判断网络连接
        if (!PublicTools.isConnect(this)) {
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }

       mobile = mobileTv.getText().toString().trim();
        Log.i(TAG, "mobile--" + mobile );
        if (TextUtils.isEmpty(mobile)) {
            PublicTools.Animshake(mobileTv);
            Toast.makeText(this, "手机号获取失败，不可发送短信验证码", Toast.LENGTH_SHORT).show();
            return;
        }


        String tag_request = "request_getValidateCode";
        String url = AppConfig.URL_GET_VERIFICATION_CODE;
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        // flag: register状态，已注册才可以发送验证码
        // unregister状态，未注册才可以发送验证码
        params.put("flag", "registered");

        //获取手机验证码
        VolleyRequestUtil.RequestPost(this, url, tag_request, params, new VolleyListenerInterface(this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {
                Log.i(TAG, "ValidateCode--change--" + result.toString());

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        verificationCode = jsonObject.getString("code");
                        Toast.makeText(ChangeActivity.this, "短信验证码发送成功" , Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "verificationCode--" + verificationCode.toString());
                    } else {
                        String message = jsonObject.getString("message");
                        Toast.makeText(ChangeActivity.this, message, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(ChangeActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
            }
        });

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

    /**
     * 更改密码
     */
    private void change() {
        //判断网络连接
        if (!PublicTools.isConnect(ChangeActivity.this)) {
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        mobile = mobileTv.getText().toString().trim();
        validateCode = validateCodeEdit.getText().toString().trim();
        psw = pswEdit.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            PublicTools.Animshake(mobileTv);
            Toast.makeText(this, "手机号获取失败，不可注册", Toast.LENGTH_SHORT).show();
            return;
        }

      /*  if (TextUtils.isEmpty(validateCode)) {
            PublicTools.Animshake(validateCodeEdit);
            Toast.makeText(this, "手机验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!validateCode.equals(verificationCode)) {
            PublicTools.Animshake(validateCodeEdit);
            Toast.makeText(this, "手机验证码输入有误，请重新获取", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if (TextUtils.isEmpty(psw)) {
            PublicTools.Animshake(pswEdit);
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String tag_request = "request_register";
        String url = AppConfig.URL_RESET_PASSWORD;
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("newPassword", psw);


        VolleyRequestUtil.RequestPost(ChangeActivity.this, url, tag_request, params, new VolleyListenerInterface(ChangeActivity.this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {

                Log.i(TAG, "onMySuccess--" + result.toString());

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        Toast.makeText(ChangeActivity.this, "密码更改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangeActivity.this, "密码更改失败", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(ChangeActivity.this, "密码更改失败", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
