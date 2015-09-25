package com.jhlc.zqb.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.jhlc.zqb.R;
import com.jhlc.zqb.beans.Constants;
import com.jhlc.zqb.beans.InitUserBean;
import com.jhlc.zqb.util.PreferenceUtils;
import com.jhlc.zqb.util.VolleyInterface;
import com.jhlc.zqb.util.VolleyRequest;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersBrowserConfig;
import net.youmi.android.offers.OffersManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/12.
 */
public class WelcomeActivity extends Activity{
    private long startTime;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        if (!PreferenceUtils.getIsLogin()) {
            startTime = System.currentTimeMillis();
            initUser();
        } else {
            handler.postDelayed(null, 2000);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 初始化用户
     */
    private void initUser() {
        String url = getResources().getString(R.string.url) + getResources().getString(R.string.url_init_user);
        String requestTag = "initUser";
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("mobilecode", getIMEI());
        ZQBApplication.getInstance().showTextToast(getIMEI());
        Log.d("mobilecode", getIMEI());
        VolleyRequest.RequestPost(this, url, requestTag, hashMap, new VolleyInterface<InitUserBean>(InitUserBean.class, "initUser ----->") {
            @Override
            public void onMySuccess(InitUserBean result) {
                PreferenceUtils.getInstance().setUserID(Integer.parseInt(result.getInitid()));
                initYoumi();
                long endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                if (time > 2000) {
                    handler.sendEmptyMessage(0);
                } else {
                    handler.postDelayed(null, time);
                }
            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });
    }


    /**
     * 有米
     */
    private void initYoumi() {
        AdManager.getInstance(this).init(Constants.youmi_appId, Constants.youmi_appSecret);
        //设置标题文字
        OffersBrowserConfig.setBrowserTitleText("免费获取积分");
        //设置背景颜色
        OffersBrowserConfig.setBrowserTitleBackgroundColor(getResources().getColor(R.color.text_result_orange));
        //设置是否显示积分
        OffersBrowserConfig.setPointsLayoutVisibility(false);
        String userId = String.valueOf(PreferenceUtils.getUserId());
        Log.i("ZQBApplication-->", userId);
        if(userId==null||"".equals(userId)){
            ZQBApplication.getInstance().showTextToast("您还没登录");
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else{
            Log.d("ZQBApplication-->",String.valueOf(PreferenceUtils.getUserId()));
            //用户ID
            OffersManager.getInstance(this).setCustomUserId(String.valueOf(PreferenceUtils.getUserId()));
            //服务端调用
            OffersManager.setUsingServerCallBack(true);
        }
        OffersManager.getInstance(this).onAppLaunch();
    }


    /**
     * 获取唯一标识符
     *
     * @return
     */
    private String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
