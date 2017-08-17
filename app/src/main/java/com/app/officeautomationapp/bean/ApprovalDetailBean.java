package com.app.officeautomationapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yu on 2017/8/17.
 */

public class ApprovalDetailBean implements Serializable{
    private Integer WorkId;
    private String WorkName;
    private String FlowGuid;
    private String FormView;
    private List<FlowHistorie> FlowHistories;
    private List<NextStep> NextSteps;
    private String[] ImageUrlList;

    public Integer getWorkId() {
        return WorkId;
    }

    public void setWorkId(Integer workId) {
        WorkId = workId;
    }

    public String getWorkName() {
        return WorkName;
    }

    public void setWorkName(String workName) {
        WorkName = workName;
    }

    public String getFlowGuid() {
        return FlowGuid;
    }

    public void setFlowGuid(String flowGuid) {
        FlowGuid = flowGuid;
    }

    public String getFormView() {
        return FormView;
    }

    public void setFormView(String formView) {
        FormView = formView;
    }

    public List<FlowHistorie> getFlowHistories() {
        return FlowHistories;
    }

    public void setFlowHistories(List<FlowHistorie> flowHistories) {
        FlowHistories = flowHistories;
    }

    public List<NextStep> getNextSteps() {
        return NextSteps;
    }

    public void setNextSteps(List<NextStep> nextSteps) {
        NextSteps = nextSteps;
    }

    public String[] getImageUrlList() {
        return ImageUrlList;
    }

    public void setImageUrlList(String[] imageUrlList) {
        ImageUrlList = imageUrlList;
    }
}
