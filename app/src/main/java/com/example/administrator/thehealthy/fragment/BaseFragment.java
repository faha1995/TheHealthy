package com.example.administrator.thehealthy.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.activity.inforactivity.LoginActivity;
import com.example.administrator.thehealthy.application.BaseApplication;

/**
 * Created by Administrator on 2016/3/3.
 */
public abstract class BaseFragment extends Fragment implements View.OnTouchListener {
    private View view;
    private static FragmentManager fm;
    private static FragmentTransaction ft;


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
    public void goToNextFragmentFromPersonal(Fragment fragment, int record_id) {
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
        bundle.putInt("record_id", record_id);
        fragment.setArguments(bundle);

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
        View view = findView(R.id.fragment_healthEducation);
        view.setOnTouchListener((View.OnTouchListener) fragment);
        ft.add(R.id.fragment_healthEducation, fragment);
        // addToBackStack() 是为了将该Fragment加入到后退栈中
        // 在返回时,可以直接返回到上一个界面
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

    }


    public void doubleClickExit(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(keyListener);
    }

    View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            int exitTime = 0;
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    if ((System.currentTimeMillis() - exitTime) > 2000) {
                        Toast.makeText(BaseApplication.getContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                        exitTime = (int) System.currentTimeMillis();
                        Log.i("baseFragment", "----------->" + "再按一次退出程序");
                    } else {
                        BaseApplication.finishAllActivity();
                    }
                    return true;
                }
            }
            return false;
        }

    };


    // acitvity的跳转
    public static <T> void activityIntent(Activity activity, Class<T> clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.overridePendingTransition(R.anim.move_in_from_right, R.anim.no_move);
        activity.startActivity(intent);
    }

    // 返回的方法
    public static void backBeforFragment() {

        fm.popBackStack();
//        fm.popBackStackImmediate(" ", 1);
    }


    /**
     * 未登录的提示Dialog
     *
     * @param string 提示的Title
     * @param i      根据i的值来判断点击确定后的操作
     */
    protected void showAlertDialog(String string, final int i) {

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final int width = display.getWidth() * 2 / 3;
        final int height = display.getHeight() / 4;


        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.show_alertdialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setCancelable(false);
        alertDialog.show();
//        Window window = alertDialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.97f;
//        lp.dimAmount = 0.7f;
//        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        window.setWindowAnimations(R.style.show_dialog_anim);
        alertDialog.getWindow().setLayout(width, height);
        alertDialog.getWindow().setContentView(view);
        TextView title = (TextView) view.findViewById(R.id.text_showDialog_personalInfor);
        title.setText(string);

        Button sureBtn = (Button) view.findViewById(R.id.btn_dialog_sure);
        Button cancleBtn = (Button) view.findViewById(R.id.btn_dialog_cancle);

        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (i) {
                    case 1:
                        // 回到登录界面
                        activityIntent(getActivity(), LoginActivity.class);
                        break;
                    case 2:
                        // 个人信息不完善，返回之前界面
                        backBeforFragment();
                        break;
                }
                alertDialog.dismiss();
            }
        });


        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

}
