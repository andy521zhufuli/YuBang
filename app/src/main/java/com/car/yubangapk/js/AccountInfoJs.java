package com.car.yubangapk.js;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;


/**
 * AccountInfoJs类：webview的个人中心 与native 交互的js接口定义
 *
 * @author andy
 * @version 1.0
 * @created 2015-08-1
 */
public class AccountInfoJs
{
    private Context mContext;


    public AccountInfoJs()
    {

    }

    public AccountInfoJs(Context context)
    {
        this.mContext = context;
    }

    /**
     * webview点击邀请好友  webview的js调用native的InviteFriend接口
     1. 消息名称:InviteFriend
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void InviteFriend()
    {
        toastMgr.builder.display("js-->native, InviteFriend", 0);
        //邀请好友 就是一个分享
        Intent intent = new Intent("MyFriendsActivity.invite.friend");
        mContext.sendBroadcast(intent);
    }


    /**
     * webview点击我的订单  webview的js调用native的MyOrder接口 跳转到我的订单
     1. 消息名称:MyOrder
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void MyOrder()
    {

    }

    /**
     * WebView 通过本接口调用 NativeApp 通知跳转我的提现
     1. 消息名称:MyCash
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void MyCash()
    {
        toastMgr.builder.display("js-->native, MyCash", 0);
        Intent intent = new Intent();

        mContext.startActivity(intent);
    }

    /**
     * WebView 通过本接口调用 NativeApp 通知跳转我要提现
     1. 消息名称:MyCash
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void CashOut()
    {
        toastMgr.builder.display("js-->native, MyCash", 0);
        Intent intent = new Intent();

        mContext.startActivity(intent);
    }


    /**
     * WebView 通过本接口调用 NativeApp 跳转到我的提现记录
     1. 消息名称:MyCash
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void MyCashList()
    {
        //我的提现记录 历史
        Intent intent = new Intent();

        mContext.startActivity(intent);
    }

    /**
     * WebView 通过本接口调用 NativeApp 跳转到我的提现明细
     1. 消息名称:MyCash
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void CashDetail(String cashid)
    {
        toastMgr.builder.display("js-->native, CashDetail cashid  = " + cashid, 0);
        Intent intent = new Intent();

        intent.putExtra("cashid", cashid);
        mContext.startActivity(intent);
    }

    /**
     * WebView 通过本接口调用 NativeApp 我的提现列表  头部点击重新加载
     1. 消息名称:MyCash
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void CashListQuery(String status)
    {
        toastMgr.builder.display("js-->native, CashListQuery status" + status, 0);
        //给我的提现历史  界面发送广播   重新加载页面 MyCashlistActivity
        Intent intent =  new Intent("MyCashList");
        intent.putExtra("status", status);
        mContext.sendBroadcast(intent);

    }








    /**
     * WebView 通过本接口调用 NativeApp 通知跳转我的好友列表
     *
     1. 消息名称:MyFriends
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void MyFriends(String fuserid)
    {
//        toastMgr.builder.display("js-->native, MyFriends", 0);
        Intent intent = new Intent();

        intent.putExtra("fuserid", fuserid);
        mContext.startActivity(intent);
    }

    /**
     * WebView 通过本接口调用 NativeApp 通知跳转我的好友个人中心
     *
     1. 消息名称:MyFriendInfo
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void MyFriendInfo(String fuserid)
    {
        //打开我的朋友的个人中心
        Intent intent = new Intent();

        intent.putExtra("fuserid",fuserid);
        mContext.startActivity(intent);
    }


    /**
     * WebView 通过本接口调用 NativeApp 通知跳转收入明细
     *
     1. 消息名称:MyIncome
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void MyIncome()
    {
        toastMgr.builder.display("js-->native, MyIncome", 0);
        Intent intent = new Intent();

        mContext.startActivity(intent);
    }



    /**
     * WebView 通过本接口调用 NativeApp 通知跳转管理收件地址
     *
     1. 消息名称:MyRecevingAddress
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void MyAddress()
    {
        Intent intent = new Intent();

        //这个标志位说明不是确认订单里面的选择地址
        intent.putExtra("choose",false);
        mContext.startActivity(intent);
    }

    /**
     * WebView 通过本接口调用 NativeApp 确认订单界面  点击选择地址
     *
     1. 消息名称:MyRecevingAddress
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void ChangeAddress()
    {
        Intent intent = new Intent();

        //这个标志位说明是确认订单里面的选择地址
        intent.putExtra("choose",true);
        mContext.startActivity(intent);
    }

    /**
     * WebView 通过本接口调用 NativeApp 通知跳转联系客服
     *
     1. 消息名称:ContactInfo
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void ContactInfo()
    {
        toastMgr.builder.display("js-->native, ContactInfo", 0);
    }


    /**
     * WebView 通过本接口调用 NativeApp 通知跳转我的信息
     *
     1. 消息名称:MyInfo
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */




