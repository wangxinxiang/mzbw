package com.jhlc.zqb.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/9/1.
 */
public class InitUserBean extends BaseBean{
    @SerializedName("initid")
    private String initid;

    public String getInitid() {
        return initid;
    }

    public void setInitid(String initid) {
        this.initid = initid;
    }
}
