package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by CS-711701-00027 on 2017/9/15.
 */

public class UpdatePhotoPostBean implements Serializable {
    private String imageData;

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
