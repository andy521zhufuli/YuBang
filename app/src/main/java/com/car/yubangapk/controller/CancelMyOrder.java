package com.car.yubangapk.controller;

import android.app.Activity;

import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqCancleOrder;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.CustomProgressDialog;

/**
 * Created by andy on 16/6/2.
 *
 * 取消订单
 *
 */
public class CancelMyOrder implements HttpReqCallback
{

    String userid;
    String orderId;

    Activity context;

    CustomProgressDialog mProgress;


    public CancelMyOrder(Activity context)
    {
        this.context = context;
        mProgress = new CustomProgressDialog(context);
    }


    public void cancleOrder(String userid, String orderId)
    {
        this.userid = userid;
        this.orderId = orderId;

        HttpReqCancleOrder cancleOrder  = new HttpReqCancleOrder();
        cancleOrder.setCallback(this);
        cancleOrder.cancelOrder(this.userid, this.orderId);

    }

    @Override
    public void onFail(int errorCode, String message) {
        mProgress.dismiss();
        if (errorCode == 100)
        {
            NotLogin.gotoLogin(this.context);
        }
    }

    @Override
    public void onSuccess(Object object) {
        toastMgr.builder.display("取消订单成功",1 );
        mProgress.dismiss();
        context.finish();
    }
}
