package com.test.midasmobile9.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.midasmobile9.R;
import com.test.midasmobile9.application.MidasMobile9Application;
import com.test.midasmobile9.model.ProfileManagerModel;
import com.test.midasmobile9.network.NetworkDefineConstantOSH;
import com.test.midasmobile9.util.Encryption;
import com.test.midasmobile9.util.ImageUtil;
import com.test.midasmobile9.util.PasswordUtil;
import com.test.midasmobile9.util.ProgressBarShow;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileManagerActivity extends AppCompatActivity {
    public static final int REQUEST_TAKE_PROFILE_FROM_ALBUM = 302;

    private final String PRIVACY_POLICY_INFO = "https://blog.naver.com/tyrano_1/221283509070";

    private File resultImageFile;
    private boolean isChangeProfileImage = false;


    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.editTextProfileManagerPasswordOriginal)
    EditText editTextProfileManagerPasswordOriginal;
    @BindView(R.id.editTextProfileManagerNewPasswordFirst)
    EditText editTextProfileManagerNewPasswordFirst;
    @BindView(R.id.editTextProfileManagerNewPasswordSecond)
    EditText editTextProfileManagerNewPasswordSecond;

    @BindView(R.id.contentsLinearLayout)
    LinearLayout contentsLinearLayout;

    @BindView(R.id.changeProfileImage)
    ImageView changeProfileImage;
    @BindView(R.id.circleImageViewProfileManagerProfileImage)
    ImageView circleImageViewProfileManagerProfileImage;
    @BindView(R.id.editTextProfileManagerNickname)
    EditText editTextProfileManagerNickname;
    @BindView(R.id.textViewProfileManagerEmail)
    TextView textViewProfileManagerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manager);

        ButterKnife.bind(this);

        setProfileInit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PROFILE_FROM_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    Uri profileImageUri = data.getData();
                    Bitmap resizeBitmap = ImageUtil.scaleImageDownToBitmap(this, profileImageUri);
                    resultImageFile = ImageUtil.scaleImageDownToFile(this, profileImageUri);
                    isChangeProfileImage = true;
                    circleImageViewProfileManagerProfileImage.setImageBitmap(resizeBitmap);
                }
                break;
        }
    }

    private void setProfileInit() {
        //서버로부터 프로필 이미지와 닉네임을 받아서 표시하는 함수
        editTextProfileManagerNickname.setText(MidasMobile9Application.user.getNickname());

        Glide.with(getApplicationContext()) // Activity 또는 Fragment의 context
                .load(NetworkDefineConstantOSH.SERVER_URL_GET_PROFILE_IMG + MidasMobile9Application.user.getProfileimg()) // drawable에 저장된 이미지
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(circleImageViewProfileManagerProfileImage); // 이미지를 보여줄 view

        textViewProfileManagerEmail.setText(MidasMobile9Application.user.getEmail());
    }

    @OnClick(R.id.textViewProfileManagerComplete)
    public void onCompleteClick() {
        //완료 버튼 클릭
        String originalPassword = editTextProfileManagerPasswordOriginal.getText().toString().trim();
        String newPasswordFirst = editTextProfileManagerNewPasswordFirst.getText().toString().trim();
        String newPasswordSecond = editTextProfileManagerNewPasswordSecond.getText().toString().trim();

        if ( newPasswordFirst.length() < 1 ) {
            newPasswordFirst = null;
        }

        // 오리지날 비밀번호 형식 체크
        if ( !PasswordUtil.checkPassword(getApplicationContext(), originalPassword, MidasMobile9Application.user.getEmail()) ) {
            return;
        }

        // 새 비밀번호 형식 체크
        if ( newPasswordFirst != null && !PasswordUtil.checkPassword(getApplicationContext(), newPasswordFirst, MidasMobile9Application.user.getEmail()) ) {
            return;
        }

        // 새 비밀번호, 새 비밀번호 확인 일치 체크
        if ( newPasswordFirst != null && !newPasswordFirst.equals(newPasswordSecond) ) {
            Snackbar.make(contentsLinearLayout, R.string.new_password_not_matched, Snackbar.LENGTH_LONG).show();
            return;
        }

        new UserUpdateTask().execute(MidasMobile9Application.user.getEmail(),
                Encryption.getMD5(originalPassword),
                editTextProfileManagerNickname.getText().toString().trim(),
                Encryption.getMD5(newPasswordFirst),
                isChangeProfileImage,
                resultImageFile);
    }

    @OnClick(R.id.textViewProfilePrivateInfoPolicy)
    public void onPrivateInfoPolicyClick() {
        String privateInfoPolicyURL = PRIVACY_POLICY_INFO;

        Intent webpageIntent = new Intent(Intent.ACTION_VIEW);
        webpageIntent.setData(Uri.parse(privateInfoPolicyURL));
        startActivity(webpageIntent);
    }

    @OnClick(R.id.imageViewProfileManagerBack)
    public void onProfileManagerBackClick() {
        Intent resultIntent = getIntent();
        setResult(RESULT_CANCELED, resultIntent);
        finish();
    }

    @OnClick(R.id.circleImageViewProfileManagerProfileImage)
    public void onChangeProfileImageClick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_PROFILE_FROM_ALBUM);
    }

    @OnClick(R.id.textViewProfileManagerDelete)
    public void onProfileDeleteClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText passwordEditText = new EditText(this);
        passwordEditText.setHint("비밀번호를 입력해주세요.");
        passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());

        builder.setView(passwordEditText)
                .setMessage(getString(R.string.profile_delete_alert_dialog))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String password = Encryption.getMD5(passwordEditText.getText().toString().trim());
                        new UserDeleteTask().execute(MidasMobile9Application.user.getEmail(), password);
                    }
                })
                .setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

    // AsyncTask ====================================================================================
    public class UserUpdateTask extends AsyncTask<Object, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBarShow.getProgressBarShowSingleton(ProfileManagerActivity.this).show(coordinatorLayout);
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            String email = (String)params[0];
            String password = (String)params[1];
            String nickname = (String)params[2];
            String newpassword = (String)params[3];
            boolean isChangeProfileImage = (boolean)params[4];
            File imageFile = (File)params[5];

            boolean isUpdated = ProfileManagerModel.updateUserInfo(email, password, nickname, newpassword, isChangeProfileImage, imageFile);

            // 유저 정보 업데이트
            if ( isUpdated ) {
                MidasMobile9Application.user.setNickname(nickname);

                if ( isChangeProfileImage ) {
                    MidasMobile9Application.user.setProfileimg(email + ".png");
                }
            }

            return isUpdated;
        }

        @Override
        protected void onPostExecute(Boolean isUpdated) {
            super.onPostExecute(isUpdated);
            ProgressBarShow.getProgressBarShowSingleton(ProfileManagerActivity.this).remove(coordinatorLayout);

            if ( isUpdated ) {
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);

                finish();
            } else {
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Toast.makeText(ProfileManagerActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class UserDeleteTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBarShow.getProgressBarShowSingleton(ProfileManagerActivity.this).show(coordinatorLayout);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String email = params[0];
            String password = params[1];

            boolean isDeleted = ProfileManagerModel.deleteUser(email, password);

            return isDeleted;
        }

        @Override
        protected void onPostExecute(Boolean isDeleted) {
            super.onPostExecute(isDeleted);
            ProgressBarShow.getProgressBarShowSingleton(ProfileManagerActivity.this).remove(coordinatorLayout);

            if (isDeleted) {
                //삭제 성공
                Intent intent = new Intent(ProfileManagerActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();
            } else {
                //삭제 실패
                Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
            }
        }
    }
    // ==============================================================================================
}
