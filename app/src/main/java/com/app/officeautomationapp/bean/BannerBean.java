package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by yu on 2017/9/19.
 */

public class BannerBean implements Serializable {

    private Integer Id;
    private String BannerTitle;
    private String ImageUrl;
    private Integer IsDelete;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getBannerTitle() {
        return BannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        BannerTitle = bannerTitle;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public Integer getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Integer isDelete) {
        IsDelete = isDelete;
    }
}
