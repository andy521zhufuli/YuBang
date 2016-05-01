package com.car.yubangapk.json.formatJson;

import com.baidu.platform.comapi.map.C;
import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.AddressBean;
import com.car.yubangapk.json.bean.Json2AddressBean;
import com.car.yubangapk.json.bean.Json2DefaultAddressBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/4/16.
 */
public class Json2Address
{



    //address json =
    // {"defaultAddress":{"id":"0","CUserid":"bf8eb70b-c29f-41c8-a9bc-7e7dd56880bb","name":"朱福利","phone":"123456789","isDefaule":1},
    // "addresses":[
    // {"id":"0","CUserid":"bf8eb70b-c29f-41c8-a9bc-7e7dd56880bb","name":"朱福利","phone":"123456789","isDefaule":1}
    // ],"isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS"}
    String json;

    public Json2Address()
    {

    }


    public Json2Address(String json)
    {
        this.json = json;
    }

    public Json2AddressBean getAddress()
    {

        Json2AddressBean json2AddressBean = new Json2AddressBean();

        boolean isJson;
        boolean isReturnStr;
        int returnCode;
        String returneMsg;


        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);


            returnCode = jsonObject.getInt("returnCode");


            isJson = JSONUtils.getBoolean(jsonObject, "isJson", false);
            isReturnStr = JSONUtils.getBoolean(jsonObject, "isReturnStr", false);
            returneMsg = JSONUtils.getString(jsonObject, "returneMsg", "");


            json2AddressBean.setReturnCode(returnCode);
            json2AddressBean.setJson(isJson);
            json2AddressBean.setReturneMsg(returneMsg);
            json2AddressBean.setReturnStr(isReturnStr);

            if (returnCode == 100)
            {
                return json2AddressBean;
            }


            List<AddressBean> addressesList = new ArrayList<>();

            JSONArray addresses = jsonObject.getJSONArray("addresses");

            int size = addresses.length();

            if (size == 0)
            {
                //没有地址 拿到后哦首先判断地址的长度, 等于0  就是没有地址
                json2AddressBean.setAddresses(addressesList);
                return json2AddressBean;
            }

            for (int i = 0; i < size; i++)
            {
                AddressBean address = new AddressBean();
                JSONObject add = (JSONObject) addresses.get(i);

                String id       = JSONUtils.getString(add, "id", "");
                String name     = JSONUtils.getString(add,"name", "");
                String CUserid  = JSONUtils.getString(add, "CUserid", "");
                int isDefaule   = JSONUtils.getInt(add, "isDefaule", 0);
                String phone    = JSONUtils.getString(add, "phone", "");
                address.setId(id);
                address.setName(name);
                address.setCUserid(CUserid);
                address.setIsDefaule(isDefaule);
                address.setPhone(phone);
                addressesList.add(address);
            }
            json2AddressBean.setAddresses(addressesList);


            JSONObject defaultAddress = jsonObject.getJSONObject("defaultAddress");
            Json2DefaultAddressBean defaultAddressBean = new Json2DefaultAddressBean();
            String id           = JSONUtils.getString(defaultAddress, "id", "");
            String name         = JSONUtils.getString(defaultAddress,"name", "");
            String CUserid      = JSONUtils.getString(defaultAddress, "CUserid", "");
            int    isDefaule    = JSONUtils.getInt(defaultAddress, "isDefaule", 0);
            String phone        = JSONUtils.getString(defaultAddress, "phone", "");

            defaultAddressBean.setId(id);
            defaultAddressBean.setCUserid(CUserid);
            defaultAddressBean.setName(name);
            defaultAddressBean.setPhone(phone);
            defaultAddressBean.setIsDefaule(isDefaule);

            json2AddressBean.setDefaultAddress(defaultAddressBean);




        } catch (JSONException e) {
            e.printStackTrace();
            return  null;
        }
        return json2AddressBean;
    }

}
