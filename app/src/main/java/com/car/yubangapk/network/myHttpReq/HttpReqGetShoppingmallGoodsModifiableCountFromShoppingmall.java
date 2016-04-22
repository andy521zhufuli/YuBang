package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;
import com.car.yubangapk.json.formatJson.Json2ProductPackageId;
import com.car.yubangapk.json.formatJson.Json2ShoppingmallBottomPics;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.MyStringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/4/22.
 *
 * 从商城进入产品包
 *      获取可修改产品包的数量
 */
public class HttpReqGetShoppingmallGoodsModifiableCountFromShoppingmall
{
    private String TAG = "产品包, 获取可编辑数目";

    String mRepairService;
    String mFrom;
    String mCarType;
    HttpReqModifiableCountFromShoppingmallCallback mCallback;

    public HttpReqGetShoppingmallGoodsModifiableCountFromShoppingmall()
    {
    }


    public void setCallbac(HttpReqModifiableCountFromShoppingmallCallback callback)
    {
        this.mCallback = callback;
    }

    public void getModifiableCount(String repairService, String from, String carType)
    {
        this.mRepairService = repairService;
        this.mFrom = from;
        this.mCarType = carType;
        httpGetLogicalService( repairService,  from,  carType);
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
                .addParams("dataReqModel.args.id",mRepairService)
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
                mCallback.onFail(ErrorCodes.ERROR_CODE_LOW_VERSION, ErrorCodes.LOW_VERSION_TO_UPGRADE_APP);
                //提示更新app
//                toastMgr.builder.display("版本太低, 请更新app", 1);
            }
            else
            {
                for (Json2ShoppingmallBottomPicsBean bean : beans)
                {
                    if (bean.isHasData() == true)
                    {

                        //有数据  拿到了数据
                        httpGetRepairServiceListByLogicalService(bean.getLogicalService());
                    }
                    else
                    {
                        mCallback.onFail(ErrorCodes.ERROR_CODE_NO_DATA, ErrorCodes.NO_DATA);
//                        toastMgr.builder.display("服务器异常,请返回重新加载!", 0);
                        break;
                    }
                }
            }
        }
    }

    /**
     * LogicalServiceCallback调用这个方法
     *
     *从店铺进来  去拿有多少个项目可以修改  最上面显示
     * 这是第二步骤
     * @param logicalService
     */
    private synchronized void httpGetRepairServiceListByLogicalService(String logicalService)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchRepairService")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .addParams("dataReqModel.args.logicalService",logicalService)
                .build()
                .executeMy(new GetRepairServiceListCallback(),0);
        L.i(TAG, "logicalService list " + " url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                + "sqlName=clientSearchRepairService&dataReqModel.args.needTotal=needTotal&dataReqModel.args.logicalService="+logicalService);
    }


    /**
     * httpGetRepairServiceListByLogicalService 调用这个回调
     */
    class GetRepairServiceListCallback extends MyStringCallback
    {
        @Override
        public void onError(Call call, int position, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
//            toastMgr.builder.display("网络连接有问题, 请教检查您的网络设置", 1);
            //这时候点击任何一个都会提示不能点击  不能进入到下一个页面
        }

        @Override
        public void onResponse(String response, int position) {
            L.i(TAG, "中部以下的图片json = " + response);
            L.i(TAG, "中部以下的图片position = " + position);
            Json2ShoppingmallBottomPics json2ShoppingmallBottomPics = new Json2ShoppingmallBottomPics(response);
            List<Json2ShoppingmallBottomPicsBean> beans = json2ShoppingmallBottomPics.getShoppingmallBottomPics();

            //TODO  很重要
            mModifyableItemShoppingmallBottomPicBeanList = beans;

            if (beans == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_LOW_VERSION, ErrorCodes.LOW_VERSION_TO_UPGRADE_APP);
                //提示更新app
//                toastMgr.builder.display("版本太低, 请更新app", 1);
            }
            else
            {
                //有数据  拿到了数据
                int size = beans.size();

                for (int pos = 0; pos < size; pos++) {
                    if (beans.get(pos).isHasData() == true)
                    {
                        String id = beans.get(pos).getId();
                        httpGetProductPackageIds(id,mCarType, pos);
                    }
                    else
                    {
                        mCallback.onFail(ErrorCodes.ERROR_CODE_NO_DATA, ErrorCodes.NO_DATA);
//                        toastMgr.builder.display("服务器异常!", 0);
                    }
                }
            }
        }
    }

    int mSize = 0;

    /**
     * GetRepairServiceListCallback调用这个方法  这个是真正的把多少个可修改显示到顶部
     * 这里是从商城进来 去拿产品包
     *
     * 根据参数去获取产品包id
     *
     * @param serviceId 门店id
     * @param carType 车主车类型
     */
    private synchronized void  httpGetProductPackageIds(String serviceId, String carType, int position) {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchCarRepairServiceProductPackage")
                .addParams("dataReqModel.args.needTotal", "needTotal")
                .addParams("dataReqModel.args.carType", carType)
                .addParams("dataReqModel.args.repairService",serviceId)
                .build()
                .executeMy(new GetProductPackageIdsCallback(), position);

        L.i(TAG, "获取产皮包修改界面 id url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                + "sqlName=" + "clientSearchCarRepairServiceProductPackage"
                + "&dataReqModel.args.carType=" + carType
                + "&dataReqModel.args.needTotal=needTotal"
                + "&dataReqModel.args.repairService=" + serviceId
        );

    }

    /**
     * httpGetProductPackageIds调用这个回调  通过这个回调真正的把多少个可修改显示在顶部
     */
    class GetProductPackageIdsCallback extends MyStringCallback {


        @Override
        public void onError(Call call, int position, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
//            toastMgr.builder.display("服务器错误", 1);

            //这里应该在布局文件里面写多一个  就是提示用户 没有相关产品包
        }

        @Override
        public void onResponse(String response, int position) {
            L.d(TAG, "产品包id json = " + response);

            synchronized (this)
            {
                Json2ProductPackageId json2ProductId = new Json2ProductPackageId(response);
                final List<Json2ProductPackageIdBean> json2ProductIdBeanList = json2ProductId.getProductIds();
                if (json2ProductId == null)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_LOW_VERSION, ErrorCodes.LOW_VERSION_TO_UPGRADE_APP);

                }
                else
                {
                    for (Json2ProductPackageIdBean bean : json2ProductIdBeanList)
                    {
                        if (bean.isHasData() == false)
                        {

                        }
                        else
                        {
                            mModifyableItemList.add(bean);
                        }
                    }
                    mCallback.onSuccess(mModifyableItemList, mModifyableItemShoppingmallBottomPicBeanList);
                }
            }
        }
    }

    private List<Json2ProductPackageIdBean> mModifyableItemList = new ArrayList<>();
    //传递给可修改的那一页
    private List<Json2ShoppingmallBottomPicsBean> mModifyableItemShoppingmallBottomPicBeanList;
}
