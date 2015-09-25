package com.jhlc.zqb.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.jhlc.zqb.R;
import com.jhlc.zqb.beans.AccountInfoBean;
import com.jhlc.zqb.util.PreferenceUtils;
import com.jhlc.zqb.util.VolleyInterface;
import com.jhlc.zqb.util.VolleyRequest;

import java.util.HashMap;
import java.util.Map;


public class AccountInfoActivity extends BaseActivity {
    private LinearLayout ll_accountinfo_group;
    private TextView tv_accountinfo_id;
    private static String[] des = {"我的5173账号", "手机号", "邀请人ID"};
    private String[] info = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountinfo_layout);
        initView();
        setOnClick();
    }

    @Override
    protected void initView() {
        super.initView();
        ll_accountinfo_group = (LinearLayout) findViewById(R.id.ll_accountinfo_group);
        tv_accountinfo_id = (TextView) findViewById(R.id.tv_accountinfo_id);
        tv_title.setText("账号信息");
        GetUserAccountInfo();
    }

    @Override
    protected void FillViewGroup(LinearLayout layout, String title, String center, String content, String result) {
        super.FillViewGroup(layout, title, center, content, result);
        item_img_icon.setVisibility(View.GONE);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 0, 0, 0);
        lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        tv_item_center.setLayoutParams(lp);
    }

    private void GetUserAccountInfo() {
        String url = getResources().getString(R.string.url) + getResources().getString(R.string.url_GetUserAccountInfo);
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("initid", String.valueOf(PreferenceUtils.getUserId()));
        VolleyRequest.RequestPost(this, url, "", hashMap, new VolleyInterface<AccountInfoBean>(AccountInfoBean.class, "GetUserAccountInfo--->") {


            @Override
            public void onMySuccess(AccountInfoBean accountInfoBean) {
                info = new String[]{"未绑定", accountInfoBean.getMobile(), accountInfoBean.getInviteid()};
                tv_accountinfo_id.setText("我的赚钱宝ID:" + String.valueOf(accountInfoBean.getInitid()));
                for (int i = 0; i < des.length; i++) {
                    FillViewGroup(ll_accountinfo_group, null, des[i], null, info[i]);
                    exchange_icon_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }

            }

            @Override
            public void onMyError(VolleyError error) {
                ZQBApplication.getInstance().showTextToast("请检查网络");
            }
        });
    }
}
