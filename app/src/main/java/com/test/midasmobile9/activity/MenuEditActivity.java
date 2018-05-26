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
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import com.test.midasmobile9.R;
import com.test.midasmobile9.data.AdminMenuItem;
import com.test.midasmobile9.model.MenuInfoModel;
import com.test.midasmobile9.network.NetworkDefineConstant;
import com.test.midasmobile9.util.ImageUtil;

import java.io.File;
import java.util.Map;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuEditActivity extends AppCompatActivity {
    public static final int REQUEST_TAKE_PROFILE_FROM_ALBUM = 160;

    public static final String PROFILE_URL_HEADER = NetworkDefineConstant.HOST_URL + "/menuimg/";

    private File resultImageFile;
    private boolean isChangeProfileImage = false;

    String name;
    int price;
    String info;
    int hotcold;

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
    @BindView(R.id.imageViewEditBack)
    ImageView imageViewEditBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_edit);
        // 버터나이프
        ButterKnife.bind(MenuEditActivity.this);

        init();
    }

    @OnClick(R.id.imageViewEditBack)
    public void onClickEditBack(View view) {
        finish();
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
        String price = String.valueOf(editItem.getPrice());
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
        if ( editTextMenuName.getText().toString().trim().length() < 1 ) {
            Snackbar.make(imageViewMenuImage, "메뉴 이름을 입력해주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        } else {
            name = editTextMenuName.getText().toString().trim();
        }

        if ( editTextMenuPrice.getText().toString().trim().length() < 1 ) {
            Snackbar.make(imageViewMenuImage, "가격을 입력해주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        } else {
            price = Integer.parseInt(editTextMenuPrice.getText().toString().trim());
        }

        if ( editTextMenuInfo.getText().toString().trim().length() < 1 ) {
            Snackbar.make(imageViewMenuImage, "메뉴정보를 입력해주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        } else {
            info = editTextMenuInfo.getText().toString().trim();
        }

        if ( checkBoxHot.isChecked() && !checkBoxCold.isChecked() ) {
            hotcold = 0;
        } else if ( !checkBoxHot.isChecked() && checkBoxCold.isChecked() ) {
            hotcold = 1;
        } else if ( checkBoxHot.isChecked() && checkBoxCold.isChecked() ) {
            hotcold = 2;
        } else {
            Snackbar.make(imageViewMenuImage, "HOT/COLD 를 선택해주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        new MenuEditTask().execute(editItem.getNo(), name, price, info, hotcold, resultImageFile, isChangeProfileImage);
    }

    public class MenuEditTask extends AsyncTask<Object, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(Object... params) {
            int no = (int)params[0];
            String name = (String)params[1];
            int price = (int)params[2];
            String info = (String)params[3];
            int hotcold = (int)params[4];
            File imageFile = (File)params[5];
            boolean isChangeProfileImage = (boolean)params[6];

            Map<String, Object> map = MenuInfoModel.editMenu(no, name, price, info, hotcold, imageFile, isChangeProfileImage);

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);

            if ( map == null ) {
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Snackbar.make(imageViewMenuImage, message, Snackbar.LENGTH_SHORT).show();
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
                    Snackbar.make(imageViewMenuImage, message, Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }
}
