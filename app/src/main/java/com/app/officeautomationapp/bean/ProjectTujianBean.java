package com.app.officeautomationapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class ProjectTujianBean implements Serializable {
    private String ProjectSite;
    private String Feeor;
    private Integer DeptId;
    private String SelfCode;
    private boolean IsDeletedInDatabase;
    private Integer IsPay;
    private String Extend1;
    private Integer ApplyType;
    private Integer EntityId;
    private String LinkMan;
    private String Extend2;
    private String Applyer;
    private String ApplyCode;
    private Integer StockId;
    private Integer Id;
    private String Remark;
    private Integer WorkId;
    private String ApplyDate;
    private Integer UserId;
    private String ProjectName;
    private Integer ProjectId;
    private String RQCode;
    private List<Detail> Details;

    public class Detail implements Serializable
    {
        private String ApplyCode;
        private String SName;
        private Integer Quantity;
        private String SupplyName;

        public String getApplyCode() {
            return ApplyCode;
        }

        public void setApplyCode(String applyCode) {
            ApplyCode = applyCode;
        }

        public String getSName() {
            return SName;
        }

        public void setSName(String SName) {
            this.SName = SName;
        }

        public Integer getQuantity() {
            return Quantity;
        }

        public void setQuantity(Integer quantity) {
            Quantity = quantity;
        }

        public String getSupplyName() {
            return SupplyName;
        }

        public void setSupplyName(String supplyName) {
            SupplyName = supplyName;
        }
    }


    public String getProjectSite() {
        return ProjectSite;
    }

    public void setProjectSite(String projectSite) {
        ProjectSite = projectSite;
    }

    public String getFeeor() {
        return Feeor;
    }

    public void setFeeor(String feeor) {
        Feeor = feeor;
    }

    public List<ProjectTujianBean.Detail> getDetails() {
        return Details;
    }

    public void setDetails(List<ProjectTujianBean.Detail> details) {
        Details = details;
    }

    public Integer getDeptId() {
        return DeptId;
    }

    public void setDeptId(Integer deptId) {
        DeptId = deptId;
    }

    public String getSelfCode() {
        return SelfCode;
    }

    public void setSelfCode(String selfCode) {
        SelfCode = selfCode;
    }

    public boolean isDeletedInDatabase() {
        return IsDeletedInDatabase;
    }

    public void setDeletedInDatabase(boolean deletedInDatabase) {
        IsDeletedInDatabase = deletedInDatabase;
    }

    public Integer getIsPay() {
        return IsPay;
    }

    public void setIsPay(Integer isPay) {
        IsPay = isPay;
    }

    public String getExtend1() {
        return Extend1;
    }

    public void setExtend1(String extend1) {
        Extend1 = extend1;
    }

    public Integer getApplyType() {
        return ApplyType;
    }

    public void setApplyType(Integer applyType) {
        ApplyType = applyType;
    }

    public Integer getEntityId() {
        return EntityId;
    }

    public void setEntityId(Integer entityId) {
        EntityId = entityId;
    }

    public String getLinkMan() {
        return LinkMan;
    }

    public void setLinkMan(String linkMan) {
        LinkMan = linkMan;
    }

    public String getExtend2() {
        return Extend2;
    }

    public void setExtend2(String extend2) {
        Extend2 = extend2;
    }

    public String getApplyer() {
        return Applyer;
    }

    public void setApplyer(String applyer) {
        Applyer = applyer;
    }

    public String getApplyCode() {
        return ApplyCode;
    }

    public void setApplyCode(String applyCode) {
        ApplyCode = applyCode;
    }

    public Integer getStockId() {
        return StockId;
    }

    public void setStockId(Integer stockId) {
        StockId = stockId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public Integer getWorkId() {
        return WorkId;
    }

    public void setWorkId(Integer workId) {
        WorkId = workId;
    }

    public String getApplyDate() {
        return ApplyDate;
    }

    public void setApplyDate(String applyDate) {
        ApplyDate = applyDate;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public Integer getProjectId() {
        return ProjectId;
    }

    public void setProjectId(Integer projectId) {
        ProjectId = projectId;
    }

    public String getRQCode() {
        return RQCode;
    }

    public void setRQCode(String RQCode) {
        this.RQCode = RQCode;
    }


}
