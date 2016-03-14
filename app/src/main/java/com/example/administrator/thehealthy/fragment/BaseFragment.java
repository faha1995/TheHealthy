package com.example.administrator.thehealthy.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.thehealthy.R;

/**
 * Created by Administrator on 2016/3/3.
 */
public abstract class BaseFragment extends Fragment implements View.OnTouchListener {
    private View view;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayoutView(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        view.setOnTouchListener(this);
        initView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    // 绑定布局
    protected abstract int setLayoutView();

    // 初始化组件
    protected abstract void initView();

    // 初始化数据
    protected abstract void initData();

    // 简化findViewById
    protected <T extends View> T findView(int resId) {
        T t = (T) view.findViewById(resId);
        return t;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    // fragment的替换
    public void goToNextFragmentFromPersonal(Fragment fragment) {
        if (fm == null) {
            fm = getActivity().getSupportFragmentManager();
        }
        ft = fm.beginTransaction();
        ft.setCustomAnimations(
                R.anim.move_in_from_right,
                R.anim.no_move,
                R.anim.no_move,
                R.anim.move_out_from_right
        );
        ft.add(R.id.fragment_personal, fragment);
        // addToBackStack() 是为了将该Fragment加入到后退栈中
        // 在返回时,可以直接返回到上一个界面
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

    }

    // fragment的替换
    public void goToNextFragmentFromEducation(Fragment fragment, int pos) {
        if (fm == null) {
            fm = getActivity().getSupportFragmentManager();
        }
        ft = fm.beginTransaction();
        ft.setCustomAnimations(
                R.anim.move_in_from_right,
                R.anim.no_move,
                R.anim.no_move,
                R.anim.move_out_from_right
        );
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        fragment.setArguments(bundle);
        ft.add(R.id.fragment_healthEducation, fragment);
        // addToBackStack() 是为了将该Fragment加入到后退栈中
        // 在返回时,可以直接返回到上一个界面
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

    }

    // acitvity的跳转
    public static <T> void activityIntent(Activity activity, Class<T> clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.overridePendingTransition(R.anim.move_in_from_right, R.anim.no_move);
        activity.startActivity(intent);
    }

    // 返回的方法
    public void backBeforFragment() {
        if (fm == null) {
            fm = getActivity().getSupportFragmentManager();
        }
        fm.popBackStack();
        fm.popBackStackImmediate(" ", 1);
    }
}
