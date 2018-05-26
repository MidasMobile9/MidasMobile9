package com.test.midasmobile9.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.midasmobile9.R;
import com.test.midasmobile9.application.MidasMobile9Application;
import com.test.midasmobile9.data.User;
import com.test.midasmobile9.model.LoginModel;
import com.test.midasmobile9.util.SharePreferencesUtil;

import java.util.Map;

import static com.test.midasmobile9.util.SharePreferencesUtil.*;

public class SplashActivity extends AppCompatActivity {

    public static SplashActivity SPLASH_ACTIVITY = null;
    private Context mContext = SplashActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SPLASH_ACTIVITY = SplashActivity.this;
        String email = SharePreferencesUtil.getPreferences(mContext,KEY_EMAIL);
        String password = SharePreferencesUtil.getPreferences(mContext,KEY_PASSWORD);
        String root = SharePreferencesUtil.getPreferences(mContext,KEY_ROOT);
        if(email!=null&&password!=null&&root!=null){
            new UserLoginTask().execute(email,password,root);
        }else{
            // 스플래시 0.7초 정도 보여주기
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }, 700);
        }

    }

    public class UserLoginTask extends AsyncTask<String, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(String... params) {
            String strEmail = params[0];
            String strPassword = params[1];
            String strRoot = params[2];
            Map<String, Object> map = LoginModel.loginUser(strEmail, strPassword,strRoot);

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);

            if ( map == null ) {
                // 통신실패
                SharePreferencesUtil.removePreferences(mContext,KEY_EMAIL);
                SharePreferencesUtil.removePreferences(mContext,KEY_PASSWORD);
                SharePreferencesUtil.removePreferences(mContext,KEY_ROOT);
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                // 통신성공
                boolean result = false;
                String message = null;
                String cookie = null;
                User user = null;

                if ( map.containsKey("result") ) {
                    result = (boolean)map.get("result");
                }

                if ( map.containsKey("message") ) {
                    message = (String)map.get("message");
                }

                if ( map.containsKey("cookie") ) {
                    cookie = (String)map.get("cookie");
                }

                if ( map.containsKey("data") ) {
                    user = (User)map.get("data");
                }

                if ( result ) {
                    // 로그인 성공
                    MidasMobile9Application.setCookie(cookie);
                    MidasMobile9Application.setUser(user.getNo(), user.getEmail(), user.getNickname(), user.getProfileimg());
                    Intent intent = new Intent(mContext,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 로그인 실패
                    SharePreferencesUtil.removePreferences(mContext,KEY_EMAIL);
                    SharePreferencesUtil.removePreferences(mContext,KEY_PASSWORD);
                    SharePreferencesUtil.removePreferences(mContext,KEY_ROOT);
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}
