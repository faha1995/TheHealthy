package com.example.administrator.thehealthy.tools;

/**
 * Created by Administrator on 2016/3/8.
 * 分割字符串
 */
public class ChangeString {

    // 将字符串2016-03-08 16：12：55转换成 16：12：55 2016-03-08
    public static String SplitTime(String string){
        String oneString,twoString,finallString;
        String[] split = string.split("//s+");
        oneString = split[0];
        twoString = split[1];
        finallString = twoString+" "+oneString;
        return  finallString;
    }
}
