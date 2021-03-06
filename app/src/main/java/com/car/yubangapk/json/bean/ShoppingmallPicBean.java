package com.car.yubangapk.json.bean;

/**
 * ShoppingmallPicBean:商城图片json类 网络获取json 转成类 保存信息
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-02
 */
public class ShoppingmallPicBean {



    String pathCode;

    String sort;

    String advertisementName;

    String status;

    String link;

    String skipType;//跳转类型

    String photoName;

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdvertisementName() {

        return advertisementName;
    }

    public void setAdvertisementName(String advertisementName) {
        this.advertisementName = advertisementName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }


    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

}
