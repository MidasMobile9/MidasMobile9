package com.test.midasmobile9.network;

public class NetworkDefineConstantCYJ {
    // Host 주소
    public static final String HOST_URL = "http://192.168.0.21:52273";

    public static String SERVER_URL_GET_ORDERS;
    public static String SERVER_URL_GET_ORDER_DONE;
    public static String SERVER_URL_UPDATE_ORDER_TAKEOUT;
    public static String SERVER_URL_GET_MENU_INFO;
    public static String SERVER_URL_ADD_NEW_MENU;
    public static String SERVER_URL_EDIT_MENU;
    public static String SERVER_URL_DELETE_MENU;
    public static String SERVER_URL_GET_CUSTOMERS;
    public static String SERVER_URL_DELETE_CUSTOMERS;
    public static String SERVER_URL_EDIT_CUSTOMERS;
    public static String SERVER_URL_ADD_CUSTOMERS;


    static {
        SERVER_URL_GET_ORDERS = HOST_URL + "/order";
        SERVER_URL_GET_ORDER_DONE = HOST_URL + "/order/done";
        SERVER_URL_UPDATE_ORDER_TAKEOUT = HOST_URL + "/order/update";
        SERVER_URL_GET_MENU_INFO = HOST_URL + "/menu";
        SERVER_URL_ADD_NEW_MENU = HOST_URL + "/menu/insert";
        SERVER_URL_EDIT_MENU = HOST_URL + "/menu/update";
        SERVER_URL_DELETE_MENU = HOST_URL + "/menu/delete";
        SERVER_URL_GET_CUSTOMERS = HOST_URL + "/userinfo";
        SERVER_URL_DELETE_CUSTOMERS = HOST_URL + "/delete/user";
        SERVER_URL_EDIT_CUSTOMERS = HOST_URL + "/update/user";
        SERVER_URL_ADD_CUSTOMERS = HOST_URL + "/join";
    }
}

