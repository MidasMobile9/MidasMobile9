package com.test.midasmobile9.network;

public class NetworkDefineConstantOSH {
    // Host 주소
    public static final String HOST_URL = "http://35.187.156.145:3000";

    //요청 URL path
    // ex) public static String SERVER_URL_NAME;
    public static String SERVER_URL_MENU;
    public static String SERVER_URL_ORDER;

    static {
        SERVER_URL_MENU = HOST_URL + "/menu/";
        SERVER_URL_ORDER = HOST_URL + "/order/";
    }
}
