package com.tec.android.network;

import android.content.Context;

import com.sales.vo.ExitLoginReq;
import com.sales.vo.PostLoginReq;
import com.sales.vo.PreLoginReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;
import com.tec.android.utils.bean.QQLoginBean;

/**
 * 登陆的网络请求
 *  PostLogin  后登陆
 *  RreLogin   预登陆
 *
 */
public class PostLoginReqHttp {


    private Context mContext;

    public PostLoginReqHttp()
    {

    }

    public PostLoginReqHttp(Context context)
    {
        this.mContext = context;
    }

    /**
     * 发送登陆请求 此时已经拿到了code  qq登陆 用户已经授权
     * @param successCallback
     * @param failedCallback
     * @param qqLoginBean
     */
    public void sendAndPostLoginReqJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, QQLoginBean qqLoginBean)
    {
        PostLoginReq postLoginReq = new PostLoginReq();
        String reqJson = ReqSetProperties.setGetPostLoginReqProperties(postLoginReq, "json",qqLoginBean);

        L.i("PostLoginReqHttp sendAndPostLoginReqJson reqJson = " + reqJson);
        L.i("PostLoginReqHttp sendAndPostLoginReqJson ip address = " + Configs.SERVER_IP_ADDRESS + postLoginReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + postLoginReq.getMsgtype(),
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
                }, reqJson);
        postLoginReq = null;
    }


    /**
     * 预登陆, 向服务器拿APPID这些信息
     */
    public void sendAndPreLoginReqJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        PreLoginReq preLoginReq = new PreLoginReq();
        //预登陆的参数设置
        String reqJson = ReqSetProperties.setGetPreLoginReqProperties(preLoginReq, "json");

        L.i("PostLoginReqHttp sendAndPreLoginReqJson reqJson = " + reqJson);
        L.i("PostLoginReqHttp sendAndPreLoginReqJson ip address = " + Configs.SERVER_IP_ADDRESS + preLoginReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + preLoginReq.getMsgtype(),
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
                }, reqJson);

    }


    /**
     * 退出登陆请求
     * @param successCallback   成功回调
     * @param failedCallback    失败回调
     * @param type 退出登陆的类型,  qq  微信  (我又发现好像不需要类型)
     */
    public void exitLoginReqJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback,String type)
    {
        ExitLoginReq exitLoginReq = new ExitLoginReq();
        //退出参数设置
        String reqJson = ReqSetProperties.setExitLoginReqProperties(exitLoginReq, "json");
        L.i("PostLoginReqHttp exitLoginReqJson reqJson = " + reqJson);
        L.i("PostLoginReqHttp exitLoginReqJson ip address = " + Configs.SERVER_IP_ADDRESS + exitLoginReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + exitLoginReq.getMsgtype(),
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
