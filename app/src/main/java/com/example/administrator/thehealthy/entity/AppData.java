package com.example.administrator.thehealthy.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/9.
 */
public class AppData {
    // 静态的集合会始终保持，直至被回收
    public static List<HealthEduEntity> eduEntityList = new ArrayList<>();

}
