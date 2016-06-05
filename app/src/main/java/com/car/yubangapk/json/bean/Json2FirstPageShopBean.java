package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Json2FirstPageShopBean:店铺信息 bean
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-05
 */
public class Json2FirstPageShopBean extends Base
{
    //{"total":0,"rows":[
    // {"shopLatitude":23.125221,
    // "id":"0513ba78-0ce8-4e00-b6c5-168d5544c4ef",
    // "shopAddress":"广东广州番禺",
    // "phoneNum":"13454678764",
    // "pathCode":"6",
    // "shopPhoto":"c162f805-1796-4ad1-8ead-4539ae795faf_LPP_1.jpg",

    // "shopName":"强哥维修",
    // "company":"30fd0183-ea3a-11e5-81df-28d244001fe5",
    // "star":"5",
    // "shopLongitude":113.32771}]}
    // "distance":7661.0,
    // "order":1,
    String id;
    String shopAddress;
    String phoneNum;
    String shopPhoto;
    String pathCode;
    String shopName;
    String company;
    double shopLatitude;
    double shopLongitude;
    String star;
    int order;
    long orderNum;
    double distance;



    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public double getDistance() {

        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public double getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(double shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    public double getShopLatitude() {

        return shopLatitude;
    }

    public void setShopLatitude(double shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getShopName() {

        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPathCode() {

        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public String getShopPhoto() {

        return shopPhoto;
    }

    public void setShopPhoto(String shopPhoto) {
        this.shopPhoto = shopPhoto;
    }

    public String getPhoneNum() {

        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getShopAddress() {

        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
