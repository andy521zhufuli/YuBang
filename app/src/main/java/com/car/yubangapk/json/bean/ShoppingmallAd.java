package com.car.yubangapk.json.bean;

/**
 * Created by andy on 16/3/29.
 *
 * 商城的广告  banner 和 每个分类下面的广告
 *
 */
public class ShoppingmallAd
{
    String pathCode;
    String sort;
    String advertisementName;

    String status;
    String link;

    String photoName;

    String skipType;//跳转类型  0，link为网页链接  1，link为产品包id

    //{advertisementName=“” , link=“” , sort=“” , status=“”, skipType=“”, photoName=“” , pathCode=“”}


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
