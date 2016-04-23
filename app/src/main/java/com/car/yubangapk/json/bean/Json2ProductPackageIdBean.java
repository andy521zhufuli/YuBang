package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Json2ProductIdBean:产皮包id
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-07
 */
public class Json2ProductPackageIdBean extends Base{

    //{"total":0,"rows":[{"packageName":"美孚速霸保养包","id":"42d3e125-e906-11e5-b9b6-28d244001fe5","packageCode":"PG0001"}]}

    private String id;    //产品包的id
    private String pathCode;
    private String packageName;
    private String packageCode;



    private String sort;//不用
    private String skipType;//不用
    private String photoName;//不用
    private String serviceName;//不用


    public String getRepairService() {
        return repairService;
    }

    public void setRepairService(String repairService) {
        this.repairService = repairService;
    }

    String repairService;//为了项目需要



    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
