package com.example.administrator.thehealthy.fragment.inforFrament;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.entity.AppData;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.tools.ChangeString;

/**
 * Created by Administrator on 2016/3/9.
 */
public class EducationInferFragment extends BaseFragment implements View.OnTouchListener{
    private final String TAG = EducationInferFragment.class.getSimpleName();
    private int position;
    private TextView eduInforTitle, eduInforContent, eduInforTime, eduInforDate;
    private GestureDetector gestureDetector;
    private ScrollView scrollViewEducationInfor;


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
        scrollViewEducationInfor.setOnTouchListener(this);

        eduInforTitle.setText(AppData.eduEntityList.get(position).getTitle());
        eduInforContent.setText(AppData.eduEntityList.get(position).getContent());
        eduInforTime.setText(ChangeString.splitForTime(AppData.eduEntityList.get(position).getCreate_at()));
        eduInforDate.setText(ChangeString.splitForDate(AppData.eduEntityList.get(position).getCreate_at()));
        // 初始化 gestureDetector,并赋与gesture对参数
        gestureDetector = new GestureDetector(getActivity(), gesture);

    }

    // 创建OnGestureListener对象
    GestureDetector.OnGestureListener gesture = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e2.getX() - e1.getX();

            if (x > 0) {
                backBeforFragment();
                Log.i("educationInfor","-------->  "+ x);
            }
            return true;
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void initData() {

    }

}
