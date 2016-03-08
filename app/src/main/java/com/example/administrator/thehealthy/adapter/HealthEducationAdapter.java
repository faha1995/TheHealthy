package com.example.administrator.thehealthy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.thehealthy.R;

/**
 * Created by Administrator on 2016/3/7.
 */
public class HealthEducationAdapter extends RecyclerView.Adapter{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_health_education_item, null);

        return new HealthEducationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class HealthEducationViewHolder extends RecyclerView.ViewHolder{

        public HealthEducationViewHolder(View itemView) {
            super(itemView);
        }
    }
}
