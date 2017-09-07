package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by CS-711701-00027 on 2017/6/2.
 *
 */

public class MyTaskPostBean implements Serializable {
    private int taskId;
    private String todoContent;
    private String imagedata;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTodoContent() {
        return todoContent;
    }

    public void setTodoContent(String todoContent) {
        this.todoContent = todoContent;
    }

    public String getImagedata() {
        return imagedata;
    }

    public void setImagedata(String imagedata) {
        this.imagedata = imagedata;
    }
}
