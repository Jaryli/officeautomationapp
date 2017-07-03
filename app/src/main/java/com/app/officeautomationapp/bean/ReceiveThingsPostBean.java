package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by yu on 2017/6/4.
 */

public class ReceiveThingsPostBean  implements Serializable {
    private int resId;//资产Id
    private int num;//领用数量
    private int projectId;//工程Id
    private int toUser;//审批人Id
    private String remark;//备注

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
