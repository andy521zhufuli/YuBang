package com.car.yubangapk.network.myHttpReq.search;

import android.content.Context;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.search.HotWordBean;
import com.car.yubangapk.json.formatJson.search.FormatHotWord;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;

import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/5/14.
 *
 * 获取搜索热词
 *
 */
public class HttpReqGerHotSearchWord
{
    private Context mContext;
    private HttpReqCallback mCallback;

    public HttpReqGerHotSearchWord()
    {


    }


    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }

    /**
     * 拿到启动后配置信息
     */
    public void getHotWord()
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientGetHotSearchWorld" )
                .build().execute(new HotWordCallback());
    }

    /**
     * 获取配置信息回调
     */
    public class HotWordCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e)
        {
            L.d("HotWordCallback", "get 热词 error" + e.toString());
            toastMgr.builder.display("网络错误, 请稍后重试", 1);

        }

        @Override
        public void onResponse(String response)
        {
            L.d("HotWordCallback", "get 热词 json" + response);
            FormatHotWord formatHotWord = new FormatHotWord(response);
            List<HotWordBean> hotWordBeans = formatHotWord.getHotWordList();

            if (hotWordBeans != null)
            {
                if (hotWordBeans.size() == 0)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_NO_DATA, ErrorCodes.NO_DATA);
                    toastMgr.builder.display(ErrorCodes.NO_DATA, 1);
                }
                else
                {
                    mCallback.onSuccess(hotWordBeans);
                }
            }
            else
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                toastMgr.builder.display(ErrorCodes.SERVER_ERROR, 1);
            }
        }
    }
}
