package com.example.administrator.thehealthy.activity.inforactivity;

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
    private TextView titleText;
    private Handler handler;
    Animation animation = null;

    @Override
    protected int setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        titleText = findView(R.id.text_splash);
        animation = AnimationUtils.loadAnimation(this, R.anim.splash_text);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 100:
                        titleText.setText("医疗卫生");
                        titleText.startAnimation(animation);
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
        handler.sendEmptyMessageDelayed(100, 1000);
        handler.sendEmptyMessageDelayed(200, 4000);

    }
}
