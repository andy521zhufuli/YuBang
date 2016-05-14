package com.car.yubangapk.json.bean.search;

/**
 * Created by andy on 16/5/14.
 */
public class SearchResultProductPackageRows
{
//    {"total":1,"rows":
//        [{"repairService":"38f28eb8-798d-492f-bf94-4a61153ee5da","packageName":"美孚刹车油","id":"53df7b5b-5cca-485f-800b-c17311cf51ef"}]}

    String packageName;
    String id;
    String repairService;

    public String getRepairService() {
        return repairService;
    }

    public void setRepairService(String repairService) {
        this.repairService = repairService;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
