package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * 请假申请
 * Created by yu on 2017/6/4.
 */

public class AddArchLeavePostBean implements Serializable {
    private String workName;//工程名称
    private int toUser;//指定审批人userId
    private String leaveType1;//请假类型：公休假，私假
    private String leaveType2;//具体类型（标黄属于公休假）：    年休假，探亲假，婚假，产假，病假，事假，公事，私事，出差，其他
    private double leaveDays;//请假天数（可包含含半天)
    private String startDate;//请假开始时间
    private String endDate;//请假结束时间
    private String reason;//请假原因

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public String getLeaveType1() {
        return leaveType1;
    }

    public void setLeaveType1(String leaveType1) {
        this.leaveType1 = leaveType1;
    }

    public String getLeaveType2() {
        return leaveType2;
    }

    public void setLeaveType2(String leaveType2) {
        this.leaveType2 = leaveType2;
    }

    public double getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(double leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
