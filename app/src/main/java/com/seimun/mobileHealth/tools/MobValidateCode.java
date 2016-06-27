package com.seimun.mobileHealth.tools;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.seimun.mobileHealth.application.BaseApplication;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.R.getStringRes;

/**
 * Created by Administrator on 2016/6/21.
 */
public class MobValidateCode  {

    //手机号
    private String phone;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    myListener.myListener(true);
                    Log.e("event", "event=提交验证码成功" + event);
                    Toast.makeText(BaseApplication.getContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(BaseApplication.getContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                    Log.e("event", "event=验证码已经发送" + event);

                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    Log.e("event", "event=获取国家列表成功" + event);
                    //返回支持发送验证码的国家列表
                    Toast.makeText(BaseApplication.getContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();

                }
            } else {
                ((Throwable) data).printStackTrace();
                int resId = getStringRes(BaseApplication.getContext(), "smssdk_network_error");
                Log.e("event", "event=验证码错误" + event);
                Toast.makeText(BaseApplication.getContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                if (resId > 0) {
                    Toast.makeText(BaseApplication.getContext(), resId, Toast.LENGTH_SHORT).show();
                }
            }

        }

    };

    private void initData() {

        SMSSDK.initSDK(BaseApplication.getContext(), "ec5fcd1358d4", "3be59251dd215317fc46aeb0ec9448c9");

        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {

                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);

    }


    //获取短信验证码
    public void getVaildateCode(String phoneStr){
        initData();
        SMSSDK.getVerificationCode("86", phoneStr);
    }
    //提交短信验证码
    public void submitVaildateCode(String phoneStr, String validateCode){
        SMSSDK.submitVerificationCode("86", phoneStr,validateCode);
    }


    OnMyListener myListener = null;

    public interface OnMyListener {
        void myListener(Boolean flag);
    }

    public void setOnMyListener(OnMyListener listener){
        this.myListener = listener;
    }

}
