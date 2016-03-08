package com.example.administrator.thehealthy.fragment.inforFrament;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.adapter.HealthReportAdapter;
import com.example.administrator.thehealthy.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/3/4.
 * 健康报告界面
 */
public class HealthReportFragment extends BaseFragment {
    private RecyclerView healthReportRv;
    private HealthReportAdapter healthReportAdapter;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_health_report;
    }

    @Override
    protected void initView() {
        healthReportRv = findView(R.id.recyclerView_healthReport);
        healthReportRv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        healthReportAdapter = new HealthReportAdapter();
        healthReportRv.setAdapter(healthReportAdapter);
    }

    @Override
    protected void initData() {

    }
}
