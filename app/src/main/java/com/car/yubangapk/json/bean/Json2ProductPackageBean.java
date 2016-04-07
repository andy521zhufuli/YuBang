package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Json2ProductPackageBean:产皮包信息
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-07
 */
/**
 * Created by andy on 16/4/7.
 */
public class Json2ProductPackageBean extends Base
{

//    {"total":0,"rows":[
//        {"categoryName":"刹车油","retailPrice":220.0,"pathCode":"2","productName":"爆发刹车油","productAmount":1,"photoName":"5f0c5700-aa3b-40cc-bfb8-3b160f507e3d.png"},
//        {"categoryName":"油管","retailPrice":1.0,"pathCode":"2","productName":"爆炸油管","productAmount":1,"photoName":"827d461c-03d5-4532-9f7d-c00fc4ac6584.png"},
//        {"categoryName":"油管","retailPrice":1.0,"pathCode":"2","productName":"爆炸油管","productAmount":1,"photoName":"827d461c-03d5-4532-9f7d-c00fc4ac6584.png"}]}

    private String categoryName;
    private double retailPrice;
    private String pathCode;
    private String productName;
    private int productAmount;
    private String photoName;

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public int getProductAmount() {

        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
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
