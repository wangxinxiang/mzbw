package com.jhlc.zqb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.jhlc.zqb.R;
import com.jhlc.zqb.beans.UserInfoBean;
import com.jhlc.zqb.beans.UserLoginBean;
import com.jhlc.zqb.util.DataVeri;
import com.jhlc.zqb.util.MD5;
import com.jhlc.zqb.util.PreferenceUtils;
import com.jhlc.zqb.util.Utils;
import com.jhlc.zqb.util.VolleyInterface;
import com.jhlc.zqb.util.VolleyRequest;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity {
    private EditText tv_login_phone, tv_login_password;
    private String phone, password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
        setOnClick();
    }

    @Override
    protected void initView() {
        super.initView();

        tv_title.setText("登录");

        tv_login_password = (EditText) findViewById(R.id.tv_login_password);
        tv_login_phone = (EditText) findViewById(R.id.tv_login_phone);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected void setOnClick() {
        super.setOnClick();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = tv_login_phone.getText().toString().trim();
                password = tv_login_password.getText().toString().trim();
                if (!DataVeri.isMobileNum(phone) || TextUtils.isEmpty(password)) {
                    ZQBApplication.getInstance().showTextToast("信息不能为空");
                } else {
                    login();
                }
            }
        });

        findViewById(R.id.btn_login_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void login(){
        String url = getResources().getString(R.string.url) + getResources().getString(R.string.url_login);
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("mobile", phone);
        hashMap.put("userpwd", MD5.getMD5Lower(password));
        VolleyRequest.RequestPost(this, url, "", hashMap, new VolleyInterface<UserLoginBean>(UserLoginBean.class, "login--->") {
            @Override
            public void onMySuccess(UserLoginBean result) {
                ZQBApplication.getInstance().showTextToast("登录成功");
                PreferenceUtils.getInstance().setIsLogin(true);
                UserInfoBean userInfoBean = result.getUserinfo();
                PreferenceUtils.getInstance().setUserID(Integer.parseInt(userInfoBean.getInitid()));
                finish();
            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });


    }
}
