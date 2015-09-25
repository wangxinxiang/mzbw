package com.jhlc.zqb.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/9/9.
 */
public class ExchangeListBean extends BaseBean{

    @SerializedName("List")
    private ExchangeBean[] List;

    public ExchangeBean[] getList() {
        return List;
    }

    public void setList(ExchangeBean[] list) {
        List = list;
    }
}
