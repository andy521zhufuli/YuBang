package com.car.yubangapk.json.bean.sysconfigs;

/**
 * Created by andy on 16/5/10.
 */
public class Sys
{
//    "sys":
//    {
//        "id": "1",
//            "czVersion": "1.0.59",
//            "wxAppid": "wx1e21e0c99e091188",
//            "wxAppSecret": "bf4619b061e33ab41781297811129d61",
//            "czUploadUrl": "https://www.pgyer.com/yubang",
//            "errorFileMd5code": "1212121"
//    },

    String id;
    String czVersion;
    String wxAppid;
    String wxAppSecret;
    String czUploadUrl;
    String errorFileMd5code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCzVersion() {
        return czVersion;
    }

    public void setCzVersion(String czVersion) {
        this.czVersion = czVersion;
    }

    public String getWxAppid() {
        return wxAppid;
    }

    public void setWxAppid(String wxAppid) {
        this.wxAppid = wxAppid;
    }

    public String getWxAppSecret() {
        return wxAppSecret;
    }

    public void setWxAppSecret(String wxAppSecret) {
        this.wxAppSecret = wxAppSecret;
    }

    public String getCzUploadUrl() {
        return czUploadUrl;
    }

    public void setCzUploadUrl(String czUploadUrl) {
        this.czUploadUrl = czUploadUrl;
    }

    public String getErrorFileMd5code() {
        return errorFileMd5code;
    }

    public void setErrorFileMd5code(String errorFileMd5code) {
        this.errorFileMd5code = errorFileMd5code;
    }
}
