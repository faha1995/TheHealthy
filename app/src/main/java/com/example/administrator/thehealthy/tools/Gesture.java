package com.example.administrator.thehealthy.tools;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/3/21.
 */
public class Gesture {
    final int RIGHT = 0;
    final int LEFT = 1;

    public void doResult(int action) {
        switch (action) {
            case RIGHT:

                break;
            case LEFT:

                break;
        }
    }
    public static GestureDetector.OnGestureListener getOnGesture() {

        GestureDetector.OnGestureListener gesture = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float x = e2.getX() - e1.getX();

                if (x > 0) {

                }
                return true;
            }
        };
        return gesture;
    }

}
