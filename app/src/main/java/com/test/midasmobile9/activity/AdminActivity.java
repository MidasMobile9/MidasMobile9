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
import com.test.midasmobile9.fragment.AdminProfileFragment;
import com.test.midasmobile9.fragment.CustomerFragment;
import com.test.midasmobile9.fragment.MenuManagerFragment;
import com.test.midasmobile9.fragment.MenuOrderFragment;
import com.test.midasmobile9.fragment.UserProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_NEW_MENU = 100;

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
        setContentView(R.layout.activity_admin);
        // 버터나이프 세팅
        ButterKnife.bind(AdminActivity.this);

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
        if ( fragment instanceof MenuOrderFragment ) {
            Intent intent = new Intent(AdminActivity.this, OrderTakeOutActivity.class);
            startActivity(intent);
        } else if ( fragment instanceof MenuManagerFragment ) {
            /****************************************************************************************************************************************************/
            // 프래그먼트에서 액티비티 띄우고 목록 갱신 해야함.
            /****************************************************************************************************************************************************/
            Intent intent = new Intent(AdminActivity.this, MenuAddActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_NEW_MENU);
        } else if ( fragment instanceof CustomerFragment ) {
            /****************************************************************************************************************************************************/
            // 프래그먼트에서 액티비티 띄우고 목록 갱신 해야함.
            /****************************************************************************************************************************************************/
            Intent intent = new Intent(AdminActivity.this, CustomerAddActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_NEW_MENU);
        }
    }

    public void setBottomNavigationView() {
        // 프래그먼트 매니저
        final FragmentManager fragmentManager = getSupportFragmentManager();
        // 주문내역 프래그먼트
        final MenuOrderFragment menuOrderFragment = MenuOrderFragment.newInstance("Order", "MenuOrderFragment");
        // 메뉴관리 프래그먼트
        final MenuManagerFragment menuManagerFragment = MenuManagerFragment.newInstance("Manager", "MenuManagerFragment");
        // 고객정보 프래그먼트
        final CustomerFragment customerFragment = CustomerFragment.newInstance("Customer", "CustomerFragment");
        // 프로필 프로그먼트
        final AdminProfileFragment adminProfileFragment = AdminProfileFragment.newInstance("AdminProfile", "AdminProfileFragment");

        // 첫 시작 프래그먼트로 홈 프래그먼트 지정
        fragmentManager.beginTransaction().replace(R.id.frameLayoutFragmentContainer, menuOrderFragment).commit();
        // 프래그먼트 초기화
        fragment = menuOrderFragment;

        BottomNavigationView.OnNavigationItemSelectedListener OnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_order:
                        fragment = menuOrderFragment;
                        break;
                    case R.id.bottom_manage:
                        fragment = menuManagerFragment;
                        break;
                    case R.id.bottom_customer:
                        fragment = customerFragment;
                        break;
                    case R.id.bottom_profile:
                        fragment = adminProfileFragment;
                }

                // 이런식으로 프래그먼트에 따라 Floating Action 버튼 기능 설정
                if ( fragment instanceof MenuOrderFragment ) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                } else if ( fragment instanceof MenuManagerFragment ) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                } else if ( fragment instanceof CustomerFragment ) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                } else if ( fragment instanceof AdminProfileFragment ) {
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

    public void doneRefresh() {
        if ( swipeRefreshLayout.isRefreshing() ) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void setSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if ( fragment instanceof MenuOrderFragment ) {
                    ((MenuOrderFragment)fragment).refreshOrderList();
                } else if ( fragment instanceof MenuManagerFragment ) {
                    ((MenuManagerFragment)fragment).refreshMenuInfo();
                } else if ( fragment instanceof CustomerFragment ) {
                    ((CustomerFragment)fragment).refreshMenuInfo();
                } else if ( fragment instanceof AdminProfileFragment ) {
                    if ( swipeRefreshLayout.isRefreshing() ) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
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
}
