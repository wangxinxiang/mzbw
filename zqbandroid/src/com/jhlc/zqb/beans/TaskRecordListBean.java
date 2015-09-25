package com.jhlc.zqb.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/9/9.
 */
public class TaskRecordListBean extends BaseBean{

    @SerializedName("List")
    private TaskRecordBean[] List;

    public TaskRecordBean[] getList() {
        return List;
    }

    public void setList(TaskRecordBean[] list) {
        List = list;
    }
}
