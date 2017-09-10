package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by yu on 2017/9/10.
 */

public class UserInfoBean implements Serializable {

    private Integer UserId;
    private Integer UserOrgId;
    private Integer UserDept;
    private String UserName;
    private String UserPwd;
    private String UserTrueName;
    private String UserSex;
    private String UserDuty;
    private String UserRole;
    private Integer UserSort;
    private String LoginTime;
    private String LoginIP;
    private Integer LoginState;
    private Integer UserLock;
    private Integer UserDelete;
    private String UserMobile;
    private String Sub_Dept;
    private String PhotoUrl;
    private String DeptName;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getUserOrgId() {
        return UserOrgId;
    }

    public void setUserOrgId(Integer userOrgId) {
        UserOrgId = userOrgId;
    }

    public Integer getUserDept() {
        return UserDept;
    }

    public void setUserDept(Integer userDept) {
        UserDept = userDept;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPwd() {
        return UserPwd;
    }

    public void setUserPwd(String userPwd) {
        UserPwd = userPwd;
    }

    public String getUserTrueName() {
        return UserTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        UserTrueName = userTrueName;
    }

    public String getUserSex() {
        return UserSex;
    }

    public void setUserSex(String userSex) {
        UserSex = userSex;
    }

    public String getUserDuty() {
        return UserDuty;
    }

    public void setUserDuty(String userDuty) {
        UserDuty = userDuty;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }

    public Integer getUserSort() {
        return UserSort;
    }

    public void setUserSort(Integer userSort) {
        UserSort = userSort;
    }

    public String getLoginTime() {
        return LoginTime;
    }

    public void setLoginTime(String loginTime) {
        LoginTime = loginTime;
    }

    public String getLoginIP() {
        return LoginIP;
    }

    public void setLoginIP(String loginIP) {
        LoginIP = loginIP;
    }

    public Integer getLoginState() {
        return LoginState;
    }

    public void setLoginState(Integer loginState) {
        LoginState = loginState;
    }

    public Integer getUserLock() {
        return UserLock;
    }

    public void setUserLock(Integer userLock) {
        UserLock = userLock;
    }

    public Integer getUserDelete() {
        return UserDelete;
    }

    public void setUserDelete(Integer userDelete) {
        UserDelete = userDelete;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String userMobile) {
        UserMobile = userMobile;
    }

    public String getSub_Dept() {
        return Sub_Dept;
    }

    public void setSub_Dept(String sub_Dept) {
        Sub_Dept = sub_Dept;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }
}
