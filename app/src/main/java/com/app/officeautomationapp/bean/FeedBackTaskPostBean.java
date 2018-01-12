package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by pc on 2018/1/12.
 */

public class FeedBackTaskPostBean  implements Serializable {

    private int taskId;
    private String feedback;
    private String toUserIds;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(String toUserIds) {
        this.toUserIds = toUserIds;
    }
}
