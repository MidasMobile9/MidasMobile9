package com.test.midasmobile9.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.midasmobile9.R;
import com.test.midasmobile9.activity.AdminActivity;
import com.test.midasmobile9.activity.LoginActivity;
import com.test.midasmobile9.activity.MainActivity;
import com.test.midasmobile9.activity.ProfileManagerActivity;
import com.test.midasmobile9.application.MidasMobile9Application;
import com.test.midasmobile9.model.ProfileModel;
import com.test.midasmobile9.network.NetworkDefineConstant;
import com.test.midasmobile9.util.SharePreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.test.midasmobile9.util.SharePreferencesUtil.KEY_EMAIL;
import static com.test.midasmobile9.util.SharePreferencesUtil.KEY_PASSWORD;
import static com.test.midasmobile9.util.SharePreferencesUtil.KEY_ROOT;

public class AdminProfileFragment extends Fragment {
    public static final int REQUEST_CODE_ADMIN_PROFILE_FRAGMENT = 110;

    public static String USER_NAME;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String PROFILE_URL_HEADER = NetworkDefineConstant.HOST_URL + "/profileimg/";

    private String mParam1;
    private String mParam2;

    private Context mContext = null;
    private AdminActivity mActivity = null;

    Unbinder unbinder = null;

    @BindView(R.id.circleImageViewProfileFragmentProfileImage)
    CircleImageView circleImageViewProfileFragmentProfileImage;
    @BindView(R.id.textViewProfileFragmentProfileNickname)
    TextView textViewProfileFragmentProfileNickname;
    @BindView(R.id.textViewProfileFragmentProfileEmail) TextView textViewProfileFragmentProfileEmail;
    @BindView(R.id.textViewProfileFragmentModifyProfile) TextView textViewProfileFragmentModifyProfile;
    @BindView(R.id.textViewProfileFragmentLogout) TextView textViewProfileFragmentLogout;

    public AdminProfileFragment() {
        // Required empty public constructor
    }

    public static AdminProfileFragment newInstance(String param1, String param2) {
        AdminProfileFragment fragment = new AdminProfileFragment();
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
        this.mActivity = (AdminActivity) getActivity();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_admin_profile, container, false);
        // 버터나이프
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

        // 유저 프로필 사진 세팅
        if ( MidasMobile9Application.user.getProfileimg() != null ) {
            Glide.with(AdminProfileFragment.this)
                    .load(PROFILE_URL_HEADER + MidasMobile9Application.user.getProfileimg())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(circleImageViewProfileFragmentProfileImage);

        } else {
            Glide.with(AdminProfileFragment.this)
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
            case REQUEST_CODE_ADMIN_PROFILE_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    // 프래그먼트 새로고침
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(AdminProfileFragment.this).attach(AdminProfileFragment.this).commit();
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
        startActivityForResult(intent, REQUEST_CODE_ADMIN_PROFILE_FRAGMENT);
    }

    @OnClick(R.id.textViewProfileFragmentLogout)
    public void onProfileLogoutClick(){
        // 로그아웃
        new UserLogoutTask().execute();
    }

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
}
