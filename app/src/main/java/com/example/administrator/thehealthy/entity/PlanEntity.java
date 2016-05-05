package com.example.administrator.thehealthy.entity;

/**
 * Created by Administrator on 2016/5/5.
 */
public class PlanEntity {
    private String status;
    private String next_date;
    private String service_item;
    private String type_alias;
    private String service_type;
    private String item_alias;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNext_date() {
        return next_date;
    }

    public void setNext_date(String next_date) {
        this.next_date = next_date;
    }

    public String getService_item() {
        return service_item;
    }

    public void setService_item(String service_item) {
        this.service_item = service_item;
    }

    public String getType_alias() {
        return type_alias;
    }

    public void setType_alias(String type_alias) {
        this.type_alias = type_alias;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getItem_alias() {
        return item_alias;
    }

    public void setItem_alias(String item_alias) {
        this.item_alias = item_alias;
    }
}
