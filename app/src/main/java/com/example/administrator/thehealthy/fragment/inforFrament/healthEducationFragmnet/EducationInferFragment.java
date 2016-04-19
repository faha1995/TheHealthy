package com.example.administrator.thehealthy.fragment.inforFrament.healthEducationFragmnet;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.entity.AppData;
import com.example.administrator.thehealthy.fragment.BaseFatherFragment;
import com.example.administrator.thehealthy.tools.ChangeString;
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;

/**
 * Created by Administrator on 2016/3/9.
 */
public class EducationInferFragment extends BaseFatherFragment implements View.OnTouchListener {
    private final String TAG = EducationInferFragment.class.getSimpleName();
    private int position;
    private TextView eduInforTitle, eduInforContent, eduInforTime, eduInforDate;
    private ScrollView scrollViewEducationInfor;
    private RelativeLayout relativeLayoutEducation;


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
        scrollViewEducationInfor = findView(R.id.scrollView_educationInfor);
        relativeLayoutEducation = findView(R.id.relative_education_infor);
        ScrollViewOnTouch.getInstance().setScrollView(scrollViewEducationInfor);
        ScrollViewOnTouch.getInstance().setViewFinishTouchFromFragment(relativeLayoutEducation);
        eduInforTitle.setText(AppData.eduEntityList.get(position).getTitle());
//        eduInforContent.setText(AppData.eduEntityList.get(position).getContent());
        eduInforTime.setText(ChangeString.splitForTime(AppData.eduEntityList.get(position).getCreate_at()));
        eduInforDate.setText(ChangeString.splitForDate(AppData.eduEntityList.get(position).getCreate_at()));

    }

    @Override
    protected void initData() {

    }

}
