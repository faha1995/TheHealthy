package com.example.administrator.thehealthy.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.thehealthy.application.BaseApplication;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/3/10.
 */
public class DBTool implements SQLValues {
    private static final String TAG = DBTool.class.getSimpleName();
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public DBTool() {
        context = BaseApplication.getContext();
        dbHelper = new DBHelper(context, DATABASE_NAME,
                null, DATABASE_VERSION);
        // 获得一个可写的数据库类
        database = dbHelper.getWritableDatabase();
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    // 标示是否是第一次进入
    public void setLogin(boolean isLogined) {
        editor.putBoolean(HAS_LOGINED, isLogined);
        editor.commit();
    }

    // 是否是第一次进入
    public boolean isLogined() {
        return sp.getBoolean(HAS_LOGINED, false);
    }

    // 向本地数据库添加用户的方法
    public void addUser(String name, String mobile, String identity,
                        String resident_id, String create_at) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("mobile", mobile);
        values.put("identity", identity);
        values.put("resident_id", resident_id);
        values.put("create_at", create_at);
        database.insert(TABLE_USER, null, values);
//        long id = database.insert(TABLE_USER, null, values);
        database.close();

//        Log.d(TAG, "New user inserted into SQLite: " + id);

    }

    // 从数据库中获得详细信息
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "select * from " + TABLE_USER;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getColumnCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("mobile", cursor.getString(2));
            user.put("identity", cursor.getString(3));
            user.put("resident_id", cursor.getString(4));
            user.put("created_at", cursor.getString(5));
        }
        cursor.close();
        db.close();

        return user;
    }

    // 删除当前数据库中所有信息
    public void deleteUser() {
        database.delete(TABLE_USER, null, null);
        database.close();

        Log.i(TAG, "Deleted all user from TABLE_USER");
    }

    // 删除当前数据库中所有个人信息
    public void deleteSummary() {
        // 由于在此之前的调用deleteUser()方法中database已经关闭
        // 所以再次调用时要初始化
        this.database = dbHelper.getWritableDatabase();
        database.delete(TABLE_SUMMARY, null, null);
        database.close();

        Log.i(TAG, "Deleted all user infor from TABLE_SUMMARY");
    }


}
