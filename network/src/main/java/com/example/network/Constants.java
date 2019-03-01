package com.example.network;

/**
 * Author      :    DongJunJie
 * Date        :    2019/3/1
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class Constants {
    public static String BASE_URL = "http://car1.i.cacf.cn/";

    public static interface NetworkStatusCode {
        int SUCCESS = 0;
        int STATUS_CODE1 = 10022;
        int STATUS_CODE2 = 120006;
        int STATUS_CODE3 = 20005;
        int STATUS_CODE4 = 20006;
        int STATUS_CODE5 = 20007; //门票申领成功
        int STATUS_CODE6 = 20001;
        int CERT_APPLY_SUCCESS = 20008;
        int FAILURE = 9999;
        int STATUS_VERCODEMUCH = 40001;    //获取验证码的次数过于频繁;
        int EXCEED_MSGNUM = 40002;    //  超出每日限制短信条数;
    }
}
