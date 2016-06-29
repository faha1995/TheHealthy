package com.seimun.mobileHealth.fragment.inforFrament.personalFragment;

import android.widget.LinearLayout;

import com.seimun.mobileHealth.R;
import com.seimun.mobileHealth.fragment.BaseSonFragment;
import com.seimun.mobileHealth.tools.ScrollViewOnTouch;

/**
 * Created by Administrator on 2016/3/15.\
 * 关于我们界面
 */
public class AboutUsFragment extends BaseSonFragment {
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
