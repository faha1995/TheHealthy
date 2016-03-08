package com.example.administrator.thehealthy.fragment.inforFrament;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.adapter.HealthEducationAdapter;
import com.example.administrator.thehealthy.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/3/3.
 * 健康教育界面
 */
public class HealthEducationFragment extends BaseFragment {
    private RecyclerView educationRv;
    private HealthEducationAdapter educationAdapter;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_health_education;
    }

    @Override
    protected void initView() {
        educationRv = findView(R.id.recyclerView_health_education);
        educationRv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        educationAdapter = new HealthEducationAdapter();
        educationRv.setAdapter(educationAdapter);
    }

    @Override
    protected void initData() {

    }
}
