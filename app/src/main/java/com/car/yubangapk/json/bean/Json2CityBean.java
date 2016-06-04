package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Created by andy on 16/6/4.
 *
 * 省  jaon类
 *
 */
public class Json2CityBean extends Base
{
    //[{"REGION_ID":2,"PARENT_ID":1,"REGION_NAME":"北京市"},
    // {"REGION_ID":3,"PARENT_ID":1,"REGION_NAME":"天津市"},

    int     REGION_ID;
    int     PARENT_ID;
    String  REGION_NAME;

    public int getREGION_ID() {
        return REGION_ID;
    }

    public void setREGION_ID(int REGION_ID) {
        this.REGION_ID = REGION_ID;
    }

    public int getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(int PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public String getREGION_NAME() {
        return REGION_NAME;
    }

    public void setREGION_NAME(String REGION_NAME) {
        this.REGION_NAME = REGION_NAME;
    }
}
