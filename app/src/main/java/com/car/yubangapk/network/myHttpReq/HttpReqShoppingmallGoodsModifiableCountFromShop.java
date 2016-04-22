package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2ShopServiceBean;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;
import com.car.yubangapk.json.formatJson.Json2ShopService;
import com.car.yubangapk.json.formatJson.Json2ShoppingmallBottomPics;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.MyStringCallback;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/4/22.
 *
 * 从门店进入产品包
 *      获取可修改产品包的数量
 *
 */
public class HttpReqShoppingmallGoodsModifiableCountFromShop
{
    private String TAG = "产品包, 获取可编辑数目";


    String mRepairService;
    String mFrom;
    String mCarType;
    String mShopInfoID;
    HttpReqModifiableCountFromShopCallback mCallback;

    public void setCallbac(HttpReqModifiableCountFromShopCallback callback)
    {
        this.mCallback = callback;
    }

    public void getModifiableCount(String serviceId, String mFrom, String carType, String shopId)
    {
        this.mRepairService = serviceId;
        this.mFrom = mFrom;
        this.mCarType = carType;
        this.mShopInfoID = shopId;
        httpGetLogicalService(serviceId, mFrom,mCarType);

    }


    /**
     * 从商城或者门店进来, 去判断有多少个项目 可以修改,  最顶部显示个数
     * 这里   就是根据上一个页面传来的repairService 去拿logicalService
     *
     * 然后  根据logicalService去拿repairService 的list
     *
     * 根据这个list 去修改界面去拿可选择的产品包名字
     *
     * 也根据这个list去显示一共多少个项目可以修改
     * @param serviceId
     * @param mFrom
     */
    private void httpGetLogicalService(String serviceId, String mFrom, String carType)
    {

            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_GETDATA)
                    .addParams("sqlName", "clientSearchRepairService")
                    .addParams("dataReqModel.args.needTotal","needTotal")
                    .addParams("dataReqModel.args.id",serviceId)
                    .build()
                    .executeMy(new LogicalServiceCallback(),0);
            L.i(TAG, "service all kinds bottom " + 0  + " url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                    + "sqlName=clientSearchRepairService&dataReqModel.args.needTotal=needTotal&dataReqModel.args.id="+serviceId);
    }


    /**
     * @function httpGetLogicalService 调用这个回调
     *
     */
    public class LogicalServiceCallback extends MyStringCallback
    {
        @Override
        public void onError(Call call, int position, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
            //这时候点击任何一个都会提示不能点击  不能进入到下一个页面
        }

        @Override
        public void onResponse(String response, int position) {
            L.i(TAG, "中部以下的图片json = " + response);
            L.i(TAG, "中部以下的图片position = " + position);
            Json2ShoppingmallBottomPics json2ShoppingmallBottomPics = new Json2ShoppingmallBottomPics(response);
            List<Json2ShoppingmallBottomPicsBean> beans = json2ShoppingmallBottomPics.getShoppingmallBottomPics();
            if (beans == null)
            {
                //提示更新app
                mCallback.onFail(ErrorCodes.ERROR_CODE_LOW_VERSION, ErrorCodes.LOW_VERSION_TO_UPGRADE_APP);
            }
            else
            {
                for (Json2ShoppingmallBottomPicsBean bean : beans)
                {
                    if (bean.isHasData() == true)
                    {
                        httpGetShopServiceListByLogicalService(bean.getLogicalService(), mShopInfoID);

                    }
                    else
                    {
                        mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                        break;
                    }
                }
            }
        }
    }


    /**
     * 这里是从门店进来 去拿多少个产品可以修改   最顶端显示  第一步
     *
     * 根据logicalService 和门店id  还有车型  去拿到  看看拿的的是什么
     * @param logicalService
     * @param mShopInfoID
     */
    private void httpGetShopServiceListByLogicalService(String logicalService, String mShopInfoID) {

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchShopService")
                .addParams("dataReqModel.args.needTotal", "needTotal")
                .addParams("dataReqModel.args.shop",mShopInfoID)
                .addParams("dataReqModel.args.carType",mCarType)
                .addParams("dataReqModel.args.logicalService", logicalService)
                .build()
                .execute(new GetShopServiceList());

        L.i("产品包", "通过logicalService获取的url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                + "sqlName=" + "clientSearchShopService"
                + "&dataReqModel.args.shop=" + mShopInfoID
                + "&dataReqModel.args.needTotal=needTotal"
                + "&dataReqModel.args.carType=" + mCarType
                + "&dataReqModel.args.logicalService=" + logicalService
        );

    }
    class GetShopServiceList extends StringCallback
    {
        @Override
        public void onError(Call call, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }
        @Override
        public void onResponse(String response) {
            L.d(TAG,"通过logicalService获取的 json = " +  response);

            Json2ShopService shopBena = new Json2ShopService(response);
            List<Json2ShopServiceBean> beans = shopBena.getShopShowData();
            if (beans == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_LOW_VERSION, ErrorCodes.LOW_VERSION_TO_UPGRADE_APP);
                return;
            }
            else
            {
                for (Json2ShopServiceBean bean : beans)
                {
                    if (bean.isHasData() == true)
                    {
                        mModifyableSHopItemList.add(bean);
                    }
                }
                mCallback.onSuccess(mModifyableSHopItemList);
            }
        }
    }

    private List<Json2ShopServiceBean> mModifyableSHopItemList = new ArrayList<>();



}
