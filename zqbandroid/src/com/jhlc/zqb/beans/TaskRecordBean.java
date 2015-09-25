package com.jhlc.zqb.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by licheng on 9/9/15.
 */
public class TaskRecordBean{
    @SerializedName("recordid")
    private String recordid;
    @SerializedName("ad")
    private String ad;
    @SerializedName("time")
    private String time;
    @SerializedName("points")
    private String points;

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
