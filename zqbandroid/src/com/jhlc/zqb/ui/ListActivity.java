package com.jhlc.zqb.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.jhlc.zqb.R;
import com.jhlc.zqb.adapter.TaskRecordAdapter;
import com.jhlc.zqb.beans.*;
import com.jhlc.zqb.util.PreferenceUtils;
import com.jhlc.zqb.util.VolleyInterface;
import com.jhlc.zqb.util.VolleyRequest;

import java.util.*;

/**
 * Created by licheng on 28/8/15.
 */
public class ListActivity extends BaseActivity {
    private int which;
    private ListView listView;
    private TaskRecordAdapter adapter;
    private List taskRecordBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_one);
        initView();
        setOnClick();
    }

    @Override
    protected void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.lv_exchange_record);
        which = getIntent().getIntExtra("which", 0);
        switch (which) {
            case 0:
                tv_title.setText("任务记录");
                String url = getResources().getString(R.string.url) + getResources().getString(R.string.url_GetTaskRecordList);
                taskRecordBeanList = new ArrayList<TaskRecordBean>();
                HttpGetList(url);
                break;
            case 1:
                tv_title.setText("兑换记录");
                url = getResources().getString(R.string.url) + getResources().getString(R.string.url_GetChangeList);
                taskRecordBeanList = new ArrayList<ExchangeBean>();
                HttpGetList(url);
                break;
            case 2:
                tv_title.setText("邀请记录");
                ZQBApplication.getInstance().showTextToast("功能还未实现");
                break;
            case 3:
                tv_title.setText("积分明细");
                ZQBApplication.getInstance().showTextToast("功能还未实现");
            default:
                break;
        }
    }

    private void HttpGetList(String url) {
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("initid", String.valueOf(PreferenceUtils.getUserId()));
        VolleyRequest.RequestPost(this, url, "", hashMap, new VolleyInterface<String>(String.class, "GetTaskRecordList--->") {

            @Override
            public void onMySuccess(String result) {
                getList(result);
                adapter = new TaskRecordAdapter<TaskRecordBean>(getApplicationContext(), taskRecordBeanList);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });
    }

    private void getList(String result) {
        switch (which) {
            case 0:
                TaskRecordListBean taskRecordListBean = new GsonBuilder().create().fromJson(result, TaskRecordListBean.class);
                Collections.addAll(taskRecordBeanList, taskRecordListBean.getList());
                break;
            case 1:
                ExchangeListBean exchangeListBean = new GsonBuilder().create().fromJson(result, ExchangeListBean.class);
                Collections.addAll(taskRecordBeanList, exchangeListBean.getList());
                break;
            case 2:

                break;
            default:
                break;
        }
    }


}
