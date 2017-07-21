package com.app.officeautomationapp.bean;

/**
 * Created by CS-711701-00027 on 2017/7/21.
 */

public class PersonBean {

    private int UserId;
    private String UserTrueName;
    private String Mobile;
    private String ShortPhone;
    private String QQ;
    private String PhotoUrl;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserTrueName() {
        return UserTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        UserTrueName = userTrueName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getShortPhone() {
        return ShortPhone;
    }

    public void setShortPhone(String shortPhone) {
        ShortPhone = shortPhone;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }
}
