package com.app.officeautomationapp.bean;

import java.io.Serializable;
import java.util.List;

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
    private List<file> fileList;

    public Integer getCheckedNum() {
        return CheckedNum;
    }

    public void setCheckedNum(Integer checkedNum) {
        CheckedNum = checkedNum;
    }

    private Integer CheckedNum;
    class file  implements Serializable
    {
        private Integer FileId;
        private String FileName;
        private Integer UserId;
        private String Lon;
        private String Lati;
        private String Remark;
        private Integer TypeId;
        private Integer SourceId;
        private String CreateTime;
        private Integer IsDelete;
        private String FileImageStr;

        public Integer getFileId() {
            return FileId;
        }

        public void setFileId(Integer fileId) {
            FileId = fileId;
        }

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String fileName) {
            FileName = fileName;
        }

        public Integer getUserId() {
            return UserId;
        }

        public void setUserId(Integer userId) {
            UserId = userId;
        }

        public String getLon() {
            return Lon;
        }

        public void setLon(String lon) {
            Lon = lon;
        }

        public String getLati() {
            return Lati;
        }

        public void setLati(String lati) {
            Lati = lati;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public Integer getTypeId() {
            return TypeId;
        }

        public void setTypeId(Integer typeId) {
            TypeId = typeId;
        }

        public Integer getSourceId() {
            return SourceId;
        }

        public void setSourceId(Integer sourceId) {
            SourceId = sourceId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public Integer getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(Integer isDelete) {
            IsDelete = isDelete;
        }

        public String getFileImageStr() {
            return FileImageStr;
        }

        public void setFileImageStr(String fileImageStr) {
            FileImageStr = fileImageStr;
        }
    }


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

    public List<file> getFileList() {
        return fileList;
    }

    public void setFileList(List<file> fileList) {
        this.fileList = fileList;
    }
}
