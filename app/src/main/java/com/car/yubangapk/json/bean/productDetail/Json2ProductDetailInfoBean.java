package com.car.yubangapk.json.bean.productDetail;


import com.car.yubangapk.json.bean.OrderDetail.BaseJson;

/**
 * Created by andy on 16/5/9.
 *
 * 产品包里面 单个产品的详情
 *
 */
public class Json2ProductDetailInfoBean extends BaseJson
{
//    {"productId":"673784aa-a22e-4515-937a-7ce930c3b739",
//            "productPhoto":"",
//            "pathCode":"",
//            "productName":"长城汽油机油",
//            "productPrice":512.0,
//            "buyNum":6,
//            "isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS","message":"查询成功"}


    String productId;
    String productPhoto;
    String pathCode;
    String productName;
    double productPrice;
    int    buyNum;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }
}
