package com.test.midasmobile9.util;

import android.content.Context;
import android.widget.Toast;


import com.test.midasmobile9.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordUtil {
    public static boolean checkPassword(Context context, String password, String email) {
        if(password.length()==0){
            //비밀번호 길이가 0일 경우
            Toast.makeText(context, context.getResources().getString(R.string.password_length_zero), Toast.LENGTH_SHORT).show();
            return false;
        }
        String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{9,12}$";
        Matcher matcher = Pattern.compile(pwPattern).matcher(password);
        pwPattern = "(.)\\1\\1\\1";
        Matcher matcher2 = Pattern.compile(pwPattern).matcher(password);
        if(!matcher.matches()){
            //영문 숫자 특수문자 구분
            Toast.makeText(context, context.getResources().getString(R.string.password_combination), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(matcher2.find()){
            //같은 문자 4자리 이상
            Toast.makeText(context, context.getResources().getString(R.string.password_repeat), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.contains(email)){
            //아이디 포함 여부
            Toast.makeText(context, context.getResources().getString(R.string.password_contain_id), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.contains(" ")){
            //공백문자 사용 불가
            Toast.makeText(context, context.getResources().getString(R.string.password_blank), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean passwordCheck(Context mContext, String passwordOriginal, String passwordNew, String strEmail){
        if(passwordOriginal.length()==0){
            //비밀번호 길이가 0일 경우
            Toast.makeText(mContext, mContext.getResources().getString(R.string.password_length_zero), Toast.LENGTH_SHORT).show();
            return false;
        }
        String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{9,12}$";
        Matcher matcher = Pattern.compile(pwPattern).matcher(passwordNew);
        pwPattern = "(.)\\1\\1\\1";
        Matcher matcher2 = Pattern.compile(pwPattern).matcher(passwordNew);
        if(!matcher.matches() && passwordNew.length() > 0){
            //영문 숫자 특수문자 구분
            Toast.makeText(mContext, mContext.getResources().getString(R.string.password_combination), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(matcher2.find() && passwordNew.length() > 0){
            //같은 문자 4자리 이상
            Toast.makeText(mContext, mContext.getResources().getString(R.string.password_repeat), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passwordNew.contains(strEmail)){
            //아이디 포함 여부
            Toast.makeText(mContext, mContext.getResources().getString(R.string.password_contain_id), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passwordNew.contains(" ")){
            //공백문자 사용 불가
            Toast.makeText(mContext, mContext.getResources().getString(R.string.password_blank), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!passwordNew.equals(passwordOriginal)){
            //비밀번호 확인
            Toast.makeText(mContext, mContext.getResources().getString(R.string.password_not_matched), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
