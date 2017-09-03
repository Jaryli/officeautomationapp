package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by CS-711701-00027 on 2017/7/21.
 */

public class SortModel implements Serializable{
    private int id;
    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母
    private String phone;
    private String s_phone;
    private String qq;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getS_phone() {
        return s_phone;
    }

    public void setS_phone(String s_phone) {
        this.s_phone = s_phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
