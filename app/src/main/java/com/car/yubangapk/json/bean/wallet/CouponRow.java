package com.car.yubangapk.json.bean.wallet;

/**
 * Created by andy on 16/6/23.
 */
public class CouponRow
{
    // {
    // "startDate":"2016-06-22 23:38:52",
    // "couponName":"测试店铺",
    // "scope":"1",
    // "regulationSubtract":10,
    // "endDate":"2016-06-22 23:38:55",
    // "regulationReach":100,
    // "subCouponid":"3cb42fc5-91dc-4921-b375-61dc6391a749"
    // },

    String startDate;
    String couponName;
    String scope;//0 总价  1商铺     2产品包  3产品
    int    regulationSubtract;
    String endDate;
    int    regulationReach;
    String subCouponid;
    String flagName;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getRegulationSubtract() {
        return regulationSubtract;
    }

    public void setRegulationSubtract(int regulationSubtract) {
        this.regulationSubtract = regulationSubtract;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getRegulationReach() {
        return regulationReach;
    }

    public void setRegulationReach(int regulationReach) {
        this.regulationReach = regulationReach;
    }

    public String getSubCouponid() {
        return subCouponid;
    }

    public void setSubCouponid(String subCouponid) {
        this.subCouponid = subCouponid;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }
}
