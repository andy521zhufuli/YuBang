package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Created by andy on 16/6/13.
 *
 * 车辆驱动形式信息
 *
 */
public class Json2MotivationBean extends Base
{
    //{"motivation":"310"},
    String motivation;

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }
}
