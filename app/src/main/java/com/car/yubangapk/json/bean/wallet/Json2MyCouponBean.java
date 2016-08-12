package com.car.yubangapk.json.bean.wallet;

import com.car.yubangapk.json.bean.OrderDetail.BaseJson;

import java.util.List;

/**
 * Created by andy on 16/6/23.
 */
public class Json2MyCouponBean extends BaseJson
{
    //{"total":3,"rows":[
    // {
        // "startDate":"2016-06-22 23:38:52",
        // "couponName":"测试店铺",
        // "scope":"1",
        // "regulationSubtract":10,
        // "endDate":"2016-06-22 23:38:55",
        // "regulationReach":100,
        // "subCouponid":"3cb42fc5-91dc-4921-b375-61dc6391a749"
    // },
    // {"startDate":"2016-06-22 23:39:27","couponName":"测试产品包","scope":"2","regulationSubtract":1,"endDate":"2016-06-22 23:39:29","regulationReach":10,"subCouponid":"412231a7-e4a1-4c29-8e50-c95c3f6ce15a"},{"startDate":"2016-06-22 23:37:53","couponName":"测试总价","scope":"0","regulationSubtract":10,"endDate":"2016-06-22 23:37:59","regulationReach":100,"subCouponid":"46495c90-ed97-49ac-8697-5aa3daf3f763"}]}

    int total;

    List<CouponRow> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CouponRow> getRows() {
        return rows;
    }

    public void setRows(List<CouponRow> rows) {
        this.rows = rows;
    }
}
