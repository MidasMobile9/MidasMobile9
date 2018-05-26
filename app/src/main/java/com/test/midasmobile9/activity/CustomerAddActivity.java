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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.midasmobile9.R;
import com.test.midasmobile9.model.CustomerModel;
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

public class CustomerAddActivity extends AppCompatActivity {
    public static final int REQUEST_TAKE_PROFILE_FROM_ALBUM = 150;

    private final String PRIVACY_POLICY_INFO = "https://blog.naver.com/tyrano_1/221283509070";
    public static final String PROFILE_URL_HEADER = NetworkDefineConstant.HOST_URL + "/profileimg/";

    private File resultImageFile;
    private boolean isChangeProfileImage = false;

    String nickname;
    String part;
    String phone;
    String email;
    String password;

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
    @BindView(R.id.textViewCustomerPassword)
    TextView textViewCustomerPassword;
    @BindView(R.id.imageViewCustomerAddBack)
    ImageView imageViewCustomerAddBack;
    @BindView(R.id.textViewCustomerProfileAddComplete)
    TextView textViewCustomerProfileAddComplete;
    @BindView(R.id.textViewProfilePrivateInfoPolicy)
    TextView textViewProfilePrivateInfoPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);
        // 버터나이프
        ButterKnife.bind(CustomerAddActivity.this);

        init();
    }

    @OnClick(R.id.imageViewCustomerAddBack)
    public void onClickCustomerAddBack(View view) {
        finish();
    }

    @OnClick(R.id.textViewProfilePrivateInfoPolicy)
    public void onClickPrivateInfoPolicy(View view) {
        String privateInfoPolicyURL = PRIVACY_POLICY_INFO;

        Intent webpageIntent = new Intent(Intent.ACTION_VIEW);
        webpageIntent.setData(Uri.parse(privateInfoPolicyURL));
        startActivity(webpageIntent);
    }

    private void init() {
        editTextCustomerPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
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

                    Glide.with(CustomerAddActivity.this)
                            .load(data.getData())
                            .into(circleImageViewCustomerProfileImage);
                }
                break;
        }
    }

    @OnClick(R.id.circleImageViewCustomerProfileImage)
    public void onCliclMenuImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_PROFILE_FROM_ALBUM);
    }

    @OnClick(R.id.textViewCustomerProfileAddComplete)
    public void OnClickCustomerAddComplete(View view) {
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

        password = textViewCustomerPassword.getText().toString().trim();

        new CustomerAddTask().execute(nickname, part, phone, email, password, resultImageFile, isChangeProfileImage, 0);
    }

    public class CustomerAddTask extends AsyncTask<Object, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(Object... params) {
            String nickname = (String)params[0];
            String part = (String)params[1];
            String phone = (String)params[2];
            String email = (String)params[3];
            String password = (String)params[4];
            password = Encryption.getMD5(password);
            File imageFile = (File)params[5];
            boolean isChangeProfileImage = (boolean)params[6];
            int root = (int)params[7];

            Map<String, Object> map = CustomerModel.addCustomer(nickname, part, phone, email, password, imageFile, isChangeProfileImage, root);

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
