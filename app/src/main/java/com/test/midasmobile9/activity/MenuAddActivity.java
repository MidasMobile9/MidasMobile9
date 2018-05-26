package com.test.midasmobile9.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.midasmobile9.R;

import butterknife.ButterKnife;

public class MenuAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add);
        // 버터나이프
        ButterKnife.bind(MenuAddActivity.this);
    }
}
