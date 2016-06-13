package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Created by andy on 16/6/13.
 *
 * 车辆马力信息
 *
 */
public class Json2HorsePowerBean extends Base
{
    //{"horsepower":"310"},
    String horsepower;

    public String getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(String horsepower) {
        this.horsepower = horsepower;
    }
}
