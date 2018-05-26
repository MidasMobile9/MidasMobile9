package com.test.midasmobile9.util;

public class ParseServerDatetime {
    public static String getYear(String datetime){
        return datetime.substring(0, 4);
    }
    public static String getMonth(String datetime){
        return datetime.substring(5, 7);
    }
    public static String getDate(String datetime){
        return datetime.substring(8, 10);
    }
    public static String getHour(String datetime){
        return datetime.substring(11, 13);
    }
    public static String getMinute(String datetime){
        return datetime.substring(14, 16);
    }
    public static String getSecond(String datetime){
        return datetime.substring(17, 19);
    }
}
