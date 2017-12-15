package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/15 0015.
 */

public class SupplyBean implements Serializable {
    private String SupplyType;
    private Integer Id;
    private String SupplyState;
    private Integer EntityId;
    private boolean IsDeletedInDatabase;
    private String SupplyRemark;
    private String SupplyName;

    public String getSupplyType() {
        return SupplyType;
    }

    public void setSupplyType(String supplyType) {
        SupplyType = supplyType;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getSupplyState() {
        return SupplyState;
    }

    public void setSupplyState(String supplyState) {
        SupplyState = supplyState;
    }

    public Integer getEntityId() {
        return EntityId;
    }

    public void setEntityId(Integer entityId) {
        EntityId = entityId;
    }

    public boolean isDeletedInDatabase() {
        return IsDeletedInDatabase;
    }

    public void setDeletedInDatabase(boolean deletedInDatabase) {
        IsDeletedInDatabase = deletedInDatabase;
    }

    public String getSupplyRemark() {
        return SupplyRemark;
    }

    public void setSupplyRemark(String supplyRemark) {
        SupplyRemark = supplyRemark;
    }

    public String getSupplyName() {
        return SupplyName;
    }

    public void setSupplyName(String supplyName) {
        SupplyName = supplyName;
    }
}
