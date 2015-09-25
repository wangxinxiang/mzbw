package com.jhlc.zqb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.jhlc.zqb.R;
import com.jhlc.zqb.beans.BaseBean;
import com.jhlc.zqb.util.PreferenceUtils;
import com.jhlc.zqb.util.VolleyInterface;
import com.jhlc.zqb.util.VolleyRequest;
import com.jhlc.zqb.view.MyDialog;

import java.util.HashMap;
import java.util.Map;

public class TaskDetailActivity extends BaseActivity {
    private ImageButton btn_sahre;
    private Button btn_daily_sign_in;
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_layout);
        initView();
        setOnClick();
    }

    @Override
    protected void initView() {
        super.initView();
        btn_sahre = (ImageButton) findViewById(R.id.btn_share);
        btn_daily_sign_in = (Button) findViewById(R.id.btn_exchangeQcoin);
        tv_title.setText("任务详情");
        ibtn_share.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setOnClick() {
        super.setOnClick();
        btn_sahre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog();
            }
        });
        btn_daily_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sign();
            }
        });
    }

    protected void showShareDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getFragmentManager(), "share");
    }

    private void Sign() {
        String url = getResources().getString(R.string.url) + getResources().getString(R.string.url_Sign);
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("points", "1000");
        hashMap.put("initid", String.valueOf(PreferenceUtils.getUserId()));
        VolleyRequest.RequestPost(this, url, "", hashMap, new VolleyInterface<String>(String.class, "Sign--->") {
            @Override
            public void onMySuccess(String result) {
                ZQBApplication.getInstance().showTextToast("签到成功,获得1000积分");

                finish();
            }

            @Override
            public void onMyError(VolleyError error) {
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (dialog != null) {
            dialog.onNewIntent(intent);         //接受微博分享的结果
            dialog.dismiss();                   //接受完后再结束分享界面
        }
    }
}
