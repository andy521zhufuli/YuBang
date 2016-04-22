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

//    产品包
//                {"total":0,"rows":[
        //        {
        //        "categoryName":"刹车油",
        //        "retailPrice":220.0,
        //        "pathCode":"2",
        //        "category":"6f134e82-23e6-4015-a6be-c16a212c7c1e",
        //        "productCode":"PP00001",
        //        "photoName":"5f0c5700-aa3b-40cc-bfb8-3b160f507e3d.png",
        //        "productShow":"合成",
        //        "productName":"爆发刹车油",
        //        "productAmount":1},
//        {"categoryName":"油管","retailPrice":1.0,"pathCode":"2","productShow":"合成","productName":"爆炸油管","productAmount":1,"photoName":"827d461c-03d5-4532-9f7d-c00fc4ac6584.png"},{"categoryName":"油管","retailPrice":1.0,"pathCode":"2","productShow":"合成","productName":"爆炸油管","productAmount":1,"photoName":"827d461c-03d5-4532-9f7d-c00fc4ac6584.png"}]}
    private String categoryName;

    private double retailPrice;

    private String pathCode;

    private String category;

    private String productCode;

    private String photoName;

    private String productShow;

    private String productName;

    private int productAmount;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String packageName;//为了适应项目 而自己加的字段  后台没有这个字段
    private String repairService;//为了适应项目 而自己加的字段  后台没有这个字段

    public String getRepairService() {
        return repairService;
    }

    public void setRepairService(String repairService) {
        this.repairService = repairService;
    }

    public String getProductPackageId() {
        return ProductPackageId;
    }

    public void setProductPackageId(String productPackageId) {
        ProductPackageId = productPackageId;
    }

    private String ProductPackageId;//为了适应项目 而自己加的字段  后台没有这个字段  此商品对应的产品包id

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

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
