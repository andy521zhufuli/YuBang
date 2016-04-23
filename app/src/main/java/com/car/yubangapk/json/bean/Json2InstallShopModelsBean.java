package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

import java.util.List;

/**
 * 安装门店的shopModels {"shopModels":[],"isJson":true,"isReturnStr":false,"returnCode":-2,"returneMsg":"SUCCESS","message":"该区域无店铺"}
 *
 *
 * shopModels":[
 *          {"shopId":"10658811-ea3c-11e5-81df-28d244001fe5",
 *          "shopName":"宇邦旗舰店","distance":7654.0,"shopAddress":"广东广州番禺",
 *          "phoneNum":"13454678764","pathCode":"6",
 *          "photoName":"c162f805-1796-4ad1-8ead-4539ae795faf_LPP_1.jpg",
 *          "lon":113.32771,"lat":23.125221,
 *          "order":0},
 *
 *          {"shopId":"0513ba78-0ce8-4e00-b6c5-168d5544c4ef","shopName":"强哥维修","distance":7654.0,"shopAddress":"广东广州番禺",
 *          "phoneNum":"13454678764","pathCode":"6","photoName":"c162f805-1796-4ad1-8ead-4539ae795faf_LPP_1.jpg","order":1}],
 * "isJson":true,
 * "isReturnStr":false,
 * "returnCode":0,
 * "returneMsg":"SUCCESS",
 * "message":"查找店铺成功！"}
 */
public class Json2InstallShopModelsBean extends Base{

    private String          shopId;
    private String          shopName;
    private double          distance;
    private String          shopAddress;
    private String          phoneNum;
    private String          pathCode;
    private String          photoName;
    private double          lat;
    private double          lon;
    private String          assess;
    private String          orderNum;
    private String          star;
    private int             order;
    private int             own;

    public int getOwn() {
        return own;
    }

    public void setOwn(int own) {
        this.own = own;
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

    public String getOrderNum() {

        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getAssess() {

        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public double getLon() {

        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {

        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
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

    public String getShopAddress() {

        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public double getDistance() {

        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getShopName() {

        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {

        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
