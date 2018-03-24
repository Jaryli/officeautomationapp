package com.app.officeautomationapp.bean;

import java.io.Serializable;
import java.util.List;

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
    private List<file> fileList;
    private String TreeName;
    private Integer ACNumInfo;
    private Integer CheckedNum;

    public Integer getCheckedNum() {
        return CheckedNum;
    }

    public void setCheckedNum(Integer checkedNum) {
        CheckedNum = checkedNum;
    }

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

    public List<file> getFileList() {
        return fileList;
    }

    public void setFileList(List<file> fileList) {
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
