package com.example.administrator.thehealthy.fragment.inforFrament;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/3/15.\
 * 关于我们界面
 */
public class AboutUsFragment extends BaseFragment {
private LinearLayout linearAboutUs;
    int startX,stopX;
    @Override
    protected int setLayoutView() {
        return R.layout.fragment_about_us;
    }

    @Override
    protected void initView() {
        linearAboutUs = findView(R.id.linear_aboutUs);

        linearAboutUs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = (int) event.getX();
                    Log.i("startX", "--------->" + startX);
                }

                else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    stopX = (int) event.getX();
                    Log.i("stopX","--------->"+ stopX);
                }

                else if (stopX - startX >200){
                    Log.i("--","--------->"+ (stopX - startX));
                    backBeforFragment();
                }
                return true;
            }
        });
    }

    @Override
    protected void initData() {

    }
}
