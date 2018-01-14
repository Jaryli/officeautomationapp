package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by pc on 2018/1/14.
 */

public class MyApprovalReceiveBean implements Serializable {

    private Integer UserId;//Int	用户编号
    private Integer ResId;//	Int	物品编号
    private String ResName;//	String	物品名称
    private Integer NumInfo;//	Int	申请数量
    private String TypeInfo;//	String	分配或领用
    private String UseDate;//	String	领用时间
    private String BackDate;//	String	归还时间
    private String ApplyDate;//	String	申请时间
    private Integer ProjectId;//	Int	关联工程id
    private String ProjectName;//	String	关联工程名称
    private String Remark;//	String	审批记录（审批信息）
    private String ToUser;//	String	审批人

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getResId() {
        return ResId;
    }

    public void setResId(Integer resId) {
        ResId = resId;
    }

    public String getResName() {
        return ResName;
    }

    public void setResName(String resName) {
        ResName = resName;
    }

    public Integer getNumInfo() {
        return NumInfo;
    }

    public void setNumInfo(Integer numInfo) {
        NumInfo = numInfo;
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

    public String getToUser() {
        return ToUser;
    }

    public void setToUser(String toUser) {
        ToUser = toUser;
    }
}
