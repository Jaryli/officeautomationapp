package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by pc on 2017/12/24.
 */

public class TujianDetailPostBean  implements Serializable {

    private Integer Id;
    private Integer Quantity;
    private double Price;
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
