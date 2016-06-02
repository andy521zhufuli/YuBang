package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.OrderDetail.BaseJson;
import com.car.yubangapk.json.formatJson.FormatBaseJson;
import com.car.yubangapk.json.formatJson.FormatBaseJsonCommonCode;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.ui.MakeNewCommentActivity;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.String2UTF8;
import com.car.yubangapk.utils.toastMgr;

import okhttp3.Call;

/**
 * Created by andy on 16/6/1.
 *
 * 提交评论
 *
 */
public class HttpPushComment
{
    private HttpReqCallback mCallback;

    public HttpPushComment()
    {

    }

    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }


    public void pushComment(String userid, String orderid, String content, int type, int star)
    {
        content = String2UTF8.getUTF8String(content);
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_ADD_COMMENT)
                .addParams("commentReq.userid", userid)
                .addParams("commentReq.orderId", orderid)
                .addParams("commentReq.comtent", content)
                .addParams("commentReq.cmd", type+"")
                .addParams("commentReq.star", star+"")
                .build()
                .execute(new CommentCallback());
        L.i(MakeNewCommentActivity.TAG, "添加评论 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_ADD_COMMENT + "?"
                        + "commentReq.userid=" + userid
                        + "&" + "commentReq.orderId=" + orderid
                        + "&" + "commentReq.comtent=" + content
                        + "&" + "commentReq.cmd=" + type
                        + "&" + "commentReq.star=" + star
        );
    }

    class CommentCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.i(MakeNewCommentActivity.TAG, "添加评论 json = " + response);
            FormatBaseJson formatBaseJsonCommonCode = new FormatBaseJson(response);
            BaseJson baseJson = formatBaseJsonCommonCode.getBaseCommon();
            if (baseJson == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                toastMgr.builder.display("服务器错误", 1);
            }
            else
            {
                if (baseJson.getReturnCode() == 0)
                {
                    mCallback.onSuccess(baseJson);
                }
                else
                {
                    mCallback.onFail(baseJson.getReturnCode(), baseJson.getMessage());
                    toastMgr.builder.display(baseJson.getMessage(), 1);
                }
            }

        }
    }


}
