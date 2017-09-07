package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by CS-711701-00027 on 2017/9/7.
 */

public class MyTaskDoDetailsBean implements Serializable {

    private Integer Id;
    private Integer TaskId;
    private Integer UserId;
    private String TodoContent;
    private String ImageUrl;
    private String ImageUrlStr;
    private Integer TaskStatus;
    private String CreateTime;
    private String UserTrueName;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getTaskId() {
        return TaskId;
    }

    public void setTaskId(Integer taskId) {
        TaskId = taskId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getTodoContent() {
        return TodoContent;
    }

    public void setTodoContent(String todoContent) {
        TodoContent = todoContent;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getImageUrlStr() {
        return ImageUrlStr;
    }

    public void setImageUrlStr(String imageUrlStr) {
        ImageUrlStr = imageUrlStr;
    }

    public Integer getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        TaskStatus = taskStatus;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUserTrueName() {
        return UserTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        UserTrueName = userTrueName;
    }
}
