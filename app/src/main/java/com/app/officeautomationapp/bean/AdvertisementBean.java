package com.app.officeautomationapp.bean;

/**
 * Created by CS-711701-00027 on 2017/3/24.
 */

public class AdvertisementBean {
    private int index;
    private String imageUrl;

    public AdvertisementBean(int index,String imageUrl)
    {
        this.index=index;
        this.imageUrl=imageUrl;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
