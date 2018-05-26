package com.test.midasmobile9.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.test.midasmobile9.R;
import com.test.midasmobile9.adapter.OrderRecyclerAdapter;
import com.test.midasmobile9.data.AdminCoffeeOrderItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderTakeOutActivity extends AppCompatActivity {

    @BindView(R.id.recyclerOrderTakeOut)
    RecyclerView recyclerOrderTakeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_take_out);
        // 버터나이프
        ButterKnife.bind(OrderTakeOutActivity.this);

        init();
    }

    private void init() {
        // 1. RecyclerView 크기 고정
        recyclerOrderTakeOut.setHasFixedSize(true);

        // 2. LayoutManager 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderTakeOutActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerOrderTakeOut.setLayoutManager(layoutManager);

        // 3. Adapter 설정
        OrderRecyclerAdapter adapter = new OrderRecyclerAdapter(OrderTakeOutActivity.this, layoutManager);
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));

        recyclerOrderTakeOut.setAdapter(adapter);
    }
}
