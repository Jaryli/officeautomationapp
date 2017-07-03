package com.app.officeautomationapp.bean;

import java.io.Serializable;

/**
 * Created by yu on 2017/6/4.
 */

public class ThingBean  implements Serializable {
    private int ResId;//用户编号
    private String ResCode;//物品编号
    private String ResName;//物品名称
    private int CateId;//类别编号
    private String CateName;//类别名称
    private String SpecialModel;//规格
    private int StockNum;//库存数量
    private double PriceInfo	;//价格
    private String Units;//单位

    public int getResId() {
        return ResId;
    }

    public void setResId(int resId) {
        ResId = resId;
    }

    public String getResCode() {
        return ResCode;
    }

    public void setResCode(String resCode) {
        ResCode = resCode;
    }

    public String getResName() {
        return ResName;
    }

    public void setResName(String resName) {
        ResName = resName;
    }

    public int getCateId() {
        return CateId;
    }

    public void setCateId(int cateId) {
        CateId = cateId;
    }

    public String getCateName() {
        return CateName;
    }

    public void setCateName(String cateName) {
        CateName = cateName;
    }

    public String getSpecialModel() {
        return SpecialModel;
    }

    public void setSpecialModel(String specialModel) {
        SpecialModel = specialModel;
    }

    public int getStockNum() {
        return StockNum;
    }

    public void setStockNum(int stockNum) {
        StockNum = stockNum;
    }

    public double getPriceInfo() {
        return PriceInfo;
    }

    public void setPriceInfo(double priceInfo) {
        PriceInfo = priceInfo;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String units) {
        Units = units;
    }
}
