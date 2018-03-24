package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/15 0015.
 */

public class WzTujianBean implements Serializable {

    private Integer Id; //明细记录Id
    private String FPCode;
    private String Extend3;
    private Integer Cid;
    private String CivilName;
    private String CivilUnits;
    private double CivilNum;
    private String CivilSpecification;
    private String PhotoUrl;
    private String[] PhotoStr;
    private String[] fileList;

    public double getCheckedNum() {
        return CheckedNum;
    }

    public void setCheckedNum(double checkedNum) {
        CheckedNum = checkedNum;
    }

    private double CheckedNum;
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

    public double getCivilNum() {
        return CivilNum;
    }

    public void setCivilNum(double civilNum) {
        CivilNum = civilNum;
    }

    public String getCivilSpecification() {
        return CivilSpecification;
    }

    public void setCivilSpecification(String civilSpecification) {
        CivilSpecification = civilSpecification;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String[] getPhotoStr() {
        return PhotoStr;
    }

    public void setPhotoStr(String[] photoStr) {
        PhotoStr = photoStr;
    }

    public String[] getFileList() {
        return fileList;
    }

    public void setFileList(String[] fileList) {
        this.fileList = fileList;
    }
}
