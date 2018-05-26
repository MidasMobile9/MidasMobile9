package com.test.midasmobile9.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.test.midasmobile9.R;
import com.test.midasmobile9.activity.MainActivity;
import com.test.midasmobile9.adapter.UserOrderMenuRecyclerAdapter;
import com.test.midasmobile9.data.CoffeeMenuItem;
import com.test.midasmobile9.model.MainModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserOrderFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Context mContext = null;
    private MainActivity mActivity = null;

    private ArrayList<CoffeeMenuItem> coffeeMenuItems;
    private LinearLayoutManager linearLayoutManager;
    private UserOrderMenuRecyclerAdapter userOrderMenuRecyclerAdapter;

    @BindView(R.id.userMenuOrderMainLayout)
    LinearLayout userMenuOrderMainLayout;

    @BindView(R.id.userOrderMenuRecyclerView)
    RecyclerView userOrderMenuRecyclerView;


    Unbinder unbinder = null;

    public UserOrderFragment() {
        // Required empty public constructor
    }

    public static UserOrderFragment newInstance(String param1, String param2) {
        UserOrderFragment fragment = new UserOrderFragment();
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
        this.mActivity = (MainActivity) getActivity();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_user_order, container, false);
        // 버터나이프
        unbinder = ButterKnife.bind(this, rootView);

        setRecyclerView();

        //addTestMenu();
        startRefreshOrderMenu();

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

        coffeeMenuItems.clear();
    }

    private void setRecyclerView() {
        coffeeMenuItems = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        userOrderMenuRecyclerView.setHasFixedSize(true);
        userOrderMenuRecyclerView.setLayoutManager(linearLayoutManager);
        userOrderMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());

        userOrderMenuRecyclerAdapter = new UserOrderMenuRecyclerAdapter(coffeeMenuItems, mActivity);
        userOrderMenuRecyclerView.setAdapter(userOrderMenuRecyclerAdapter);
    }

    public void startRefreshOrderMenu() {
        coffeeMenuItems.clear();
        userOrderMenuRecyclerAdapter.notifyDataSetChanged();
        new GetMenuAsyncTask().execute();
    }

    private void addTestMenu() {
        coffeeMenuItems.add(new CoffeeMenuItem(0, "카페라떼", "카페라떼는 맛있습니다", 3000, "none", 1, 1));
        coffeeMenuItems.add(new CoffeeMenuItem(0, "아메리카노", "아메리카노도 맛있습니다", 3000, "none", 1, 1));
        coffeeMenuItems.add(new CoffeeMenuItem(0, "카페모카", "카페모카또한 맛있습니다", 3000, "none", 1, 1));
    }


    // AsyncTask ====================================================================================
    public class GetMenuAsyncTask extends AsyncTask<String, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(String... params) {

            Map<String, Object> map = MainModel.getAllMenu();

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);


            if (map == null) {
                // 통신실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Snackbar.make(userMenuOrderMainLayout, message, Snackbar.LENGTH_SHORT).show();
            } else {
                // 통신성공
                boolean result = false;
                String message = null;
                ArrayList<CoffeeMenuItem> getCoffeeMenuItems = null;

                if (map.containsKey("result")) {
                    result = (boolean) map.get("result");
                }

                if (!result) {
                    if (map.containsKey("message")) {
                        message = (String) map.get("message");
                    } else {
                        message = "통신 실패";
                        Snackbar.make(userMenuOrderMainLayout, message, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (map.containsKey("message")) {
                        message = (String) map.get("message");
                    } else {
                        message = "...";
                    }
                    if (map.containsKey("data")) {
                        getCoffeeMenuItems = (ArrayList<CoffeeMenuItem>) map.get("data");
                        for (CoffeeMenuItem coffeeMenuItem : getCoffeeMenuItems) {
                            coffeeMenuItems.add(coffeeMenuItem);
                        }
                    }
                }
            }
            mActivity.endRefreshOrderMenu();
            userOrderMenuRecyclerAdapter.notifyDataSetChanged();
        }
    }
    // ==============================================================================================
}
