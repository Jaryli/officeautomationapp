package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by CS-711701-00027 on 2017/5/23.
 */

public class HiddenPorjectPostBean implements Serializable {

    private int Id;
    private int ProjectId;
    private String BeforeWorkPhoto;
    private String WorkingPhoto;
    private String AfterWorkPhoto;
    private String SupplyName;
    private String WorkContent;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getProjectId() {
        return ProjectId;
    }

    public void setProjectId(int projectId) {
        ProjectId = projectId;
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
}
