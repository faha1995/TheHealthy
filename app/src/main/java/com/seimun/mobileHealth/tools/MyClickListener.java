package com.seimun.mobileHealth.tools;

/**
 * Created by Administrator on 2016/3/9.
 * 跳转接口
 */
public interface MyClickListener {
    void myOnClickListener(int pos);
    void myOnClickListener(String type_alias,String item_alias, String title,int record_id);
}
