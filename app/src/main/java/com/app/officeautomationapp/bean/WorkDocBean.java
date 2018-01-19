package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class WorkDocBean implements Serializable {

    private String FileName;//，文件名，string
    private String DocGuid;//：流程guid
    private Integer workId;//：工作编号
    private String FileType;//：文件类型（后缀）
    private String FileUrl;//：文件地址

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getDocGuid() {
        return DocGuid;
    }

    public void setDocGuid(String docGuid) {
        DocGuid = docGuid;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String fileType) {
        FileType = fileType;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }
}
