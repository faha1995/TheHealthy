package com.example.administrator.thehealthy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/3/10.
 * 数据库帮助类
 */
public class DBHelper extends SQLiteOpenHelper implements SQLValues {
    private final String TAG = DBHelper.class.getSimpleName();
    private String CREATE_LOGIN_TABLE = "create table " + TABLE_USER + "("
            + KEY_USER_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT, "
            + KEY_MOBILE + " TEXT UNIQUE," + KEY_IDENTITY + " TEXT UNIQUE,"
            + KEY_UID + " TEXT," + KEY_CREATED_AT + " TEXT)";

    private String CREATE_SUMMARY_TABLE = "create table " + TABLE_SUMMARY + "("
            + KEY_SUMMARY_ID + " INTEGER PRIMARY KEY,"
            + KEY_RECORD_ID + " TEXT," + KEY_TITLE + " TEXT,"
            + KEY_CLINIC + " TEXT," + KEY_PROVIDER + " TEXT,"
            + KEY_SERVICE_TIME + " TEXT," + KEY_TYPE_ALIAS + " TEXT,"
            + KEY_ITEM_ALIAS + " TEXT)";

    private String CREATE_HEALTH_EDUCATION_TABLE = "create table " + TABLE_HEALTH + "("
            + KEY_HEALTH_ID + " INTEGER PRIMARY KEY," + KEY_HEALTH_TITLE + " TEXT,"
            + KEY_HEALTH_DESCRIP + " TEXT," + KEY_HEALTH_CREATE_AT + " TEXT,"
            + KEY_HEALTH_CONTENT_URL + " TEXT," + KEY_HEALTH_IMAGE_URL + " TEXT,"
            + KEY_HEALTH_ITEM_ID + " INTEGER," + KEY_HEALTH_CREATE_BY + " TEXT)";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i("DBHelper", "------>  DBHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBHelper","------>  onCreate0");
        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_SUMMARY_TABLE);
        db.execSQL(CREATE_HEALTH_EDUCATION_TABLE);
        Log.i("DBHelper", "------>  onCreate1");
//        Log.d(TAG, "----->" + "LOGIN_TABLE 和 SUMMARY_TABLE 数据库已创建完毕");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_HEALTH);
        onCreate(db);
    }


}
