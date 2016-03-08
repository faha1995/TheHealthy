package com.example.administrator.thehealthy.fragment.inforFrament;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/3/4.
 * TabLayout个人界面
 */
public class PersonalInforFragment extends BaseFragment implements View.OnClickListener {
    private ImageView editImg;
    private LinearLayout inforLinearFirst, inforLinearSecond, inforLinearThird;
    private Button exitBtn;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_personal_infor;
    }

    @Override
    protected void initView() {
        inforLinearFirst = findView(R.id.linear_personalInfor_first);
        inforLinearSecond = findView(R.id.linear_personalInfor_second);
        inforLinearThird = findView(R.id.linear_personalInfor_third);
        exitBtn = findView(R.id.btn_personalInfor_exit);

        inforLinearFirst.setOnClickListener(this);
        inforLinearSecond.setOnClickListener(this);
        inforLinearThird.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 资料信息
            case R.id.linear_personalInfor_first:
                goToNextFragment(new InformationFragment());
                break;
            // 生活习惯
            case R.id.linear_personalInfor_second:
                goToNextFragment(new HabitFragment());
                break;
            // 既往病史
            case R.id.linear_personalInfor_third:
                goToNextFragment(new MedicalHistoryFragment());
                break;
            // 退出
            case R.id.btn_personalInfor_exit:

                break;
        }
    }
}
