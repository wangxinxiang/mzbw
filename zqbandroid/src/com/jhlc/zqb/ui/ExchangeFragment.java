package com.jhlc.zqb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.android.volley.VolleyError;
import com.jhlc.zqb.R;
import com.jhlc.zqb.beans.AccountInfoBean;
import com.jhlc.zqb.util.PreferenceUtils;
import com.jhlc.zqb.util.VolleyInterface;
import com.jhlc.zqb.util.VolleyRequest;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class ExchangeFragment extends BaseFragment {
    private View view;
    private LinearLayout ll_exchange_list;
    private RelativeLayout rl_titlebar;
    private TextView tv_title,tv_current_score,tv_money_account;
    private ImageButton ibtn_share,ibtn_back;
    private static Integer[] img_icon = {R.drawable.qcoin_icon,R.drawable.phonecharge_icon,R.drawable.traffic_icon,
    R.drawable.withdrawal_5173_acount,R.drawable.pointcard_gamecurrency};
    private static String[] exchange_thing = {"Q币","话费","流量","提现到5173账号","点卡/游戏币"};
    private static String[] exchange_money = {"10万起","100万起","20万起","100万起","100万起"};
    private String num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.exchange_center_layout,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        GetUserAccountInfo();
    }

    private void initView() {
        tv_current_score = (TextView) view.findViewById(R.id.tv_current_score);
        tv_money_account = (TextView) view.findViewById(R.id.tv_money_account);
        rl_titlebar = (RelativeLayout) view.findViewById(R.id.exchangetitlebar);
        rl_titlebar.setVisibility(View.GONE);
        tv_current_score.setText("当前可兑换积分");
        ll_exchange_list = (LinearLayout) view.findViewById(R.id.ll_QCoinDes);
        for (int i = 0; i < exchange_thing.length; i++) {
            FillViewGroup(ll_exchange_list, img_icon[i], null, exchange_thing[i], null, exchange_money[i]);
            if("Q币".equals(tv_item_center.getText())){
                exchange_icon_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ExchangeQcionActivity.class);
                        intent.putExtra("exchangetype","1");
                        intent.putExtra("num", num);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    private void GetUserAccountInfo() {
        String url = getResources().getString(R.string.url) + getResources().getString(R.string.url_GetUserAccountInfo);
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("initid", String.valueOf(PreferenceUtils.getUserId()));
        VolleyRequest.RequestPost(getActivity(), url, "", hashMap, new VolleyInterface<AccountInfoBean>(AccountInfoBean.class, "GetUserAccountInfo--->") {
            @Override
            public void onMySuccess(AccountInfoBean accountInfoBean) {
                num = score(accountInfoBean.getAccoutmoney());
                setNum(num + "万", tv_money_account);

            }

            @Override
            public void onMyError(VolleyError error) {
                ZQBApplication.getInstance().showTextToast("请检查网络");
            }
        });
    }

    private String score(double score) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(score / 10000);
    }

    /**
     * 设置积分字体
     * @param num   积分数
     * @param view  textView
     */
    private void setNum(String num, TextView view) {
        SpannableStringBuilder builder = new SpannableStringBuilder(num);
        int start = num.length() - 2;
        int end = num.length();
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan((float) 0.7);
        builder.setSpan(sizeSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        view.setText(builder);
    }
}
