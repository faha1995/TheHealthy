package com.seimun.mobileHealth.tools;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.seimun.mobileHealth.fragment.BaseFatherFragment;
import com.example.administrator.thehealthy.R;
import com.seimun.mobileHealth.entity.AppData;

/**
 * Created by Administrator on 2016/3/24.
 * 封装了ScrollView和relativeLayout的onTouch方法
 */
public class ScrollViewOnTouch {
    private static ScrollViewOnTouch scrollViewOnTouch;

    public static ScrollViewOnTouch getInstance() {
        if (scrollViewOnTouch == null) {
            scrollViewOnTouch = new ScrollViewOnTouch();
            return scrollViewOnTouch;
        }
        return scrollViewOnTouch;
    }

    int startX, stopX, startY, stopY, poorX, poorY;


    public void setScrollView(ScrollView scrollView) {
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = (int) event.getX();
                    startY = (int) event.getY();

//                    Log.i("startY", "--------->" + startY);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    poorX = stopX - startX;
                    poorY = stopY - startY;

//                    Log.i("stopY", "--------->" + stopY);

                    if (poorX > 200 && Math.abs(poorY) < 80) {
//                        Log.i("--", "--------->" + poorX + "------>" + poorY);
                        BaseFatherFragment.backBeforFragment();
                        AppData.counts = 0;
                    }
                    return false;
                }
                return false;
            }
        });
    }


    public void setViewFinishTouchFromFragment(View view) {

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = (int) event.getX();
                    startY = (int) event.getY();

//                    Log.i("startY", "--------->" + startY);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    poorX = stopX - startX;
                    poorY = stopY - startY;

//                    Log.i("stopY", "--------->" + stopY);

                    if (poorX > 200 && Math.abs(poorY) < 80) {
//                        Log.i("--", "--------->" + poorX + "------>" + poorY);
                        BaseFatherFragment.backBeforFragment();
                        AppData.counts = 0;
                    }
                    return true;
                }
                return true;
            }
        });
    }

    public void setViewFinishTouchFromActivity(View view, final Activity activity) {

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = (int) event.getX();
                    startY = (int) event.getY();
//                    Log.i("startX", "--------->" + startY);
//                    Log.i("startY", "--------->" + startY);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    poorX = stopX - startX;
                    poorY = stopY - startY;

//                    Log.i("endY", "--------->" + stopY);
//                    Log.i("endX", "--------->" + stopX);

//                    Log.i("poor", "--------->" + poorX + "------>" + Math.abs(poorY));
                    if (poorX > 200 && Math.abs(poorY) < 80) {
//                        Log.i("--", "--------->" + poorX + "------>" + Math.abs(poorY));
                       activity.finish();
                        activity.overridePendingTransition(R.anim.no_move, R.anim.move_out_from_right);
                        AppData.counts = 0;
                    }
                    return false;
                }
                return false;
            }
        });
    }

}
