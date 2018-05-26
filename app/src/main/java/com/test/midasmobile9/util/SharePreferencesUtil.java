package com.test.midasmobile9.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferencesUtil {
    public static String KEY_TOKEN = "token";
    public static String KEY_EMAIL = "email";
    public static String KEY_PASSWORD = "password";
    public static String KEY_ROOT = "root";

    /**
     * 값 불러오기
     * @param context 컨텍스트
     * @param key 키값
     * @return String
     */
    public static String getPreferences(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString(key, null);
    }

    /**
     * 값 저장하기
     * @param context 컨텍스트
     * @param key 키값
     * @param value 밸류값
     * @return boolean
     */
    public static boolean savePreferences(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 키값 지우기
     * @param context 컨텍스트
     * @param key 키값
     * @return boolean
     */
    public static boolean removePreferences(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 키값 전부 지우기
     * @param context 컨텍스트
     * @return boolean
     */
    public static boolean removeAllPreferences(Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        return editor.commit();
    }
}
