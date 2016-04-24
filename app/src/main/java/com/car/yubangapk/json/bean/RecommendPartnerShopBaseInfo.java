package com.car.yubangapk.json.bean;

import java.io.Serializable;

/**
 * Created by andy on 16/4/24.
 *
 * 推荐合伙人  店铺基本信息保存
 *
 */
public class RecommendPartnerShopBaseInfo implements Serializable
{
    String userid;
    String contactName;
    String contactPhone;
    double lon;
    double lat;
    String province;
    String city;
    String district;
    String address;
    String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {

        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {

        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getLat() {

        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {

        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getContactPhone() {

        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactName() {

        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getUserid() {

        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
