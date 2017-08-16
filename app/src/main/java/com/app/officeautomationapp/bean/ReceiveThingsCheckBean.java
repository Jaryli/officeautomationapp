package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by yu on 2017/8/16.
 */

public class ReceiveThingsCheckBean  implements Serializable {
    private Integer Id;
    private Integer UserId;
    private Integer DeptId;
    private Integer ResId;
    private Integer NumInfo;
    private Integer ResApplyState;
    private String TypeInfo;
    private String UseDate;
    private String BackDate;
    private String ApplyDate;
    private double MoneyNum;
    private Integer ProjectId;
    private String ProjectName;
    private String Remark;
    private String Remark1;
    private String Remark2;
    private Integer UserId1;
    private Integer UserId2;
    private String ToUser;
    private String ResName;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getDeptId() {
        return DeptId;
    }

    public void setDeptId(Integer deptId) {
        DeptId = deptId;
    }

    public Integer getResId() {
        return ResId;
    }

    public void setResId(Integer resId) {
        ResId = resId;
    }

    public Integer getNumInfo() {
        return NumInfo;
    }

    public void setNumInfo(Integer numInfo) {
        NumInfo = numInfo;
    }

    public Integer getResApplyState() {
        return ResApplyState;
    }

    public void setResApplyState(Integer resApplyState) {
        ResApplyState = resApplyState;
    }

    public String getTypeInfo() {
        return TypeInfo;
    }

    public void setTypeInfo(String typeInfo) {
        TypeInfo = typeInfo;
    }

    public String getUseDate() {
        return UseDate;
    }

    public void setUseDate(String useDate) {
        UseDate = useDate;
    }

    public String getBackDate() {
        return BackDate;
    }

    public void setBackDate(String backDate) {
        BackDate = backDate;
    }

    public String getApplyDate() {
        return ApplyDate;
    }

    public void setApplyDate(String applyDate) {
        ApplyDate = applyDate;
    }

    public double getMoneyNum() {
        return MoneyNum;
    }

    public void setMoneyNum(double moneyNum) {
        MoneyNum = moneyNum;
    }

    public Integer getProjectId() {
        return ProjectId;
    }

    public void setProjectId(Integer projectId) {
        ProjectId = projectId;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getRemark1() {
        return Remark1;
    }

    public void setRemark1(String remark1) {
        Remark1 = remark1;
    }

    public String getRemark2() {
        return Remark2;
    }

    public void setRemark2(String remark2) {
        Remark2 = remark2;
    }

    public Integer getUserId1() {
        return UserId1;
    }

    public void setUserId1(Integer userId1) {
        UserId1 = userId1;
    }

    public Integer getUserId2() {
        return UserId2;
    }

    public void setUserId2(Integer userId2) {
        UserId2 = userId2;
    }

    public String getToUser() {
        return ToUser;
    }

    public void setToUser(String toUser) {
        ToUser = toUser;
    }

    public String getResName() {
        return ResName;
    }

    public void setResName(String resName) {
        ResName = resName;
    }
}
