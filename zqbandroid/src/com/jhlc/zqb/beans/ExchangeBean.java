package com.jhlc.zqb.beans;

import com.google.gson.annotations.SerializedName;


public class ExchangeBean extends EmptyBean {
    @SerializedName("orderid")
    private String orderid;
    @SerializedName("awardget")
    private String awardget;
    @SerializedName("createdate")
    private String createdate;
    @SerializedName("orderstatus")
    private String orderstatus;
    @SerializedName("exchangetype")
    private int exchangetype;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getAwardget() {
        return awardget;
    }

    public void setAwardget(String awardget) {
        this.awardget = awardget;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public int getExchangetype() {
        return exchangetype;
    }

    public void setExchangetype(int exchangetype) {
        this.exchangetype = exchangetype;
    }
}
