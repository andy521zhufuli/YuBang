package com.car.yubangapk.json.bean;

/**
 * Created by andy on 16/4/4.
 */
public class Json2CarSeriesBean {

    //{"total":0,"rows":[{"id":"739ed567-f97a-11e5-ba79-28d244001fe5","carSeriesName":"奥驰","pathCode":"4"},{"id":"7d1942d0-f97a-11e5-ba79-28d244001fe5","carSeriesName":"奥微","pathCode":"4"}]}
    private String id;
    private String pathCode;
    private String carSeriesName;


    public String getCarSeriesName() {
        return carSeriesName;
    }

    public void setCarSeriesName(String carSeriesName) {
        this.carSeriesName = carSeriesName;
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
