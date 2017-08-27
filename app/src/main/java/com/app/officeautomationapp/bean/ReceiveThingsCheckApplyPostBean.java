package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by yu on 2017/8/26.
 * id：记录主键编号，resultCode：审批结果，2-同意，9-不同意。msg：审批意见。
 */

public class ReceiveThingsCheckApplyPostBean implements Serializable {
    private int id;
    private int resultCode;
    private String msg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
