package com.example.administrator.thehealthy.fragment.inforFrament;

import android.widget.LinearLayout;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;

/**
 * Created by Administrator on 2016/3/15.\
 * 关于我们界面
 */
public class AboutUsFragment extends BaseFragment {
    private LinearLayout linearAboutUs;


    @Override
    protected int setLayoutView() {
        return R.layout.fragment_about_us;
    }

    @Override
    protected void initView() {
        linearAboutUs = findView(R.id.linear_aboutUs);
        ScrollViewOnTouch.getInstance().setViewFinishTouchFromFragment(linearAboutUs);
    }

    @Override
    protected void initData() {

    }
}
