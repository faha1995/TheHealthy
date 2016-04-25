package com.example.administrator.thehealthy.activity.inforactivity;

import android.content.Intent;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.activity.BaseActivity;
import com.example.administrator.thehealthy.entity.AppData;
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;

public class EducateWebViewActivity extends BaseActivity {
    private WebView webView;
    private String url;
    private int position;
    private TextView titleWv;
    private ImageView dialogIcon;


    @Override
    protected int setLayout() {
        return R.layout.fragment_web_view;
    }

    @Override
    protected void initView() {
        webView = findView(R.id.webView_health_educate);
        titleWv = findView(R.id.text_educate_webView_title);
        ScrollViewOnTouch.getInstance().setViewFinishTouchFromActivity(webView,EducateWebViewActivity.this);
        Intent intent = getIntent();
        position = intent.getIntExtra("pos",0);
        titleWv.setText(AppData.eduEntityList.get(position).getTitle());
        url = AppData.eduEntityList.get(position).getContent_url();
        dialogIcon = findView(R.id.img_loading);
        initData();
    }


    protected void initData() {
        setWebView(EducateWebViewActivity.this, webView, url,dialogIcon);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.no_move, R.anim.move_out_from_right);
    }


}
