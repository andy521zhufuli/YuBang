package com.tec.android.utils;

import android.content.Context;

import com.sales.vo.base.AddressInfo;

/**
 * 检查收货人信息的工具类
 *
 * @author andy
 * @version 1.0
 * @created 2015-8-5
 */
public class CheckReceivingAddressUtil
{
    private Context mContext;

    public CheckReceivingAddressUtil() {}

    public CheckReceivingAddressUtil(Context context)
    {
        this.mContext = context;
    }

    /**
     * 检查收货地址是否存在
     * @return 返回true 存在  否则 不存在
     */
    public boolean isReceivingAddressExits()
    {

        return true;
    }

    /**
     * 拿到选中的收货地址信息
     * @return 收货地址信息
     */
    public AddressInfo getSelectedAddress()
    {
        AddressInfo addressInfo = new AddressInfo();

        return addressInfo;
    }

}
