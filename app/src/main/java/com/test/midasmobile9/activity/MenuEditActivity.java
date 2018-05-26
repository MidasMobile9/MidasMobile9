package com.test.midasmobile9.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import com.test.midasmobile9.R;
import com.test.midasmobile9.data.AdminMenuItem;
import com.test.midasmobile9.network.NetworkDefineConstant;
import com.test.midasmobile9.util.ImageUtil;

import java.io.File;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuEditActivity extends AppCompatActivity {
    public static final int REQUEST_TAKE_PROFILE_FROM_ALBUM = 160;

    public static final String PROFILE_URL_HEADER = NetworkDefineConstant.HOST_URL + "/profileimg/";

    private File resultImageFile;
    private boolean isChangeProfileImage = false;

    AdminMenuItem editItem = null;

    @BindView(R.id.imageViewMenuImage)
    ImageView imageViewMenuImage;
    @BindView(R.id.editTextMenuName)
    EditText editTextMenuName;
    @BindView(R.id.editTextMenuPrice)
    EditText editTextMenuPrice;
    @BindView(R.id.editTextMenuInfo)
    EditText editTextMenuInfo;
    @BindView(R.id.checkBoxHot)
    CheckBox checkBoxHot;
    @BindView(R.id.checkBoxCold)
    CheckBox checkBoxCold;
    @BindView(R.id.textViewEditComplete)
    TextView textViewEditComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_edit);
        // 버터나이프
        ButterKnife.bind(MenuEditActivity.this);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        editItem = intent.getParcelableExtra("editMenuItem");

        // 커피 이미지
        Glide.with(MenuEditActivity.this)
                .load(PROFILE_URL_HEADER + editItem.getImg())
                .into(imageViewMenuImage);
        // 메뉴 이름
        editTextMenuName.setText(editItem.getName());
        // 메뉴 가격
        String price = editItem.getPrice() + " 원";
        editTextMenuPrice.setText(price);
        // 메뉴 정보
        editTextMenuInfo.setText(editItem.getInfo());
        // HOT/COLD 체크박스
        int hotcold = editItem.getHotcold();
        if ( hotcold == 0 ) {
            checkBoxHot.setChecked(true);
            checkBoxCold.setChecked(false);
        } else if ( hotcold == 1 ) {
            checkBoxHot.setChecked(false);
            checkBoxCold.setChecked(true);
        } else if ( hotcold == 2 ) {
            checkBoxHot.setChecked(true);
            checkBoxCold.setChecked(true);
        }
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

                    Glide.with(MenuEditActivity.this)
                            .load(data.getData())
                            .into(imageViewMenuImage);
                }
                break;
        }
    }

    @OnClick(R.id.imageViewMenuImage)
    public void onClickMenuImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_PROFILE_FROM_ALBUM);
    }

    @OnClick(R.id.textViewEditComplete)
    public void onClickEditComplete(View view) {
        finish();
    }
}
