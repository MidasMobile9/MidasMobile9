package com.test.midasmobile9.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. Snackbar의 사용을 위해
 * implementation 'com.android.support:design:27.1.1'
 * 를 app/gradle에 추가하여야 함
 * <p>
 * <p>
 * 2. manifest에 다음과 같이 원하는 권한을 추가하여야함
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.CAMERA" />
 * <p>
 * <p>
 * 3. 다음과 같이 원하는 권한이 들어있는 String배열을 만들어서 생성자의 인자로 넣어주어야 함
 * private String[] permissions = {
 * Manifest.permission.READ_EXTERNAL_STORAGE,
 * Manifest.permission.WRITE_EXTERNAL_STORAGE,
 * Manifest.permission.CAMERA
 * };
 * <p>
 * <p>
 * 4. 프로젝트의 MainActivity에
 * mPermission = new Permission(this, permissions);
 * 으로 인스턴스 생성
 * <p>
 * <p>
 * 5. 권한 요구 대화상자가 보여지기를 원하는 곳에
 * mPermission.checkPermissions();
 * 를 선언
 * <p>
 * <p>
 * 6. Snackbar가 보여지길 원한다면
 * setSnackbar(View);
 * 를 5번과 함께 선언
 * <p>
 * <p>
 * 7. onRequestPermissionResult를 오버라이드하여 다음과 같이 선언
 *
 * @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
 * mPermission.requestPermissionsResult(requestCode, permissions, grantResults);
 * }
 * 이 함수는 유저가 Permission을 승인하거나 거부 하면 콜백되는 함수임
 */

public class Permission {
    static private final String TAG = "Permission";

    private String[] permissions;
    private final int MULTIPLE_PERMISSIONS = 200; //권한 동의 여부 문의 후 CallBack 함수에 쓰일 변수
    private Snackbar permissionSnackbar, permissionSnackbarRational;

    private Activity mActivity;
    private Context mContext;
    private boolean mIsSetSnackbar;

    public Permission(Activity activity, String[] permissions) {
        this.mActivity = activity;
        this.mContext = activity.getApplicationContext();
        this.permissions = permissions;
        mIsSetSnackbar = false;
    }

    public void requestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        for (String perm : this.permissions) {
                            if (permissions[i].equals(perm)) {
                                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                    showNoPermissionSnackbarAndFinish();
                                    return;
                                }
                            }
                        }
                    }
                } else {
                    showNoPermissionSnackbarAndFinish();
                }
                return;
            }
        }
    }

    private void showNoPermissionSnackbarAndFinish() {
        if (!mIsSetSnackbar) return;
        boolean isNotRational = true;

        for (String perm : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(mActivity, perm)) {
                isNotRational = false;
                break;
            }
        }

        if (isNotRational) {
            permissionSnackbar.show();
        } else {
            permissionSnackbarRational.show();
        }

    }

    public boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(mContext, pm);
            if (result != PackageManager.PERMISSION_GRANTED) { //사용자가 해당 권한을 가지고 있지 않을 경우 리스트에 해당 권한명 추가
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) { //권한이 추가되었으면 해당 리스트가 empty가 아니므로 request 즉 권한을 요청합니다.
            ActivityCompat.requestPermissions(mActivity, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    public void setSnackbar(View v) {
        mIsSetSnackbar = true;
        permissionSnackbar = Snackbar.make(v, "권한 거절시 보여질 스낵바의 텍스트", Snackbar.LENGTH_LONG);
        // CLICK을 누르면 권한을 요구하는 대화상자가 다시 뜸
        permissionSnackbar.setAction("CLICK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });
        permissionSnackbarRational = Snackbar.make(v, "권한 거절 + 다지 보지 않기를 클릭할 시 보여질 스낵바의 텍스트", Snackbar.LENGTH_LONG);
    }
    public void setSnackbar(View v, String text1, String text2) {
        mIsSetSnackbar = true;
        permissionSnackbar = Snackbar.make(v, text1, Snackbar.LENGTH_LONG);
        // CLICK을 누르면 권한을 요구하는 대화상자가 다시 뜸
        permissionSnackbar.setAction("확인", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });
        permissionSnackbarRational = Snackbar.make(v, text2, Snackbar.LENGTH_LONG);
    }
    public void resetSnackbar() {
        mIsSetSnackbar = false;
    }

}
