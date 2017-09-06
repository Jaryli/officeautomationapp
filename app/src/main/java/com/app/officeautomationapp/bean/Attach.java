package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by yu on 2017/9/6.
 */

public class Attach implements Serializable{
    private String AttachType;
    private String AttachUrl;
    private String AttachName;

    public String getAttachType() {
        return AttachType;
    }

    public void setAttachType(String attachType) {
        AttachType = attachType;
    }

    public String getAttachUrl() {
        return AttachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        AttachUrl = attachUrl;
    }

    public String getAttachName() {
        return AttachName;
    }

    public void setAttachName(String attachName) {
        AttachName = attachName;
    }
}
