package com.test.midasmobile9.network;

public class NetworkDefineConstant_PIJ {
    // Host 주소
    public static final String HOST_URL = "http://192.168.0.21:52273";

    //요청 URL path
    // ex) public static String SERVER_URL_NAME;
    public static String join;
    public static String checkNickname;
    public static String checkEmail;
    public static String checkRootCode;
    public static String SERVER_URL_LOGIN_USER;
    public static String SERVER_URL_UPDATE;
    public static String SERVER_URL_DELETE;
    public static String SERVER_URL_LOGOUT_USER;

    static {
        join = HOST_URL + "/join";
        checkNickname = HOST_URL + "/check/nickname/";
        checkEmail = HOST_URL + "/check/email/";
        checkRootCode = HOST_URL + "/check/rootcode/";
        SERVER_URL_LOGIN_USER = HOST_URL + "/login";
        SERVER_URL_UPDATE = HOST_URL + "/update/user/";
        SERVER_URL_DELETE = HOST_URL + "/delete/user/";
        SERVER_URL_LOGOUT_USER = HOST_URL + "/logout/";
    }
}
