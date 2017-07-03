package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by yu on 2017/2/27.
 */
public class HiddenProjectBean implements Serializable {

    private int Id;
    private int UserId;
    private int ProjectId;
    private String ProjectName;
    private String SupplyName;
    private String WorkContent;
    private String BeforeWorkPhoto;
    private String WorkingPhoto;
    private String AfterWorkPhoto;
    private String CreateTime;
    private int Status;
    private int IsComplete;
    private String BeforeWorkPhotoStr;
    private String WorkingPhotoStr;
    private String AfterWorkPhotoStr;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getProjectId() {
        return ProjectId;
    }

    public void setProjectId(int projectId) {
        ProjectId = projectId;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getSupplyName() {
        return SupplyName;
    }

    public void setSupplyName(String supplyName) {
        SupplyName = supplyName;
    }

    public String getWorkContent() {
        return WorkContent;
    }

    public void setWorkContent(String workContent) {
        WorkContent = workContent;
    }

    public String getBeforeWorkPhoto() {
        return BeforeWorkPhoto;
    }

    public void setBeforeWorkPhoto(String beforeWorkPhoto) {
        BeforeWorkPhoto = beforeWorkPhoto;
    }

    public String getWorkingPhoto() {
        return WorkingPhoto;
    }

    public void setWorkingPhoto(String workingPhoto) {
        WorkingPhoto = workingPhoto;
    }

    public String getAfterWorkPhoto() {
        return AfterWorkPhoto;
    }

    public void setAfterWorkPhoto(String afterWorkPhoto) {
        AfterWorkPhoto = afterWorkPhoto;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getIsComplete() {
        return IsComplete;
    }

    public void setIsComplete(int isComplete) {
        IsComplete = isComplete;
    }

    public String getBeforeWorkPhotoStr() {
        return BeforeWorkPhotoStr;
    }

    public void setBeforeWorkPhotoStr(String beforeWorkPhotoStr) {
        BeforeWorkPhotoStr = beforeWorkPhotoStr;
    }

    public String getWorkingPhotoStr() {
        return WorkingPhotoStr;
    }

    public void setWorkingPhotoStr(String workingPhotoStr) {
        WorkingPhotoStr = workingPhotoStr;
    }

    public String getAfterWorkPhotoStr() {
        return AfterWorkPhotoStr;
    }

    public void setAfterWorkPhotoStr(String afterWorkPhotoStr) {
        AfterWorkPhotoStr = afterWorkPhotoStr;
    }
}
