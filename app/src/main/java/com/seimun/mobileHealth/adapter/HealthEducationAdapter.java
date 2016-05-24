package com.seimun.mobileHealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.seimun.mobileHealth.tools.ChangeString;
import com.seimun.mobileHealth.volley.VolleySingleton;
import com.example.administrator.thehealthy.R;
import com.seimun.mobileHealth.entity.HealthEduEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 * 健康教育适配器
 */
public class HealthEducationAdapter extends BaseAdapter {
    private final String TAG = HealthEducationAdapter.class.getSimpleName();
    private Context context;
    private List<HealthEduEntity> eduEntityList;
    private ImageLoader imageLoader;


    public HealthEducationAdapter(Context context) {
        this.context = context;
        this.imageLoader = VolleySingleton.getImageLoader();
    }

    // 添加数剧的方法
    public void addData(List<HealthEduEntity> eduEntityList) {
        this.eduEntityList = eduEntityList;
        notifyDataSetChanged();
    }

    // 刷新后添加数剧的方法
    public void addRefreshData(HealthEduEntity eduEntity) {
        this.eduEntityList.add(0, eduEntity);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eduEntityList != null && eduEntityList.size() > 0
                ? eduEntityList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HealthEducationViewHolder healthHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_health_education_item, parent, false);
            healthHolder = new HealthEducationViewHolder();
            healthHolder.healtEduTitle = (TextView) convertView.findViewById(R.id.text_health_educate_item_title);
            healthHolder.healthEduDescrip = (TextView) convertView.findViewById(R.id.text_health_educate_item_descrip);
            healthHolder.healthEduTime = (TextView) convertView.findViewById(R.id.text_health_educate_item_time);
            healthHolder.imageHealthEducate = (NetworkImageView) convertView.findViewById(R.id.image_health_educate_item);
            convertView.setTag(healthHolder);
        } else {
            healthHolder = (HealthEducationViewHolder) convertView.getTag();
        }
        healthHolder.healtEduTitle.setText(eduEntityList.get(position).getTitle());
        healthHolder.healthEduDescrip.setText(eduEntityList.get(position).getDescription());
        healthHolder.healthEduTime.setText(ChangeString.splitTime(eduEntityList.get(position).getCreate_at()));
        healthHolder.imageHealthEducate.setImageUrl(eduEntityList.get(position).getImage_url(), imageLoader);
        return convertView;
    }


    class HealthEducationViewHolder {
        private TextView healtEduTitle, healthEduTime, healthEduDescrip;
        private NetworkImageView imageHealthEducate;


    }
}
