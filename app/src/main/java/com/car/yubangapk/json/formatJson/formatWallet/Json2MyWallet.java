package com.car.yubangapk.json.formatJson.formatWallet;

import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.Json2CarBrandBean;
import com.car.yubangapk.json.bean.wallet.Json2MyWalletBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/6/17.
 *
 * 钱包Json转为类
 *
 */
public class Json2MyWallet
{
    String json;

    public Json2MyWallet(String json)
    {
        this.json = json;
    }

    public Json2MyWalletBean getMyWallet() {

        Json2MyWalletBean myWalletBean = new Json2MyWalletBean();
        int returnCode;
        float totalMoney;//总额
        float rechargeMoney;//充值
        float consumeMoney;//会员消费提成
        float adMoney;//广告转发
        float invetMoney;//邀请佣金
        float waitTotalMoney;//带返还总额
        float todayMoney;//今日返还

        JSONObject total = null;
        try {
            total = new JSONObject(json);
            returnCode = JSONUtils.getInt(total, "returnCode", -1);
            if (returnCode == -1)
            {
                //服务器返回的Json数据有错误  因为按照规定  正常要返回returnCode
                return null;
            }
            else if (returnCode != 0)
            {
                myWalletBean.setReturnCode(returnCode);
                String message = JSONUtils.getString(total, "message", "");
                myWalletBean.setMessage(message);
                myWalletBean.setHasData(false);
                return myWalletBean;
                //其他错误
            }
            //正常的
            totalMoney = (float) JSONUtils.getDouble(total, "totalMoney", 0.0);//总额
            rechargeMoney = (float) JSONUtils.getDouble(total, "rechargeMoney", 0.0);;//充值
            consumeMoney = (float) JSONUtils.getDouble(total, "consumeMoney", 0.0);;//会员消费提成
            adMoney = (float) JSONUtils.getDouble(total, "adMoney", 0.0);;//广告转发
            invetMoney = (float) JSONUtils.getDouble(total, "invetMoney", 0.0);;//邀请佣金
            waitTotalMoney = (float) JSONUtils.getDouble(total, "waitTotalMoney", 0.0);;//带返还总额
            todayMoney = (float) JSONUtils.getDouble(total, "todayMoney", 0.0);;//今日返还
            String message = JSONUtils.getString(total, "message", "");

            myWalletBean.setHasData(true);
            myWalletBean.setReturnCode(returnCode);
            myWalletBean.setTotalMoney(totalMoney);
            myWalletBean.setRechargeMoney(rechargeMoney);
            myWalletBean.setConsumeMoney(consumeMoney);
            myWalletBean.setAdMoney(adMoney);
            myWalletBean.setInvetMoney(invetMoney);
            myWalletBean.setWaitTotalMoney(waitTotalMoney);
            myWalletBean.setTodayMoney(todayMoney);
            myWalletBean.setMessage(message);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return myWalletBean;
    }
}
