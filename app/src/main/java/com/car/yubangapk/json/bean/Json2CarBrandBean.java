package com.car.yubangapk.json.bean;

/**
 * Json2CarBrandBean: 汽车品牌
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-04
 */
public class Json2CarBrandBean {
//    {"total":0,
//            "rows":
//            [
//                {"id":"8c13263b-ef63-11e5-97ba-28d244001fe5","pathCode":"4","carBrandName":"奥驰汽车"},
//                {"id":"f2c23af5-ef64-11e5-97ba-28d244001fe5","pathCode":"4","carBrandName":"北奔重卡"}
//            ]
//    }


    private String id;
    private String pathCode;
    private String carBrandName;

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
