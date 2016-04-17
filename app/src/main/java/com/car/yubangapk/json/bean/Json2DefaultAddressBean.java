package com.car.yubangapk.json.bean;


import com.car.yubangapk.json.Base;

/**
 * Created by andy on 16/4/16.
 */
public class Json2DefaultAddressBean extends Base{


    //{"defaultAddress":{},"addresses":[],"isJson":true,"isReturnStr":false,"returnCode":-100,"returneMsg":"SUCCESS","message":"服务器错误"}
    // {"defaultAddress":{},"addresses":[],"isJson":true,"isReturnStr":false,"returnCode":1,"returneMsg":"SUCCESS","message":"该用户无收货地址"}
    //address json = {"defaultAddress":{"id":"0","CUserid":"bf8eb70b-c29f-41c8-a9bc-7e7dd56880bb","name":"朱福利","phone":"123456789","isDefaule":1},




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
