package com.test.midasmobile9.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.midasmobile9.R;
import com.test.midasmobile9.activity.LoginActivity;
import com.test.midasmobile9.activity.MainActivity;
import com.test.midasmobile9.activity.ProfileManagerActivity;
import com.test.midasmobile9.adapter.UserHistoryRecyclerAdapter;
import com.test.midasmobile9.application.MidasMobile9Application;
import com.test.midasmobile9.data.CoffeeOrderItem;
import com.test.midasmobile9.model.MainModel;
import com.test.midasmobile9.model.ProfileModel;
import com.test.midasmobile9.network.NetworkDefineConstantOSH;
import com.test.midasmobile9.util.SharePreferencesUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.test.midasmobile9.util.SharePreferencesUtil.KEY_EMAIL;
import static com.test.midasmobile9.util.SharePreferencesUtil.KEY_PASSWORD;
import static com.test.midasmobile9.util.SharePreferencesUtil.KEY_ROOT;

public class UserProfileFragment extends Fragment {
    public static final int REQUEST_CODE_PROFILE_MANAGER_ACTIVITY = 301;

    public static String USER_NAME;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Context mContext = null;
    private MainActivity mActivity = null;

    Unbinder unbinder = null;

    private int selectedYear;
    private int selectedMonth;
    private int totalPrice = 0;

