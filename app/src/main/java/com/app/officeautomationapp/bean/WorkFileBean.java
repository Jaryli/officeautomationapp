package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class WorkFileBean implements Serializable {
    private String FileName;
    private double Lon;
    private double Lati;
    private String FileImageStr;

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public double getLati() {
        return Lati;
    }

    public void setLati(double lati) {
        Lati = lati;
    }

    public String getFileImageStr() {
        return FileImageStr;
    }

    public void setFileImageStr(String fileImageStr) {
        FileImageStr = fileImageStr;
    }
}
