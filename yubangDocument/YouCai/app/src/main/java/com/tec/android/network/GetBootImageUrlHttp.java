package com.tec.android.network;

import android.content.Context;

import com.sales.vo.AppConfigReq;
import com.sales.vo.AppConfigResp;
import com.sales.vo.base.SalesBaseMsg;
import com.sales.vo.base.SalesMsgUtils;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * 拿到启动app的初始化界面图片
 *
 * @author andyzhu
 * @data   2015-8-1
 */
public class GetBootImageUrlHttp {

    private Context mContext;

    public GetBootImageUrlHttp() {

    }

    public GetBootImageUrlHttp(Context context) {
        this.mContext = context;
    }

    public SalesBaseMsg sendAndRecv(final GetBootImageUrlHttp.DoingSuccessCallback successCallback,GetBootImageUrlHttp.DoingFailedCallback failedCallback)
    {
        //配置参数
        final AppConfigReq req = new AppConfigReq();
        String reqJson = ReqSetProperties.setAppConfigReqProperties(req, "json");
        L.i("GetBootImageUrlHttp reqJson = " + reqJson);
        L.i("GetBootImageUrlHttp ip address = " + Configs.SERVER_IP_ADDRESS + req.getMsgtype());
        NetConnectionReq netConnectionReq = new NetConnectionReq(Configs.SERVER_IP_ADDRESS + req.getMsgtype(),
                new NetConnectionReq.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        SalesBaseMsg salesBaseMsg = SalesMsgUtils.fromJson(result, req.getMsgtype(),false);
                        SalesResp salesResp = (SalesResp) salesBaseMsg;
                        AppConfigResp appConfigResp = (AppConfigResp) salesBaseMsg;
                        String bootImageUrl = appConfigResp.getBootimgurl();

                        if (bootImageUrl == null || bootImageUrl.equals(""))
                        {
                            //bootimage 没有
                        }
                        if (successCallback != null)
                        {
                            //返回启动图片
                            successCallback.onSuccess(bootImageUrl);
                        }

                    }
                },
                new NetConnectionReq.FailCallback() {
                    @Override
                    public void onFail(String error) {

                    }
                },
                reqJson
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
