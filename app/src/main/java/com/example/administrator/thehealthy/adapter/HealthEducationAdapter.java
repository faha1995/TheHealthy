package com.example.administrator.thehealthy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.entity.HealthEduEntity;
import com.example.administrator.thehealthy.tools.ChangeString;
import com.example.administrator.thehealthy.tools.MyClickListener;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 * 健康教育适配器
 */
public class HealthEducationAdapter extends RecyclerView.Adapter {
    private final String TAG = HealthEducationAdapter.class.getSimpleName();
    private Context context;
    private List<HealthEduEntity> eduEntityList;
    private View view;
    private MyClickListener myClickListener;
    private ImageLoader imageLoader;

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public HealthEducationAdapter(Context context) {
        this.context = context;
        this.imageLoader =  VolleySingleton.getImageLoader();
    }

    // 添加数剧的方法
    public void addData(List<HealthEduEntity> eduEntityList) {
        this.eduEntityList = eduEntityList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_health_education_item, null);

        return new HealthEducationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HealthEducationViewHolder eduHolder = null;
        if (eduEntityList != null && eduEntityList.size() > 0) {
            eduHolder = (HealthEducationViewHolder) holder;
            eduHolder.healtEduTitle.setText(eduEntityList.get(position).getTitle());
            eduHolder.healthEduDescrip.setText(eduEntityList.get(position).getDescription());
            eduHolder.healthEduTime.setText(ChangeString.splitTime(eduEntityList.get(position).getCreate_at()));
//            imageLoader.get(eduEntityList.get(position).getImage_url(),eduHolder.imageHealthEducate);
            eduHolder.imageHealthEducate.setImageUrl(eduEntityList.get(position).getImage_url(), imageLoader);
            eduHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClickListener.myOnClickListener(position);
                    Log.i(TAG, "---------->" + position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return eduEntityList != null && eduEntityList.size() > 0 ?
                eduEntityList.size() : 0;
    }


    class HealthEducationViewHolder extends RecyclerView.ViewHolder {
        private TextView healtEduTitle, healthEduTime, healthEduDescrip;
        private NetworkImageView imageHealthEducate;

        public HealthEducationViewHolder(View itemView) {
            super(itemView);
            healtEduTitle = (TextView) itemView.findViewById(R.id.text_health_educate_item_title);
            healthEduDescrip = (TextView) itemView.findViewById(R.id.text_health_educate_item_descrip);
            healthEduTime = (TextView) itemView.findViewById(R.id.text_health_educate_item_time);
            imageHealthEducate = (NetworkImageView) itemView.findViewById(R.id.image_health_educate_item);
        }


    }
}
