package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by yu on 2017/9/4.
 */

public class ApprovalPostBean  implements Serializable {
    private int workId;
    private String msg;
    private int resultCode;
    private String userIds2;
    private int nextStepSort;
    private int hId;
    private String userIds1;

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getUserIds2() {
        return userIds2;
    }

    public void setUserIds2(String userIds2) {
        this.userIds2 = userIds2;
    }

    public int getNextStepSort() {
        return nextStepSort;
    }

    public void setNextStepSort(int nextStepSort) {
        this.nextStepSort = nextStepSort;
    }

    public int gethId() {
        return hId;
    }

    public void sethId(int hId) {
        this.hId = hId;
    }

    public String getUserIds1() {
        return userIds1;
    }

    public void setUserIds1(String userIds1) {
        this.userIds1 = userIds1;
    }
}
