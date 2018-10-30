package com.example.xlq_tm.planbfortickets;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText mAccountNumber;
    private EditText mPassword;
    private Button mLoginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        setStatusBarColor();
        initView();
    }

    public void setStatusBarColor(){
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor,null));
        ActionBar actionBar = getSupportActionBar();
        int color = Color.parseColor("#339933");
        ColorDrawable drawable = new ColorDrawable(color);
        actionBar.setBackgroundDrawable(drawable);
    }

    public void initView(){
        mAccountNumber = findViewById(R.id.account);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.login_btn);
    }
}
