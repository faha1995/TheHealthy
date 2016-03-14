package com.example.administrator.thehealthy.db;

/**
 * Created by Administrator on 2016/3/10.
 */
public interface SQLValues {

    // 版本号
    static final int DATABASE_VERSION = 1;
    // 数据库名
    static final String DATABASE_NAME = "health.db";
    // 登录表名
    static final String TABLE_USER = "health_user";
    // 登录表列名
    static final String KEY_USER_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_MOBILE = "mobile";
    static final String KEY_IDENTITY = "identity";
    static final String KEY_UID = "resident_id";
    static final String KEY_CREATED_AT = "create_at";

    // 具体数据表名
    static final String TABLE_SUMMARY = "summary";
    // 具体数据表列名
    static final String KEY_SUMMARY_ID = "id";
    static final String KEY_RECORD_ID = "record_id";
    static final String KEY_TITLE = "title";
    static final String KEY_CLINIC = "clinic";
    static final String KEY_PROVIDER = "provider";
    static final String KEY_SERVICE_TIME = "service_time";
    static final String KEY_TYPE_ALIAS = "type_alias";
    static final String KEY_ITEM_ALIAS = "item_alias";

    static final String SP_NAME = "notFirst";
    static final String HAS_LOGINED = "hasLogined";
}