    private ArrayList<CoffeeOrderItem> coffeeHistoryOrderItems;
    private UserHistoryRecyclerAdapter userHistoryRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.circleImageViewProfileFragmentProfileImage)
    CircleImageView circleImageViewProfileFragmentProfileImage;
    @BindView(R.id.textViewProfileFragmentProfileNickname)
    TextView textViewProfileFragmentProfileNickname;
    @BindView(R.id.textViewProfileFragmentProfileEmail)
    TextView textViewProfileFragmentProfileEmail;
    @BindView(R.id.textViewProfileFragmentModifyProfile)
    TextView textViewProfileFragmentModifyProfile;
    @BindView(R.id.textViewProfileFragmentLogout)
    TextView textViewProfileFragmentLogout;

    @BindView(R.id.userProfileMainLayout)
    LinearLayout userProfileMainLayout;
    @BindView(R.id.userHistoryRecyclerView)
    RecyclerView userHistoryRecyclerView;


    @BindView(R.id.userHistoryYearTextView)
    TextView userHistoryYearTextView;

    @BindView(R.id.userHistoryMonthTextView)
    TextView userHistoryMonthTextView;

    @BindView(R.id.userHistoryTotalPriceTextView)
    TextView userHistoryTotalPriceTextView;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_user_profile, container, false);
        // 버터나이프
        unbinder = ButterKnife.bind(this, rootView);

        setRecyclerView();
        setYearMonth();

        return rootView;
    }

    public void setYearMonth(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        selectedYear =  calendar.get(Calendar.YEAR);
        selectedMonth =  calendar.get(Calendar.MONTH) + 1;
        userHistoryYearTextView.setText(selectedYear + "");
        userHistoryMonthTextView.setText(selectedMonth + "");

        Log.d("TESTEMP", selectedYear + ", " + selectedMonth);
    }

    @Override
    public void onResume() {
        super.onResume();

        // 유저 프로필 사진 세팅
        if ( MidasMobile9Application.user.getProfileimg() != null ) {
            Glide.with(UserProfileFragment.this)
                    .load(NetworkDefineConstantOSH.SERVER_URL_GET_PROFILE_IMG + MidasMobile9Application.user.getProfileimg())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(circleImageViewProfileFragmentProfileImage);

        } else {
            Glide.with(UserProfileFragment.this)
                    .load(R.drawable.ic_profile_black_48dp)
                    .into(circleImageViewProfileFragmentProfileImage);
        }

        // 유저 닉네임 세팅
        textViewProfileFragmentProfileNickname.setText(MidasMobile9Application.user.getNickname());
        // 유저 이메일 세팅
        textViewProfileFragmentProfileEmail.setText(MidasMobile9Application.user.getEmail());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PROFILE_MANAGER_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {
                    // 프래그먼트 새로고침
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(UserProfileFragment.this).attach(UserProfileFragment.this).commit();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    /**
                     * 프로필을 변경 하지 않았음
                     */
                }
                break;
        }
    }

    @OnClick(R.id.textViewProfileFragmentModifyProfile)
    public void onModifyProfileCllick() {
        Intent intent = new Intent(mContext, ProfileManagerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_PROFILE_MANAGER_ACTIVITY);
    }

    @OnClick(R.id.textViewProfileFragmentLogout)
    public void onProfileLogoutClick(){
        // 로그아웃
        new UserLogoutTask().execute();
    }

    private void setRecyclerView() {
        coffeeHistoryOrderItems = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        userHistoryRecyclerView.setHasFixedSize(true);
        userHistoryRecyclerView.setLayoutManager(linearLayoutManager);
        userHistoryRecyclerView.setItemAnimator(new DefaultItemAnimator());

        userHistoryRecyclerAdapter = new UserHistoryRecyclerAdapter(coffeeHistoryOrderItems, mActivity);
        userHistoryRecyclerView.setAdapter(userHistoryRecyclerAdapter);
    }


    public void startRefreshHistory(){
        coffeeHistoryOrderItems.clear();
        userHistoryRecyclerAdapter.notifyDataSetChanged();
        new GetHistoryAsyncTask().execute();
    }

    public void changeTotalPrice(){
        totalPrice = 0;
        for(CoffeeOrderItem coffeeOrderItem : coffeeHistoryOrderItems){
            totalPrice += coffeeOrderItem.getPrice();
        }
        userHistoryTotalPriceTextView.setText(totalPrice + " 원");
    }

    @OnClick({R.id.userHistoryYearIncreaseImageView,
            R.id.userHistoryYearReduceImageView,
            R.id.userHistoryMonthIncreaseImageView,
            R.id.userHistoryMonthReduceImageView})
    public void onUserHistorySearchOptionClick(View v){
        switch (v.getId()){
            case R.id.userHistoryYearIncreaseImageView:
                if(selectedYear < 2199 && selectedYear > 1970 ){
                    selectedYear++;
                }
                break;
            case R.id.userHistoryYearReduceImageView:
                if(selectedYear < 2199 && selectedYear > 1970 ){
                    selectedYear--;
                }
                break;
            case R.id.userHistoryMonthIncreaseImageView:
                if(selectedMonth < 12 && selectedMonth > 1){
                    selectedMonth++;
                }
                break;
            case R.id.userHistoryMonthReduceImageView:
                if(selectedMonth < 12 && selectedMonth > 1){
                    selectedMonth--;
                }
                break;
        }

        userHistoryYearTextView.setText(selectedYear + "");
        userHistoryMonthTextView.setText(selectedMonth + "");


        Log.d("TESTEMP", selectedYear + ", " + selectedMonth);
    }

    @OnClick(R.id.userHistorySearchTextView)
    public void onUserHistorySearchTextViewClick(){
        coffeeHistoryOrderItems.clear();
        userHistoryRecyclerAdapter.notifyDataSetChanged();
        new GetHistoryAsyncTask().execute();
    }

    // AsyncTask ====================================================================================


    public class UserLogoutTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean isLogouted = ProfileModel.logoutUser();

            return isLogouted;
        }

        @Override
        protected void onPostExecute(Boolean isLogouted) {
            super.onPostExecute(isLogouted);

            if ( isLogouted ) {
                // 로그아웃 성공
                MidasMobile9Application.clearCookie();
                MidasMobile9Application.clearUser();
                SharePreferencesUtil.removePreferences(mContext,KEY_EMAIL);
                SharePreferencesUtil.removePreferences(mContext,KEY_PASSWORD);
                SharePreferencesUtil.removePreferences(mContext,KEY_ROOT);
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                mActivity.finish();
            } else {
                // 로그아웃 실패
                Toast.makeText(mActivity, "로그아웃에 실패하였습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class GetHistoryAsyncTask extends AsyncTask<String, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(String... params) {

            Map<String, Object> map = MainModel.getUserHistory(selectedYear + "", selectedMonth + "");

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);

            if (map == null) {
                // 통신실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Snackbar.make(userProfileMainLayout, message, Snackbar.LENGTH_SHORT).show();
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
                        Snackbar.make(userProfileMainLayout, message, Snackbar.LENGTH_SHORT).show();
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
                            coffeeHistoryOrderItems.add(eachCoffeeOrderItem);
                        }
                    }
                }
            }
            mActivity.endRefreshHistory();
            changeTotalPrice();
            userHistoryRecyclerAdapter.notifyDataSetChanged();
        }
    }
    // ==============================================================================================
}
