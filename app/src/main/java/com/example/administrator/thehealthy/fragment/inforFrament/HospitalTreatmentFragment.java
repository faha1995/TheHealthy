package com.example.administrator.thehealthy.fragment.inforFrament;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment.HospitalTreamInforFragment;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2016/3/4.
 * 医院诊疗界面
 */
public class HospitalTreatmentFragment extends BaseFragment {
    private DBTool dbTool;
    private CardView cardViewFirst;
    private ScrollView scrollView;
    private LinearLayout linearHospital;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_hospital_tream;
    }

    @Override
    protected void initView() {
        dbTool = new DBTool();
        scrollView = findView(R.id.scrollView_hospital_tream);
        cardViewFirst = findView(R.id.cardView_hospital_first);
        linearHospital = findView(R.id.linear_hospital_tream);

        initNow();
    }

    private void initNow() {
//
            cardViewFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToNextFragmentFromPersonal(new HospitalTreamInforFragment());
                }
            });
        }




    @Override
    protected void initData() {
//        EventBus.getDefault().register(this);

    }

    @Subscribe
    public void onEvent(String string) {
        initNow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
