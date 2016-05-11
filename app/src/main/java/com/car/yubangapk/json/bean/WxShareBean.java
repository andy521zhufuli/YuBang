package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.bean.OrderDetail.BaseJson;

/**
 * Created by andy on 16/5/10.
 */
public class WxShareBean extends BaseJson
{
//    {
//            "url":"www.qq.com?phone\u003d12",
//            "title":"yubang",
//            "dest":"专业的修车网站",
//            "buildTransaction":"webpage",
//            "imageUrl":"",
//
//
//            "isJson":true,
//            "isReturnStr":false,
//            "returnCode":0,
//            "returneMsg":"SUCCESS",
//            "message":"获取配置成功"}


    String url;
    String title;
    String dest;
    String buildTransaction;
    String imageUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getBuildTransaction() {
        return buildTransaction;
    }

    public void setBuildTransaction(String buildTransaction) {
        this.buildTransaction = buildTransaction;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
