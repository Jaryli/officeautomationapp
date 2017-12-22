package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by pc on 2017/12/23.
 */

public class TujianBean  implements Serializable {

    private String FPCode;
    private String CivilName;
    private Integer Id;
    private String CivilUnits;
    private Integer CivilNum;
    private String CivilSpecification;
    private String PhotoUrl;
    private String Extend3;
    private Integer Cid;
    private String[] PhotoStr;
    private String[] fileList;

    public String getFPCode() {
        return FPCode;
    }

    public void setFPCode(String FPCode) {
        this.FPCode = FPCode;
    }

    public String getCivilName() {
        return CivilName;
    }

    public void setCivilName(String civilName) {
        CivilName = civilName;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getCivilUnits() {
        return CivilUnits;
    }

    public void setCivilUnits(String civilUnits) {
        CivilUnits = civilUnits;
    }

    public Integer getCivilNum() {
        return CivilNum;
    }

    public void setCivilNum(Integer civilNum) {
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
