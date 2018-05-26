package com.test.midasmobile9.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.test.midasmobile9.R;
import com.test.midasmobile9.application.MidasMobile9Application;
import com.test.midasmobile9.fragment.UserLookupFragment;
import com.test.midasmobile9.fragment.UserOrderFragment;
import com.test.midasmobile9.fragment.UserProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final int MENU_ORDER_OPTION_REQUEST_CODE = 301;
    public static final String FRAGMENT_USER_ORDER_TAG = "FRAGMENT_USER_ORDER_TAG";
    public static final String FRAGMENT_USER_LOOKUP_TAG = "FRAGMENT_USER_LOOKUP_TAG";
    public static final String FRAGMENT_USER_PROFILE_TAG = "FRAGMENT_USER_PROFILE_TAG";

    Fragment fragment = null;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.mainActivityMainLayout)
    CoordinatorLayout mainActivityMainLayout;

    long mBackPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        // ButterKnife 세팅
        ButterKnife.bind(this);

        // BottomNavigationView 세팅
        setBottomNavigationView();
        // SwipeRefreshLayout 세팅
        setSwipeRefreshLayout();

        /**
         * SwipeRefresh 종료하려면
         * SwipeRefresh가 돌아가는 상태에서
         * swipeRefreshLayout.setRefreshing(false); 호출
         *
         * ex) AsyncTask의 onPostExcute()에서 데이터 리프레쉬 후 호출
         * */
    }



    @Override
    public void onBackPressed() {
        // 드로어가 닫혀있으면 앱 종료
        if (System.currentTimeMillis() - mBackPressedTime > 2000) {
            Snackbar.make(mainActivityMainLayout, "뒤로 버튼을 한번 더 눌리시면 종료합니다", Snackbar.LENGTH_LONG)
                    .setAction("EXIT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .show();
            mBackPressedTime = System.currentTimeMillis();
        } else {
            finish();
        }

    }

    public void endRefreshOrderMenu() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void endRefreshLookup(){
        swipeRefreshLayout.setRefreshing(false);
    }

    public void endRefreshHistory() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void finish() {
        super.finish();

        MidasMobile9Application.clearCookie();
        MidasMobile9Application.clearUser();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MENU_ORDER_OPTION_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    orderSuccess();
                } else if (resultCode == RESULT_CANCELED){
                    orderCancle();
                }
                break;
            case UserProfileFragment.REQUEST_CODE_PROFILE_MANAGER_ACTIVITY:
                if(resultCode == RESULT_OK){
                }
                break;
        }
    }


    public void setBottomNavigationView() {
        // 프래그먼트 매니저
        final FragmentManager fragmentManager = getSupportFragmentManager();
        // 홈 프래그먼트
        final UserOrderFragment userOrderFragment = UserOrderFragment.newInstance("Home", "MainActivity");
        // 콘텐츠 프래그먼트
        final UserLookupFragment userLookupFragment = UserLookupFragment.newInstance("Content", "MainActivity");
        // 프로필 프래그먼트
        final UserProfileFragment userProfileFragment = UserProfileFragment.newInstance("Profile", "MainActivity");

        // 첫 시작 프래그먼트로 홈 프래그먼트 지정
        fragmentManager.beginTransaction().replace(R.id.frameLayoutFragmentContainer, userOrderFragment, FRAGMENT_USER_ORDER_TAG).commit();
        // 프래그먼트 초기화
        fragment = userOrderFragment;
        final String[] tag = {""};

        BottomNavigationView.OnNavigationItemSelectedListener OnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomMenuOrder:
                        fragment = userOrderFragment;
                        tag[0] = FRAGMENT_USER_ORDER_TAG;
                        break;
                    case R.id.bottomMenuLookup:
                        fragment = userLookupFragment;
                        tag[0] = FRAGMENT_USER_LOOKUP_TAG;
                        break;
                    case R.id.bottomMenuProfile:
                        fragment = userProfileFragment;
                        tag[0] = FRAGMENT_USER_PROFILE_TAG;
                        break;
                }

                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out)
                        .replace(R.id.frameLayoutFragmentContainer, fragment, tag[0])
                        .commit();
                return true;
            }
        };

        bottomNavigationView.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener);
    }

    public void setSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if ( fragment instanceof UserOrderFragment) {
                    Log.e("@@@", "주문");
                    ((UserOrderFragment) fragment).startRefreshOrderMenu();
                } else if ( fragment instanceof UserLookupFragment) {
                    Log.e("@@@", "조회");
                    ((UserLookupFragment) fragment).startRefreshLookup();
                } else if ( fragment instanceof UserProfileFragment) {
                    Log.e("@@@", "프로필");
                    ((UserProfileFragment) fragment).startRefreshHistory();
                }
            }
        });

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
    }

    private void orderSuccess() {
        showMenuLookupFragment();
    }

    private void showMenuLookupFragment() {
        bottomNavigationView.setSelectedItemId(R.id.bottomMenuLookup);
    }

    private void orderCancle(){
        showMenuOrderFragment();
    }

    private void showMenuOrderFragment() {
        bottomNavigationView.setSelectedItemId(R.id.bottomMenuOrder);
    }

}
