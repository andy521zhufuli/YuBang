package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

import java.util.List;

/**
 * Created by andy on 16/4/25.
 */
public class Json2MyRecommendPartnersBean extends Base{
    //{"total":53,"rows":[{id ,shopName,shopAddress,shopPhoto,pathCode,phoneNum}]}无shopPhoto显示yubang图标，无其他字段丢弃
    int total;

    List<MyRecommendPartnersBean> rows;

    int returnCode;//没有这个字段  这是根据特定的项目需求来做的  翻译点击我的推荐的合伙人 但是还没有登录  那就需要这个字段了


    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public List<MyRecommendPartnersBean> getRows() {
        return rows;
    }

    public void setRows(List<MyRecommendPartnersBean> rows) {
        this.rows = rows;
    }

    public int getTotal() {


        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
