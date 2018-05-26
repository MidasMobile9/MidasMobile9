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
import com.test.midasmobile9.adapter.UserLookupMenuRecyclerAdapter;
import com.test.midasmobile9.data.CoffeeOrderItem;
import com.test.midasmobile9.model.MainModel;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserLookupFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private Context mContext = null;
    private MainActivity mActivity = null;

    private ArrayList<CoffeeOrderItem> coffeeOrderItems;
    private UserLookupMenuRecyclerAdapter userLookupMenuRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.userMenuLookupMainLayout)
    LinearLayout userMenuLookupMainLayout;

    @BindView(R.id.userLookupMenuRecyclerView)
    RecyclerView userLookupMenuRecyclerView;

    Unbinder unbinder = null;

    public UserLookupFragment() {
        // Required empty public constructor
    }

    public static UserLookupFragment newInstance(String param1, String param2) {
        UserLookupFragment fragment = new UserLookupFragment();
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
        this.mActivity = (MainActivity)getActivity();
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
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_user_lookup, container, false);
        // 버터나이프
        unbinder = ButterKnife.bind(this, rootView);

        setRecyclerView();

        startRefreshLookup();

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

    private void setRecyclerView() {
        coffeeOrderItems = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        userLookupMenuRecyclerView.setHasFixedSize(true);
        userLookupMenuRecyclerView.setLayoutManager(linearLayoutManager);
        userLookupMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());

        userLookupMenuRecyclerAdapter = new UserLookupMenuRecyclerAdapter(coffeeOrderItems, mActivity);
        userLookupMenuRecyclerView.setAdapter(userLookupMenuRecyclerAdapter);
    }

    public void startRefreshLookup(){
        coffeeOrderItems.clear();
        userLookupMenuRecyclerAdapter.notifyDataSetChanged();
        new GetLookupAsyncTask().execute();
    }

    // AsyncTask ====================================================================================
    public class GetLookupAsyncTask extends AsyncTask<String, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(String... params) {

            Map<String, Object> map = MainModel.getUserOrderLookup();

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);

            if (map == null) {
                // 통신실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Snackbar.make(userMenuLookupMainLayout, message, Snackbar.LENGTH_SHORT).show();
            } else {
                // 통신성공
                boolean result = false;
                String message = null;
                ArrayList<CoffeeOrderItem> getCoffeeOrderItems = null;

                if (map.containsKey("result")) {
                    result = (boolean) map.get("result");
                }

                if (!result) {
                    if (map.containsKey("message")) {
                        message = (String) map.get("message");
                    } else {
                        message = "통신 실패";
                        Snackbar.make(userMenuLookupMainLayout, message, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (map.containsKey("message")) {
                        message = (String) map.get("message");
                    } else {
                        message = "...";
                    }
                    if (map.containsKey("data")) {
                        getCoffeeOrderItems = (ArrayList<CoffeeOrderItem>) map.get("data");
                        for (CoffeeOrderItem eachCoffeeOrderItem : getCoffeeOrderItems) {
                            coffeeOrderItems.add(eachCoffeeOrderItem);
                        }
                    }
                }
            }
            mActivity.endRefreshLookup();
            userLookupMenuRecyclerAdapter.notifyDataSetChanged();
        }
    }
    // ==============================================================================================
}
