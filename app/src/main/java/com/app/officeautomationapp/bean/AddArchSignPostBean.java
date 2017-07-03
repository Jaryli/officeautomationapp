package com.app.officeautomationapp.bean;

/**
 * 用章申请
 * Created by CS-711701-00027 on 2017/6/2.
 */

public class AddArchSignPostBean {
    private String flowGuid;//工作流标识
    private String workName;//工程名称
    private int toUser;//指定审批人
    private String[] imagedata;	//用章文件照片Base64编码
    private String  signName;//公章名称
    private String fileName;//文件名称
    private String  fileRemark;//申请文件名称摘要
    private String signNum;//用章数量

    public String getFlowGuid() {
        return flowGuid;
    }

    public void setFlowGuid(String flowGuid) {
        this.flowGuid = flowGuid;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public String[] getImagedata() {
        return imagedata;
    }

    public void setImagedata(String[] imagedata) {
        this.imagedata = imagedata;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileRemark() {
        return fileRemark;
    }

    public void setFileRemark(String fileRemark) {
        this.fileRemark = fileRemark;
    }

    public String getSignNum() {
        return signNum;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }
}
