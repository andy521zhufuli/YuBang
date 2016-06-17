package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;
import com.car.yubangapk.json.bean.OrderDetail.BaseJson;


/**
 * Created by andy on 16/4/16.
 */
public class AddressBean extends BaseJson{

    String id;
    String CUserid;
    String name;
    String phone;
    int    isDefaule;

    public int getIsDefaule() {
        return isDefaule;
    }

    public void setIsDefaule(int isDefaule) {
        this.isDefaule = isDefaule;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCUserid() {

        return CUserid;
    }

    public void setCUserid(String CUserid) {
        this.CUserid = CUserid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
