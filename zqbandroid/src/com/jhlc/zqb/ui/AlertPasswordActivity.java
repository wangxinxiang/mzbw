package com.jhlc.zqb.ui;


import android.os.Bundle;
import com.jhlc.zqb.R;

public class AlertPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_password_question);
        setOnClick();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title.setText("注册");
    }
}
