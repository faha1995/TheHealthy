package com.example.administrator.thehealthy.tools;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.example.administrator.thehealthy.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/3/24.
 * 封装了ScrollView和relativeLayout的onTouch方法
 */
public class ScrollViewOnTouch {

    int startX, stopX, startY, stopY, poorX, poorY;

    public void setScrollView(ScrollView scrollView) {
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = (int) event.getX();
                    startY = (int) event.getY();

                    Log.i("startY", "--------->" + startY);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    poorX = stopX - startX;
                    poorY = stopY - startY;

                    Log.i("stopY", "--------->" + stopY);

                    if (poorX > 200 &&   Math.abs(poorY) < 80) {
                        Log.i("--", "--------->" + poorX + "------>" + poorY);
                        BaseFragment.backBeforFragment();
                    }
                    return false;
                }
                return false;
            }
        });
    }


    public void setLinearRelative(View view) {

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = (int) event.getX();
                    startY = (int) event.getY();

                    Log.i("startY", "--------->" + startY);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    poorX = stopX - startX;
                    poorY = stopY - startY;

                    Log.i("stopY", "--------->" + stopY);

                    if (poorX > 200 &&   Math.abs(poorY) < 80) {
                        Log.i("--", "--------->" + poorX + "------>" + poorY);
                        BaseFragment.backBeforFragment();
                    }
                    return true;
                }
                return true;
            }
        });
    }

}
