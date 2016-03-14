package com.example.administrator.thehealthy.entity;

/**
 * Created by Administrator on 2016/3/10.
 */
public class Summary {
    private int record_id;
    private String title,clinic,provider,service_time,
    type_alias,item_alias;

    public Summary() {
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getType_alias() {
        return type_alias;
    }

    public void setType_alias(String type_alias) {
        this.type_alias = type_alias;
    }

    public String getItem_alias() {
        return item_alias;
    }

    public void setItem_alias(String item_alias) {
        this.item_alias = item_alias;
    }
}
