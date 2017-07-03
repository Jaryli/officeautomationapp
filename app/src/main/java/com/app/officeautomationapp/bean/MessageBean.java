package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by CS-711701-00027 on 2017/5/3.
 */

public class MessageBean implements Serializable {

    private int NT_Id;//id
    private int NT_UserId;

    private int NT_Type;
    private String NT_OrgId;
    private String NT_DeptName;//部门
    private String NT_DeptId;
    private String NT_Title;//标题
    private String NT_Date;//时间
    private String NT_StartDate;
    private String NT_EndDate;
    private String NT_Content;
    private int NT_Remind;
    private int NT_RemindId;
    private int IsRead;

    public int getNT_Id() {
        return NT_Id;
    }

    public void setNT_Id(int NT_Id) {
        this.NT_Id = NT_Id;
    }

    public int getNT_UserId() {
        return NT_UserId;
    }

    public void setNT_UserId(int NT_UserId) {
        this.NT_UserId = NT_UserId;
    }

    public int getNT_Type() {
        return NT_Type;
    }

    public void setNT_Type(int NT_Type) {
        this.NT_Type = NT_Type;
    }

    public String getNT_OrgId() {
        return NT_OrgId;
    }

    public void setNT_OrgId(String NT_OrgId) {
        this.NT_OrgId = NT_OrgId;
    }

    public String getNT_DeptName() {
        return NT_DeptName;
    }

    public void setNT_DeptName(String NT_DeptName) {
        this.NT_DeptName = NT_DeptName;
    }

    public String getNT_DeptId() {
        return NT_DeptId;
    }

    public void setNT_DeptId(String NT_DeptId) {
        this.NT_DeptId = NT_DeptId;
    }

    public String getNT_Title() {
        return NT_Title;
    }

    public void setNT_Title(String NT_Title) {
        this.NT_Title = NT_Title;
    }

    public String getNT_Date() {
        return NT_Date;
    }

    public void setNT_Date(String NT_Date) {
        this.NT_Date = NT_Date;
    }

    public String getNT_StartDate() {
        return NT_StartDate;
    }

    public void setNT_StartDate(String NT_StartDate) {
        this.NT_StartDate = NT_StartDate;
    }

    public String getNT_EndDate() {
        return NT_EndDate;
    }

    public void setNT_EndDate(String NT_EndDate) {
        this.NT_EndDate = NT_EndDate;
    }

    public String getNT_Content() {
        return NT_Content;
    }

    public void setNT_Content(String NT_Content) {
        this.NT_Content = NT_Content;
    }

    public int getNT_Remind() {
        return NT_Remind;
    }

    public void setNT_Remind(int NT_Remind) {
        this.NT_Remind = NT_Remind;
    }

    public int getNT_RemindId() {
        return NT_RemindId;
    }

    public void setNT_RemindId(int NT_RemindId) {
        this.NT_RemindId = NT_RemindId;
    }

    public int getIsRead() {
        return IsRead;
    }

    public void setIsRead(int isRead) {
        IsRead = isRead;
    }

    //暂时没有下面的
//            "Dbvalue":{
//        "Keys":[],
//        "Values":[],
//        "Count":0},
//            "IsDeletedInDatabase":false,
//            "EntityId":0
//    public MessageBean(int notieId,String title,String type,String createTime)
//    {
//        this.notieId=notieId;
//        this.title=title;
//        this.type=type;
//        this.createTime=createTime;
//    }
//    private int notieId;
//    private String title;
//    private String type;
//    private String createTime;
//
//    public int getNotieId() {
//        return notieId;
//    }
//
//    public void setNotieId(int notieId) {
//        this.notieId = notieId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(String createTime) {
//        this.createTime = createTime;
//    }
}
