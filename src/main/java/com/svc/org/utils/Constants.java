package com.svc.org.utils;

/**
 * 作者 mcy
 * 日期 2018/9/1 15:56
 * 常量
 */
public class Constants {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;

    /**
     * 存放Token的header字段  (@author: rico)
     */
    public static final String DEFAULT_TOKEN_NAME = "Token";

    public static final int SUCCESS_CODE = 1;
    public static final int ERROR_CODE = 0;
    public static final String SUCCESS_STR = "成功";
    public static final String ERROR_STR = "失败";
}
