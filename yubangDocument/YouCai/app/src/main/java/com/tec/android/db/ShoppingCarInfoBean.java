package com.tec.android.db;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by andy on 15/8/4.
 */
public class ShoppingCarInfoBean implements Serializable{

    private int goodsId;
    private String goodsJson;
    private int googsCount;



    public int getGoogsCount() {
        return googsCount;
    }

    public void setGoogsCount(int googsCount) {
        this.googsCount = googsCount;
    }



    public String getGoodsJson() {
        return goodsJson;
    }

    public void setGoodsJson(String goodsJson) {
        this.goodsJson = goodsJson;
    }



    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsId() {
        return goodsId;
    }


}
