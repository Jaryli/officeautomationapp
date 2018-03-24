package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/15 0015.
 */

public class WzBean implements Serializable {

    private Integer Id; //明细记录Id
    private String ApplyMainCode;//关联单号
    private String TreeName;//品种名称
    private String Units;//单位
    private Double ACNumInfo;//采购数量
    private String ACXiongJing;//胸径
    private String ACHeight;//高度
    private String ACPengXing;//蓬型
    private Double YSNumInfo;//验收数量
    private Double CheckedNum;//已验收
    public Double getCheckedNum() {
        return CheckedNum;
    }
    public void setCheckedNum(Double checkedNum) {
        CheckedNum = checkedNum;
    }
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

    public Double getACNumInfo() {
        return ACNumInfo;
    }

    public void setACNumInfo(Double ACNumInfo) {
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

    public Double getYSNumInfo() {
        return YSNumInfo;
    }

    public void setYSNumInfo(Double YSNumInfo) {
        this.YSNumInfo = YSNumInfo;
    }
}
