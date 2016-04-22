package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Json2ShopShowBean:门店 店铺的服务
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-06
 */
public class Json2ShopServiceBean extends Base{




//    {"total":0,"rows":[
//        {"skipType":"1","id":"0c454072-12be-4e67-ba50-7e26aa80db90","serviceName":"齿轮油"},
//        {"skipType":"1","id":"049cb409-340c-4fb0-8bdb-8cee4ed6ca1d","serviceName":"相位传感器"}]}

    private String id;//也是repairservice来的
    private String skipType;
    private String serviceName;

    //这里hasData应该是一个基类 所有的都应该继承这个类的这个属性 以后再改

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSkipType() {

        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
