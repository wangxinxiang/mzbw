package com.jhlc.zqb.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.*;
import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.jhlc.zqb.R;
import com.jhlc.zqb.beans.InitUserBean;
import com.jhlc.zqb.util.PreferenceUtils;
import com.jhlc.zqb.util.VolleyInterface;
import com.jhlc.zqb.util.VolleyRequest;
import com.umeng.update.UmengUpdateAgent;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private RadioButton rbtn_firstpage, rbtn_exchange, rbtn_me, rbtn_more;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private RadioGroup radioGroup;
    private static Boolean isQuit = false;
    private final Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        setContentView(R.layout.maintab);
        initView();
        setOnClick();
    }


    protected void initView() {
        rbtn_firstpage = (RadioButton) findViewById(R.id.rbtn_firstpage);
        rbtn_exchange = (RadioButton) findViewById(R.id.rbtn_exchange);
        rbtn_me = (RadioButton) findViewById(R.id.rbtn_me);
        rbtn_more = (RadioButton) findViewById(R.id.rbtn_more);
        radioGroup = (RadioGroup) this.findViewById(R.id.radiogroup);
        manager = getFragmentManager();
        setSelected(0);
    }

    protected void setOnClick() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_firstpage:
                        setSelected(0);
                        break;
                    case R.id.rbtn_exchange:
                        setSelected(1);
                        break;
                    case R.id.rbtn_me:
                        setSelected(2);
                        break;
                    case R.id.rbtn_more:
                        setSelected(3);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isQuit == false) {
                isQuit = true;
                ZQBApplication.getInstance().showTextToast("双击两次退出");
                TimerTask task = null;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
            } else {
                ZQBApplication.getInstance().exit();
            }
        }
        return true;
    }


    private void setSelected(int position) {
        transaction = manager.beginTransaction();
        switch (position) {
            case 0:
                rbtn_firstpage.setTextColor(getResources().getColor(R.color.text_result_orange));
                rbtn_exchange.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_me.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_more.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_firstpage.setChecked(true);
                transaction.replace(R.id.content, new FirstPageFragment());
                break;
            case 1:
                rbtn_firstpage.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_exchange.setTextColor(getResources().getColor(R.color.text_result_orange));
                rbtn_me.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_more.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_exchange.setChecked(true);
                transaction.replace(R.id.content, new ExchangeFragment());
                break;
            case 2:
                rbtn_firstpage.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_exchange.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_me.setTextColor(getResources().getColor(R.color.text_result_orange));
                rbtn_more.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_me.setChecked(true);
                transaction.replace(R.id.content, new MeFragment());
                break;
            case 3:
                rbtn_firstpage.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_exchange.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_me.setTextColor(getResources().getColor(R.color.text_content));
                rbtn_more.setTextColor(getResources().getColor(R.color.text_result_orange));
                rbtn_more.setChecked(true);
                transaction.replace(R.id.content, new MoreFragment());
                break;
            default:
                break;
        }
        transaction.commit();
    }



}
