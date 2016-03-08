package com.example.administrator.thehealthy.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.fragment.inforFrament.HealthEducationFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.PersonalFragment;

public class MainActivity extends BaseActivity {
    private TabHost mTabHost;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
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

        replaceFragment(R.id.inforView, new HealthEducationFragment());
        replaceFragment(R.id.personalView,new PersonalFragment());

    }
}
