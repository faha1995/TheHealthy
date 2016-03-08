package com.example.administrator.thehealthy.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 2016/3/3.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initView();
    }

    // 绑定布局
    protected abstract int setLayout();
    // 初始化控件
    protected abstract void initView();
    // 初始化数据
    private void initData() {};
    // 加载布局的方法

    // 简化findViewById
    protected <T extends View> T findView(int resId){
        T t = (T)findViewById(resId);
        return t;
    }

    // 替换Fragment的方法
    protected Fragment replaceFragment(int resId, Fragment fragment){
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        // addToBackStack(null) 方法的作用是在替换后不会被finish
        // 好处是 再次返回时,会直接显示原来界面的内容
        fragmentTransaction.replace(resId,fragment).addToBackStack(null);
        fragmentTransaction.commit();
        return fragment;
    }

}
