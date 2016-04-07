package com.car.yubangapk.json.bean;

/**
 * Json2CarCapacityBean: 汽车排量
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-04
 */
public class Json2CarCapacityBean
{
    private String id;


    private String capacityName;
//    {"total":0,
//            "rows":
//        [{"id":"30ef459f-f97f-11e5-bc6d-52540022d049","capacityName":"2.0"},
//        {"id":"43057778-f97f-11e5-bc6d-52540022d049","capacityName":"3.0"},
//        {"id":"4700f302-f97f-11e5-bc5d-52540022d049","capacityName":"5.0"},
//        {"id":"4700f302-f97f-11e5-bc6d-52540022d049","capacityName":"4.0"}]}



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCapacityName() {
        return capacityName;
    }

    public void setCapacityName(String capacityName) {
        this.capacityName = capacityName;
    }
}
