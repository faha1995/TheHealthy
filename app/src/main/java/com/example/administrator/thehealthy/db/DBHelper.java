package com.example.administrator.thehealthy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_SUMMARY_TABLE);
//        Log.d(TAG, "----->" + "LOGIN_TABLE 和 SUMMARY_TABLE 数据库已创建完毕");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USER);
        onCreate(db);
    }


}
