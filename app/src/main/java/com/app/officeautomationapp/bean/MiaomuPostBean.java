package com.app.officeautomationapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class MiaomuPostBean implements Serializable {
    private String ysHeight;//验收高度
    private Integer Id;
    private String ysXiongJing;//验收胸径
    private String arriveDate;//到场时间
    private String[] imagedata;
    private String applyCode;//
    private String xiuJianReq;//修剪要求
    private Integer isPay;//是否付款
    private Integer numInfo;//验收数量
    private String raoGanReq;//绕干要求
    private String supplyName;//供应商
    private String ysPengXing;//验收蓬茎
    private List<MiaomuPostBean.Pic> piclist;

    public static class Pic implements Serializable{
        double lon;
        double lati;
        String pic;

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLati() {
            return lati;
        }

        public void setLati(double lati) {
            this.lati = lati;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

    public List<Pic> getPiclist() {
        return piclist;
    }

    public void setPiclist(List<Pic> piclist) {
        this.piclist = piclist;
    }

    public String getYsHeight() {
        return ysHeight;
    }

    public void setYsHeight(String ysHeight) {
        this.ysHeight = ysHeight;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getYsXiongJing() {
        return ysXiongJing;
    }

    public void setYsXiongJing(String ysXiongJing) {
        this.ysXiongJing = ysXiongJing;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String[] getImagedata() {
        return imagedata;
    }

    public void setImagedata(String[] imagedata) {
        this.imagedata = imagedata;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public String getXiuJianReq() {
        return xiuJianReq;
    }

    public void setXiuJianReq(String xiuJianReq) {
        this.xiuJianReq = xiuJianReq;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Integer getNumInfo() {
        return numInfo;
    }

    public void setNumInfo(Integer numInfo) {
        this.numInfo = numInfo;
    }

    public String getRaoGanReq() {
        return raoGanReq;
    }

    public void setRaoGanReq(String raoGanReq) {
        this.raoGanReq = raoGanReq;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getYsPengXing() {
        return ysPengXing;
    }

    public void setYsPengXing(String ysPengXing) {
        this.ysPengXing = ysPengXing;
    }
}
