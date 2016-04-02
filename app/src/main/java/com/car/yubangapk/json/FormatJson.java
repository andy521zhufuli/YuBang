package com.car.yubangapk.json;




import android.content.Context;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.BannerAd;
import com.car.yubangapk.json.bean.ShoppingmallPicBean;
import com.car.yubangapk.json.bean.SysConfig;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/3/29.
 */
public class FormatJson
{

    private static final String TAG = "FormatJson 工具类";
    String json;

    public FormatJson()
    {

    }

    public FormatJson(String json)
    {
        this.json = json;
    }



    /**
     * 返回商城首页bannner的广告链接
     * @return List<BannerAd>
     */
    public List<BannerAd> getBannerAdImageList()
    {

        List<BannerAd> bannerList = new ArrayList<BannerAd>();

        JSONObject total = null;

        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();
            String pathCode;
            String sort;
            String advertisementName;
            String status;
            String link;
            String photoName;
            String skipType;//跳转类型
            for (int i = 0; i < size; i++)
            {
                JSONObject row = (JSONObject) rows.get(i);
                pathCode = row.getString("pathCode");
                sort = row.getString("sort");
                advertisementName = row.getString("advertisementName");
                status = row.getString("status");
                link = row.getString("link");
                photoName = row.getString("photoName");
                skipType = row.getString("skipType");//跳转类型
                BannerAd ad = new BannerAd();
                ad.setPathCode(pathCode);
                ad.setSort(sort);
                ad.setAdvertisementName(advertisementName);
                ad.setStatus(status);
                ad.setLink(link);
                ad.setPhotoName(photoName);
                ad.setSkipType(skipType);
                bannerList.add(ad);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }



        return bannerList;
    }

    /**
     * 获取商城主页的图片的信息
     * json转成类
     * @return List<ShoppingmallPicBean>
     */
    public List<ShoppingmallPicBean> getShoppingMallPic()
    {
        List<ShoppingmallPicBean> shoppingmallPicBeans = new ArrayList<ShoppingmallPicBean>();

        JSONObject total = null;

        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();
            String pathCode;
            String sort;
            String advertisementName;
            String status;
            String link;
            String photoName;
            String skipType;//跳转类型
            for (int i = 0; i < size; i++)
            {
                JSONObject row = (JSONObject) rows.get(i);
                pathCode = row.getString("pathCode");
                sort = row.getString("sort");
                advertisementName = row.getString("advertisementName");
                status = row.getString("status");
                link = row.getString("link");
                photoName = row.getString("photoName");
                skipType = row.getString("skipType");//跳转类型
                ShoppingmallPicBean shoppingmallPicBean = new ShoppingmallPicBean();
                shoppingmallPicBean.setPathCode(pathCode);
                shoppingmallPicBean.setSort(sort);
                shoppingmallPicBean.setAdvertisementName(advertisementName);
                shoppingmallPicBean.setStatus(status);
                shoppingmallPicBean.setLink(link);
                shoppingmallPicBean.setPhotoName(photoName);
                shoppingmallPicBean.setSkipType(skipType);
                shoppingmallPicBeans.add(shoppingmallPicBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return shoppingmallPicBeans;
    }





    /**
     * 程序一启动时候的获取配置信息
     * @return
     */
    public SysConfig getSystemConfig(Context context)
    {
        String jsonCfg = (String) SPUtils.get(context, Configs.APP_SYS_CONFIG,null);
        //没有拿到数据
        if (jsonCfg == null)
        {
            L.d(TAG, "get MyAppConfigCallback onResponse ====" + "从sp里面拿到数据失败");
            return  null;
        }

        String errorFileMd5Code;

        boolean isJson;

        boolean isReturnStr;

        String returnCode;

        String returneMsg;

        SysConfig sysConfig = new SysConfig();

        JSONObject systemconfig = null;
        try {
            systemconfig = new JSONObject(jsonCfg);
            errorFileMd5Code = systemconfig.getString("errorFileMd5Code");
            sysConfig.setErrorFileMd5Code(errorFileMd5Code);
            isJson = systemconfig.getBoolean("isJson");
            sysConfig.setIsJson(isJson);
            isReturnStr = systemconfig.getBoolean("isReturnStr");
            sysConfig.setIsReturnStr(isReturnStr);
            returnCode = systemconfig.getString("returnCode");
            sysConfig.setReturnCode(returnCode);
            returneMsg = systemconfig.getString("returneMsg");
            sysConfig.setReturneMsg(returneMsg);

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }


        return sysConfig;
    }

}
