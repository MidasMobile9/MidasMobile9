package com.test.midasmobile9.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.midasmobile9.R;
import com.test.midasmobile9.data.CoffeeMenuItem;
import com.test.midasmobile9.network.NetworkDefineConstantOSH;
import com.test.midasmobile9.util.ParseHotCold;
import com.test.midasmobile9.util.ParseSize;

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
    public static int COFFEE_QUANTITY_MAX = 10;

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
    int coffeeItemSize;
    int coffeeItemTemper;

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
        if(coffeeItemQuantity < COFFEE_QUANTITY_MAX){
            coffeeItemQuantity++;
            userOrderOptionQuantityTextView.setText(coffeeItemQuantity + "");
        }
    }

    @OnClick(R.id.userOrderOptionQuantityReduceImageView)
    public void onUserOrderOptionQuantityReduceImageViewClick(){
        if(coffeeItemQuantity > 1){
            coffeeItemQuantity--;
            userOrderOptionQuantityTextView.setText(coffeeItemQuantity + "");
        }
    }

    private void setInitData(){
        coffeeItemQuantity = 1;
        coffeeItemSize = 0;
        coffeeItemTemper = 0;
    }

    private void setCoffeeMenuItemToView() {
        Glide.with(this)
                .load(NetworkDefineConstantOSH.SERVER_URL_MENU + coffeeMenuItem.getImg()) // 이미지 URL 주소
                .into(userOrderOptionImageView);
        //Glide.with(this)
        //        .load(R.drawable.ic_coffee_24dp)
        //        .into(userOrderOptionImageView);
        userOrderOptionTitleTextView.setText(coffeeMenuItem.getName());
        userOrderOptionPriceTextView.setText(String.format(Locale.KOREA, "%d 원", coffeeMenuItem.getPrice()));

    }

    private void setOrderOptionInit() {
        userOrderOptionQuantityTextView.setText(coffeeItemQuantity + "");

        ArrayAdapter sizeAdapter = ArrayAdapter.createFromResource(this, R.array.order_option_size, R.layout.spinner_item_simple_layout);
        userOrderOptionSizeSpinner.setAdapter(sizeAdapter);
        userOrderOptionSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coffeeItemSize = ParseSize.getSizeInt(getResources().getStringArray(R.array.order_option_size)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter temperAdapter = ArrayAdapter.createFromResource(this, R.array.order_option_temper, R.layout.spinner_item_simple_layout);
        userOrderOptionTemperSpinner.setAdapter(temperAdapter);
        userOrderOptionTemperSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coffeeItemTemper = ParseHotCold.getHotColdInt(getResources().getStringArray(R.array.order_option_temper)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
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
        Toast.makeText(this,
                "커피이름 : " + coffeeMenuItem.getName() + ", 갯수 : " + coffeeItemQuantity + ", 사이즈 : " + coffeeItemSize + ", 온도 : " + coffeeItemTemper,
                Toast.LENGTH_LONG)
                .show();

        Intent finishIntent = new Intent(this, MainActivity.class);
        finishIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(RESULT_OK);
        finish();
    }
}
