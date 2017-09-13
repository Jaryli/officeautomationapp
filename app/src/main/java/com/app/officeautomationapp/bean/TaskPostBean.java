package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by CS-711701-00027 on 2017/9/13.
 */

public class TaskPostBean implements Serializable {
    private Integer Id;
    private String userIds;
    private String endDate;
    private String startDate;
    private String taskName;
    private String taskContent;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }
}
