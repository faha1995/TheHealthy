package com.example.administrator.thehealthy.tools;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.thehealthy.application.BaseApplication;

/**
 * Created by Administrator on 2016/3/18.
 */
public class DoubleClickExit {

    public static void doubleClickExit(View view) {

        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int exitTime = 0;
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if ((System.currentTimeMillis() - exitTime) > 2000) {
                        Toast.makeText(BaseApplication.getContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                        exitTime = (int) System.currentTimeMillis();
                    } else {
                        BaseApplication.finishAllActivity();
                    }
                    return true;
                }
                return false;
            }

        };

        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(keyListener);

    }
}
