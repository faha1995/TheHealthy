package com.example.administrator.thehealthy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.thehealthy.R;

/**
 * Created by Administrator on 2016/3/7.
 */
public class HealthReportAdapter extends RecyclerView.Adapter
        <HealthReportAdapter.HealthReportViewHolder> {
    private Context context;



    // 绑定布局
    @Override
    public HealthReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_health_report_item,null);
        return new HealthReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HealthReportViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HealthReportViewHolder extends RecyclerView.ViewHolder {

        public HealthReportViewHolder(View itemView) {
            super(itemView);
        }
    }
}
