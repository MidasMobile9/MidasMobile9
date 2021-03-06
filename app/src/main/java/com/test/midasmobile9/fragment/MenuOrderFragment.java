package com.test.midasmobile9.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.midasmobile9.R;
import com.test.midasmobile9.activity.AdminActivity;
import com.test.midasmobile9.activity.MainActivity;
import com.test.midasmobile9.adapter.OrderRecyclerAdapter;
import com.test.midasmobile9.data.AdminCoffeeOrderItem;
import com.test.midasmobile9.data.User;
import com.test.midasmobile9.model.OrderModel;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MenuOrderFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Context mContext = null;
    private AdminActivity mActivity = null;

    Unbinder unbinder = null;

    @BindView(R.id.recyclerViewOrderList)
    RecyclerView recyclerViewOrderList;
    OrderRecyclerAdapter adapter;

    public MenuOrderFragment() {
        // Required empty public constructor
    }

    public static MenuOrderFragment newInstance(String param1, String param2) {
        MenuOrderFragment fragment = new MenuOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mContext = context;
        this.mActivity = (AdminActivity)getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_menu_order, container, false);
        // 버터나이프
        unbinder = ButterKnife.bind(this, rootView);
        // 초기화
        init();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        new OrderGetTask().execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // 버터나이프 해제
        unbinder.unbind();
        // mContext 해제
        this.mContext = null;
        // mActivity 해제
        this.mActivity = null;
    }

    private void init() {
        // 1. RecyclerView 크기 고정
        recyclerViewOrderList.setHasFixedSize(true);

        // 2. LayoutManager 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewOrderList.setLayoutManager(layoutManager);

        // 3. Adapter 설정
        adapter = new OrderRecyclerAdapter(mActivity, layoutManager);
        recyclerViewOrderList.setAdapter(adapter);
    }

    public void refreshOrderList() {
        new OrderGetTask().execute();
    }

    public View getRootView() {
        return recyclerViewOrderList;
    }

    public class OrderGetTask extends AsyncTask<Void, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(Void... voids) {

            Map<String, Object> map = OrderModel.getOrders();

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);

            if ( map == null ) {
                // 통신실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Snackbar.make(recyclerViewOrderList, message, Snackbar.LENGTH_SHORT).show();
            } else {
                // 통신성공
                boolean result = false;
                String message = null;
                List<AdminCoffeeOrderItem> coffeeOrderList = null;

                if ( map.containsKey("result") ) {
                    result = (boolean)map.get("result");
                }

                if ( map.containsKey("message") ) {
                    message = (String)map.get("message");
                }

                if ( map.containsKey("coffeeOrderList") ) {
                    coffeeOrderList = (List<AdminCoffeeOrderItem>)map.get("coffeeOrderList");
                }

                if ( result ) {
                    // 주문 목록 가져오기 성공
                    adapter.clearItems();

                    for ( int i = 0; i < coffeeOrderList.size(); i++ ) {
                        adapter.addItem(coffeeOrderList.get(i));
                    }

                    ((AdminActivity)mActivity).doneRefresh();

                    adapter.notifyDataSetChanged();
                } else {
                    // 주문 목록 가져오기 실패
                    Snackbar.make(recyclerViewOrderList, message, Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }
}
