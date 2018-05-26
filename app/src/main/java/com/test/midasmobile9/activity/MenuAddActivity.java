package com.test.midasmobile9.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.midasmobile9.R;
import com.test.midasmobile9.network.NetworkDefineConstant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuAddActivity extends AppCompatActivity {

    public static final String PROFILE_URL_HEADER = NetworkDefineConstant.HOST_URL + "/profileimg/";

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
        setContentView(R.layout.activity_menu_add);
        // 버터나이프
        ButterKnife.bind(MenuAddActivity.this);
    }
}
