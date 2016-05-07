package com.car.yubangapk.json.bean.OrderDetail;

/**
 * Created by andy on 16/5/7.
 *
 * 我的订单里面的产品包里面的产品
 *
 */
public class OrderProductModels
{
//    {
//        "productId": "028be795-b7ce-419d-8883-0bee934f6d33",
//            "productName": "不告诉你",
//            "price": 2222,
//            "num": 1,
//            "pathCode": "2",
//            "photoName": "4e304edf-d3f6-43c9-b751-3dba522d3254.jpg",
//            "des": "半合成"
//    },



    String productId;
    String productName;
    double price;
    int num;
    String pathCode;
    String photoName;
    String des;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
