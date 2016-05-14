package com.car.yubangapk.network.myHttpReq.search;

import android.speech.RecognitionService;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2MyOrderBean;
import com.car.yubangapk.json.bean.search.SearchResultProductPackage;
import com.car.yubangapk.json.bean.search.SearchResultProductPackageRows;
import com.car.yubangapk.json.formatJson.Json2MyOrder;
import com.car.yubangapk.json.formatJson.search.FormatSearchProductPackage;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;

import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/5/14.
 */
public class HttpReqGetSearchReuslt
{
    private String TAG = HttpReqGetSearchReuslt.class.getSimpleName();

    private String mUserId;
    private String mPage;
    private String mRows;
    private String mOrderStatus;
    private String mAddressId;

    private HttpReqCallback callback;


    private static final String ARGS1 = "sqlName";// = clientUserOrder
    private static final String ARGS2 = "page";//第几页
    private static final String ARGS3 = "rows";//一页几条记录数
    private static final String ARGS4 = "dataReqModel.args.orderStatus";//订单状态，参考上图
    private static final String ARGS5 = "dataReqModel.userid";


    /**
     * 设置回调
     * @param callback
     */
    public void setCallback(HttpReqCallback callback)
    {
        this.callback = callback;
    }

    public void getSearchResult(String  carType, String Ppage, String rows, String word, String userid) {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_SEARCH_SEARCH)
                .addParams("sqlName", "clientSearchProductPackage")
                .addParams(ARGS2, Ppage)
                .addParams(ARGS3, rows)
                .addParams("dataReqModel.args.likeField", "packageName")
                .addParams("dataReqModel.args.packageName", word)
                .addParams("dataReqModel.args.carType", carType)

                .build()
                .execute(new GetSearchResultCallBack());

        L.i(TAG, "获取搜索结果 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_SEARCH_SEARCH + "?"
                        + ARGS1 + "=" + "clientSearchProductPackage"
                        + "&" + ARGS2 + "=" + Ppage
                        + "&" + ARGS3 + "=" + rows
                        + "&" + "dataReqModel.args.likeField" + "=" + "packageName"
                        + "&" + "dataReqModel.args.packageName" + "=" + word
                        + "&" + "dataReqModel.args.carType" + "=" + carType

        );

    }
    class GetSearchResultCallBack extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            L.d(TAG, "");
            callback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
            toastMgr.builder.display("网络错误", 1);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "h获取搜索结果 json = " + response);
            FormatSearchProductPackage searchProductPackage = new FormatSearchProductPackage(response);
            SearchResultProductPackage resultProductPackage = searchProductPackage.getSearchResult();

            if (resultProductPackage == null)
            {
                callback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
            }
            else
            {
                List<SearchResultProductPackageRows> rows = resultProductPackage.getRows();
                if (resultProductPackage.getReturnCode() != 0)
                {
                    callback.onFail(ErrorCodes.ERROR_CODE_NOT_LOGIN, ErrorCodes.NOT_LOGIN);

                }
                else if (rows.size() == 0)
                {
                    callback.onFail(ErrorCodes.ERROR_CODE_NO_DATA, "没有数据");
                    toastMgr.builder.display("没有更多数据", 1);
                }
                else
                {
                    callback.onSuccess(rows);
                }
            }
        }
    }
}
