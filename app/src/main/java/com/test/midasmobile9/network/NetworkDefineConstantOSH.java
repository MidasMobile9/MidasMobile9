package com.test.midasmobile9.network;

public class NetworkDefineConstantOSH {
    // Host 주소
    public static final String HOST_URL = "http://192.168.0.21:52273";

    //요청 URL path
    // ex) public static String SERVER_URL_NAME;
    public static String SERVER_URL_GET_MENU;
    public static String SERVER_URL_GET_ORDER;
    public static String SERVER_URL_GET_MENU_IMG;
    public static String SERVER_URL_GET_PROFILE_IMG;

    static {
        SERVER_URL_GET_MENU = HOST_URL + "/menu";
        SERVER_URL_GET_ORDER = HOST_URL + "/order";
        SERVER_URL_GET_MENU_IMG = HOST_URL + "/menuimg/";
        SERVER_URL_GET_PROFILE_IMG = HOST_URL + "/profileimg/";
    }
}
