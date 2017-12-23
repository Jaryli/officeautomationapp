package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by pc on 2017/12/23.
 */

public class TujianDetailBean  implements Serializable {
    private Integer Id;
    private String FPCode;
    private String Extend3;
    private Integer Cid;
    private String CivilName;
    private String CivilUnits;
    private double ACNumInfo;
    private String  CivilSpecification;
    private String[] PhotoStr;
    private String ACPrice;
    private String RefuseNum;
    private String RefuseReason;
    private String Remark;
    private double MoneyNum;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFPCode() {
        return FPCode;
    }

    public void setFPCode(String FPCode) {
        this.FPCode = FPCode;
    }

    public String getExtend3() {
        return Extend3;
    }

    public void setExtend3(String extend3) {
        Extend3 = extend3;
    }

    public Integer getCid() {
        return Cid;
    }

    public void setCid(Integer cid) {
        Cid = cid;
    }

    public String getCivilName() {
        return CivilName;
    }

    public void setCivilName(String civilName) {
        CivilName = civilName;
    }

    public String getCivilUnits() {
        return CivilUnits;
    }

    public void setCivilUnits(String civilUnits) {
        CivilUnits = civilUnits;
    }

    public double getACNumInfo() {
        return ACNumInfo;
    }

    public void setACNumInfo(double ACNumInfo) {
        this.ACNumInfo = ACNumInfo;
    }

    public String getCivilSpecification() {
        return CivilSpecification;
    }

    public void setCivilSpecification(String civilSpecification) {
        CivilSpecification = civilSpecification;
    }

    public String[] getPhotoStr() {
        return PhotoStr;
    }

    public void setPhotoStr(String[] photoStr) {
        PhotoStr = photoStr;
    }

    public String getACPrice() {
        return ACPrice;
    }

    public void setACPrice(String ACPrice) {
        this.ACPrice = ACPrice;
    }

    public String getRefuseNum() {
        return RefuseNum;
    }

    public void setRefuseNum(String refuseNum) {
        RefuseNum = refuseNum;
    }

    public String getRefuseReason() {
        return RefuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        RefuseReason = refuseReason;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public double getMoneyNum() {
        return MoneyNum;
    }

    public void setMoneyNum(double moneyNum) {
        MoneyNum = moneyNum;
    }
}
