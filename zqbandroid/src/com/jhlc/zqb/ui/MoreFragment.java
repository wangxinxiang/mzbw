package com.jhlc.zqb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jhlc.zqb.R;

public class MoreFragment extends BaseFragment {
    private TextView tv_title;
    private ImageButton ibtn_back,ibtn_share;
    private View view;
    private LinearLayout ll_more_group;
    private static Integer[] img_icon = {R.drawable.extension_icon,R.drawable.account_regist_icon,R.drawable.green_help};
    private static String[] des = {"推广有奖","账号注册","帮助中心"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.more_layout,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        RelativeLayout title = (RelativeLayout) view.findViewById(R.id.more_titlebar);
        title.setBackgroundResource(R.color.white);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ibtn_back = (ImageButton) view.findViewById(R.id.btn_back);
        ibtn_share = (ImageButton) view.findViewById(R.id.btn_share);
        ll_more_group = (LinearLayout) view.findViewById(R.id.ll_more_group);
        tv_title.setText("更多");
        ibtn_share.setVisibility(View.GONE);
        ibtn_back.setVisibility(View.GONE);
        for(int i = 0;i<img_icon.length;i++) {
            FillViewGroup(ll_more_group, img_icon[i], null, des[i], null, null);
//                    Toast.makeText(getActivity(), "点击", Toast.LENGTH_SHORT).show();
            if ("账号注册".equals(tv_item_center.getText())) {
                exchange_icon_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), RegistActivity.class);
                        startActivity(intent);
                    }
                });
        }
        }
    }


}
