package com.example.administrator.thehealthy.fragment.inforFrament;

import android.support.v7.widget.CardView;
import android.view.View;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.HospitalTreamInforFragment;

/**
 * Created by Administrator on 2016/3/4.
 * 医院诊疗界面
 */
public class HospitalTreatmentFragment extends BaseFragment {
    private CardView cardView;
    @Override
    protected int setLayoutView() {
        return R.layout.fragment_hospital_tream;
    }

    @Override
    protected void initView() {
        cardView = findView(R.id.cardView_hospital_first);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragmentFromPersonal(new HospitalTreamInforFragment());
            }
        });

    }

    @Override
    protected void initData() {

    }
}
