package com.test.midasmobile9.util;

import java.security.MessageDigest;

public class Encryption {
    public static String getMD5(String str) {
        StringBuffer sb = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());

            byte byteData[] = md.digest();

            for ( int i = 0; i < byteData.length; i++ )  {
                sb.append(Integer.toHexString((int)byteData[i] & 0x00ff));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
