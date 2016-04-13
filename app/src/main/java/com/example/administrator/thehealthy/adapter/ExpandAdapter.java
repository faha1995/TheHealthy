package com.example.administrator.thehealthy.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.entity.Summary;
import com.example.administrator.thehealthy.tools.ChangeString;
import com.example.administrator.thehealthy.tools.MyClickListener;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ExpandAdapter extends BaseExpandableListAdapter implements View.OnClickListener{
    private List<String> groups;
    private List<List<Summary>> childs;
    private Context context;
    final String TAG = ExpandAdapter.class.getSimpleName();
    private MyClickListener myClickListener;

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public ExpandAdapter(Context context, List<String> groups, List<List<Summary>> childs) {
        this.groups = groups;

        this.childs = childs;
        this.context = context;
    }

    public void addGroups(List<String> groups) {
        this.groups = groups;
        notifyDataSetChanged();
        Log.i(TAG, "--------> group.size" + groups.size());
    }

    public void addChilds(List<List<Summary>> childs) {
        this.childs = childs;
        Log.i(TAG, "-----> child.0.1" + childs.get(0).size());
        Log.i(TAG, "-----> child.0.1" + childs.get(0).get(1).getTitle());
        Log.i(TAG,"-----> child.1.1"+ childs.get(1).get(0).getResident());
        Log.i(TAG,"-----> child.1.1"+ childs.get(1).get(0).getTitle());
        Log.i(TAG,"-----> child.1.1"+ childs.get(1).get(1).getResident());
        Log.i(TAG,"-----> child.1.1"+ childs.get(1).get(1).getTitle());
        notifyDataSetChanged();
        Log.i(TAG, "--------> child.size" + childs.size());
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.i(TAG, " ------>   +childs.size" + childs.size());
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
            groupHolder.groupImg = (ImageView) convertView.findViewById(R.id.img_health_report_group);
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
            childHolder.purposeText = (TextView) convertView.findViewById(R.id.text_healthReport_adapter_purpose);
            childHolder.clinicText = (TextView) convertView.findViewById(R.id.adapter_health_report_clinic);
            childHolder.doctorText = (TextView) convertView.findViewById(R.id.adapter_health_report_doctor);
            childHolder.timeText = (TextView) convertView.findViewById(R.id.text_healthReport_adapter_time);

            convertView.setOnClickListener(this);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildViewHolder) convertView.getTag();
        }
//        if (childs.get(groupPosition).get(childPosition).getResident().equals(groups.get(groupPosition))) {


            childHolder.titleText.setText(childs.get(groupPosition).get(childPosition).getTitle());
            childHolder.purposeText.setText(
                    ChangeString.splitForPurpose(childs.get(groupPosition).get(childPosition).getTitle()));
            childHolder.doctorText.setText(childs.get(groupPosition).get(childPosition).getProvider());
            childHolder.clinicText.setText(childs.get(groupPosition).get(childPosition).getClinic());
            childHolder.timeText.setText(childs.get(groupPosition).get(childPosition).getServiceTime());

//        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        groupsPosition = groupPosition ;
//        childsPosition = childPosition;
//        Log.i(TAG, "------------> groupPositon " +groupsPosition+" childsPostion "+ childsPosition);
        return true;
    }

public void setPosition(int groupsPosition,int childsPosition){
    this.groupsPosition = groupsPosition;
    this.childsPosition = childsPosition;
    notifyDataSetChanged();

}

//    @Override
    public void onClick(View v) {
//        EventBus.getDefault().post(childs);
        myClickListener.myOnClickListener(childs.get(groupsPosition).get(childsPosition).getTypeAlias(),
                childs.get(groupsPosition).get(childsPosition).getItemAlias(),
                ChangeString.splitForPurpose(childs.get(groupsPosition).get(childsPosition).getTitle()),
                childs.get(groupsPosition).get(childsPosition).getRecordId());
    }

    class GroupViewHolder {
        private TextView groupText;
        private ImageView groupImg;
    }

        int groupsPosition, childsPosition;
    class ChildViewHolder {
        TextView titleText, purposeText, clinicText, doctorText, timeText;
    }
}
