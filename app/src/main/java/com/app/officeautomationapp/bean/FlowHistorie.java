package com.app.officeautomationapp.bean;

/**
 * Created by yu on 2017/8/17.
 */

public class FlowHistorie {

    private Integer HId;
    private String StepName;
    private Integer UserId;
    private String UserTrueName;
    private Integer Status;
    private String CreateTime;
    private String AFHMessage;

    public Integer getHId() {
        return HId;
    }

    public void setHId(Integer HId) {
        this.HId = HId;
    }

    public String getStepName() {
        return StepName;
    }

    public void setStepName(String stepName) {
        StepName = stepName;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getUserTrueName() {
        return UserTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        UserTrueName = userTrueName;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getAFHMessage() {
        return AFHMessage;
    }

    public void setAFHMessage(String AFHMessage) {
        this.AFHMessage = AFHMessage;
    }
}
