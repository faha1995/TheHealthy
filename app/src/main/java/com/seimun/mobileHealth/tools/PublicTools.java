package com.seimun.mobileHealth.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/6/21.
 */
public class PublicTools {

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnect(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.v("error", e.toString());
        }
        return false;
    }


    /**
     * 控件的动画
     *
     * @param view
     */
    public static void Animshake(View view) {
        final TranslateAnimation anim = new TranslateAnimation(0, 10, 0, 0);

        // 利用 CycleInterpolator 参数 为float 的数 表示 抖动的次数，而抖动的快慢是由 duration 和
        // CycleInterpolator 的参数的大小 联合确定的
        anim.setInterpolator(new CycleInterpolator(6f));
        anim.setDuration(800);
        view.startAnimation(anim);
    }

    /**
     * 判断是否为手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,4,7,8]))//d{8}$");
        Matcher m = p.matcher(mobiles);
        Log.i("mytest", "手机号码正确性验证    " + m.matches() + "---");
        return m.matches();

    }


    /**
     * 校验手机号
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {

        final String REGEX_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
        Log.i("mytest", "身份证号码正确性验证    " + Pattern.matches(REGEX_MOBILE, mobile) + "---");
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验身份证
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
        Log.i("mytest", "身份证号码正确性验证    " + Pattern.matches(REGEX_ID_CARD, idCard) + "---");
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }
}
