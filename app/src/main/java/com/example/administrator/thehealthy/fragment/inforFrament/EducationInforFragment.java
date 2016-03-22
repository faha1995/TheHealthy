package com.example.administrator.thehealthy.fragment.inforFrament;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.entity.AppData;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.tools.ChangeString;

/**
 * Created by Administrator on 2016/3/9.
 */
public class EducationInforFragment extends BaseFragment {
    private final String TAG = EducationInforFragment.class.getSimpleName();
    private int position;
    private TextView eduInforTitle,eduInforContent,eduInforTime,eduInforDate;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_education_infor;
    }

    @Override
    protected void initView() {
       int position = getArguments().getInt("pos");
        Log.i(TAG, "---------->" + position);
        eduInforTitle = findView(R.id.text_EducationInfor_title);
        eduInforContent = findView(R.id.text_EducationInfor_content);
        eduInforTime = findView(R.id.text_EducationInfor_time);
        eduInforDate = findView(R.id.text_EducationInfor_date);

        eduInforTitle.setText(AppData.eduEntityList.get(position).getTitle());
        eduInforContent.setText(AppData.eduEntityList.get(position).getContent());
        eduInforTime.setText(ChangeString.splitForTime(AppData.eduEntityList.get(position).getCreate_at()));
        eduInforDate.setText(ChangeString.splitForDate(AppData.eduEntityList.get(position).getCreate_at()));


    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }
}
