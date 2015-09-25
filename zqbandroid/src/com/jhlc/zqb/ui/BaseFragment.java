package com.jhlc.zqb.ui;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhlc.zqb.R;
import com.jhlc.zqb.util.MyColor;
import com.jhlc.zqb.view.MyDialog;


public class BaseFragment extends Fragment {
    protected RelativeLayout exchange_icon_item;
    protected ImageView item_img_icon;
    protected TextView tv_item_title,tv_item_center,tv_item_content,tv_result;
    protected ImageButton ibtn_arrow;

    public BaseFragment() {
    }

    public void FillViewGroup(LinearLayout layout,Integer img_icon,String title,String center,String content,String result){
        exchange_icon_item = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item, null);
        item_img_icon = (ImageView) exchange_icon_item.findViewById(R.id.img_icon);
        tv_item_title = (TextView) exchange_icon_item.findViewById(R.id.tv_item_title);
        tv_item_center = (TextView) exchange_icon_item.findViewById(R.id.tv_item_center);
        tv_item_content = (TextView) exchange_icon_item.findViewById(R.id.tv_item_content);
        tv_result = (TextView) exchange_icon_item.findViewById(R.id.tv_result);
        ibtn_arrow = (ImageButton) exchange_icon_item.findViewById(R.id.btn_arrow);

        tv_item_title.setVisibility(View.GONE);
        tv_item_content.setVisibility(View.GONE);
        ibtn_arrow.setVisibility(View.VISIBLE);
        tv_result.setTextColor(MyColor.ORANGE);
        tv_item_title.setText(title);
        item_img_icon.setImageResource(img_icon);
        tv_item_center.setText(center);
        tv_result.setText(result);
        tv_item_content.setText(content);

        layout.addView(exchange_icon_item);
    }

    protected  void showToast(String message){
        ZQBApplication.getInstance().showTextToast(message);
    }

    protected void showShareDialog(){
        MyDialog dialog = new MyDialog();
        dialog.show(getFragmentManager(), "share");
    }
}
