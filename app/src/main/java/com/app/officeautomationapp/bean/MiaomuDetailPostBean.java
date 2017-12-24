package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by pc on 2017/12/24.
 */

public class MiaomuDetailPostBean  implements Serializable {
    private Integer Id;
    private Integer Quantity;
    private double Price;
    private String XiongJing;
    private double YsHeight;
    private String PengXing;
    private String XiuJianReq;
    private String Special;
    private String Remark;
    private double RefuseNum;
    private String RefuseReason;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getXiongJing() {
        return XiongJing;
    }

    public void setXiongJing(String xiongJing) {
        XiongJing = xiongJing;
    }

    public double getYsHeight() {
        return YsHeight;
    }

    public void setYsHeight(double ysHeight) {
        YsHeight = ysHeight;
    }

    public String getPengXing() {
        return PengXing;
    }

    public void setPengXing(String pengXing) {
        PengXing = pengXing;
    }

    public String getXiuJianReq() {
        return XiuJianReq;
    }

    public void setXiuJianReq(String xiuJianReq) {
        XiuJianReq = xiuJianReq;
    }

    public String getSpecial() {
        return Special;
    }

    public void setSpecial(String special) {
        Special = special;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public double getRefuseNum() {
        return RefuseNum;
    }

    public void setRefuseNum(double refuseNum) {
        RefuseNum = refuseNum;
    }

    public String getRefuseReason() {
        return RefuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        RefuseReason = refuseReason;
    }
}
