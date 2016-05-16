package com.example.administrator.thehealthy.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/9.
 */
public class AppData {
    // 静态的集合会始终保持，直至被回收
    public static List<HealthEduEntity> eduEntityList = new ArrayList<>();
    public static List<String> hrGroups = new ArrayList<>();
    public static List<List<Summary>> hrChilds = new ArrayList<>();

    public static List<String> spGroups = new ArrayList<>();
    public static List<List<PlanEntity>> spChilds = new ArrayList<>();
    public static int counts = 0;
    public static int eduCounts = 0;
    public static List<Integer> reportCounts = new ArrayList<>();
    public static List<Integer> planeCounts  = new ArrayList<>();

}
