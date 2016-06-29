package com.seimun.mobileHealth.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.seimun.mobileHealth.R;
import com.seimun.mobileHealth.adapter.PersonalAdapter;
import com.seimun.mobileHealth.entity.AppData;
import com.seimun.mobileHealth.fragment.inforFrament.HealthReportFragment;
import com.seimun.mobileHealth.fragment.inforFrament.PersonalInforFragment;
import com.seimun.mobileHealth.fragment.inforFrament.ServicePlanFragment;

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


    @Override
    protected int setLayoutView() {

        return R.layout.fragment_personal;
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
        AppData.isOnResume = true;
    }


}
