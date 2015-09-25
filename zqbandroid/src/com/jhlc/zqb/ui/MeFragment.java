package com.jhlc.zqb.ui;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.jhlc.zqb.R;
import com.jhlc.zqb.beans.BaseBean;
import com.jhlc.zqb.beans.MeBean;
import com.jhlc.zqb.beans.MyInfoBean;
import com.jhlc.zqb.util.PreferenceUtils;
import com.jhlc.zqb.util.ProgressDialog;
import com.jhlc.zqb.util.VolleyInterface;
import com.jhlc.zqb.util.VolleyRequest;

import net.youmi.android.offers.EarnPointsOrderList;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsChangeNotify;
import net.youmi.android.offers.PointsEarnNotify;
import net.youmi.android.offers.PointsManager;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class MeFragment extends BaseFragment implements PointsChangeNotify, PointsEarnNotify {
    private View view;
    private LinearLayout ll_me_task_record;
    private ImageButton btn_arrow;
    private static Integer[] img_icon = {R.drawable.task_record, R.drawable.extension_icon, R.drawable.invitation_friends
            , R.drawable.integral_subsidiary};
    private static String[] des = {"任务记录", "兑换记录", "邀请记录", "积分明细"};
    private String[] counts = null;
    private TextView tv_total_score, tv_task_score, tv_extension_score, tv_already_exchange_score, tv_maybe_exchange_score;
    private TextView tv_UserID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.me_layout, null);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        //联网
        GetMyInfo();
    }


    private void initView() {
        ll_me_task_record = (LinearLayout) view.findViewById(R.id.ll_me_task_record);
        tv_total_score = (TextView) view.findViewById(R.id.tv_total_score);
        tv_task_score = (TextView) view.findViewById(R.id.tv_task_score);
        tv_extension_score = (TextView) view.findViewById(R.id.tv_extension_score);
        tv_already_exchange_score = (TextView) view.findViewById(R.id.tv_already_exchange_score);
        tv_maybe_exchange_score = (TextView) view.findViewById(R.id.tv_maybe_exchange_score);
        tv_UserID = (TextView) view.findViewById(R.id.tv_userID);
        btn_arrow = (ImageButton) view.findViewById(R.id.btn_arrow);

        Log.i("LoginTest-->", PreferenceUtils.getIsLogin() + "");

        tv_UserID.setText("赚钱宝账号:" + PreferenceUtils.getUserId());

        btn_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private SpannableStringBuilder setRichText(View view, String number) {

        SpannableStringBuilder spannatext = new SpannableStringBuilder();
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.text_result_orange));
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan((float) 1.4);
        int start = 5;
        int end = 6 + number.length();
        switch (view.getId()) {
            case R.id.tv_total_score:
                spannatext.append("总赚积分\n");
                spannatext.append(number);
                spannatext.append("万");
                ForegroundColorSpan span1 = new ForegroundColorSpan(getResources().getColor(R.color.white));
                spannatext.setSpan(span1, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan((float) 2.0);
                spannatext.setSpan(sizeSpan1, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case R.id.tv_task_score:
                spannatext.append("任务获得\n");
                spannatext.append(number);
                spannatext.append("万");
                spannatext.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannatext.setSpan(sizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case R.id.tv_extension_score:
                spannatext.append("推广获得\n");
                spannatext.append(number);
                spannatext.append("万");
                spannatext.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannatext.setSpan(sizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case R.id.tv_already_exchange_score:
                spannatext.append("已兑换\n");
                spannatext.append(number);
                spannatext.append("万");
                start--;
                end--;
                spannatext.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannatext.setSpan(sizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case R.id.tv_maybe_exchange_score:
                spannatext.append("可兑换\n");
                spannatext.append(number);
                spannatext.append("万");
                start--;
                end--;
                spannatext.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannatext.setSpan(sizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            default:
                break;
        }
        return spannatext;
    }

    private void GetMyInfo() {
        String url = getResources().getString(R.string.url) + getResources().getString(R.string.url_GetMyInfo);
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("initid", String.valueOf(PreferenceUtils.getUserId()));
        VolleyRequest.RequestPost(getActivity(), url, "", hashMap, new VolleyInterface<MeBean>(MeBean.class, "GetMyInfo--->") {
            @Override
            public void onMySuccess(MeBean bean) {
                try {
                    MyInfoBean myInfoBean = bean.getMyinfo();
                    counts = new String[]{String.valueOf(myInfoBean.getTaskaccount()), String.valueOf(myInfoBean.getChangeaccount()),
                            String.valueOf(myInfoBean.getInviteaccount()), null};

                    tv_total_score.setText(setRichText(tv_total_score, String.valueOf(score(myInfoBean.getTotalpoints()))));
                    tv_task_score.setText(setRichText(tv_task_score, String.valueOf(score(myInfoBean.getTaskpoints()))));
                    tv_extension_score.setText(setRichText(tv_extension_score, String.valueOf(score(myInfoBean.getChangeaccount()))));
                    tv_already_exchange_score.setText(setRichText(tv_already_exchange_score, String.valueOf(score(myInfoBean.getAlreadchangeypoints()))));
                    tv_maybe_exchange_score.setText(setRichText(tv_maybe_exchange_score, String.valueOf(score(myInfoBean.getCanchangepoints()))));

                    for (int i = 0; i < img_icon.length; i++) {
                        final int which = i;
                        FillViewGroup(ll_me_task_record, img_icon[i], null, des[i], null, counts[i] + "次");
                        if ("积分明细".equals(tv_item_center.getText())) {
                            tv_result.setVisibility(View.GONE);
                        }
                        exchange_icon_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), ListActivity.class);
                                intent.putExtra("which", which);
                                startActivity(intent);
                            }
                        });
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
            }
        });
    }

    @Override
    public void onPointBalanceChange(int i) {
        tv_total_score.setText(setRichText(tv_total_score, String.valueOf(i)));
    }

    @Override
    public void onPointEarn(Context context, EarnPointsOrderList earnPointsOrderList) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OffersManager.getInstance(getActivity()).onAppExit();
        PointsManager.getInstance(getActivity()).unRegisterNotify(this);
        PointsManager.getInstance(getActivity()).unRegisterPointsEarnNotify(this);

    }

    private String score(int score) {
        double value = Double.valueOf(score);
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(value / 10000);
    }
}
