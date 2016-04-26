package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Created by andy on 16/4/26.
 */
public class MyRecommendPartnersBean extends Base
{
    //{"total":53,"rows":[{id ,shopName,shopAddress,shopPhoto,pathCode,phoneNum}]}无shopPhoto显示yubang图标，无其他字段丢弃

    String id;
    String shopName;
    String shopAddress;
    String shopPhoto;
    String pathCode;
    String phoneNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhoto() {
        return shopPhoto;
    }

    public void setShopPhoto(String shopPhoto) {
        this.shopPhoto = shopPhoto;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
