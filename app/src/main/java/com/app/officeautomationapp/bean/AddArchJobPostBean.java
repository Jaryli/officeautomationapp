package com.app.officeautomationapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 用工提交
 * Created by CS-711701-00027 on 2017/6/2.
 */

public class AddArchJobPostBean  implements Serializable {
    private int projectId;//工程Id
    private String flowGuid;//工作流标识
    private String workName;//工程名称
    private int toUser;//指定审批人
    private String[] imagedata;	//Json数组台班实施图片数组
    private String  gis;//图片地址定位
    private String projectName;//工程名称
    private String  cbren;//承包人
    private String jobDate;//作业时间
    private String jobAddress;//作业地址
    private String jobType;//大工种
    private String jobNum;//大工种人数
    private String jobContent;//用工内容
    private double jobDay;//用工折合工日数量
    private double jobPrice;//每工日单价
    private String jobRegister;//签发人，默认项目部
    private String jobDayInfo;//同用工内容
    private String jobType1;//小工种
    private String jobNum1;//小工种人数
    private List<AddArchJobPostBean.Pic> piclist;

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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

//    public String getImagedata() {
//        return imagedata;
//    }
//
//    public void setImagedata(String imagedata) {
//        this.imagedata = imagedata;
//    }


    public String[] getImagedata() {
        return imagedata;
    }

    public void setImagedata(String[] imagedata) {
        this.imagedata = imagedata;
    }

    public String getGis() {
        return gis;
    }

    public void setGis(String gis) {
        this.gis = gis;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCbren() {
        return cbren;
    }

    public void setCbren(String cbren) {
        this.cbren = cbren;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public String getJobAddress() {
        return jobAddress;
    }

    public void setJobAddress(String jobAddress) {
        this.jobAddress = jobAddress;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public double getJobDay() {
        return jobDay;
    }

    public void setJobDay(double jobDay) {
        this.jobDay = jobDay;
    }

    public double getJobPrice() {
        return jobPrice;
    }

    public void setJobPrice(double jobPrice) {
        this.jobPrice = jobPrice;
    }

    public String getJobRegister() {
        return jobRegister;
    }

    public void setJobRegister(String jobRegister) {
        this.jobRegister = jobRegister;
    }

    public String getJobDayInfo() {
        return jobDayInfo;
    }

    public void setJobDayInfo(String jobDayInfo) {
        this.jobDayInfo = jobDayInfo;
    }

    public String getJobType1() {
        return jobType1;
    }

    public void setJobType1(String jobType1) {
        this.jobType1 = jobType1;
    }

    public String getJobNum1() {
        return jobNum1;
    }

    public void setJobNum1(String jobNum1) {
        this.jobNum1 = jobNum1;
    }
}
