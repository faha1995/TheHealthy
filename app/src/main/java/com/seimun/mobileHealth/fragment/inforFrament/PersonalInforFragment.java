package com.seimun.mobileHealth.fragment.inforFrament;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.seimun.mobileHealth.activity.inforactivity.LoginActivity;
import com.seimun.mobileHealth.db.DBTool;
import com.seimun.mobileHealth.fragment.BaseFatherFragment;
import com.seimun.mobileHealth.fragment.inforFrament.personalFragment.AboutUsFragment;
import com.seimun.mobileHealth.fragment.inforFrament.personalFragment.HabitFragment;
import com.seimun.mobileHealth.fragment.inforFrament.personalFragment.InformationFragment;
import com.seimun.mobileHealth.fragment.inforFrament.personalFragment.MedicalHistoryFragment;
import com.seimun.mobileHealth.util.RoundImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/3/4.
 * TabLayout个人界面
 */
public class PersonalInforFragment extends BaseFatherFragment implements View.OnClickListener {
    private final String TAG = PersonalInforFragment.class.getSimpleName();
    private LinearLayout inforLinearFirst, inforLinearSecond, inforLinearThird,
            inforLinearFourth, linearInforIcon;
    private Button exitBtn;
    private TextView nameText, mobileText;
    private DBTool dbTool;
    private RoundImageView personalImg;
    private String sex;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_personal_infor;
    }


    @Override
    protected void initView() {
        inforLinearFirst = findView(R.id.linear_personalInfor_first);
        inforLinearSecond = findView(R.id.linear_personalInfor_second);
        inforLinearThird = findView(R.id.linear_personalInfor_third);
        inforLinearFourth = findView(R.id.linear_personalInfor_fourth);
        linearInforIcon = findView(R.id.linear_personal_icon);
        personalImg = findView(R.id.roundImage_personalInfor);
        exitBtn = findView(R.id.btn_personalInfor_exit);
        nameText = findView(R.id.text_personalInfor_name);
        mobileText = findView(R.id.text_personalInfor_mobile);


        inforLinearFirst.setOnClickListener(this);
        inforLinearSecond.setOnClickListener(this);
        inforLinearThird.setOnClickListener(this);
        inforLinearFourth.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        nameText.setOnClickListener(this);
        mobileText.setOnClickListener(this);
        dbTool = new DBTool();
        Log.i(TAG, "-------->" + "initView()");
        Log.i(TAG, "-------->" + "perBac" + getActivity().getSupportFragmentManager().getBackStackEntryCount());

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "-------->  onResume()");

        if (dbTool.isLogined()) {
            final HashMap<String, String> user = dbTool.getUserDetails();
            Log.i(TAG, "-------->" + "isLogined()");


            nameText.setText(user.get("name"));
            mobileText.setText(user.get("mobile"));

            Log.i(TAG, "-----> mobile :" + user.get("mobile"));
        }

    }

    @Override
    protected void initData() {
        Log.i(TAG, "----->  initData()");


    }


    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 资料信息
            case R.id.linear_personalInfor_first:
                //没有登录的判断
                if (!dbTool.isLogined()) {
                    Log.i(TAG, "-------->" + "onCLickfirstLinear()");
                    logoutUser();
                    Log.i(TAG, "-------->" + "afterLogoutUser()");
                    // 跳转到登录界面
                    showAlertDialog("请先进行登录", 1);
                } else {

                    goToNextFragmentFromPersonal(new InformationFragment());
                }
                break;
            // 生活习惯
            case R.id.linear_personalInfor_second:
                //没有登录的判断
                if (!dbTool.isLogined()) {

                    logoutUser();
                    // 跳转到登录界面
                    showAlertDialog("请先进行登录", 1);

                } else {
                    goToNextFragmentFromPersonal(new HabitFragment());
                }
                break;
            // 既往病史
            case R.id.linear_personalInfor_third:
                //没有登录的判断
                if (!dbTool.isLogined()) {
                    logoutUser();
                    // 跳转到登录界面
                    showAlertDialog("请先进行登录", 1);

                } else {
                    goToNextFragmentFromPersonal(new MedicalHistoryFragment());
                }
                break;

            // 关于我们
            case R.id.linear_personalInfor_fourth:
                goToNextFragmentFromPersonal(new AboutUsFragment());
                break;

            // 退出
            case R.id.btn_personalInfor_exit:
                if (!dbTool.isLogined()) {
                    System.exit(0);
                } else {
                    logoutUser();
                    EventBus.getDefault().post("退出当前用户");
                    activityIntent(getActivity(), LoginActivity.class);
                    nameText.setText("未设置");
                    mobileText.setText("未知");
//                getActivity().finish();
                    break;
                }
                // 名字和电话
            case R.id.text_personalInfor_name:
                if (!dbTool.isLogined()) {
                    showAlertDialog("请先进行登录", 1);
                }
                break;
            case R.id.text_personalInfor_mobile:
                if (!dbTool.isLogined()) {
                    showAlertDialog("请先进行登录", 1);
                }
                return;
        }
    }


    // 一系列删除数据库的方法
    private void logoutUser() {
        dbTool.setLogin(false);
        dbTool.deleteUser();
        dbTool.deleteSummary();
    }


}
