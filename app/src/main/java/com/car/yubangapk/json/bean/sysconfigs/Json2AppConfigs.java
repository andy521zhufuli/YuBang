package com.car.yubangapk.json.bean.sysconfigs;

import com.car.yubangapk.json.bean.OrderDetail.BaseJson;

/**
 * Created by andy on 16/5/10.
 * app配置相关
 *
 */
public class Json2AppConfigs extends BaseJson
{

//    {
//        "errorFileMd5Code": "908ccdc8717d200e2e90d6b43fe7d129",
//        "maxDiatance": 100000000,
//
//        "isJson": true, "isReturnStr": false, "returnCode": 0, "returneMsg": "SUCCESS", "message": "获取配置成功"
//    }


    String errorFileMd5Code;
    long   maxDiatance;
    Sys    sys;

    public long getMaxDiatance() {
        return maxDiatance;
    }

    public void setMaxDiatance(long maxDiatance) {
        this.maxDiatance = maxDiatance;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getErrorFileMd5Code() {

        return errorFileMd5Code;
    }

    public void setErrorFileMd5Code(String errorFileMd5Code) {
        this.errorFileMd5Code = errorFileMd5Code;
    }
}
