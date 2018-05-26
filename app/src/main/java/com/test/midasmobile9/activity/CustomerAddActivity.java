package com.test.midasmobile9.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.test.midasmobile9.R;
import com.test.midasmobile9.network.NetworkDefineConstant;
import com.test.midasmobile9.util.ImageUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerAddActivity extends AppCompatActivity {
    public static final int REQUEST_TAKE_PROFILE_FROM_ALBUM = 150;

    public static final String PROFILE_URL_HEADER = NetworkDefineConstant.HOST_URL + "/profileimg/";

    private File resultImageFile;
    private boolean isChangeProfileImage = false;

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
    TextView editTextCustomerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);
        // 버터나이프
        ButterKnife.bind(CustomerAddActivity.this);
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
                    circleImageViewCustomerProfileImage.setImageBitmap(resizeBitmap);
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
}
