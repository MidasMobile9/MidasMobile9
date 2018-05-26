package com.test.midasmobile9.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.midasmobile9.R;
import com.test.midasmobile9.application.MidasMobile9Application;
import com.test.midasmobile9.data.CoffeeMenuItem;
import com.test.midasmobile9.data.CoffeeOrderItem;
import com.test.midasmobile9.model.MainModel;
import com.test.midasmobile9.model.UserOrderOptionModel;
import com.test.midasmobile9.network.NetworkDefineConstantOSH;
import com.test.midasmobile9.util.ParseHotCold;
import com.test.midasmobile9.util.ParseSize;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

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

    @BindView(R.id.userOrderOptionTotalPriceTextView)
    TextView userOrderOptionTotalPriceTextView;


    long mBackPressedTime;

    int coffeeItemQuantity;
    int coffeeItemSize;
    int coffeeItemTemper;
    int coffeeItemTotalPrice;

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
        changeTotalPrice();
    }

    @OnClick(R.id.userOrderOptionQuantityReduceImageView)
    public void onUserOrderOptionQuantityReduceImageViewClick(){
        if(coffeeItemQuantity > 1){
            coffeeItemQuantity--;
            userOrderOptionQuantityTextView.setText(coffeeItemQuantity + "");
        }
        changeTotalPrice();
    }

    private void changeTotalPrice(){
        int sumSizePrice = 0;
        //size가 0이면 스몰 -> 가격추가 없고
        //size가 1이면 미디엄 -> 가격추가 잔당 500원
        //size가 2이면 라지 -> 가격추가 잔당 1000원
        switch (coffeeItemSize){
            case 0:
                sumSizePrice = 0;
                break;
            case 1:
                sumSizePrice = 500;
                break;
            case 2:
                sumSizePrice = 1000;
                break;
        }
        coffeeItemTotalPrice = (coffeeMenuItem.getPrice() + sumSizePrice) * coffeeItemQuantity;
        userOrderOptionTotalPriceTextView.setText(coffeeItemTotalPrice + "");
    }

    private void setInitData(){
        coffeeItemQuantity = 1;
        coffeeItemSize = 0;
        coffeeItemTemper = 0;
    }

    private void setCoffeeMenuItemToView() {
        Glide.with(this)
                .load(NetworkDefineConstantOSH.SERVER_URL_GET_MENU_IMG + coffeeMenuItem.getImg()) // 이미지 URL 주소
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(userOrderOptionImageView);
        //Glide.with(this)
        //        .load(R.drawable.ic_coffee_24dp)
        //        .into(userOrderOptionImageView);
        userOrderOptionTitleTextView.setText(coffeeMenuItem.getName());
        userOrderOptionPriceTextView.setText(String.format(Locale.KOREA, "%d 원", coffeeMenuItem.getPrice()));

    }

    private void setOrderOptionInit() {
        userOrderOptionQuantityTextView.setText(coffeeItemQuantity + "");

        int menuHotCold = coffeeMenuItem.getHotcold();

        String[] hotcoldItems = {};

        switch (menuHotCold){
            case 0: //hot
                hotcoldItems = new String[]{"HOT"};
                break;
            case 1: // cold
                hotcoldItems = new String[]{"COLD"};
                break;
            case 2: // hot and cold
                hotcoldItems = new String[]{"HOT", "COLD"};
                break;
        }

        ArrayAdapter sizeAdapter = ArrayAdapter.createFromResource(this, R.array.order_option_size, R.layout.spinner_item_simple_layout);
        //ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_simple_layout, hotcoldItems);
        userOrderOptionSizeSpinner.setAdapter(sizeAdapter);
        userOrderOptionSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coffeeItemSize = ParseSize.getSizeInt(getResources().getStringArray(R.array.order_option_size)[position]);
                changeTotalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //ArrayAdapter temperAdapter = ArrayAdapter.createFromResource(this, R.array.order_option_temper, R.layout.spinner_item_simple_layout);
        ArrayAdapter<String> temperAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_simple_layout, hotcoldItems);
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
        new PostMenuOrder().execute();

        Intent finishIntent = new Intent(this, MainActivity.class);
        finishIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(RESULT_OK);
        finish();
    }


    // AsyncTask ====================================================================================
    public class PostMenuOrder extends AsyncTask<String, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(String... params) {

            Map<String, Object> map = UserOrderOptionModel.postOrderMenu(
                    MidasMobile9Application.user.getNo(),
                    coffeeMenuItem.getNo(),
                    coffeeItemSize,
                    coffeeItemTemper,
                    coffeeItemQuantity,
                    coffeeItemTotalPrice
                    );

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);

            if (map == null) {
                // 통신실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Snackbar.make(userOrderOptionMainLayout, message, Snackbar.LENGTH_SHORT).show();
            } else {
                // 통신성공
                boolean result = false;
                String message = null;

                if (map.containsKey("result")) {
                    result = (boolean) map.get("result");
                }

                if (!result) {
                    if (map.containsKey("message")) {
                        message = (String) map.get("message");
                    } else {
                        message = "통신 실패";
                    }
                } else {
                    if (map.containsKey("message")) {
                        message = (String) map.get("message");
                    } else {
                        message = "...";
                    }
                }
                Snackbar.make(userOrderOptionMainLayout, message, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
    // ==============================================================================================
}
