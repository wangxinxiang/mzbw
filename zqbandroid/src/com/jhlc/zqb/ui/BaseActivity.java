package com.jhlc.zqb.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.jhlc.zqb.R;

public class BaseActivity extends Activity {
    protected RelativeLayout exchange_icon_item;
    protected TextView tv_item_title,tv_item_center,tv_item_content,tv_result;
    protected ImageView item_img_icon;
    protected ImageButton ibtn_arrow;
    protected TextView tv_title;
    protected ImageButton ibtn_back,ibtn_share;

    protected void setOnClick() {
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_back = (ImageButton) findViewById(R.id.btn_back);
        ibtn_share = (ImageButton) findViewById(R.id.btn_share);
        ibtn_share.setVisibility(View.GONE);
    }

    protected void FillViewGroup(LinearLayout layout,String title,String center,String content,String result){
        exchange_icon_item = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, null);
        item_img_icon = (ImageView) exchange_icon_item.findViewById(R.id.img_icon);
        tv_item_title = (TextView) exchange_icon_item.findViewById(R.id.tv_item_title);
        tv_item_center = (TextView) exchange_icon_item.findViewById(R.id.tv_item_center);
        tv_item_content = (TextView) exchange_icon_item.findViewById(R.id.tv_item_content);
        tv_result = (TextView) exchange_icon_item.findViewById(R.id.tv_result);
        ibtn_arrow = (ImageButton) exchange_icon_item.findViewById(R.id.btn_arrow);

        tv_item_title.setText(title);
        tv_item_content.setText(content);
        tv_item_center.setText(center);
        tv_result.setText(result);

        layout.addView(exchange_icon_item);
    }
}
