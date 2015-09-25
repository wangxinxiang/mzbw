package com.jhlc.zqb.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/9/11.
 */
public class MeBean extends BaseBean{
    @SerializedName("myinfo")
    private MyInfoBean myinfo;

    public MyInfoBean getMyinfo() {
        return myinfo;
    }
}
