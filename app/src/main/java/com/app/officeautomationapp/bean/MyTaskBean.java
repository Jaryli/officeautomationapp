package com.app.officeautomationapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yu on 2017/7/23.
 */

public class MyTaskBean  implements Serializable {

    private int Id;
    private String TaskName;
    private int UserId;
    private String UserTrueName;
    private String StartDate;
    private String EndDate;
    private String ToUserIds;
    private String TaskContent;
    private int TaskStatus;
    private String TaskStatusStr;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserTrueName() {
        return UserTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        UserTrueName = userTrueName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getToUserIds() {
        return ToUserIds;
    }

    public void setToUserIds(String toUserIds) {
        ToUserIds = toUserIds;
    }

    public String getTaskContent() {
        return TaskContent;
    }

    public void setTaskContent(String taskContent) {
        TaskContent = taskContent;
    }

    public int getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        TaskStatus = taskStatus;
    }

    public String getTaskStatusStr() {
        return TaskStatusStr;
    }

    public void setTaskStatusStr(String taskStatusStr) {
        TaskStatusStr = taskStatusStr;
    }

}
