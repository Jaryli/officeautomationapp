package com.app.officeautomationapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pc on 2017/12/23.
 */

public class MiaomuDetailBean implements Serializable {
    private Integer Id;
    private String ApplyMainCode;
    private String TQThickness;
    private String TreeName;
    private String Units;
    private String TreeSpecification;
    private double ACNumInfo;
    private String ACXiongJing;
    private String ACHeight;
    private String ACPengXing;
    private String ACXiuJianReq;
    private double YSNumInfo;
    private String[] PhotoStr;
    private String ACPriceInfo;
    private String Remark;
    private String RefuseNum;
    private String RefuseReason;
    private double MoneyNum;

    public List<MiaomuDetailImagesBean> fileList;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getApplyMainCode() {
        return ApplyMainCode;
    }

    public void setApplyMainCode(String applyMainCode) {
        ApplyMainCode = applyMainCode;
    }

    public String getTQThickness() {
        return TQThickness;
    }

    public void setTQThickness(String TQThickness) {
        this.TQThickness = TQThickness;
    }

    public String getTreeName() {
        return TreeName;
    }

    public void setTreeName(String treeName) {
        TreeName = treeName;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String units) {
        Units = units;
    }

    public String getTreeSpecification() {
        return TreeSpecification;
    }

    public void setTreeSpecification(String treeSpecification) {
        TreeSpecification = treeSpecification;
    }

    public double getACNumInfo() {
        return ACNumInfo;
    }

    public void setACNumInfo(double ACNumInfo) {
        this.ACNumInfo = ACNumInfo;
    }

    public String getACXiongJing() {
        return ACXiongJing;
    }

    public void setACXiongJing(String ACXiongJing) {
        this.ACXiongJing = ACXiongJing;
    }

    public String getACHeight() {
        return ACHeight;
    }

    public void setACHeight(String ACHeight) {
        this.ACHeight = ACHeight;
    }

    public String getACPengXing() {
        return ACPengXing;
    }

    public void setACPengXing(String ACPengXing) {
        this.ACPengXing = ACPengXing;
    }

    public String getACXiuJianReq() {
        return ACXiuJianReq;
    }

    public void setACXiuJianReq(String ACXiuJianReq) {
        this.ACXiuJianReq = ACXiuJianReq;
    }

    public double getYSNumInfo() {
        return YSNumInfo;
    }

    public void setYSNumInfo(double YSNumInfo) {
        this.YSNumInfo = YSNumInfo;
    }

    public String[] getPhotoStr() {
        return PhotoStr;
    }

    public void setPhotoStr(String[] photoStr) {
        PhotoStr = photoStr;
    }

    public String getACPriceInfo() {
        return ACPriceInfo;
    }

    public void setACPriceInfo(String ACPriceInfo) {
        this.ACPriceInfo = ACPriceInfo;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
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

    public double getMoneyNum() {
        return MoneyNum;
    }

    public void setMoneyNum(double moneyNum) {
        MoneyNum = moneyNum;
    }
}
