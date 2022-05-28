package me.candybox.core.config;

public class ConstantConfig {

    public static int RESULT_STATUS_SUCC=0;             //成功
    public static int RESULT_STATUS_FAIL=1;             //失败
    public static int RESULT_STATUS_PARA_FAIL=2;        //参数错误
    public static int RESULT_STATUS_NO_ACCESSTOKEN=3;   //access token 过期
    public static int RESULT_STATUS_NO_AUTH=4;          //access token 权限错误
    public static int RESULT_STATUS_EXCEP=-1;           //异常


    public static String ACCESS_TOKEN_KEY = "access-token";

    public static final String ACCESS_TOKEN_ID = "oauth:access-token:id:";
    public static final String ACCESS_TOKEN_VALUE = "oauth:access-token:value:";

    
    

    
}
