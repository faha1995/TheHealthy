package com.example.administrator.thehealthy.tools;

/**
 * Created by Administrator on 2016/3/8.
 * 分割字符串
 */
public class ChangeString {

// 测试
//    public static void main(String[] arg){
//        String a = "2016-03-08 16：12：55";
//        System.out.print(SplitTime(a));
//    }

    // 将字符串2016-03-08 16：12：55 转换成 16：12：55 2016-03-08
    public static String SplitTime(String string){
        String oneString,twoString,finallString;
        String[] split = string.split("\\s+");
        oneString = split[0];
        twoString = split[1];
        finallString = twoString+"   "+oneString;
        return  finallString;
    }

    // 将字符串2016-03-08 16：12：55 截取成 16：12：55
    public static String SplitForTime(String string){
        String timeString;
        String[] split = string.split("\\s+");
        timeString = split[1];
        return timeString;
    }

    // 将字符串2016-03-08 16：12：55 截取成 2016-03-08
    public static String SplitForDate(String string){
        String dateString;
        String[] split = string.split("\\s+");
        dateString = split[0];
        return dateString;
    }


}
