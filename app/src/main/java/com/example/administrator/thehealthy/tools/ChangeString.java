package com.example.administrator.thehealthy.tools;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8.
 * 分割字符串
 */
public class ChangeString {

    // 测试
    public static void main(String[] arg) {
        String a = "2016-03-08";
        System.out.print(splitTime(a));
    }

    // 将字符串2016-03-08 16：12：55 转换成 16：12：55 2016-03-08
    public static String splitTime(String string) {
        String oneString, twoString, finallString;
        String[] split = string.split("\\s+");
        oneString = split[0];
        twoString = split[1];
        finallString = twoString + "   " + oneString;
        return finallString;
    }

    // 将字符串2016-03-08 16：12：55 截取成 16：12：55
    public static String splitForTime(String string) {
        String timeString;
        timeString = string.split("\\s+")[1];

        return timeString;
    }

    // 将字符串2016-03-08 16：12：55 截取成 2016-03-08
    public static String splitForDate(String string) {
        String dateString;
        dateString = string.split("\\s+")[0];

        return dateString;
    }

    // 将字符串["全公费"] 截取为 全公费
    public static String splitMain(String string) {
        String mainString;

        String[] split = string.split("\"");
        mainString = split[1];
        return mainString;
    }

    // 将字符串["多饮","少饮"] 截取为 多饮 少饮
    public static String splitMainMore(String string) {
        String allString;

        String[] split = string.split("\"");
        if (split.length > 4) {
            allString = split[1] + "  " + split[3];
        } else {
            allString = split[1];
        }
        return allString;
    }

    // 将字符串 预防接种服务：乙肝疫苗 截取为 预防接种服务
    public static String splitForTitle(String string) {
        String titleString;
        titleString = string.split(":")[0];

        return titleString;
    }

    // 将字符串 预防接种服务：乙肝疫苗 截取为 乙肝疫苗
    public static String splitForPurpose(String string) {
        String purposeString;
        purposeString = string.split(":")[1];

        return purposeString;
    }

    // 将字符串 "全公费" 截取为 全公费
    public static String splitOut(String string) {
        String mainString;

        String[] split = string.split("\"");
        mainString = split[0];
        return mainString;
    }


    // 判断服务计划是否过期的方法
    public static int planStatus(String string, int nowYear, int nowMonth, int nowDate) {
        // 值为 1 代表计划， 2 代表即将过期， 3 代表已经过期

        // 获得分割后的 “年 月 日” 与传进来的值进行比较
        List<String> split = Arrays.asList(string.split("-"));
        int year = Integer.parseInt(split.get(0));
        int month = Integer.parseInt(split.get(1));
        int date = Integer.parseInt(split.get(2));

        Log.i("int", "------> year " + year + " month " + month + " date ");
        int status = 0;

        if (nowYear > year) {
            // 如果是相邻的两年
            if (nowYear == year + 1 && nowMonth == 1 && month == 12) {
                if (nowYear % 4 == 0) {
                    if (366 - howDays(year, month, date) - 1 > 3) {
                        status = 1;
                    } else if (366 - howDays(year, month, date) - 1 > 0) {
                        status = 2;
                    } else {
                        status = 3;
                    }

                }
            } else {
                status = 1;
            }
        }


        // 同一年
        if (nowYear == year) {
            int sum = howDays(nowYear, nowMonth, nowDate) - howDays(year, month, date) - 1;

            if (sum > 3) {
                status = 1;
            } else if (sum > 0) {
                status = 2;

            } else {
                status = 3;
            }
        }


        return status;
    }

    // 计算当前日期是当年的第几天
    public static int howDays(int year, int month, int date) {
        int days = 0;
        // 润年

        switch (month) {
            case 1:
                days = 0;
                break;
            case 2:
                days = 31;
                break;
            case 3:
                days = 59;
                break;
            case 4:
                days = 90;
                break;
            case 5:
                days = 120;
                break;
            case 6:
                days = 151;
                break;
            case 7:
                days = 181;
                break;
            case 8:
                days = 212;
                break;
            case 9:
                days = 243;
                break;
            case 10:
                days = 273;
                break;
            case 11:
                days = 304;
                break;
            case 12:
                days = 334;
                break;

        }
        // 如果是闰年
        if (year % 4 == 0) {
            days += date + 1;
        } else {
            days += date;
        }

        return days;

    }


    private static boolean isleapyear(int y) {
        return (y % 4 == 0 || y % 400 == 0);
    }

    private static int sum(int y, int m, int d) {


        int[] md = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int cnt = y * 365;
        cnt += (y - 1) / 4 + 1;
        cnt -= (y - 1) / 100 + 1;
        cnt += (y - 1) / 400 + 1;
        for (int i = 1; i < m; ++i) cnt += md[i];
        if (m > 2 && isleapyear(y)) ++cnt;
        cnt += d;
        return cnt;
    }

    public static int Count(String string, int y1, int m1, int d1) {
        List<String> split = Arrays.asList(string.split("-"));
        int y2 = Integer.parseInt(split.get(0));
        int m2 = Integer.parseInt(split.get(1));
        int d2 = Integer.parseInt(split.get(2));
        int status = 0;
        if (sum(y2, m2, d2) - sum(y1, m1, d1) +1 > 3) {
            status = 1;
        } else if (sum(y2, m2, d2) - sum(y1, m1, d1) + 1 > 0) {
            status = 2;
        } else {
            status = 3;
        }

        return status;
    }


}
