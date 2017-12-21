package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class TujianPostBean implements Serializable {

    private String[] imagedata;//	String	图片集合
    private Integer Id;//	Int	采购明细Id
    private String applyCode;//	String	表头的单号：即添加表头接口返回的orderCode
    private double numInfo;//	Double	验收数量
    private String remark;//	String	备注
    private String specialRequest;//	String	规格要求
    private String supplyName;//	String	供应商

    public String[] getImagedata() {
        return imagedata;
    }

    public void setImagedata(String[] imagedata) {
        this.imagedata = imagedata;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public double getNumInfo() {
        return numInfo;
    }

    public void setNumInfo(double numInfo) {
        this.numInfo = numInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }
}
