package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by yu on 2017/8/6.
 */

public class ApprovalBean  implements Serializable {
    private Integer AFH_Id;
    private String AFW_Name;
    private String AFF_Name;
    private String AFW_FlowGuid;
    private String LastStepInfo;
    private String CreateTime;
    private String FlowType;
    private String FlowShortName;

    public Integer getAFH_Id() {
        return AFH_Id;
    }

    public void setAFH_Id(Integer AFH_Id) {
        this.AFH_Id = AFH_Id;
    }

    public String getAFW_Name() {
        return AFW_Name;
    }

    public void setAFW_Name(String AFW_Name) {
        this.AFW_Name = AFW_Name;
    }

    public String getAFF_Name() {
        return AFF_Name;
    }

    public void setAFF_Name(String AFF_Name) {
        this.AFF_Name = AFF_Name;
    }

    public String getAFW_FlowGuid() {
        return AFW_FlowGuid;
    }

    public void setAFW_FlowGuid(String AFW_FlowGuid) {
        this.AFW_FlowGuid = AFW_FlowGuid;
    }

    public String getLastStepInfo() {
        return LastStepInfo;
    }

    public void setLastStepInfo(String lastStepInfo) {
        LastStepInfo = lastStepInfo;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getFlowType() {
        return FlowType;
    }

    public void setFlowType(String flowType) {
        FlowType = flowType;
    }

    public String getFlowShortName() {
        return FlowShortName;
    }

    public void setFlowShortName(String flowShortName) {
        FlowShortName = flowShortName;
    }
}
