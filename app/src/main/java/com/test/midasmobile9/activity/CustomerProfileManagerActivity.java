package com.test.midasmobile9.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.midasmobile9.R;
import com.test.midasmobile9.data.CustomerInfoItem;
import com.test.midasmobile9.model.CustomerModel;
import com.test.midasmobile9.model.MenuInfoModel;
import com.test.midasmobile9.network.NetworkDefineConstant;
import com.test.midasmobile9.util.Encryption;
import com.test.midasmobile9.util.ImageUtil;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerProfileManagerActivity extends AppCompatActivity {
    public static final int REQUEST_TAKE_PROFILE_FROM_ALBUM = 120;

    private final String PRIVACY_POLICY_INFO = "https://blog.naver.com/tyrano_1/221283509070";
    public static final String PROFILE_URL_HEADER = NetworkDefineConstant.HOST_URL + "/profileimg/";

    private File resultImageFile;
    private boolean isChangeProfileImage = false;

    String nickname;
    String part;
    String phone;
    String email;
    String password;

    CustomerInfoItem editItem = null;

    @BindView(R.id.circleImageViewCustomerProfileImage)
    CircleImageView circleImageViewCustomerProfileImage;
    @BindView(R.id.editTextCustomerNickname)
    EditText editTextCustomerNickname;
    @BindView(R.id.editTextCustomerPart)
    EditText editTextCustomerPart;
    @BindView(R.id.editTextCustomerPhone)
    EditText editTextCustomerPhone;
    @BindView(R.id.editTextCustomerEmail)
    EditText editTextCustomerEmail;
    @BindView(R.id.editTextCustomerPassword)
    EditText editTextCustomerPassword;
    @BindView(R.id.textViewCustomerProfileManagerComplete)
    TextView textViewCustomerProfileManagerComplete;
    @BindView(R.id.imageViewCustomerProfileManagerBack)
    ImageView imageViewCustomerProfileManagerBack;
    @BindView(R.id.textViewProfilePrivateInfoPolicy)
    TextView textViewProfilePrivateInfoPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cumtomer_profile_manager);
        // 버터나이프
        ButterKnife.bind(CustomerProfileManagerActivity.this);

        init();
    }

    @OnClick(R.id.imageViewCustomerProfileManagerBack)
    public void onClickProfileManagerBack(View view) {
        finish();
    }

    private void init() {
        Intent intent = getIntent();
        editItem = intent.getParcelableExtra("editCustomerItem");

        // 고객 이미지
        Glide.with(CustomerProfileManagerActivity.this)
                .load(PROFILE_URL_HEADER + editItem.getProfileimg())
                .into(circleImageViewCustomerProfileImage);
        // 고객 닉네임
        editTextCustomerNickname.setText(editItem.getNickname());
        // 고객 부서
        editTextCustomerPart.setText(editItem.getPart());
        // 고객 전화번호
        editTextCustomerPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        editTextCustomerPhone.setText(editItem.getPhone());
        // 고객 이메일
        editTextCustomerEmail.setText(editItem.getEmail());
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

                    Glide.with(CustomerProfileManagerActivity.this)
                            .load(data.getData())
                            .into(circleImageViewCustomerProfileImage);
                }
                break;
        }
    }

    @OnClick(R.id.circleImageViewCustomerProfileImage)
    public void onClickCustomerProfileImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_PROFILE_FROM_ALBUM);
    }

    @OnClick(R.id.textViewProfilePrivateInfoPolicy)
    public void onClickPrivateInfoPolicy(View view) {
        String privateInfoPolicyURL = PRIVACY_POLICY_INFO;

        Intent webpageIntent = new Intent(Intent.ACTION_VIEW);
        webpageIntent.setData(Uri.parse(privateInfoPolicyURL));
        startActivity(webpageIntent);
    }

    @OnClick(R.id.textViewCustomerProfileManagerComplete)
    public void onCLickCustomerProfileManagerComplete(View view) {
        if ( editTextCustomerNickname.getText().toString().trim().length() < 1 ) {
            Snackbar.make(circleImageViewCustomerProfileImage, "닉네임을 입력해주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        } else {
            nickname = editTextCustomerNickname.getText().toString().trim();
        }

        if ( editTextCustomerPart.getText().toString().trim().length() < 1 ) {
            Snackbar.make(circleImageViewCustomerProfileImage, "부서를 입력해주세요", Snackbar.LENGTH_SHORT).show();
            return;
        } else {
            part = editTextCustomerPart.getText().toString().trim();
        }

        if ( editTextCustomerPhone.getText().toString().trim().length() < 1 ) {
            Snackbar.make(circleImageViewCustomerProfileImage, "전화번호를 입력해 주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        } else {
            phone = editTextCustomerPhone.getText().toString().trim();
        }

        if ( editTextCustomerEmail.getText().toString().trim().length() < 1 ) {
            Snackbar.make(circleImageViewCustomerProfileImage, "이메일을 입력해 주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        } else {
            email = editTextCustomerEmail.getText().toString().trim();
        }

        String emailRegExp = "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+";
        Matcher matcher = Pattern.compile(emailRegExp).matcher(email);
        if (!matcher.matches()) {
            //이메일 형식이 아닐 경우
            Snackbar.make(circleImageViewCustomerProfileImage, getString(R.string.email_combination), Snackbar.LENGTH_SHORT).show();
            editTextCustomerEmail.requestFocus();
            return;
        }

        if ( editTextCustomerPassword.getText().toString().trim().length() <1 ) {
            password = null;
        } else {
            password = editTextCustomerPassword.getText().toString().trim();
        }

        if ( password != null ) {
            String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{9,12}$";
            Matcher matcher3 = Pattern.compile(pwPattern).matcher(password);
            pwPattern = "(.)\\1\\1\\1";
            Matcher matcher2 = Pattern.compile(pwPattern).matcher(password);
            if (!matcher3.matches()) {
                //영문 숫자 특수문자 구분
                Snackbar.make(circleImageViewCustomerProfileImage, getString(R.string.password_combination), Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (matcher2.find()) {
                //같은 문자 4자리 이상
                Snackbar.make(circleImageViewCustomerProfileImage, getString(R.string.password_repeat), Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (password.contains(email)) {
                //아이디 포함 여부
                Snackbar.make(circleImageViewCustomerProfileImage, getString(R.string.password_contain_id), Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (password.contains(" ")) {
                //공백문자 사용 불가
                Snackbar.make(circleImageViewCustomerProfileImage, getString(R.string.password_blank), Snackbar.LENGTH_SHORT).show();
                return;
            }
        }

        new CustomerEditTask().execute(editItem.getNo(), nickname, part, phone, email, password, resultImageFile, isChangeProfileImage);
    }

    public class CustomerEditTask extends AsyncTask<Object, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(Object... params) {
            int no = (int)params[0];
            String nickname = (String)params[1];
            String part = (String)params[2];
            String phone = (String)params[3];
            String email = (String)params[4];
            String newpassword = (String)params[5];
            newpassword = Encryption.getMD5(newpassword);
            File imageFile = (File)params[6];
            boolean isChangeProfileImage = (boolean)params[7];

            Map<String, Object> map = CustomerModel.editCustomer(no, nickname, part, phone, email, newpassword, imageFile, isChangeProfileImage);

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);

            if ( map == null ) {
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Snackbar.make(circleImageViewCustomerProfileImage, message, Snackbar.LENGTH_SHORT).show();
            } else {
                // 통신성공
                boolean result = false;
                String message = null;

                if ( map.containsKey("result") ) {
                    result = (boolean)map.get("result");
                }

                if ( map.containsKey("message") ) {
                    message = (String)map.get("message");
                }

                if ( result ) {
                    // 메뉴 수정 성공
                    finish();
                } else {
                    // 메뉴 수정 실패
                    Snackbar.make(circleImageViewCustomerProfileImage, message, Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }
}
