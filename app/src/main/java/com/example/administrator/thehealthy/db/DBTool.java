package com.example.administrator.thehealthy.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.thehealthy.application.BaseApplication;
import com.example.administrator.thehealthy.entity.HealthEduEntity;
import com.example.administrator.thehealthy.entity.Summary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
//        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("mobile", mobile);
        values.put("identity", identity);
        values.put("resident_id", resident_id);
        values.put("create_at", create_at);
        database.insert(TABLE_USER, null, values);
//        long id = database.insert(TABLE_USER, null, values);
//        database.close();

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
//        database = dbHelper.getWritableDatabase();
        database.delete(TABLE_USER, null, null);
//        database.close();

        Log.i(TAG, "Deleted all user from TABLE_USER");
    }

    // 删除当前数据库中所有个人信息
    public void deleteSummary() {
        // 由于在此之前的调用deleteUser()方法中database已经关闭
        // 所以再次调用时要初始化
//        this.database = dbHelper.getWritableDatabase();
        database.delete(TABLE_SUMMARY, null, null);

        Log.i(TAG, "Deleted all user infor from TABLE_SUMMARY");
    }

//    // 复写添加健康报告方法
//    public void addSummary(Summary summary) {
//        addSummary(summary.getRecordId(), summary.getTitle(), summary.getClinic(),
//                summary.getProvider(), summary.getServiceTime(), summary.getTypeAlias(),
//                summary.getItemAlias());
//    }
//
//    // 添加健康报告方法
//    public void addSummary(int record_id, String title, String clinic, String provider,
//                           String service_time, String type_alias, String item_alias) {
////        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        Log.i(TAG, "开始添加健康报告数据");
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_RECORD_ID, record_id);
//        values.put(KEY_TITLE, title);
//        values.put(KEY_CLINIC, clinic);
//        values.put(KEY_PROVIDER, provider);
//        values.put(KEY_SERVICE_TIME, service_time);
//        values.put(KEY_TYPE_ALIAS, type_alias);
//        values.put(KEY_ITEM_ALIAS, item_alias);
////        database.close();
//        Log.i(TAG, "健康报告数据添加完毕");
//    }


    public List<Summary> querySummary(){return null;};


    // 将解析后的数据添加到数据库中
    public void saveHealthEduData(List<HealthEduEntity> healthEduEntity) {
//        database = dbHelper.getWritableDatabase();
        /**循环方式将数据插入到数据库中*/
        for (HealthEduEntity entity : healthEduEntity) {
            ContentValues values = new ContentValues();
            values.put(KEY_HEALTH_TITLE, entity.getTitle());
            values.put(KEY_HEALTH_DESCRIP, entity.getDescription());
            values.put(KEY_HEALTH_IMAGE_URL, entity.getImage_url());
            values.put(KEY_HEALTH_CONTENT_URL, entity.getContent_url());
            values.put(KEY_HEALTH_CREATE_AT, entity.getCreate_at());
            values.put(KEY_HEALTH_CREATE_BY, entity.getCreate_by());
            database.insert(TABLE_HEALTH, null, values);
        }
    }

    public void saveRefreshHealthEduData(HealthEduEntity healthEduEntity) {
//        database = dbHelper.getWritableDatabase();
        /**循环方式将数据插入到数据库中*/

        ContentValues values = new ContentValues();
        values.put(KEY_HEALTH_TITLE, healthEduEntity.getTitle());
        values.put(KEY_HEALTH_DESCRIP, healthEduEntity.getDescription());
        values.put(KEY_HEALTH_IMAGE_URL, healthEduEntity.getImage_url());
        values.put(KEY_HEALTH_CONTENT_URL, healthEduEntity.getContent_url());
        values.put(KEY_HEALTH_CREATE_AT, healthEduEntity.getCreate_at());
        values.put(KEY_HEALTH_CREATE_BY, healthEduEntity.getCreate_by());
        database.insert(TABLE_HEALTH, null, values);

    }

    // 删除数据库中的数据
    public void deleteHealthEduData() {
        database.delete(TABLE_HEALTH, null, null);
    }

    // 查询数据库中的HealthEdu的数据
    public List<HealthEduEntity> queryHealthData() {
        Cursor cursor = database.query(TABLE_HEALTH, null, null, null, null, null, null);
        if (cursor != null) {
            List<HealthEduEntity> entities = new ArrayList<>();

            while (cursor.moveToNext()) {
                // 通过列名所在的列序取列值
                String title = cursor.getString(cursor.getColumnIndex(KEY_HEALTH_TITLE));
                String descrip = cursor.getString(cursor.getColumnIndex(KEY_HEALTH_DESCRIP));
                String create_at = cursor.getString(cursor.getColumnIndex(KEY_HEALTH_CREATE_AT));
                String create_by = cursor.getString(cursor.getColumnIndex(KEY_HEALTH_CREATE_BY));
                int item_id = cursor.getInt(cursor.getColumnIndex(KEY_HEALTH_ITEM_ID));
                String image_url = cursor.getString(cursor.getColumnIndex(KEY_HEALTH_IMAGE_URL));
                String content_ulr = cursor.getString(cursor.getColumnIndex(KEY_HEALTH_CONTENT_URL));

                HealthEduEntity eduEntity = new HealthEduEntity();
                eduEntity.setTitle(title);
                eduEntity.setDescription(descrip);
                eduEntity.setCreate_by(create_by);
                eduEntity.setCreate_at(create_at);
                eduEntity.setImage_url(image_url);
                eduEntity.setItem_id(item_id);
                eduEntity.setContent_url(content_ulr);

                entities.add(eduEntity);
            }
            cursor.close();
            return entities;
        }

        return null;
    }


}
