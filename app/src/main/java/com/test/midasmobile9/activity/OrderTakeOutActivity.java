package com.test.midasmobile9.activity;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.test.midasmobile9.R;
import com.test.midasmobile9.adapter.OrderRecyclerAdapter;
import com.test.midasmobile9.data.AdminCoffeeOrderItem;
import com.test.midasmobile9.model.OrderModel;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderTakeOutActivity extends AppCompatActivity {

    @BindView(R.id.imageViewTakeOutBack)
    ImageView imageViewTakeOutBack;
    @BindView(R.id.recyclerOrderTakeOut)
    RecyclerView recyclerOrderTakeOut;

    OrderRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_take_out);
        // 버터나이프
        ButterKnife.bind(OrderTakeOutActivity.this);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new OrderDoneGetTask().execute();
    }

    @OnClick(R.id.imageViewTakeOutBack)
    public void onClickTakeOutBack(View view) {
        finish();
    }

    private void init() {
        // 1. RecyclerView 크기 고정
        recyclerOrderTakeOut.setHasFixedSize(true);

        // 2. LayoutManager 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderTakeOutActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerOrderTakeOut.setLayoutManager(layoutManager);

        // 3. Adapter 설정
        adapter = new OrderRecyclerAdapter(OrderTakeOutActivity.this, layoutManager);

        recyclerOrderTakeOut.setAdapter(adapter);
    }

    public class OrderDoneGetTask extends AsyncTask<Void, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(Void... voids) {
            Map<String, Object> map = OrderModel.getOrderDone();

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);

            if ( map == null ) {
                // 통신실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Snackbar.make(recyclerOrderTakeOut, message, Snackbar.LENGTH_SHORT).show();
            } else {
                // 통신성공
                boolean result = false;
                String message = null;
                List<AdminCoffeeOrderItem> orderDoneList = null;

                if ( map.containsKey("result") ) {
                    result = (boolean)map.get("result");
                }

                if ( map.containsKey("message") ) {
                    message = (String)map.get("message");
                }

                if ( map.containsKey("orderDoneList") ) {
                    orderDoneList = (List<AdminCoffeeOrderItem>)map.get("orderDoneList");
                }

                if ( result ) {
                    // 주문 목록 가져오기 성공
                    for ( int i = 0; i < orderDoneList.size(); i++ ) {
                        adapter.addItem(orderDoneList.get(i));
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    // 주문 목록 가져오기 실패
                    Snackbar.make(recyclerOrderTakeOut, message, Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }
}
