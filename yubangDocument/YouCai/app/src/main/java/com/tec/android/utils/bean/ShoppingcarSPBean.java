package com.tec.android.utils.bean;

import java.util.List;

/**
 * Created by andy on 15/8/14.
 */
public class ShoppingcarSPBean {
    private List<GoodsInShoppingCarBean> goodsInShoppingCarBeans;

    public List<GoodsInShoppingCarBean> getGoodsInShoppingCarBeans() {
        return goodsInShoppingCarBeans;
    }

    public void setGoodsInShoppingCarBeans(List<GoodsInShoppingCarBean> goodsInShoppingCarBeans) {
        this.goodsInShoppingCarBeans = goodsInShoppingCarBeans;
    }
}
