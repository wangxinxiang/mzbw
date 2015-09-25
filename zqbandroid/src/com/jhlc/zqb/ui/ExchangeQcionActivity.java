package com.jhlc.zqb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.*;
import com.jhlc.zqb.R;

public class ExchangeQcionActivity extends BaseActivity{
    private LinearLayout ll_QCoinDes;
    private RelativeLayout rl_titlebar,rl_exchangebar;
    private TextView tv_current_score,tv_money_account;
    private static String[] QIconNums = {"Q币1个","Q币10个","Q币20个","Q币30个","Q币100个"};
    private static String[] price = {"10万","100万","195万","290万","970万"};
    //兑换类型
    private String exchangetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_center_layout);
        initView();
        setOnClick();
    }

    @Override
    protected void initView() {
        super.initView();
        rl_titlebar = (RelativeLayout) findViewById(R.id.exchangetitlebar);
        rl_exchangebar = (RelativeLayout) findViewById(R.id.exchangebar);
        ll_QCoinDes = (LinearLayout) findViewById(R.id.ll_QCoinDes);
        ibtn_share = (ImageButton) findViewById(R.id.btn_share);
        ibtn_back = (ImageButton) findViewById(R.id.btn_back);
        tv_current_score = (TextView) findViewById(R.id.tv_current_score);
        tv_money_account = (TextView) findViewById(R.id.tv_money_account);
        exchangetype = getIntent().getStringExtra("exchangetype");

        rl_exchangebar.setVisibility(View.GONE);
        tv_title.setText("兑换Q币");
        ibtn_share.setVisibility(View.GONE);
        tv_current_score.setText("当前可兑换积分");
        final String num = getIntent().getStringExtra("num");
        setNum(num + "万", tv_money_account);
        for (int i=0;i<QIconNums.length;i++){
            final String coinnum = QIconNums[i];
            final String coinprice = price[i];
            FillViewGroup(ll_QCoinDes, null, coinnum, null, coinprice);
            exchange_icon_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ExchangeQCoinInputActivity.class);
                    intent.putExtra("exchangetype",exchangetype);
                    intent.putExtra("QCionNumber", coinnum);
                    intent.putExtra("QCoinMoney", coinprice);
                    intent.putExtra("num", num);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    @Override
    protected void FillViewGroup(LinearLayout layout, String title, String center,String content, String result) {
        super.FillViewGroup(layout, title, center, content, result);
        tv_result.setTextColor(getResources().getColor(R.color.text_result_orange));
        tv_item_title.setVisibility(View.GONE);
        tv_item_content.setVisibility(View.GONE);
        ibtn_arrow.setVisibility(View.VISIBLE);
        item_img_icon.setImageResource(R.drawable.qcoin_icon);
    }

    private void setNum(String num, TextView view) {
        SpannableStringBuilder builder = new SpannableStringBuilder(num);
        int start = num.length() - 2;
        int end = num.length();
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan((float) 0.7);
        builder.setSpan(sizeSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        view.setText(builder);
    }
}
