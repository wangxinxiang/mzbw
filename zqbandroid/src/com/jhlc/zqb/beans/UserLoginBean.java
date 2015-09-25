package com.jhlc.zqb.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/9/1.
 */
public class UserLoginBean extends BaseBean{
    @SerializedName("userinfo")
    private UserInfoBean userinfo;

    public UserInfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoBean userinfo) {
        this.userinfo = userinfo;
    }
}
