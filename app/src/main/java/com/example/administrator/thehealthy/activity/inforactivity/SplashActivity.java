package com.example.administrator.thehealthy.activity.inforactivity;

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
import com.example.administrator.thehealthy.fragment.BaseFragment;


public class SplashActivity extends BaseActivity {
    private TextView titleText, discripText;
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
        animation = AnimationUtils.loadAnimation(this, R.anim.splash_text);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 100:
                        Typeface discripType = Typeface.createFromAsset(getAssets(), "fonts/splash_discrip_text_type.ttf");
                        Typeface titleType = Typeface.createFromAsset(getAssets(),"fonts/splash_text_title_type.ttf");
                        titleText.setTypeface(titleType);
                        titleText.setText("医疗卫生");
                        discripText.setTypeface(discripType);
                        discripText.setText("让你对健康, 了如指掌");
                        titleText.startAnimation(animation);
                        discripText.startAnimation(animation);
                        Log.i("Splash", "------->" + titleText.getText().toString());
                        break;
                    case 200:
                        Log.i("Splash", "------->" + "Intent.toString()");
                        BaseFragment.activityIntent(SplashActivity.this, MainActivity.class);
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
