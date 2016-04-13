package com.example.administrator.thehealthy.entity;

/**
 * Created by Administrator on 2016/3/10.
 */
public class Summary {
    private int recordId;
    private String title;
    private String clinic;
    private String provider;
    private String serviceTime;
    private String typeAlias;
    private String itemAlias;

    public String getResident() {
        return resident;
    }

    public void setResident(String resident) {
        this.resident = resident;
    }

    private String resident;

    public Summary() {
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int record_id) {
        this.recordId = record_id;
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

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getTypeAlias() {
        return typeAlias;
    }

    public void setTypeAlias(String typeAlias) {
        this.typeAlias = typeAlias;
    }

    public String getItemAlias() {
        return itemAlias;
    }

    public void setItemAlias(String itemAlias) {
        this.itemAlias = itemAlias;
    }
}
