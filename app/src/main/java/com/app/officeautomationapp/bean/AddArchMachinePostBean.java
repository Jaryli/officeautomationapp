package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by CS-711701-00027 on 2017/6/2.
 * 台班机械台班信息提交
 */

public class AddArchMachinePostBean implements Serializable {
    private int projectId;//工程Id
    private String flowGuid;//工作流标识
    private String workName;//工程名称
    private int toUser;//指定审批人
    private String[] imagedata;	//Json数组台班实施图片数组
    private String  gis;//图片地址定位
    private double afternoon;//下午工时
    private String  bussinessType;//业务类型，固定为土建或绿化
    private String  cbren;//承包人
    private double morning;//上午工时
    private String  jobContent;//工作内容
    private String  jobDate;//作业时间
    private double overTime;//加班
    private double perPrice;//单位价格
    private String  machineCode;//机械编码（可空）
    private String remark;//工作量
    private String  managerAdver;//经办人意见

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

    public double getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(double afternoon) {
        this.afternoon = afternoon;
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getCbren() {
        return cbren;
    }

    public void setCbren(String cbren) {
        this.cbren = cbren;
    }

    public double getMorning() {
        return morning;
    }

    public void setMorning(double morning) {
        this.morning = morning;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public double getOverTime() {
        return overTime;
    }

    public void setOverTime(double overTime) {
        this.overTime = overTime;
    }

    public double getPerPrice() {
        return perPrice;
    }

    public void setPerPrice(double perPrice) {
        this.perPrice = perPrice;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getManagerAdver() {
        return managerAdver;
    }

    public void setManagerAdver(String managerAdver) {
        this.managerAdver = managerAdver;
    }
}
