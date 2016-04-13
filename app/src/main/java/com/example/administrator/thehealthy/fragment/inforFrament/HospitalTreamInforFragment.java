package com.example.administrator.thehealthy.fragment.inforFrament;

import android.widget.ScrollView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;

/**
 * Created by Administrator on 2016/4/12.
 */
public class HospitalTreamInforFragment extends BaseFragment {
    private ScrollView scrollView;
    private ScrollViewOnTouch scrollViewOnTouch = new ScrollViewOnTouch();

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_hospitaltreat_infor;
    }

    @Override
    protected void initView() {
        scrollView = findView(R.id.scrollView_hospital_infor);
        scrollViewOnTouch.setScrollView(scrollView);
    }

    @Override
    protected void initData() {

    }
}
