package com.tec.android.network;


import android.content.Context;

import com.sales.vo.AppConfigResp;
import com.sales.vo.LoadGoodsListReq;
import com.sales.vo.base.SalesBaseMsg;
import com.sales.vo.base.SalesMsgUtils;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * 加载商品列表的http类
 *
 * 说明: 在GoodListActivity里调用 成功的时候 回调,  在GoodListActivity里调用handler
 * //TODO   调试 从NetConnectionReq拿来的result到底是什么
 * @author andyzhu
 * @data   2015-8-1
 */

public class LoadGoodsListHttp extends WebViewLoadConfigBase
{
    private Context mContext;
    private LoadGoodsListReq req;

    public LoadGoodsListHttp()
    {
        super();
    }

    // 代参构造函数
    public LoadGoodsListHttp(Context context)
    {
        mContext = context;
    }

    public SalesBaseMsg sendAndRecv(SalesReq salesReq, final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        //配置参数
        final LoadGoodsListReq loadGoodsListReq = new LoadGoodsListReq();
        String reqJson  = ReqSetProperties.setLoadGoodsListReqProperties(loadGoodsListReq, "html");
        L.i("LoadGoodsListHttp, json = " + reqJson);
        L.i("LoadGoodsListHttp ip address = " + Configs.SERVER_IP_ADDRESS + loadGoodsListReq.getMsgtype());
        NetConnectionReq connection = new NetConnectionReq(Configs.SERVER_IP_ADDRESS + loadGoodsListReq.getMsgtype(),
                new NetConnectionReq.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        //响应response
//                        SalesBaseMsg resp = SalesMsgUtils.fromJson(result, req.getMsgtype(), false);
//                        SalesResp salesResp = (SalesResp)resp;
//                        int returnCode = salesResp.getReturncode();
//                        L.i("return code = " + returnCode);
                        //处理返回码
                        if (true)
                        {
                            if (successCallback != null)
                            {
                                successCallback.onSuccess(result);
                            }

                        }
                        else
                        {
                            if (failedCallback != null)
                            {
                                failedCallback.onFail(result);
                            }
                        }

                    }
                },
                new NetConnectionReq.FailCallback() {
                    @Override
                    public void onFail(String error) {
                        // 网络操作失败  一般是因为网络问题吧   要么没有网络  之类的问题
                        if (failedCallback != null)
                        {
                            failedCallback.onFail("");
                        }
                    }
                },reqJson
        );
        return null;
    }







    //设计一个回调函数, 因为这个类的主要操作是在子线程里面执行, 调用的时候, 我要知道什么时候执行成功, 和失败

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
