package com.test.midasmobile9.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.midasmobile9.R;
import com.test.midasmobile9.data.CoffeeMenuItem;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserOrderOptionActivity extends AppCompatActivity {
    public static final String COFFEE_MENU_ITEM_EXTRA_NAME = "COFFEE_ITEM";

    CoffeeMenuItem coffeeMenuItem;

    @BindView(R.id.userOrderOptionImageView)
    ImageView userOrderOptionImageView;
    @BindView(R.id.userOrderOptionTitleTextView)
    TextView userOrderOptionTitleTextView;
    @BindView(R.id.userOrderOptionPriceTextView)
    TextView userOrderOptionPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_option);

        ButterKnife.bind(this);

        //유저가 선택한 커피
        Intent coffeeIntent = getIntent();
        coffeeMenuItem = coffeeIntent.getParcelableExtra(COFFEE_MENU_ITEM_EXTRA_NAME);

        setCoffeeMenuItemToView();
    }

    private void setCoffeeMenuItemToView() {
        //Glide.with(activity)
        //        .load(URL_IMAGE + coffeeMuenuItem.getImg()) // 이미지 URL 주소
        //        .into(userOrderOptionImageView);
        Glide.with(this)
                .load(R.drawable.ic_coffee_24dp)
                .into(userOrderOptionImageView);
        userOrderOptionTitleTextView.setText(coffeeMenuItem.getName());
        userOrderOptionPriceTextView.setText(String.format(Locale.KOREA, "%d 원", coffeeMenuItem.getPrice()));

    }
}
