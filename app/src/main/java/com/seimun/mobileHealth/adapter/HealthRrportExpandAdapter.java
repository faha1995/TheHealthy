package com.seimun.mobileHealth.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.viewbadger.BadgeView;
import com.seimun.mobileHealth.R;
import com.seimun.mobileHealth.entity.Summary;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class HealthRrportExpandAdapter extends BaseExpandableListAdapter {
    private List<String> groups;
    private List<List<Summary>> childs;
    private Context context;
    private final String TAG = HealthRrportExpandAdapter.class.getSimpleName();


    public HealthRrportExpandAdapter(Context context, List<String> groups, List<List<Summary>> childs) {
        this.groups = groups;
        this.childs = childs;
        this.context = context;
    }

    // 添加groups集合的方法
//    public void addGroups(List<String> groups) {
//        this.groups = groups;
//        notifyDataSetChanged();
//    }

    // 添加groups和childs集合的方法
    public void addToReportGroupsChilds(List<String> groups,List<List<Summary>> childs) {
        this.groups = groups;
        this.childs = childs;
        notifyDataSetChanged();
    }

    // 添加childs集合的方法
//    public void addChilds(List<List<Summary>> childs) {
//        this.childs = childs;
//        notifyDataSetChanged();
//    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.i(TAG,"--------> getChildrenCount  "+childs.get(groupPosition).size() );
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
        GroupViewHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.fragment_health_report_group, parent, false);
            groupHolder = new GroupViewHolder();
            groupHolder.groupText = (TextView) convertView.findViewById(R.id.text_health_report_group);
            groupHolder.badgerText = (TextView) convertView.findViewById(R.id.text_for_badgerView_group);
            groupHolder.groupImg = (ImageView) convertView.findViewById(R.id.img_health_report_group);
            groupHolder.groupLinear = (LinearLayout) convertView.findViewById(R.id.linear_health_report_group);
            if (groupPosition == (groups.size() - 1)) {
                groupHolder.groupLinear.setBackgroundResource(R.drawable.expandable_listview_group_background_final);
            }
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupViewHolder) convertView.getTag();
        }
        if (isExpanded) {
            groupHolder.groupImg.setImageResource(R.mipmap.groupopen_icon);
        } else {
            groupHolder.groupImg.setImageResource(R.mipmap.groupclose_icon);
        }
        groupHolder.groupText.setText(groups.get(groupPosition));

//        groupHolder.badgeView = new BadgeView(context,groupHolder.badgerText);
//        groupHolder.badgeView.setText("2");
//        groupHolder.badgeView.setBadgePosition(BadgeView.POSITION_CENTER);
//        groupHolder.badgeView.setBackgroundResource(R.drawable.button_shape);
//        groupHolder.badgeView.show();
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_health_report_item, parent, false);
            childHolder = new ChildViewHolder();
            childHolder.titleText = (TextView) convertView.findViewById(R.id.text_healthReport_adapter_title);
            childHolder.clinicText = (TextView) convertView.findViewById(R.id.adapter_health_report_clinic);
            childHolder.doctorText = (TextView) convertView.findViewById(R.id.adapter_health_report_doctor);
            childHolder.timeText = (TextView) convertView.findViewById(R.id.text_healthReport_adapter_time);
            childHolder.cardView = (CardView) convertView.findViewById(R.id.cardView_health_report_item);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildViewHolder) convertView.getTag();
        }
        childHolder.titleText.setText(childs.get(groupPosition).get(childPosition).getTitle());
        childHolder.doctorText.setText(childs.get(groupPosition).get(childPosition).getProvider());
        childHolder.clinicText.setText(childs.get(groupPosition).get(childPosition).getClinic());
        childHolder.timeText.setText(childs.get(groupPosition).get(childPosition).getServiceTime());
//        为了让最后一个的childitem 与下面的groupitem有些距离
//        if (childs.get(groupPosition).size() == childPosition + 1) {
//            Log.i("SSS","------------------>"+childs.get(groupPosition).get(childPosition).getTitle());
//       childHolder.cardView.setContentPadding(0,0,0,15);
//        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class GroupViewHolder {
        private LinearLayout groupLinear;
        private TextView groupText,badgerText;
        private ImageView groupImg;
        private BadgeView badgeView;
    }

    class ChildViewHolder {
        TextView titleText, clinicText, doctorText, timeText;
        CardView cardView;
    }
}
