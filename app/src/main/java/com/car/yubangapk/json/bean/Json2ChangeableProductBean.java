package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Json2ChangeableProductBean:可更换信息
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-010
 */

/**
 * Created by andy on 16/4/7.
 */
public class Json2ChangeableProductBean extends Base
{

//获取更换产品 json = {"total":0,"rows":[
// {"categoryName":"刹车油",
// "category":"6f134e82-23e6-4015-a6be-c16a212c7c1e",
// "pathCode":"2",
// "retailPrice":220.0,
// "productCode":"PP00001",
// "photoName":"5f0c5700-aa3b-40cc-bfb8-3b160f507e3d.png",
// "productShow":"合成",
// "productName":"爆发刹车油"}]}
    private String categoryName;

    private double retailPrice;

    private String pathCode;

    private String category;

    private String productCode;

    private String photoName;

    private String productShow;

    private String productName;



    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductShow() {
        return productShow;
    }

    public void setProductShow(String productShow) {
        this.productShow = productShow;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getProductName() {

        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPathCode() {

        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public double getRetailPrice() {

        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getCategoryName() {

        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
