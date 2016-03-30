package com.example.administrator.thehealthy.activity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.application.BaseApplication;
import com.example.administrator.thehealthy.fragment.inforFrament.HealthEducationFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.PersonalFragment;

public class MainActivity extends BaseActivity {
    private TabHost mTabHost;
    public TextView mainTitle;
    private long exitTime = 0;
    //    private BackHandledFragment backHandledFragment;

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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                Log.i("MainActivity", "------>" + getFragmentManager().getBackStackEntryCount());
                if ((System.currentTimeMillis() - exitTime) > 2000) {
//                    Toast.makeText(getApplicationContext(), "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
        }
        return true;
    }


    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else{
//                finish();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
