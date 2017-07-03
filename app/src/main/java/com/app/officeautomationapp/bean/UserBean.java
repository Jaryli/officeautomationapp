package com.app.officeautomationapp.bean;

/**
 * Created by CS-711701-00027 on 2017/4/28.
 * user单例
 */

public class UserBean {
    private UserBean() {
    }

    private static class UserBeanFactory {
        private static UserBean instance = new UserBean();
    }

    public static UserBean getInstance() {
        return UserBeanFactory.instance;
    }

    private int UserId;
    private String UserTrueName;
    private String DeptName;
    private int DeptId;
    private String PhotoUrl;
    private String AccessToken;

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

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public int getDeptId() {
        return DeptId;
    }

    public void setDeptId(int deptId) {
        DeptId = deptId;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }
}
