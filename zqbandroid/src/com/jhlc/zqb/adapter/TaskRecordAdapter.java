package com.jhlc.zqb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhlc.zqb.R;
import com.jhlc.zqb.beans.ExchangeBean;
import com.jhlc.zqb.beans.TaskRecordBean;

import java.util.List;

public class TaskRecordAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> recordBeanList;
    private LayoutInflater inflater;
    private ViewHolder holder;

    public TaskRecordAdapter(Context context, List<T> recordBeanList) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.recordBeanList = recordBeanList;
    }

    @Override
    public int getCount() {
        return recordBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.task_record_item,null);
            holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
//            holder.btn_arrow = (ImageButton) convertView.findViewById(R.id.btn_arrow);
//            holder.tv_center = (TextView) convertView.findViewById(R.id.tv_item_center);
            holder.tv_up = (TextView) convertView.findViewById(R.id.tv_item_title);
            holder.tv_down = (TextView) convertView.findViewById(R.id.tv_item_content);
            holder.tv_right = (TextView) convertView.findViewById(R.id.tv_result);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
//
//        holder.btn_arrow.setVisibility(View.GONE);
//        holder.tv_center.setVisibility(View.INVISIBLE);
        if (recordBeanList.get(position) instanceof TaskRecordBean) {
            TaskRecordBean t = (TaskRecordBean) recordBeanList.get(position);
            holder.tv_up.setText(t.getAd());
            holder.tv_down.setText(t.getTime());
            holder.tv_right.setText("+" + toWang(Integer.parseInt(t.getPoints())) + "万");

        } else if (recordBeanList.get(position) instanceof ExchangeBean) {
            ExchangeBean t = (ExchangeBean) recordBeanList.get(position);
            holder.tv_up.setText(numToText(t.getAwardget(), t.getExchangetype()));
            holder.tv_down.setText("兑换时间：" + t.getCreatedate());
            holder.tv_right.setText(t.getOrderstatus());
            if ("已兑换".equals(t.getOrderstatus())) {
                holder.tv_right.setTextColor(context.getResources().getColor(R.color.text_result_blue));
            } else if ("已退回".equals(t.getOrderstatus())){
                holder.tv_right.setTextColor(context.getResources().getColor(R.color.transparent));
            }
        }


        return convertView;
    }

    class ViewHolder{
        ImageView img_icon;
        ImageButton btn_arrow;
        TextView tv_up;
        TextView tv_center;
        TextView tv_down;
        TextView tv_right;
    }

    /**
     * 任务记录界面
     * 将数值转为万为单位
     * @param num
     * @return
     */
    private float toWang(int num) {
        return (float)num / 10000;
    }

    /**
     * 兑换记录界面
     * 将记录中的数字转为用户可见的文字
     * 如1转为Q币1个
     * @param num  1
     * @return    Q币1个
     */
    private String numToText(String num, int type) {
        String result = "";
        switch (type) {
            case 1:
                result += "Q币";
                result += num + "个";
                break;
            case 2:
                result += "话费";
                result += num + "元";
                break;
            case 3:
                result += "流量";
                result += num + "M";
                break;
            case 4:
                result += "提现到5173账号";
                break;
            case 5:
                result += "点卡/游戏币";
                break;
        }
        return result;
    }
}
