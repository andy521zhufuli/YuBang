package com.car.yubangapk.js;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.car.yubangapk.utils.toastMgr;


/**
 * GoodsListjs类：商品列表界面 与 JS 交互 类
 *
 * @author andy
 * @version 1.0
 * @created 2015-08-1
 */
public class GoodsListjs {
    private Context mContext;

    public GoodsListjs()
    {

    }

    public GoodsListjs(Context context)
    {
        this.mContext = context;
    }
    //需不需要判断一下是不是4.2以及以上的版本
    //TODO  在小米上测试一下   如果可以  就不用判断
    @JavascriptInterface
    public void TestJs(String msg)
    {
        toastMgr.builder.display("测试js" + msg, 0);
    }
    @JavascriptInterface
    public void goodsListItemClick(String goodId)
    {
        toastMgr.builder.display("服务端调用客户端--goodsItemClick-->goodsDetail  "  + goodId, 0);
        Intent intent = new Intent();
        Bundle bundle =new Bundle();
        bundle.putString("goodId", goodId);
//        intent.setClass(mContext, GoodsDetailActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @JavascriptInterface
    public void loadFinished()
    {
        toastMgr.builder.display("loadFinished called", 0);
//        GoodsListActivity.isLoadMoreFinished = true;
    }



    /**
     * 以下是在goodsDetailWebview中的js交互
     */
    // 分享
    public void goodsDetailShareClick()
    {

    }
    // 购物车 点击进入购物车界面   展示购物车里面有什么内容
    public void goodsDetailShoppingCarClick()
    {

    }
    // 加入购物车
    public void goodsDetailAddToShoppingCarClick()
    {

    }


}
