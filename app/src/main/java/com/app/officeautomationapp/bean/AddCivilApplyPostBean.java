package com.app.officeautomationapp.bean;

/**
 * Created by yu on 2017/11/17.
 * 添加土建验收表头[Post]
 * 添加苗木验收表头
 */

public class AddCivilApplyPostBean {

    private int projectId;//	Int	工程Id
    private String projectName;//	String	工程名称
    private String buyCode;//	String	引用的采购单号
    private Double fee;//	Double	运费
    private String supplyName;//	String	供应商
    private String remark;
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

    public String getBuyCode() {
        return buyCode;
    }

    public void setBuyCode(String buyCode) {
        this.buyCode = buyCode;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
