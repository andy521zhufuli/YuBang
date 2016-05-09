package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.productDetail.Json2ProductCommentsDetailbean;
import com.car.yubangapk.json.bean.productDetail.Json2ProductDetailInfoBean;
import com.car.yubangapk.json.formatJson.formatProductDetail.Json2ProductCommentDetail;
import com.car.yubangapk.json.formatJson.formatProductDetail.Json2ProductDetailInfo;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import okhttp3.Call;

/**
 * Created by andy on 16/5/9.
 *
 * 网络请求  获取单个产品线详情
 *
 */
public class HttpReqGetProductDetailIinfo
{
    HttpReqCallback mCallback;
    String TAG = HttpReqGetProductDetailIinfo.class.getSimpleName();

    public HttpReqGetProductDetailIinfo()
    {

    }

    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }


    public void getProductDetail(String productId)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_PRODUCT_DETAIL_INFO)
                .addParams("productDetailReq.productId",productId)
                .build()
                .execute(new GetProductDetailInfoCallBack());

        L.i(TAG, "产品详情 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_PRODUCT_DETAIL_INFO + "?"
                        + "productDetailReq.productId" + "=" + productId
        );
    }



    public void getProductCommentDetail(String productId, int page, int row)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_PRODUCT_COMMENT_DETAIL_INFO)
                .addParams("sqlName", "clientGetProductComment")
                .addParams("page", page + "")//第几页
                .addParams("rows", row + "")//一页几条记录数
                .addParams("dataReqModel.args.productId", "673784aa-a22e-4515-937a-7ce930c3b739")
                .build()
                .execute(new GetProductCommentDetailInfoCallBack());

        L.i(TAG, "产品评论 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_PRODUCT_COMMENT_DETAIL_INFO + "?"
                        + "productDetailReq.productId" + "=" + productId
                        + "&sqlName=clientGetProductComment"
                        + "&page=" + page
                        + "&rows=" + row
                        + "&dataReqModel.args.productId=" + productId
        );
    }

    class GetProductDetailInfoCallBack extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            L.d(TAG, "网络错误");
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK,ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "产品详情 json = " + response);

            Json2ProductDetailInfo productDetailInfo = new Json2ProductDetailInfo(response);
            Json2ProductDetailInfoBean productDetailInfoBean = productDetailInfo.getProductDetailInfo();
            if (productDetailInfoBean == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
            }
            else
            {
                if (productDetailInfoBean.getReturnCode() == ErrorCodes.ERROR_CODE_NOT_LOGIN)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_NOT_LOGIN, productDetailInfoBean.getMessage());
                }
                else if (productDetailInfoBean.getReturnCode() != 0)
                {
                    mCallback.onFail(productDetailInfoBean.getReturnCode(), productDetailInfoBean.getMessage());
                }
                else
                {
                    mCallback.onSuccess(productDetailInfoBean);
                }
            }
        }
    }

    class GetProductCommentDetailInfoCallBack extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            L.d(TAG, "网络错误");
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK,ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "产品评论 json = " + response);

            Json2ProductCommentDetail commentDetail = new Json2ProductCommentDetail(response);
            Json2ProductCommentsDetailbean commentsDetailbean = commentDetail.getProductCommentDetail();

            if (commentsDetailbean == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
            }
            else
            {
                if (commentsDetailbean.getReturnCode() == ErrorCodes.ERROR_CODE_NOT_LOGIN)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_NOT_LOGIN, commentsDetailbean.getMessage());
                }
                else if (commentsDetailbean.getReturnCode() != 0)
                {
                    mCallback.onFail(commentsDetailbean.getReturnCode(), commentsDetailbean.getMessage());
                    toastMgr.builder.display(commentsDetailbean.getMessage(), 1);
                }
                else
                {
                    mCallback.onSuccess(commentsDetailbean);
                }
            }
        }
    }



}