    /**
     * WebView 通过本接口调用 NativeApp 订单列表
     *
     1. 消息名称:MyInfo
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void OrderListQuery(String status)
    {
        if(status.equals("ToCharge"))
        {
            //待支付的订单 就去请求网络  然后我的订单页面就重新加载一次
            Intent intent = new Intent("myorder.reload");
            intent.putExtra("status", status);
            mContext.sendBroadcast(intent);
        }
        else if (status.equals("ToDeliver;ToReceive"))
        {
            //代签收的订单  就去请求网络  然后我的订单页面就重新加载一次
            Intent intent = new Intent("myorder.reload");
            intent.putExtra("status", status);
            mContext.sendBroadcast(intent);
        }
        else if (status.equals("ToCancel;Cancelling"))
        {
            //退货的订单   就去请求网络  然后我的订单页面就重新加载一次
            Intent intent = new Intent("myorder.reload");
            intent.putExtra("status", status);
            mContext.sendBroadcast(intent);
        }
        else if (status.equals("Finished"))
        {
            //完成的订单  就去请求网络  然后我的订单页面就重新加载一次
            Intent intent = new Intent("myorder.reload");
            intent.putExtra("status", status);
            mContext.sendBroadcast(intent);
        }
        else if(status.equals("all"))
        {
            //完成的订单  就去请求网络  然后我的订单页面就重新加载一次
            Intent intent = new Intent("myorder.reload");
            intent.putExtra("status", status);
            mContext.sendBroadcast(intent);
        }
        L.i("OrderListQuery status = " + status);
        toastMgr.builder.display("status = " +  status, 0);
    }

    /**
     * WebView 通过本接口调用 NativeApp 去支付订单
     *
     1. 消息名称:MyInfo
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void OrderCharge(String orderid)
    {

        L.i("OrderCharge orderid = " + orderid);
        toastMgr.builder.display("支付 = ", 0);
        Intent intent = new Intent();

        Bundle bundle = new Bundle();
        bundle.putString("orderid", orderid);
        bundle.putString("status", "待支付");
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * WebView 通过本接口调用 NativeApp 确认收货
     *
     1. 消息名称:MyInfo
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void OrderReceive(String orderid)
    {
        L.i("确认收货 orderid = " + orderid);
        toastMgr.builder.display("确认收货 ", 0);

        //改变订单状态哦  发送广播  MyOrderActivity
        Intent intent = new Intent("myorder.reload");
        intent.putExtra("status","getgoods");//确认收货
        intent.putExtra("orderid", orderid);
        mContext.sendBroadcast(intent);
    }

    /**
     * WebView 通过本接口调用 NativeApp 退货
     *
     1. 消息名称:MyInfo
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void OrderReturn(String orderid)
    {
        L.i("退货 orderid = " + orderid);
        toastMgr.builder.display("退货 ", 0);
    }

    /**
     * WebView 通过本接口调用 NativeApp 我的订单历史  点击订单item 到订单详情
     *
     1. 消息名称:MyInfo
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void OrderDetail(String orderid)
    {
        L.i("OrderDetail orderid = " + orderid);
        toastMgr.builder.display("订单详情 orderid = " + orderid, 0);
        Intent intent = new Intent();
//        intent.setClass(mContext, OrderDetailActivity.class);/**/
        intent.putExtra("orderid", orderid);
        mContext.startActivity(intent);

    }

    /**
     * WebView 通过本接口调用 NativeApp 提现  支持银行列表  item点击  选择
     *
     1. 消息名称:MyInfo
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void BankSelected(String bankname)
    {
        //给支持银行列表 发送广播
        L.i("BankSelected bankname" + bankname);
        Intent intent = new Intent("banklist");
        intent.putExtra("bankname", bankname);
        mContext.sendBroadcast(intent);

    }

    /**
     * WebView 通过本接口调用 NativeApp 提现  已有收款人列表  item点击  调用
     *
     1. 消息名称:MyInfo
     2. 请求方:WebView
     3. 响应方:NativeApp
     4. 传输方式:Javascript 接口调用
     5. 报文格式:N/A
     6. 是否加密:否
     7. 请求消息参数列表
     */
    public void CashAccountSelected(String accountname, String accountid, String bankname,String accounttype)
    {
        //给已有账户列表
        Intent intent = new Intent("cashnamelist");
        Bundle bundle = new Bundle();
        bundle.putString("accountname", accountname);
        bundle.putString("accountid", accountid);
        bundle.putString("bankname", bankname);
        bundle.putString("accounttype", accounttype);
        intent.putExtra("cashnamelistitem", bundle);
        mContext.sendBroadcast(intent);
    }


}
