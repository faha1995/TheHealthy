package com.example.administrator.thehealthy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.entity.Summary;
import com.example.administrator.thehealthy.fragment.inforFrament.HealthReportFragment;
import com.example.administrator.thehealthy.tools.ChangeString;
import com.example.administrator.thehealthy.tools.MyClickListener;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 */
public class HealthReportAdapter extends RecyclerView.Adapter
        <HealthReportAdapter.HealthReportViewHolder> {
    private final String TAG = HealthReportFragment.class.getSimpleName();
    private Context context;
    private List<Summary> summaryList;
    private MyClickListener myClickListener;

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public HealthReportAdapter(Context context) {
        this.context = context;
    }

    // 将集合传过来
    public void addData(List<Summary> summaryList) {
        this.summaryList = summaryList;
        notifyDataSetChanged();
    }

    // 绑定布局
    @Override
    public HealthReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_health_report_item, null);
        return new HealthReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HealthReportViewHolder holder, int position) {
        if (summaryList.size() > 0) {
            holder.titleText.setText(
                    ChangeString.splitForTitle(summaryList.get(position).getTitle()));
            holder.purposeText.setText(
                    ChangeString.splitForPurpose(summaryList.get(position).getTitle()));
            holder.doctorText.setText(summaryList.get(position).getProvider());
            holder.clinicText.setText(summaryList.get(position).getClinic());
            holder.timeText.setText(
                    ChangeString.splitTime(summaryList.get(position).getServiceTime()));
            holder.itemPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return summaryList != null && summaryList.size() > 0 ?
                summaryList.size() : 0;
    }



    public class HealthReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleText, purposeText, clinicText, doctorText, timeText;
        private int itemPos;

        public HealthReportViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.text_healthReport_adapter_title);
            purposeText = (TextView) itemView.findViewById(R.id.text_healthReport_adapter_purpose);
            clinicText = (TextView) itemView.findViewById(R.id.adapter_health_report_clinic);
            doctorText = (TextView) itemView.findViewById(R.id.adapter_health_report_doctor);
            timeText = (TextView) itemView.findViewById(R.id.text_healthReport_adapter_time);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            myClickListener.myOnClickListener(
                    summaryList.get(itemPos).getTypeAlias(),summaryList.get(itemPos).getItemAlias()
            ,ChangeString.splitForPurpose(summaryList.get(itemPos).getTitle()),summaryList.get(itemPos).getRecordId());
        }
    }
}
