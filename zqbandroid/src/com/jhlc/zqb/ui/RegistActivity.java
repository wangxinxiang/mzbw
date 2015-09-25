package com.jhlc.zqb.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jhlc.zqb.R;
import com.jhlc.zqb.beans.UserInfoBean;
import com.jhlc.zqb.beans.UserLoginBean;
import com.jhlc.zqb.util.DataVeri;
import com.jhlc.zqb.util.MD5;
import com.jhlc.zqb.util.PreferenceUtils;
import com.jhlc.zqb.util.SmsObserver;
import com.jhlc.zqb.util.VolleyInterface;
import com.jhlc.zqb.util.VolleyRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistActivity extends BaseActivity {
    private EditText et_register_phone, et_register_ver;
    private TextView tv_register_code;
    private Button btn_register;
    private String phone, ver_code, nickname, userpwd;
    public static final int MSG_RECEIVE_CODE = 1011;

    private SmsObserver observer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
        initSmsObserver();
        setOnClick();
    }


    private void initSmsObserver() {
        observer = new SmsObserver(RegistActivity.this,handler);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri,true,observer);
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title.setText("注册");

        et_register_phone = (EditText) findViewById(R.id.et_register_phone);
        tv_register_code = (TextView) findViewById(R.id.tv_register_code);
        btn_register = (Button) findViewById(R.id.btn_register);
        et_register_ver = (EditText) findViewById(R.id.et_register_ver);
    }

    @Override
    protected void setOnClick() {
        super.setOnClick();

        tv_register_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataVeri.isMobileNum(et_register_phone.getText().toString().trim())) {
                    phone = et_register_phone.getText().toString().trim();
                    getChecCode();
                }

            }
        });


    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==MSG_RECEIVE_CODE){
                String code = (String)msg.obj;
                Log.d("RegistCode-->",code);
                et_register_ver.setText(code);
            }
        }
    };

    /**
     * 点击下一步后的界面
     */
    public void next(View view) {
        if (TextUtils.isEmpty(et_register_ver.getText())) return;
        ver_code = et_register_ver.getText().toString().trim();
        dismissAnimation(findViewById(R.id.ll_register_item));
        dismissAnimation(findViewById(R.id.btn_register_login));
        dismissAnimation(tv_register_code);
        showAnimation(findViewById(R.id.ll_register_question));

        btn_register.setText("完成");
        ((TextView) findViewById(R.id.tv_register_phone)).setText("密码");
        ((TextView) findViewById(R.id.tv_register_ver)).setText("用户名");
        et_register_phone.setText("");
        et_register_phone.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_register_ver.setText("");
        et_register_ver.setInputType(InputType.TYPE_CLASS_TEXT);
        et_register_phone.requestFocus();
//        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = et_register_ver.getText().toString().trim();
                userpwd = et_register_phone.getText().toString().trim();
                if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(userpwd)) {
                    ZQBApplication.getInstance().showTextToast("信息不能为空");
                    return;
                }
                register();
            }
        });
    }

    /**
     * 账号问题设置
     */
    public void question(View view) {
        //TODO 账号问题设置
        ZQBApplication.getInstance().showTextToast("暂未开放");
    }

    /**
     * 跳转到登录界面
     */
    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    private void dismissAnimation(View view) {
        ScaleAnimation alphaAnimation = new ScaleAnimation(1.0f, 0, 1.0f, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        alphaAnimation.setDuration(300);
        view.startAnimation(alphaAnimation);
        view.setVisibility(View.GONE);
    }

    private void showAnimation(View view) {
        view.setVisibility(View.VISIBLE);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(300);
        view.startAnimation(scaleAnimation);
    }

    /**
     * 账号注册
     */
    private void register() {
        String url = getResources().getString(R.string.url) + getResources().getString(R.string.url_register);
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("mobile", phone);
        hashMap.put("validatenum", ver_code);
        hashMap.put("nickname", nickname);
        hashMap.put("userpwd", MD5.getMD5Lower(userpwd));
        hashMap.put("initid", PreferenceUtils.getUserId() + "");
        VolleyRequest.RequestPost(this, url, "", hashMap, new VolleyInterface<UserLoginBean>(UserLoginBean.class, "register--->") {
                    @Override
                    public void onMySuccess(UserLoginBean result) {
                        ZQBApplication.getInstance().showTextToast("注册成功");
                        PreferenceUtils.getInstance().setIsLogin(true);
                        UserInfoBean userInfoBean = result.getUserinfo();
                        PreferenceUtils.getInstance().setUserID(Integer.parseInt(userInfoBean.getInitid()));
                        finish();
                    }

                    @Override
                    public void onMyError(VolleyError error) {

                    }
                }

        );
    }


    /**
     * 获取验证码
     */

    private void getVer() {
        String url = getResources().getString(R.string.url) + getResources().getString(R.string.url_ver);
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("mobile", et_register_phone.getText().toString());
        VolleyRequest.RequestPost(this, url, "", hashMap, new VolleyInterface<String>(String.class, "getVer --->") {
            @Override
            public void onMySuccess(String result) {
                ZQBApplication.getInstance().showTextToast("验证码已发送");
            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });
    }


    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p/>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p/>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        @Override
        public void onFinish() {
            tv_register_code.setText("重新获取\n验证码");
            tv_register_code.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_register_code.setText("重新获取\n验证码(" + millisUntilFinished / 1000 + "秒)");
        }
    }

    /**
     * 获取验证码
     */
    private void getChecCode() {
        getVer();
        MyCountDownTimer mc = new MyCountDownTimer(60000, 1000);
        mc.start();
        tv_register_code.setEnabled(false);
//        SLHApplication.getInstance().showTextToast("获取验证码后请不要再修改信息");
    }
}
