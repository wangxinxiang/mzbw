package com.jhlc.zqb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhlc.zqb.R;

public class NewAccountTaskActivity extends BaseActivity {
    protected LinearLayout exchange_icon_item;
    protected TextView tv_item_title,tv_item_center,tv_item_content,tv_result;
    protected ImageView item_img_icon;
    protected ImageButton ibtn_arrow;

    private LinearLayout ll_new_account_task;
    private static Integer[] icons = {R.drawable.withdrawal_5173_acount,R.drawable.invitation_friends};
    private static String[] content = {"绑定5173账号","邀请好友"};
    private static String[] detail = {"绑定账号信息后才能兑换物品","最丰厚的邀请奖励等你来拿"};
    private static String[] money = {"+1万","+30万"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account_task_layout);
        initView();
        setOnClick();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title.setText("新手任务");
        ll_new_account_task = (LinearLayout) findViewById(R.id.ll_new_account_task);
        for(int i = 0;i<content.length;i++){
            FillViewGroup(ll_new_account_task,icons[i],content[i],detail[i],money[i]);
        }
    }

    protected void FillViewGroup(LinearLayout layout,Integer img_icon, String title, String content, String result) {
        exchange_icon_item = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.task_record_item, null);
        item_img_icon = (ImageView) exchange_icon_item.findViewById(R.id.img_icon);
        tv_item_title = (TextView) exchange_icon_item.findViewById(R.id.tv_item_title);
        tv_item_content = (TextView) exchange_icon_item.findViewById(R.id.tv_item_content);
        tv_result = (TextView) exchange_icon_item.findViewById(R.id.tv_result);
        ibtn_arrow = (ImageButton) exchange_icon_item.findViewById(R.id.btn_arrow);

        ibtn_arrow.setVisibility(View.VISIBLE);
        item_img_icon.setImageResource(img_icon);
        item_img_icon.setVisibility(View.VISIBLE);
        tv_item_title.setText(title);
        tv_item_content.setText(content);
        tv_result.setText(result);

        layout.addView(exchange_icon_item);
    }
}
