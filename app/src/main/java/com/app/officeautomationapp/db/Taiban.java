package com.app.officeautomationapp.db;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by CS-711701-00027 on 2017/6/2.
 * 台班机械台班信息提交
 */
@Table(name="worktaiban")
public class Taiban implements Serializable {
    @Column(name = "id",isId = true,autoGen = true,property = "NOT NULL")
    private int id;
    @Column(name = "projectId")
    private int projectId;//工程Id
    @Column(name = "projectName")
    private String projectName;//
    @Column(name = "bussinessType")
    private String  bussinessType;//业务类型，固定为土建或绿化
    @Column(name = "imagedata")
    private String imagedata;	//Json数组台班实施图片数组
    //默认的构造方法必须写出，如果没有，这张表是创建不成功的
    public Taiban(){}

    public Taiban(int projectId, String projectName,String bussinessType, String imagedata) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.bussinessType = bussinessType;
        this.imagedata = imagedata;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getImagedata() {
        return imagedata;
    }

    public void setImagedata(String imagedata) {
        this.imagedata = imagedata;
    }

    @Override
    public String toString() {
        return "User{id="+id+",projectId="+projectId+",projectName="+projectName+"}";
    }
}
