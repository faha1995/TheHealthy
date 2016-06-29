package com.seimun.mobileHealth.activity.inforactivity;

import android.content.Intent;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.seimun.mobileHealth.R;
import com.seimun.mobileHealth.activity.BaseActivity;
import com.seimun.mobileHealth.db.DBTool;
import com.seimun.mobileHealth.entity.AppData;
import com.seimun.mobileHealth.tools.ScrollViewOnTouch;

public class EducateWebViewActivity extends BaseActivity {
    private WebView webView;
    private String url;
    private int position;
    private TextView titleWv;
    private ImageView dialogIcon;
private DBTool dbTool;

    @Override
    protected int setLayout() {
        return R.layout.fragment_web_view;
    }

    @Override
    protected void initView() {
        dbTool = new DBTool();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
