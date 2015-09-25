package com.jhlc.zqb.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jhlc.zqb.R;

/**
 * Created by licheng on 26/8/15.
 */
public class MyItemView extends RelativeLayout {

    private View view;

    public MyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.item,this,true);

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.MyViewItem,0,0);
        String title = array.getString(R.styleable.MyViewItem_Title);
        String content = array.getString(R.styleable.MyViewItem_Content);
        String center = array.getString(R.styleable.MyViewItem_Center);
        Integer icon = array.getInteger(R.styleable.MyViewItem_Icon, R.drawable.qcoin_icon);
        Boolean visible = array.getBoolean(R.styleable.MyViewItem_TileContentVisibale, false);

        ((TextView)view.findViewById(R.id.tv_item_title)).setText(title);
        ((TextView)view.findViewById(R.id.tv_item_center)).setText(center);
        ((TextView)view.findViewById(R.id.tv_item_content)).setText(content);
        ((ImageView)view.findViewById(R.id.img_icon)).setImageResource(icon);
        if(!visible) {
            view.findViewById(R.id.tv_item_title).setVisibility(INVISIBLE);
            view.findViewById(R.id.tv_item_content).setVisibility(INVISIBLE);
            view.findViewById(R.id.tv_item_center).setVisibility(VISIBLE);
        }else{
            view.findViewById(R.id.tv_item_title).setVisibility(VISIBLE);
            view.findViewById(R.id.tv_item_content).setVisibility(VISIBLE);
            view.findViewById(R.id.tv_item_center).setVisibility(INVISIBLE);
        }
        array.recycle();
    }


}
