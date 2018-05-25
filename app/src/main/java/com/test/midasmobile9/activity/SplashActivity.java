package com.test.midasmobile9.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.midasmobile9.R;

public class SplashActivity extends AppCompatActivity {

    public static SplashActivity SPLASH_ACTIVITY = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SPLASH_ACTIVITY = SplashActivity.this;

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
