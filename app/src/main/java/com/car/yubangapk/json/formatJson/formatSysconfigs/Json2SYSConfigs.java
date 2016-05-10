package com.car.yubangapk.json.formatJson.formatSysconfigs;

import android.content.Context;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.sysconfigs.Json2AppConfigs;
import com.car.yubangapk.json.bean.sysconfigs.Sys;
import com.car.yubangapk.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andy on 16/5/10.
 */
public class Json2SYSConfigs
{
    Context mContext;

    public Json2SYSConfigs(Context context)
    {
        this.mContext = context;
    }

    public Json2AppConfigs getAppConfigs()
    {
        Json2AppConfigs json2AppConfigs = new Json2AppConfigs();
        String json = (String) SPUtils.get(mContext, Configs.APP_SYS_CONFIG, "");
        if (json == null)
        {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            String errorFileMd5Code = JSONUtils.getString(jsonObject, "errorFileMd5Code", "");

            long   maxDiatance = JSONUtils.getLong(jsonObject, "maxDiatance", 0);
            boolean isJson;
            boolean isReturnStr;
            int returnCode;
            String returneMsg;
            String message;

            isJson = jsonObject.getBoolean("isJson");
            isReturnStr = jsonObject.getBoolean("isReturnStr");
            returnCode = jsonObject.getInt("returnCode");
            returneMsg = jsonObject.getString("returneMsg");
            message = jsonObject.getString("message");

            json2AppConfigs.setErrorFileMd5Code(errorFileMd5Code);
            json2AppConfigs.setMaxDiatance(maxDiatance);
            json2AppConfigs.setIsJson(isJson);
            json2AppConfigs.setIsReturnStr(isReturnStr);
            json2AppConfigs.setReturnCode(returnCode);
            json2AppConfigs.setReturneMsg(returneMsg);
            json2AppConfigs.setMessage(message);

            JSONObject sysObject = JSONUtils.getJSONObject(jsonObject, "sys", null);
            String id = JSONUtils.getString(sysObject, "id", "");
            String czVersion = JSONUtils.getString(sysObject, "czVersion", "");
            String wxAppid = JSONUtils.getString(sysObject, "wxAppid", "");
            String wxAppSecret = JSONUtils.getString(sysObject, "wxAppSecret", "");
            String czUploadUrl = JSONUtils.getString(sysObject, "czUploadUrl", "");
            String errorFileMd5code1 = JSONUtils.getString(sysObject, "errorFileMd5code", "");

            Sys sys = new Sys();
            sys.setId(id);
            sys.setCzVersion(czVersion);
            sys.setCzUploadUrl(czUploadUrl);
            sys.setWxAppid(wxAppid);
            sys.setWxAppSecret(wxAppSecret);
            sys.setErrorFileMd5code(errorFileMd5code1);
            json2AppConfigs.setSys(sys);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return json2AppConfigs;

    }

}
