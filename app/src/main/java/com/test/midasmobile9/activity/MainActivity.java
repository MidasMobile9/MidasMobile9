package com.test.midasmobile9.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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

    Fragment fragment = null;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

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
    public void finish() {
        super.finish();

        MidasMobile9Application.clearCookie();
        MidasMobile9Application.clearUser();
    }

    @OnClick(R.id.floatingActionButton)
    public void onClickFloatingActionButton(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MENU_ORDER_OPTION_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    showMenuLookupFragment();
                } else if (resultCode == RESULT_CANCELED){
                    showMenuOrderFragment();
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
        fragmentManager.beginTransaction().replace(R.id.frameLayoutFragmentContainer, userOrderFragment).commit();
        // 프래그먼트 초기화
        fragment = userOrderFragment;

        BottomNavigationView.OnNavigationItemSelectedListener OnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomMenuOrder:
                        fragment = userOrderFragment;
                        break;
                    case R.id.bottomMenuLookup:
                        fragment = userLookupFragment;
                        break;
                    case R.id.bottomMenuProfile:
                        fragment = userProfileFragment;
                        break;
                }

                // 이런식으로 프래그먼트에 따라 Floating Action 버튼 기능 설정
                if ( fragment instanceof UserOrderFragment) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                } else if ( fragment instanceof UserLookupFragment) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                } else if ( fragment instanceof UserProfileFragment) {
                    floatingActionButton.setVisibility(View.GONE);
                }

                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out)
                        .replace(R.id.frameLayoutFragmentContainer, fragment)
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
                    Log.e("@@@", "홈");
                } else if ( fragment instanceof UserLookupFragment) {
                    Log.e("@@@", "콘텐츠");
                } else if ( fragment instanceof UserProfileFragment) {
                    Log.e("@@@", "프로필");
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


    private void showMenuLookupFragment() {
        bottomNavigationView.setSelectedItemId(R.id.bottomMenuLookup);
    }

    private void showMenuOrderFragment() {
        bottomNavigationView.setSelectedItemId(R.id.bottomMenuOrder);
    }

}
