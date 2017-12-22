package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by pc on 2017/12/22.
 */

public class MiaomuBean implements Serializable {

    private String ACXiongJing;
    private String TQThickness;
    private String ACPengXing;
    private Integer Id;
    private String ACHeight;
    private String ApplyMainCode;
    private Integer YSNumInfo;
    private String[] PhotoStr;
    private String PhotoUrl;
    private String Units;
    private String[] fileList;
    private String TreeName;
    private Integer ACNumInfo;

    public String getACXiongJing() {
        return ACXiongJing;
    }

    public void setACXiongJing(String ACXiongJing) {
        this.ACXiongJing = ACXiongJing;
    }

    public String getTQThickness() {
        return TQThickness;
    }

    public void setTQThickness(String TQThickness) {
        this.TQThickness = TQThickness;
    }

    public String getACPengXing() {
        return ACPengXing;
    }

    public void setACPengXing(String ACPengXing) {
        this.ACPengXing = ACPengXing;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getACHeight() {
        return ACHeight;
    }

    public void setACHeight(String ACHeight) {
        this.ACHeight = ACHeight;
    }

    public String getApplyMainCode() {
        return ApplyMainCode;
    }

    public void setApplyMainCode(String applyMainCode) {
        ApplyMainCode = applyMainCode;
    }

    public Integer getYSNumInfo() {
        return YSNumInfo;
    }

    public void setYSNumInfo(Integer YSNumInfo) {
        this.YSNumInfo = YSNumInfo;
    }

    public String[] getPhotoStr() {
        return PhotoStr;
    }

    public void setPhotoStr(String[] photoStr) {
        PhotoStr = photoStr;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String units) {
        Units = units;
    }

    public String[] getFileList() {
        return fileList;
    }

    public void setFileList(String[] fileList) {
        this.fileList = fileList;
    }

    public String getTreeName() {
        return TreeName;
    }

    public void setTreeName(String treeName) {
        TreeName = treeName;
    }

    public Integer getACNumInfo() {
        return ACNumInfo;
    }

    public void setACNumInfo(Integer ACNumInfo) {
        this.ACNumInfo = ACNumInfo;
    }
}
