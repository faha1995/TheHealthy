package com.example.administrator.thehealthy.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;

/**
 * Created by Administrator on 2016/4/20.
 */
public class CustomProgressDialog extends Dialog {

    private Context context;
    private ImageView spaceshipImage;
    private TextView tipTextVIew;
    private int res;


    public CustomProgressDialog(Context context,int res) {
        super(context);
        this.context = context;
        this.res = res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载View
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);//加载布局
        spaceshipImage = (ImageView) v.findViewById(R.id.img_loading);
//        tipTextVIew = (TextView) v.findViewById(R.id.tipTextView);
        // 加载动画
        Animation animation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        animation.setInterpolator(new LinearInterpolator());//不停顿
        animation.setFillAfter(true);//停在最后
        spaceshipImage.startAnimation(animation);

//        tipTextVIew.setText("正在加载中...");
        // 创建自定义样式dialog
//       spaceshipImage.setImageResource(res);

        // 设置布局
       setContentView(v);

    }


}
