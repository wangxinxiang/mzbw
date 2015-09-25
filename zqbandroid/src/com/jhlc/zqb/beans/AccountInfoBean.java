package com.jhlc.zqb.beans;


import com.google.gson.annotations.SerializedName;

public class AccountInfoBean extends BaseBean {
    @SerializedName("initid")
    private String initid;
    @SerializedName("unionname")
    private String unionname;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("inviteid")
    private String inviteid;
    @SerializedName("accoutmoney")
    private Double accoutmoney;

    public String getInitid() {
        return initid;
    }

    public void setInitid(String initid) {
        this.initid = initid;
    }

    public String getUnionname() {
        return unionname;
    }

    public void setUnionname(String unionname) {
        this.unionname = unionname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInviteid() {
        return inviteid;
    }

    public void setInviteid(String inviteid) {
        this.inviteid = inviteid;
    }

    public Double getAccoutmoney() {
        return accoutmoney;
    }

    public void setAccoutmoney(Double accoutmoney) {
        this.accoutmoney = accoutmoney;
    }
}
