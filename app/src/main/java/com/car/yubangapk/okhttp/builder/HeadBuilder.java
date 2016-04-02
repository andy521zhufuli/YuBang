package com.car.yubangapk.okhttp.builder;

import com.car.yubangapk.okhttp.OkHttpUtils;
import com.car.yubangapk.okhttp.request.OtherRequest;
import com.car.yubangapk.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}
