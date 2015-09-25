package com.jhlc.zqb.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by licheng on 8/9/15.
 */
public class MyInfoBean extends BaseBean {
    @SerializedName("initid")
    private String initid;
    @SerializedName("totalpoints")
    private int totalpoints;
    @SerializedName("taskpoints")
    private int taskpoints;
    @SerializedName("invitepoints")
    private int invitepoints;
    @SerializedName("alreadchangeypoints")
    private int alreadchangeypoints;
    @SerializedName("canchangepoints")
    private int canchangepoints;
    @SerializedName("taskaccount")
    private int taskaccount;
    @SerializedName("changeaccount")
    private int changeaccount;
    @SerializedName("inviteaccount")
    private int inviteaccount;

    public String getInitid() {
        return initid;
    }

    public void setInitid(String initid) {
        this.initid = initid;
    }

    public int getTotalpoints() {
        return totalpoints;
    }

    public void setTotalpoints(int totalpoints) {
        this.totalpoints = totalpoints;
    }

    public int getTaskpoints() {
        return taskpoints;
    }

    public void setTaskpoints(int taskpoints) {
        this.taskpoints = taskpoints;
    }

    public int getInvitepoints() {
        return invitepoints;
    }

    public void setInvitepoints(int invitepoints) {
        this.invitepoints = invitepoints;
    }

    public int getAlreadchangeypoints() {
        return alreadchangeypoints;
    }

    public void setAlreadchangeypoints(int alreadchangeypoints) {
        this.alreadchangeypoints = alreadchangeypoints;
    }

    public int getCanchangepoints() {
        return canchangepoints;
    }

    public void setCanchangepoints(int canchangepoints) {
        this.canchangepoints = canchangepoints;
    }

    public int getTaskaccount() {
        return taskaccount;
    }

    public void setTaskaccount(int taskaccount) {
        this.taskaccount = taskaccount;
    }

    public int getChangeaccount() {
        return changeaccount;
    }

    public void setChangeaccount(int changeaccount) {
        this.changeaccount = changeaccount;
    }

    public int getInviteaccount() {
        return inviteaccount;
    }

    public void setInviteaccount(int inviteaccount) {
        this.inviteaccount = inviteaccount;
    }
}
