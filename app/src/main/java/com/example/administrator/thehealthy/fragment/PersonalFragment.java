package com.example.administrator.thehealthy.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.adapter.PersonalAdapter;
import com.example.administrator.thehealthy.fragment.inforFrament.HealthReportFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.PersonalInforFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.ServicePlanFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/3.
 * TabHost个人界面
 */
public class PersonalFragment extends BaseFatherFragment {
    private TabLayout personalTabLayout;
    private ViewPager personalViewPager;
    private PersonalAdapter personalAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    public static boolean startService = false;

    @Override
    protected int setLayoutView() {

        return R.layout.fragment_personal;
    }

    public static boolean isStartService(){
        return startService;
    }



    @Override
    protected void initView() {
        personalTabLayout = findView(R.id.tabLayout_per);
        personalViewPager = findView(R.id.viewPager_per);
        // 预缓存为2
        personalViewPager.setOffscreenPageLimit(2);
        // 添加Fragment
        fragmentList.add(new PersonalInforFragment());
        fragmentList.add(new HealthReportFragment());
        fragmentList.add(new ServicePlanFragment());

        // 添加Title
        titles.add("个人信息");
        titles.add("健康报告");
        titles.add("服务计划");

        personalAdapter = new PersonalAdapter(
                getActivity().getSupportFragmentManager(), getActivity(), fragmentList, titles);
        personalViewPager.setAdapter(personalAdapter);
        // 设置tabLayout的模式
        personalTabLayout.setTabMode(TabLayout.MODE_FIXED);

        // 添加tab对应的文字
        personalTabLayout.addTab(personalTabLayout.newTab().setText(titles.get(0)));
        personalTabLayout.addTab(personalTabLayout.newTab().setText(titles.get(1)));
        personalTabLayout.addTab(personalTabLayout.newTab().setText(titles.get(2)));
        personalTabLayout.setupWithViewPager(personalViewPager);
    }

    @Override
    protected void initData() {
        startService = true;
    }


}
