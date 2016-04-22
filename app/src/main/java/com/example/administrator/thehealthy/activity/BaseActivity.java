package com.example.administrator.thehealthy.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.util.CustomProgressDialog;

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
        dialog = new CustomProgressDialog(this,R.style.loading_dialog);
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

    // 设置WebView属性的方法
    public void setWebView(final Context context, WebView webView, String url) {
//        if (!dialog.isShowing()) {
//            dialog.show();
//        }
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
                view.loadUrl(url);
                // 返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。

                return true;
            }
        });

        // 得到网页标题的方法
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
                Log.i(MainActivity.class.getSimpleName(), "-----> title" + title);
//                mtitle.setText(title);
            }
        });

        // 得到网页加载进度的方法
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i(MainActivity.class.getSimpleName(), "-----> newProgress" + newProgress);
                if (newProgress == 100) {
//                    createLoadingDialog(context).dismiss();
//                    dialog.dismiss();
                }

            }
        });

    }

    public static Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载View
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);//加载布局
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img_progressBar);
        TextView tipTextVIew = (TextView) v.findViewById(R.id.tipTextView);
        // 加载动画
        Animation animation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 用I妈个View显示动画
        spaceshipImage.startAnimation(animation);
        tipTextVIew.setText("正在加载中");
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

