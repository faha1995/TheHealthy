package com.example.administrator.thehealthy.entity;

/**
 * Created by Administrator on 2016/3/8.
 * 健康教育实体类
 */
public class HealthEduEntity {
    private String title,content,create_at,create_by;
    private int item_id;

    public HealthEduEntity() {
    }

    public HealthEduEntity(String title, String content, String create_at, String create_by, int item_id) {
        this.title = title;
        this.content = content;
        this.create_at = create_at;
        this.create_by = create_by;
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
}
