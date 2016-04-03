package com.car.yubangapk.json.bean;

/**
 * Json2CarCompany:返回汽车公司的json格式
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-02
 */
public class Json2CarCompanyBean {

    //{"total":0,"rows":[{"companyName":"一汽","id":"fb617c59-f97e-11e5-bc6d-52540022d049"}]}


    private String companyName;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
