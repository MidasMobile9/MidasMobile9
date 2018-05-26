package com.test.midasmobile9.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.midasmobile9.R;
import com.test.midasmobile9.data.CoffeeMenuItem;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserOrderOptionActivity extends AppCompatActivity {
    public static final String COFFEE_MENU_ITEM_EXTRA_NAME = "COFFEE_ITEM";
    private static final String[] orderOptionSizeStrArray = {
            "Small",
            "Medium",
            "Large"
    };

    CoffeeMenuItem coffeeMenuItem;

    @BindView(R.id.userOrderOptionMainLayout)
    LinearLayout userOrderOptionMainLayout;

    @BindView(R.id.userOrderOptionImageView)
    ImageView userOrderOptionImageView;
    @BindView(R.id.userOrderOptionTitleTextView)
    TextView userOrderOptionTitleTextView;
    @BindView(R.id.userOrderOptionPriceTextView)
    TextView userOrderOptionPriceTextView;

    @BindView(R.id.userOrderOptionTemperSpinner)
    Spinner userOrderOptionTemperSpinner;
    @BindView(R.id.userOrderOptionSizeSpinner)
    Spinner userOrderOptionSizeSpinner;

    @BindView(R.id.userOrderOptionQuantityIncreaseImageView)
    ImageView userOrderOptionQuantityIncreaseImageView;
    @BindView(R.id.userOrderOptionQuantityReduceImageView)
    ImageView userOrderOptionQuantityReduceImageView;

    @BindView(R.id.userOrderOptionQuantityTextView)
    TextView userOrderOptionQuantityTextView;


    long mBackPressedTime;

    int coffeeItemQuantity;
    String coffeeItemSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_option);

        ButterKnife.bind(this);

        //유저가 선택한 커피
        Intent coffeeIntent = getIntent();
        coffeeMenuItem = coffeeIntent.getParcelableExtra(COFFEE_MENU_ITEM_EXTRA_NAME);

        setInitData();
        setCoffeeMenuItemToView();
        setOrderOptionInit();
    }

    @Override
    public void onBackPressed() {
        // 드로어가 닫혀있으면 앱 종료
        if (System.currentTimeMillis() - mBackPressedTime > 2000) {
            Snackbar.make(userOrderOptionMainLayout, "뒤로 버튼을 한번 더 눌리시면 주문을 취소합니다", Snackbar.LENGTH_LONG)
                    .setAction("EXIT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancleOrder();
                        }
                    })
                    .show();
            mBackPressedTime = System.currentTimeMillis();
        } else {
            cancleOrder();
        }

    }


    @OnClick(R.id.menuOrderOptionBackImageView)
    public void onMenuOrderOptionBackImageViewClick(){
        cancleOrder();
    }
    @OnClick(R.id.userOrderOptionTotalOrderTextView)
    public void onUserOrderOptionTotalOrderTextViewClick(){
        menuOrder();
    }

    @OnClick(R.id.userOrderOptionQuantityIncreaseImageView)
    public void onUserOrderOptionQuantityIncreaseImageViewClick(){
        
    }

    private void setInitData(){
        coffeeItemQuantity = 1;
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

    private void setOrderOptionInit() {
        userOrderOptionQuantityTextView.setText(String.format("%s", coffeeMenuItem));
        userOrderOptionSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void cancleOrder(){
        Intent finishIntent = new Intent(this, MainActivity.class);
        finishIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(RESULT_CANCELED);
        finish();
    }


    private void menuOrder() {
        /**
         * 서버로 주문을 전송
         */

        Intent finishIntent = new Intent(this, MainActivity.class);
        finishIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(RESULT_OK);
        finish();
    }
}
