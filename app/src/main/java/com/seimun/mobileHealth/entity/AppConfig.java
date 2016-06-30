package com.seimun.mobileHealth.entity;

/**
 * Created by Administrator on 2016/3/8.
 * 接口
 */
public class AppConfig {

    //BaseUrl
    public static String BASE_URL = "http://124.248.40.142/backend/";

    // Server user login url
    public static String URL_LOGIN = "http://124.248.40.142/backend/login/";

    // Server user register url
    public static String URL_REGISTER = "http://124.248.40.142/backend/register/";
    public static String URL_PERSONAL_INFO = "http://124.248.40.142/backend/personal_info/";
    //new register url
    public static String URL_REGISTER_NEW = BASE_URL + "register_new/";

    //get verification code
    public static String URL_GET_VERIFICATION_CODE = BASE_URL + "get_verification_code/";
    //reset password
    public static String URL_RESET_PASSWORD = BASE_URL + "reset_password/";

    // url to get service list
    public static String URL_SUMMARYS = "http://124.248.40.142/backend/summaries/";
    public static String URL_EDUCATION = "http://124.248.40.142/backend/educations/";
    public static String URL_DETAIL = "http://124.248.40.142/backend/detail/";
    public static String URL_EVALUATE = "http://124.248.40.142/backend/evaluate/";
    public static String URL_PLANS = "http://124.248.40.142/backend/plans/";

}
