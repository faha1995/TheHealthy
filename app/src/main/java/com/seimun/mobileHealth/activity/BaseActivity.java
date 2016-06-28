package com.seimun.mobileHealth.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.thehealthy.R;
import com.seimun.mobileHealth.util.CustomProgressDialog;

/**
 * Created by Administrator on 2016/3/3.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        dialog = new CustomProgressDialog(this, R.style.loading_dialog);
        dialog.setCanceledOnTouchOutside(false);
        initView();
    }

    // 绑定布局
    protected abstract int setLayout();

    // 初始化控件
    protected abstract void initView();

    // 初始化数据
    private void initData() {
    }

    ;
    // 加载布局的方法

    // 简化findViewById
    protected <T extends View> T findView(int resId) {
        T t = (T) findViewById(resId);
        return t;
    }

    //public void showDialog(){dialog.show();}
//    public void dismissDiaog(){dialog.dismiss();}

    // 替换Fragment的方法
    protected Fragment replaceFragment(int resId, Fragment fragment) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        // addToBackStack(null) 方法的作用是在替换后不会被finish
        // 好处是 再次返回时,会直接显示原来界面的内容
        fragmentTransaction.add(resId, fragment).addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
        return fragment;
    }

    // 替换Fragment的方法
    protected void replaceFragmentNoBacStack(int resId, Fragment fragment) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        // addToBackStack(null) 方法的作用是在替换后不会被finish
        // 好处是 再次返回时,会直接显示原来界面的内容
        fragmentTransaction.add(resId, fragment);
        fragmentTransaction.commitAllowingStateLoss();
//        return fragment;
    }

    protected void cleanTask() {
        if (fragmentManager != null) {
            fragmentManager.getFragments().clear();
        }

    }

    // acitvity的跳转
    public static <T> void activityIntent(Activity activity, Class<T> clazz, int pos) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtra("pos", pos);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.move_in_from_right, R.anim.no_move);
    }

    boolean loadingFinished = true;
    boolean redirect = false;

    // 设置WebView属性的方法
    public void setWebView(final Context context, WebView webView, String url
            , final ImageView image) {

//        final ImageView imageView = (ImageView) findViewById(R.id.img_loading);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_dialog);
        webView.loadUrl(url);


        WebSettings settings = webView.getSettings();
        // 设置该属性可以将网页完全加载出来
        settings.setJavaScriptEnabled(true);
        // 支持缩放
//        settings.setSupportZoom(true);
//         显示缩放大小
//        settings.setBuiltInZoomControls(true);

        //设置加载进来的页面自适应手机屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);


        // 设置后在当前app中浏览网页
        webView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(MainActivity.class.getSimpleName(), "---------> url " + url);
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                view.loadUrl(url);
                // 返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingFinished = false;

                Animation animation = AnimationUtils.loadAnimation(context,
                        R.anim.loading_animation);
                // 设置动画效果没有停顿
                animation.setInterpolator(new LinearInterpolator());
                image.startAnimation(animation);
                animation.startNow();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!redirect) {
                    loadingFinished = true;
                }

                if (loadingFinished && !redirect) {
                Log.i(MainActivity.class.getSimpleName(), "---------> onPageFinished " + url);

                    linearLayout.setVisibility(View.GONE);
                } else {
                    redirect = false;
                }


            }
        });


    }

    public static Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载View
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);//加载布局
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img_loading);
//        TextView tipTextVIew = (TextView) v.findViewById(R.id.tipTextView);
        // 加载动画
        Animation animation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 用I妈个View显示动画
        spaceshipImage.startAnimation(animation);
//        tipTextVIew.setText("正在加载中");
        // 创建自定义样式dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        // 不可以用"返回键"取消
        loadingDialog.setCancelable(false);
        // 设置布局
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));

        return loadingDialog;
    }
}

