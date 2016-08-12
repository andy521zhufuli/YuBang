package com.tec.android.network;

import android.content.Context;

import com.sales.vo.LoadGoodsDetailReq;
import com.sales.vo.base.SalesBaseMsg;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * 加载商品详情的http类
 *
 * 说明: 请求后台, 拿到商品详情的页面html, 然后在webview里面加载
 *
 * @author andyzhu
 * @data   2015-8-3
 */
public class LoadGoodsDetailHttp extends WebViewLoadConfigBase{
    private Context mContext;

    public LoadGoodsDetailHttp()
    {}

    public LoadGoodsDetailHttp(Context context)
    {

    }

    /**
     * sendAndRecv: 通过商品id  获取商品展示的html代码  调用成功, 回调success   到详情页去展示商品
     *
     * @param goodsId 商品id
     * @param successCallback 成功拿到html 成功回调
     * @param failedCallback 失败回调
     * @return
     */
    public SalesBaseMsg sendAndRecv(String goodsId, final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        final LoadGoodsDetailReq loadGoodsDetailReq = new LoadGoodsDetailReq();
        String reqJson = ReqSetProperties.setLoadGoodsDetailReqProperties(loadGoodsDetailReq, goodsId, "html");

        L.i("LoadGoodsDetailReq, json = " + reqJson);
        L.i("LoadGoodsDetailReq ip address = " + Configs.SERVER_IP_ADDRESS + loadGoodsDetailReq.getMsgtype());

        NetConnectionReq netConnectionReq = new NetConnectionReq(Configs.SERVER_IP_ADDRESS + loadGoodsDetailReq.getMsgtype(),
                new NetConnectionReq.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        if (successCallback != null)
                        {
                            successCallback.onSuccess(result);
                        }
                    }
                },
                new NetConnectionReq.FailCallback() {
                    @Override
                    public void onFail(String error) {
                        if (failedCallback != null)
                        {
                            failedCallback.onFail(error);
                        }
                    }
                },
                reqJson
        );

        return null;
    }

    /**
     * sendAndGetGoodsDetailJson: 通过商品id  获取商品详细信息的json数据  调用成功, 回调success
     *
     * @param goodsId 商品id
     * @param successCallback 成功拿到json 成功回调
     * @param failedCallback 失败回调
     * @return
     */
    public String sendAndGetGoodsDetailJson(String goodsId, final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        final LoadGoodsDetailReq loadGoodsDetailReq = new LoadGoodsDetailReq();
        loadGoodsDetailReq.setSeq("123");
        loadGoodsDetailReq.setWidth(10);
        loadGoodsDetailReq.setHeight(10);
        loadGoodsDetailReq.setApptype("test");
        loadGoodsDetailReq.setAppversion("1.0");
        loadGoodsDetailReq.setDeviceid("d");
        loadGoodsDetailReq.setOs("test");
        loadGoodsDetailReq.setFormat("json");
        loadGoodsDetailReq.setGoodsid(goodsId);
        String json = SalesMsgUtils.toJson(loadGoodsDetailReq);

        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + loadGoodsDetailReq.getMsgtype(),
                new NetConnectionReq.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        //成功, 返回json数据
                        if (successCallback != null)
                        {
                            successCallback.onSuccess(result);
                        }
                    }
                },
                new NetConnectionReq.FailCallback() {
                    @Override
                    public void onFail(String error) {
                        if (failedCallback != null)
                        {
                            failedCallback.onFail(error);
                        }
                    }
                },
                json
        );

        return null;
    }





    /**
     *执行成功的时候回调
     */
    public interface DoingSuccessCallback
    {
        /**
         *
         * @param result 成功的时候, 返回的应该是html
         */
        void onSuccess(String result);
    }

    /**
     * 执行失败时候的回调
     */
    public interface DoingFailedCallback
    {
        /**
         *
         * @param resultMsg 失败的信息, 或者是失败码
         */
        void onFail(String resultMsg);
    }

}
