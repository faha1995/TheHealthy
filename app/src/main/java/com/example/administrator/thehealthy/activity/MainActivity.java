package com.example.administrator.thehealthy.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.application.BaseApplication;
import com.example.administrator.thehealthy.entity.AppData;
import com.example.administrator.thehealthy.fragment.HealthEducationFragment;
import com.example.administrator.thehealthy.fragment.PersonalFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity {
    private TabHost mTabHost;
    public TextView mainTitle;
    private long exitTime = 0;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mainTitle = findView(R.id.text_main_title);

        mTabHost = findView(android.R.id.tabhost);
        mTabHost.setup();
        TabHost.TabSpec spec0 = mTabHost.newTabSpec("spec0");
        View inforView = LayoutInflater.from(this)
                .inflate(R.layout.tabwidget_infor, null);
        spec0.setIndicator(inforView);
        spec0.setContent(R.id.inforView);
        mTabHost.addTab(spec0);

        TabHost.TabSpec spec1 = mTabHost.newTabSpec("spec1");
        View personalView = LayoutInflater.from(this)
                .inflate(R.layout.tabwidget_personal, null);
        spec1.setIndicator(personalView);
        spec1.setContent(R.id.personalView);
        mTabHost.addTab(spec1);

        replaceFragmentNoBacStack(R.id.inforView, new HealthEducationFragment());
        replaceFragmentNoBacStack(R.id.personalView, new PersonalFragment());

        BaseApplication.addActivity(new MainActivity());
        Log.i("MainActivity", "-------------->  initView()");

        EventBus.getDefault().register(this);
    }


    // 防止
    @Subscribe
    public void onEvent(String string) {
        if (string.equals("forMainActivityBackPressed")) {
//            counts = 2;
//            Log.i("MainActivity","-----> counts" + counts);
        }
    }

    @Override
    public void onBackPressed() {
        // 只有在子fragment时，会有点击返回按钮返回上一层的事件
        // 在每一个子fragment创建时都要调用setCounts（）方法
        // 在返回按键监听中，当后退栈为 0 时，
        // 若 AppData.counts = 1, 调用onBackPressed（）的父类方法，可以实现点击返回键退出详情界面的功能
        // 界面到达父类fragment
        //AppData.counts  --,此时 AppData.counts = 0；
        // 再次点击返回键时 会提示 “再按一次返回桌面”
        // 若 在2秒内再次点击返回键，则会终结app

        if (getFragmentManager().getBackStackEntryCount() == 0) {

            if (AppData.counts > 0 && AppData.counts < 2) {
                super.onBackPressed();
                AppData.counts--;
            } else if (AppData.counts < 1) {
                if ((System.currentTimeMillis() - exitTime) > 1300) {
                    Toast.makeText(getApplicationContext(), "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
