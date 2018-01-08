package com.app.officeautomationapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 用章申请
 * Created by CS-711701-00027 on 2017/6/2.
 */

public class AddArchSignPostBean implements Serializable {
    private String flowGuid;//工作流标识
    private String workName;//工程名称
    private int toUser;//指定审批人
    private String[] imagedata;	//用章文件照片Base64编码
    private String  signName;//公章名称
    private String fileName;//文件名称
    private String  fileRemark;//申请文件名称摘要
    private String signNum;//用章数量
    private List<AddArchSignPostBean.Pic> piclist;


    public static class Pic implements Serializable{
        double lon;
        double lati;
        String pic;

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLati() {
            return lati;
        }

        public void setLati(double lati) {
            this.lati = lati;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

    public List<Pic> getPiclist() {
        return piclist;
    }

    public void setPiclist(List<Pic> piclist) {
        this.piclist = piclist;
    }

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
