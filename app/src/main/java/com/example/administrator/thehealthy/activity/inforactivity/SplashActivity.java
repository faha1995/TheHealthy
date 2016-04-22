package com.example.administrator.thehealthy.activity.inforactivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.activity.BaseActivity;
import com.example.administrator.thehealthy.activity.MainActivity;


public class SplashActivity extends BaseActivity {
    private TextView titleText, discripText,versionText;
    private Handler handler;
    Animation animation = null;

    @Override
    protected int setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        titleText = findView(R.id.text_splash_title);
        discripText = findView(R.id.text_splash_discrip);
        versionText = findView(R.id.text_version);
        animation = AnimationUtils.loadAnimation(this, R.anim.splash_text);

// 注册监听有无网络的广播
//        Intent intent = new Intent();
//        intent.setAction("android.net.conn.CONNECTIVITY_CHANGE");
//        sendBroadcast(intent);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 100:
                        Typeface discripType = Typeface.createFromAsset(getAssets(), "fonts/splash_discrip_text_type.ttf");
                        Typeface titleType = Typeface.createFromAsset(getAssets(),"fonts/splash_text_title_type.ttf");
                        titleText.setTypeface(titleType);
                        titleText.setText("公共卫生");
                        discripText.setTypeface(discripType);
                        discripText.setText("让你对健康, 了如指掌");
                        versionText.setTypeface(discripType);
                        versionText.setText("V 2.1");

                        titleText.startAnimation(animation);
                        discripText.startAnimation(animation);
                        versionText.startAnimation(animation);
                        Log.i("Splash", "------->" + titleText.getText().toString());
                        break;
                    case 200:
                        Log.i("Splash", "------->" + "Intent.toString()");
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;
            }
        });
        handler.sendEmptyMessageDelayed(100, 500);
        handler.sendEmptyMessageDelayed(200, 3500);




    }
}
