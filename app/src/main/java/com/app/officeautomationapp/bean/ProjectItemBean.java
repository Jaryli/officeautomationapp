package com.app.officeautomationapp.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by CS-711701-00027 on 2017/7/14.
 */

public class ProjectItemBean implements Serializable {

    private int Id;
    private String MenuTitle;
    private int MenuType;
    private String MenuImage;
    private String MenuImageStr;
    private int MenuStatus;
    private int MenuCate;
    private int MenuSort;
    private int IsIndex;

    private Drawable localPic;//本地图片
    private int num;//数量

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMenuTitle() {
        return MenuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        MenuTitle = menuTitle;
    }

    public int getMenuType() {
        return MenuType;
    }

    public void setMenuType(int menuType) {
        MenuType = menuType;
    }

    public String getMenuImage() {
        return MenuImage;
    }

    public void setMenuImage(String menuImage) {
        MenuImage = menuImage;
    }

    public String getMenuImageStr() {
        return MenuImageStr;
    }

    public void setMenuImageStr(String menuImageStr) {
        MenuImageStr = menuImageStr;
    }

    public int getMenuStatus() {
        return MenuStatus;
    }

    public void setMenuStatus(int menuStatus) {
        MenuStatus = menuStatus;
    }

    public int getMenuCate() {
        return MenuCate;
    }

    public void setMenuCate(int menuCate) {
        MenuCate = menuCate;
    }

    public int getMenuSort() {
        return MenuSort;
    }

    public void setMenuSort(int menuSort) {
        MenuSort = menuSort;
    }

    public int getIsIndex() {
        return IsIndex;
    }

    public void setIsIndex(int isIndex) {
        IsIndex = isIndex;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Drawable getLocalPic() {
        return localPic;
    }

    public void setLocalPic(Drawable localPic) {
        this.localPic = localPic;
    }
}
