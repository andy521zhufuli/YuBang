package com.car.yubangapk.json.bean.wallet;

import com.car.yubangapk.json.Base;
import com.car.yubangapk.json.bean.OrderDetail.BaseJson;

/**
 * Created by andy on 16/6/16.
 */
public class Json2MyWalletBean extends BaseJson {

    //{returnCode:””,message:””,totalMoney:0,rechargeMoney:0,consumeMoney:0,adMoney:0,waitTotalMoney:0,todayMoney:0,invetMoney:0}

    float totalMoney;//总额
    float rechargeMoney;//充值
    float consumeMoney;//会员消费提成
    float adMoney;//广告转发
    float invetMoney;//邀请佣金
    float waitTotalMoney;//带返还总额
    float todayMoney;//今日返还

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public float getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(float rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public float getConsumeMoney() {
        return consumeMoney;
    }

    public void setConsumeMoney(float consumeMoney) {
        this.consumeMoney = consumeMoney;
    }

    public float getAdMoney() {
        return adMoney;
    }

    public void setAdMoney(float adMoney) {
        this.adMoney = adMoney;
    }

    public float getInvetMoney() {
        return invetMoney;
    }

    public void setInvetMoney(float invetMoney) {
        this.invetMoney = invetMoney;
    }

    public float getWaitTotalMoney() {
        return waitTotalMoney;
    }

    public void setWaitTotalMoney(float waitTotalMoney) {
        this.waitTotalMoney = waitTotalMoney;
    }

    public float getTodayMoney() {
        return todayMoney;
    }

    public void setTodayMoney(float todayMoney) {
        this.todayMoney = todayMoney;
    }
}

