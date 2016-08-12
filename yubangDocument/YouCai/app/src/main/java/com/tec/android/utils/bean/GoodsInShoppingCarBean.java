package com.tec.android.utils.bean;

import com.sales.vo.LoadGoodsDetailResp;

/**
 * 购物车里面的商品   的一个bean类
 *
 *
 *
 */
public class GoodsInShoppingCarBean {

    private int num;//购物车商品的个数
    private LoadGoodsDetailResp loadGoodsDetailResp;//购物车商品详情
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public LoadGoodsDetailResp getLoadGoodsDetailResp() {
        return loadGoodsDetailResp;
    }

    public void setLoadGoodsDetailResp(LoadGoodsDetailResp loadGoodsDetailResp) {
        this.loadGoodsDetailResp = loadGoodsDetailResp;
    }




}
