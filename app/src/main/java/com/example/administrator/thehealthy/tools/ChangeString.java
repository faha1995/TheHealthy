package com.example.administrator.thehealthy.tools;

/**
 * Created by Administrator on 2016/3/8.
 * 分割字符串
 */
public class ChangeString {

// 测试
    public static void main(String[] arg){
        String a = "预防接种服务:乙肝疫苗";
        System.out.print(splitForPurpose(a));
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

        String[] split=string.split("\"");
        mainString = split[1];
        return mainString;
    }

    // 将字符串 预防接种服务：乙肝疫苗 截取为 预防接种服务
    public static String splitForTitle(String string){
        String titleString;
        titleString = string.split(":")[0];

        return titleString;
        }

    // 将字符串 预防接种服务：乙肝疫苗 截取为 乙肝疫苗
    public static String splitForPurpose(String string){
        String purposeString;
        purposeString = string.split(":")[1];

        return purposeString;
    }


}
