package com.test.midasmobile9.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
        OrderRecyclerAdapter adapter = new OrderRecyclerAdapter(mActivity, layoutManager);

        /********************************************************************************************/
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 0, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 1, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 2, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "가나다라마바사아자카타", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 4, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "qqq@qqq.com","티라노", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        adapter.addNewItem(new AdminCoffeeOrderItem(1, 1, 1, 2, 2, 4, 5000, 3, "날짜", "eee@eee.com","닉네임", "baseprofile.png", "010-0000-0000", "부서", "아메리카노", "겁나 맛있는 커피","basemenu.png", 1));
        /********************************************************************************************/

        recyclerViewOrderList.setAdapter(adapter);

    }
}
