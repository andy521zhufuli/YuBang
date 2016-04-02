package com.car.yubangapk.json.bean;

/**
 * SysConfig:app一启动就去拿这个数据  一些配置参数
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-02
 */
public class SysConfig {
    //json格式0
    // {"errorFileMd5Code":"908ccdc8717d200e2e90d6b43fe7d129","isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS"}
    private String errorFileMd5Code;

    private boolean isJson;

    private boolean isReturnStr;

    private String returnCode;

    private String returneMsg;

    public String getErrorFileMd5Code() {
        return errorFileMd5Code;
    }

    public void setErrorFileMd5Code(String errorFileMd5Code) {
        this.errorFileMd5Code = errorFileMd5Code;
    }

    public boolean getIsJson() {
        return isJson;
    }

    public void setIsJson(boolean isJson) {
        this.isJson = isJson;
    }

    public boolean getIsReturnStr() {
        return isReturnStr;
    }

    public void setIsReturnStr(boolean isReturnStr) {
        this.isReturnStr = isReturnStr;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturneMsg() {
        return returneMsg;
    }

    public void setReturneMsg(String returneMsg) {
        this.returneMsg = returneMsg;
    }
}
