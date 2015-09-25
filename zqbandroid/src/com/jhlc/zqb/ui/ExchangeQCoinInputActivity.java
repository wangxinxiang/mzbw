package com.jhlc.zqb.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.jhlc.zqb.R;
import com.jhlc.zqb.beans.BaseBean;
import com.jhlc.zqb.util.PreferenceUtils;
import com.jhlc.zqb.util.VolleyInterface;
import com.jhlc.zqb.util.VolleyRequest;
import com.jhlc.zqb.view.ExchangeCallDialog;

import java.util.HashMap;
import java.util.Map;

public class ExchangeQCoinInputActivity extends BaseActivity {
    private TextView tv_QCoinNums,tv_QCoinMoney;
    private Button btn_daily_sign_in,btn_exchangeQcoin;
    private String exchangetype,QcoinNums,QCoinMoney, num;
    private EditText et_QQNum,et_reConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_qcoin_input_layout);
        initView();
        setOnClick();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_QCoinMoney = (TextView) findViewById(R.id.tv_QCoinMoney);
        tv_QCoinNums = (TextView) findViewById(R.id.tv_QCoinNums);
        btn_daily_sign_in = (Button) findViewById(R.id.btn_daily_sign_in);
        et_QQNum = (EditText) findViewById(R.id.et_QQNumber);
        et_reConfirm = (EditText) findViewById(R.id.et_reConfirm);
        tv_title.setText("兑换Q币");
        exchangetype = getIntent().getStringExtra("exchangetype");
        QcoinNums = getIntent().getStringExtra("QCionNumber");
        QCoinMoney = getIntent().getStringExtra("QCoinMoney");
        tv_QCoinNums.setText(QcoinNums);
        tv_QCoinMoney.setText(QCoinMoney);

        num = getIntent().getStringExtra("num");
    }

    @Override
    protected void setOnClick() {
        super.setOnClick();
        btn_daily_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 是否需要登录才能兑换
                if (!compareNum(num, QCoinMoney)) {
                    ExchangeCallDialog dialog = new ExchangeCallDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString("exchangecall_title", getResources().getString(R.string.exchangecallfail));
                    bundle.putString("exchangecall_content", getResources().getString(R.string.exchangecallfailcontent));
                    dialog.setArguments(bundle);
                    dialog.show(getFragmentManager(), "exchangecall");
                    return;
                }

                if (PreferenceUtils.getIsLogin()) {
                    ZQBApplication.getInstance().showTextToast("请先登录...");
                    return;
                }

                if((et_QQNum.getText().toString().trim()).equals(et_reConfirm.getText().toString().trim())){
                    exchange(et_QQNum.getText().toString());
                }else {
                    ZQBApplication.getInstance().showTextToast("两次输入不一致");
                }
            }
        });
    }

    private void exchange(String QQnumber) {
        String url = getResources().getString(R.string.url) + getResources().getString(R.string.url_exchange);
        String money = QCoinMoney.replace("万","0000");
        String coinnums = QcoinNums.substring(QcoinNums.indexOf("币")+1,QcoinNums.indexOf("个"));
        Log.i("Exchange-->",exchangetype+"|"+money+"|"+coinnums+"|"+QQnumber);
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("exchangetype", exchangetype);
        hashMap.put("pointscost", money);
        hashMap.put("initid", String.valueOf(PreferenceUtils.getUserId()));
        hashMap.put("awardget", coinnums);
        hashMap.put("payaccount", QQnumber);
        VolleyRequest.RequestPost(this, url, "", hashMap, new VolleyInterface<String>(String.class, "Exchange--->") {

            @Override
            public void onMySuccess(String result) {
                ExchangeCallDialog dialog = new ExchangeCallDialog();
                Bundle bundle = new Bundle();
                bundle.putString("exchangecall_title", getResources().getString(R.string.exchangecallsuccess));
                bundle.putString("exchangecall_content", getResources().getString(R.string.exchangecallsuccesscontent));
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "exchangecall");
            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });
    }

    /**
     * 比较已有积分和要兑换积分的大小
     * @param num      已有积分
     * @param QCoinMoney    要兑换的积分
     * @return 可以兑换返回 true
     */
    private boolean compareNum(String num, String QCoinMoney) {
        double num1 = Double.parseDouble(num);
        QCoinMoney = QCoinMoney.substring(0, QCoinMoney.length() - 2);
        long num2 = Long.parseLong(QCoinMoney);
        return num1 > num2;
    }
}
