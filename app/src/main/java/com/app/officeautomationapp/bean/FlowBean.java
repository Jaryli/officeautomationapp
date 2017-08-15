package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * 工作流分类
 * Created by CS-711701-00027 on 2017/8/15.
 */

public class FlowBean  implements Serializable{
    private Integer AFF_Id;
    private String AFF_Guid;
    private Integer AFF_Type;
    private String AFF_Name;
    private Integer AFF_Sort;
    private String AFF_FlowType;
    private String AFF_FormGuid;
    private String AFF_Doc;
    private Integer AFF_State;
    private String AFF_ExAdd;
    private String AFF_Dept;
    private String AFF_Static;

    public Integer getAFF_Id() {
        return AFF_Id;
    }

    public void setAFF_Id(Integer AFF_Id) {
        this.AFF_Id = AFF_Id;
    }

    public String getAFF_Guid() {
        return AFF_Guid;
    }

    public void setAFF_Guid(String AFF_Guid) {
        this.AFF_Guid = AFF_Guid;
    }

    public Integer getAFF_Type() {
        return AFF_Type;
    }

    public void setAFF_Type(Integer AFF_Type) {
        this.AFF_Type = AFF_Type;
    }

    public String getAFF_Name() {
        return AFF_Name;
    }

    public void setAFF_Name(String AFF_Name) {
        this.AFF_Name = AFF_Name;
    }

    public Integer getAFF_Sort() {
        return AFF_Sort;
    }

    public void setAFF_Sort(Integer AFF_Sort) {
        this.AFF_Sort = AFF_Sort;
    }

    public String getAFF_FlowType() {
        return AFF_FlowType;
    }

    public void setAFF_FlowType(String AFF_FlowType) {
        this.AFF_FlowType = AFF_FlowType;
    }

    public String getAFF_FormGuid() {
        return AFF_FormGuid;
    }

    public void setAFF_FormGuid(String AFF_FormGuid) {
        this.AFF_FormGuid = AFF_FormGuid;
    }

    public String getAFF_Doc() {
        return AFF_Doc;
    }

    public void setAFF_Doc(String AFF_Doc) {
        this.AFF_Doc = AFF_Doc;
    }

    public Integer getAFF_State() {
        return AFF_State;
    }

    public void setAFF_State(Integer AFF_State) {
        this.AFF_State = AFF_State;
    }

    public String getAFF_ExAdd() {
        return AFF_ExAdd;
    }

    public void setAFF_ExAdd(String AFF_ExAdd) {
        this.AFF_ExAdd = AFF_ExAdd;
    }

    public String getAFF_Dept() {
        return AFF_Dept;
    }

    public void setAFF_Dept(String AFF_Dept) {
        this.AFF_Dept = AFF_Dept;
    }

    public String getAFF_Static() {
        return AFF_Static;
    }

    public void setAFF_Static(String AFF_Static) {
        this.AFF_Static = AFF_Static;
    }
}
