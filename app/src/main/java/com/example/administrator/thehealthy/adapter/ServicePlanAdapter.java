package com.example.administrator.thehealthy.adapter;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.entity.PlanEntity;
import com.example.administrator.thehealthy.tools.ChangeString;

import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ServicePlanAdapter extends BaseExpandableListAdapter {
    private final String TAG = ServicePlanAdapter.class.getSimpleName();
    private List<String> groups;
    private List<List<PlanEntity>> childs;
    private Context context;
    private int nowYear, nowMonth, nowDate;
    private Time time;


    public ServicePlanAdapter(Context context, List<String> groups, List<List<PlanEntity>> childs) {
        this.groups = groups;
        this.childs = childs;
        this.context = context;
        time = new Time("GMT+8:00");
        time.setToNow();
        nowYear = time.year;
        nowMonth = time.month + 1;
        nowDate = time.monthDay;
    }


    // 同时添加groups 和 childs 的集合
    public void addToPlanGroupsChilds(List<String> groups, List<List<PlanEntity>> childs) {
        this.groups = groups;
        this.childs = childs;
        notifyDataSetChanged();
    }


    @Override
    public int getGroupCount() {
        Log.i(TAG, "--------->  groupCount " + groups.size());
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childs != null && childs.get(groupPosition).size() > 0 ?
                childs.get(groupPosition).size() : 0;

    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childs.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.fragment_health_report_group, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.groupText = (TextView) convertView.findViewById(R.id.text_health_report_group);
            groupViewHolder.groupImg = (ImageView) convertView.findViewById(R.id.img_health_report_group);
            groupViewHolder.groupLinear = (LinearLayout) convertView.findViewById(R.id.linear_health_report_group);

            if (groupPosition == (groups.size() - 1)) {
                groupViewHolder.groupLinear.setBackgroundResource(R.drawable.expandable_listview_group_background_final);
            }
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        if (isExpanded) {
            groupViewHolder.groupImg.setImageResource(R.mipmap.groupopen_icon);
        } else {
            groupViewHolder.groupImg.setImageResource(R.mipmap.groupclose_icon);
        }
        groupViewHolder.groupText.setText(groups.get(groupPosition));


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.service_plan_child_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.titleText = (TextView) convertView.findViewById(R.id.text_service_plan_child_title);
            childViewHolder.statusText = (TextView) convertView.findViewById(R.id.text_service_plan_child_status);
            childViewHolder.dateText = (TextView) convertView.findViewById(R.id.text_service_plan_child_date);

            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        Log.i("int","----> int " + ChangeString.planStatus(childs.get(groupPosition).get(childPosition).getNext_date(),
                nowYear, nowMonth, nowDate));
        switch (ChangeString.Count(childs.get(groupPosition).get(childPosition).getNext_date(),
                nowYear, nowMonth, nowDate)) {

            case 1:
                childViewHolder.statusText.setText("未过期");
                childViewHolder.statusText.setBackgroundResource(R.drawable.service_plan_child_status_text_background_green);
                break;
            case 2:
                childViewHolder.statusText.setText("快过期");
                childViewHolder.statusText.setBackgroundResource(R.drawable.service_plan_child_status_text_background_yellow);
                break;
            case 3:
                childViewHolder.statusText.setText("过期");
                childViewHolder.statusText.setBackgroundResource(R.drawable.service_plan_child_status_text_background_red);
                break;
        }
        childViewHolder.titleText.setText(childs.get(groupPosition)
                .get(childPosition).getService_item());

        childViewHolder.dateText.setText(childs.get(groupPosition)
                .get(childPosition).getNext_date());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        private LinearLayout groupLinear;
        private TextView groupText;
        private ImageView groupImg;
    }

    class ChildViewHolder {
        TextView titleText, statusText, dateText;
    }
}
