package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Json2ShoppingmallBottomPicsBean:商城,分类8图标下面的每一个栏目的几个图片信息
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-08
 */
public class Json2ShoppingmallBottomPicsBean extends Base{

//    {"total":0,"rows":[
//        {"id":"e0635993-2f23-428c-9b42-5b53dd5a3734",
//                "logicalService":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6",
//
//                "pathCode":"3",
//
//                "photoName":"de994362-0c6b-4ff1-a8eb-b70ec9ee4a05.png",
//
//                "serviceCode":"6240130101",
//
//                "repairServiceSort":1,
//                "serviceName":"汽机油"},
//        {"id":"522317e1-8dc7-44fa-a671-75890d83375f",
//                "logicalService":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6",
//                "pathCode":"",
//                "photoName":"",
//                "serviceCode":"62401301",
//                "repairServiceSort":1,
//                "serviceName":"油品类"},
//        {"id":"79199302-bd72-4466-9a0c-6f8a03b2fe92",
//                "logicalService":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6",
//                "pathCode":"",
//                "photoName":"",
//                "serviceCode":"624017",
//                "repairServiceSort":1,
//                "serviceName":"黄油"},
//        {"id":"9bc9e6ae-7309-4f08-903b-942b1867f495","logicalService":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6",
//                "pathCode":"","photoName":"","serviceCode":"62401302","repairServiceSort":2,"serviceName":"养护用品"},
//        {"id":"4b5df5f4-34c5-4f0c-a5d7-4cd97487abf9","logicalService":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6","pathCode":"3",
//                "photoName":"11891430-7952-4175-9925-010632f36a7c.png","serviceCode":"6240130102","repairServiceSort":2,"serviceName":"柴机油"},
//        {"id":"5864b942-4fd8-4b70-9be1-521031e6d13c","logicalService":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6","pathCode":"3","photoName":"1b9798b0-3eb9-420e-b708-67d1d67921b8.png",
//                "serviceCode":"6240130103","repairServiceSort":3,"serviceName":"自动变速箱油"},
//        {"id":"0c454072-12be-4e67-ba50-7e26aa80db90","logicalService":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6","pathCode":"3",
//                "photoName":"ea33ec15-cfe9-4d92-934e-b44b16c15734.png","serviceCode":"6240130104","repairServiceSort":4,
//                "serviceName":"齿轮油"},
//        {"id":"38f28eb8-798d-492f-bf94-4a61153ee5da","logicalService":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6",
//                "pathCode":"3","photoName":"cbb237d9-544d-49ff-88e4-6e69bb10b5e5.png","serviceCode":"6240130105",
//                "repairServiceSort":5,"serviceName":"刹车油"},{"id":"6e85da78-7034-4da9-97d8-5959089f9cb5",
//            "logicalService":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6","pathCode":"3","photoName":"432cec95-da04-4e7a-8cc4-fae008a1a1d8.png",
//            "serviceCode":"6240130106","repairServiceSort":6,"serviceName":"助力转向油"},
//        {"id":"d34affe5-b7b0-4edc-af5b-ca12d649c763","logicalService":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6","pathCode":"",
//                "photoName":"","serviceCode":"624014","repairServiceSort":14,"serviceName":"保养"}]


    private String id;
    private String logicalService;
    private String pathCode;
    private String photoName;
    private String serviceCode;
    private int    repairServiceSort;
    private String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getRepairServiceSort() {

        return repairServiceSort;
    }

    public void setRepairServiceSort(int repairServiceSort) {
        this.repairServiceSort = repairServiceSort;
    }

    public String getServiceCode() {

        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getPhotoName() {

        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPathCode() {

        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public String getLogicalService() {

        return logicalService;
    }

    public void setLogicalService(String logicalService) {
        this.logicalService = logicalService;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
