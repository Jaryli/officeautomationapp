package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class AcceptanceItem implements Serializable {
    public  AcceptanceItem(String hProjectType,Integer state){
        this.hProjectType=hProjectType;
        this.state=state;
    }
    private String hProjectType;
    private Integer state;

    public String gethProjectType() {
        return hProjectType;
    }

    public void sethProjectType(String hProjectType) {
        this.hProjectType = hProjectType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
