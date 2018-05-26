package com.test.midasmobile9.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.midasmobile9.R;
import com.test.midasmobile9.model.MenuInfoModel;
import com.test.midasmobile9.network.NetworkDefineConstant;
import com.test.midasmobile9.util.ImageUtil;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuAddActivity extends AppCompatActivity {
    public static final int REQUEST_TAKE_PROFILE_FROM_ALBUM = 130;

    public static final String PROFILE_URL_HEADER = NetworkDefineConstant.HOST_URL + "/menuimg/";

    private File resultImageFile;
    private boolean isChangeProfileImage = false;

    String name;
    int price;
    String info;
    int hotcold;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.imageViewMenuImage)
    ImageView imageViewMenuImage;
    @BindView(R.id.editTextMenuName)
    EditText editTextMenuName;
    @BindView(R.id.editTextMenuPrice)
    EditText editTextMenuPrice;
    @BindView(R.id.editTextMenuInfo)
    EditText editTextMenuInfo;
    @BindView(R.id.checkBoxAddHot)
    CheckBox checkBoxAddHot;
    @BindView(R.id.checkBoxAddCold)
    CheckBox checkBoxAddCold;
    @BindView(R.id.textViewAddComplete)
    TextView textViewAddComplete;
    @BindView(R.id.imageViewMenuAddBack)
    ImageView imageViewMenuAddBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add);
        // 버터나이프
        ButterKnife.bind(MenuAddActivity.this);
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

                    Glide.with(MenuAddActivity.this)
                            .load(data.getData())
                            .into(imageViewMenuImage);
                }
                break;
        }
    }

    @OnClick(R.id.imageViewMenuImage)
    public void onCliclMenuImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_PROFILE_FROM_ALBUM);
    }

    @OnClick(R.id.imageViewMenuAddBack)
    public void onClickMenuAddBack(View view) {
        finish();
    }

    @OnClick(R.id.textViewAddComplete)
    public void onClickMenuItemAddComplete(View view) {
        if ( editTextMenuName.getText().toString().trim().length() < 1 ) {
            Snackbar.make(textViewAddComplete, "메뉴 이름을 입력해주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        } else {
            name = editTextMenuName.getText().toString().trim();
        }

        if ( editTextMenuPrice.getText().toString().trim().length() < 1 ) {
            Snackbar.make(textViewAddComplete, "가격을 입력해주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        } else {
            price = Integer.parseInt(editTextMenuPrice.getText().toString().trim());
        }

        if ( editTextMenuInfo.getText().toString().trim().length() < 1 ) {
            Snackbar.make(textViewAddComplete, "메뉴정보를 입력해주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        } else {
            info = editTextMenuInfo.getText().toString().trim();
        }

        if ( checkBoxAddHot.isChecked() && !checkBoxAddCold.isChecked() ) {
            hotcold = 0;
        } else if ( !checkBoxAddHot.isChecked() && checkBoxAddCold.isChecked() ) {
            hotcold = 1;
        } else if ( checkBoxAddHot.isChecked() && checkBoxAddCold.isChecked() ) {
            hotcold = 2;
        } else {
            Snackbar.make(textViewAddComplete, "HOT/COLD 를 선택해주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // 어싱크태스크
        new MenuAddTask().execute(name, price, info, hotcold, resultImageFile, isChangeProfileImage);
    }

    public class MenuAddTask extends AsyncTask<Object, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(Object... params) {
            String name = (String)params[0];
            int price = (int)params[1];
            String info = (String)params[2];
            int hotcold = (int)params[3];
            File imageFile = (File)params[4];
            boolean isChangeProfileImage = (boolean)params[5];

            Map<String, Object> map = MenuInfoModel.addNewMenu(name, price, info, hotcold, imageFile, isChangeProfileImage);

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);

            if ( map == null ) {
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Snackbar.make(textViewAddComplete, message, Snackbar.LENGTH_SHORT).show();
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
                    // 주문 목록 가져오기 성공
                    finish();
                } else {
                    // 주문 목록 가져오기 실패
                    Snackbar.make(textViewAddComplete, message, Snackbar.LENGTH_SHORT).show();

                }
            }
        }
    }
}
