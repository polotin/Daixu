package com.polotin.daixu.values;

public class Constant {
    public static final String MSG_EMPTY_ID = "请输入账号";
    public static final String MSG_EMPTY_PHONE = "请输入手机号";
    public static final String MSG_EMPTY_PASSWORD = "请输入密码";
    public static final String MSG_ILLEGAL_PHONE_NUMBER = "请输入正确的手机号";
    public static final String MSG_SEND_MSG_SUCCESS = "发送消息成功，请注意接收";
    public static final String MSG_SEND_MSG_FAIL = "发送消息失败，请检查后重试";

    public static final int LOGIN_SUCCESS = 1001;
    public static final int LOGIN_FAILED_INVALID_ACCOUNT = 1002;

    public static String MOBILE = "";
    public static String VALIDATE_CODE = "";
    public static String VALIDATE_CODE_TIME = "0";
    public static long VALIDATE_CODE_EXPIRE_TIME = 600000;
    public static String INVALID_CODE = "验证码错误";

    public static final int CODE_INTERNET = 8888;
    public static final int CODE_WRITE_EXTERNAL = 8889;
    public static final int CODE_READ_EXTERNAL = 8890;
}
