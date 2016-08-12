package com.tec.android.db;

import java.util.List;

/**
 * Created by andy on 15/8/4.
 */
public class DetailGoods {
    private List<String> imglist = null;
    private String secondarytitle;
    private String originalprice;
    private String discountprice;
    private String goodsid;
    private String imgurl;
    private String title;


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsid() {
        return goodsid;
    }


    public String getDiscountprice() {
        return discountprice;
    }

    public void setDiscountprice(String discountprice) {
        this.discountprice = discountprice;
    }

    public String getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(String originalprice) {
        this.originalprice = originalprice;
    }

    public void setSecondarytitle(String secondarytitle) {
        this.secondarytitle = secondarytitle;
    }

    public String getSecondarytitle() {
        return secondarytitle;
    }


    public List<String> getImglist() {
        return imglist;
    }

    public void setImglist(List<String> imglist) {
        this.imglist = imglist;
    }
}
