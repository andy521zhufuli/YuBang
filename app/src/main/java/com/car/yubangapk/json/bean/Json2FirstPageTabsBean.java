package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Json2FirstPageTabsBean:首页tabs bean
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-05
 */
public class Json2FirstPageTabsBean extends Base{
//    [
        //{"id":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6","pathCode":"1","sort":"1","skipType":"2","tabDisplayName":"保养","photoName":"f8c7b5a5-36a6-48cd-bec5-a36fef2f0cca.png","serviceName":"保养维护"},
        //{"id":"841ffbca-8063-4561-b7f3-9193f43ff731","pathCode":"1","sort":"2","skipType":"2","tabDisplayName":"电路","photoName":"74b87f9c-c54c-42f3-9a9b-b3ef0005acf9.png","serviceName":"电子电路"},
        //{"id":"76e787e5-123c-4bde-8abd-e758f268f706","pathCode":"1","sort":"3","skipType":"2","tabDisplayName":"机件","photoName":"eaec2fcf-1ece-4e30-8a41-065b2940b785.png","serviceName":"发动机件"},
        //{"id":"91f3b4a2-537e-42c7-a9f5-bed66ae5ef46","pathCode":"1","sort":"4","skipType":"2","tabDisplayName":"黄油","photoName":"c1f700aa-695c-4383-aa77-2374a63e06f8.png","serviceName":"打黄油"},
        //{"id":"4792a819-67ac-47e1-ad42-ab5f6ee3f854","pathCode":"1","sort":"5","skipType":"2","tabDisplayName":"底盘","photoName":"299d4112-d903-471f-87b8-2108ce35238a.png","serviceName":"底盘配件"},
        //{"id":"f10b454f-0ae0-4747-a5ca-57f6a12a979a","pathCode":"1","sort":"6","skipType":"2","tabDisplayName":"车架","photoName":"f8c7b5a5-36a6-48cd-bec5-a36fef2f0cca.png","serviceName":"车架配件"},
        //{"id":"150091b7-0e11-489c-ac34-bdaa63711bcd","pathCode":"1","sort":"7","skipType":"2","tabDisplayName":"拖架","photoName":"625aaef7-153c-4dee-83e7-a6b66a92a433.png","serviceName":"拖架配件"}]
//    ]


    private String id;
    private String pathCode;
    private String sort;
    private String skipType;
    String         tabDisplayName;
    private String photoName;
    private String serviceName;

    public String getTabDisplayName() {
        return tabDisplayName;
    }

    public void setTabDisplayName(String tabDisplayName) {
        this.tabDisplayName = tabDisplayName;
    }

    public String getServiceName() {

        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPhotoName() {

        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getSkipType() {

        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getSort() {

        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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
