package com.car.yubangapk.network.myHttpReq;

/**
 * HttpReqProductPackageFromMallBannerShop:产品包界面的网络请求
 *
 * 从商城 banner 门店进入  都需要serviceId 去拿对应的产品包
 *
 *
 * @author andy
 * @version 1.0
 * @created 2016-02-22
 */

import android.content.Context;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;
import com.car.yubangapk.json.formatJson.Json2ProductPackage;
import com.car.yubangapk.json.formatJson.Json2ProductPackageId;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.MyObjectStringCallback;
import com.car.yubangapk.network.okhttp.callback.MyPPStringCallback;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/4/16.
 */
public class HttpReqProductPackageFromMallBannerShop
{


    private String TAG = HttpReqProductPackageFromMallBannerShop.class.getSimpleName();
    private String TAG_GET_P_PKG_CONTENT_BY_PPID = "根据产皮包id获取产品包内容";

    private String LOW_VERSION_TO_UPGRADE_APP = "您当前版本太低,请升级版本.";
    private String NETWORK_ERROR = "网络错误";
    private String SERVER_ERROR = "服务器错误";
    private String NO_PROCUDT_PACKAGE = "对不起, 没有相关产品包";

    private int ERROR_CODE_LOW_VERSION = 0;
    private int ERROR_CODE_NETWORK = 1;
    private int ERROR_CODE_SERVER  = 2;
    private int ERROR_CODE_NO_PRODUCT_PKG = 3;

    private String mServiceId;
    private String mCarType;
    private Context mContext;
    private GetProductPackageContent mCallback;

    private static final String SQL_NAME_KEY = "sqlName";
    private static final String SQL_NAME_VALYE_GET_PRODUCK_PKG_ID = "clientSearchCarRepairServiceProductPackage";//获取产皮包的id
    private static final String ARGS_NEED_TOTAL = "dataReqModel.args.needTotal";
    private static final String ARGG_CARTYPE = "dataReqModel.args.carType";
    private static final String ARGG_REPAIR_SERVICE = "dataReqModel.args.repairService";
    private static final String ARGG_PRODUCT_PACKAGE = "dataReqModel.args.productPackage";






    public HttpReqProductPackageFromMallBannerShop()
    {

    }

    public HttpReqProductPackageFromMallBannerShop(String serviceId, String carType, Context context)
    {
        this.mServiceId = serviceId;
        this.mCarType = carType;
        this.mContext = context;
    }


    /**
     * HttpReqProductPackageFromMallBannerShop  类new出来之后, 一定要设置回调  不然不能完成功能
     * @param callback
     */
    public void setInterface(GetProductPackageContent callback)
    {
        this.mCallback = callback;
    }

    /**
     * 根据serviceId 还有carType 去拿到对应产品包的id
     */
    public void getProductPackageId()
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams(SQL_NAME_KEY, SQL_NAME_VALYE_GET_PRODUCK_PKG_ID)
                .addParams(ARGS_NEED_TOTAL, "needTotal")
                .addParams(ARGG_CARTYPE, mCarType)
                .addParams(ARGG_REPAIR_SERVICE,mServiceId)
                .build()
                .execute(new GetProductPackageIdCallback());

        L.i(TAG, "获取产品包id url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                + "sqlName=" + "clientSearchCarRepairServiceProductPackage"
                + "&dataReqModel.args.carType=" + mCarType
                + "&dataReqModel.args.needTotal=needTotal"
                + "&dataReqModel.args.repairService=" + mServiceId
        );
    }

    /**
     * httpGetProductPackageId调用
     */
    private class GetProductPackageIdCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e) {


            mCallback.onGetPPkgFail(ERROR_CODE_SERVER);
            //这里应该在布局文件里面写多一个  就是提示用户 没有相关产品包
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "产品包id json = " + response);

            Json2ProductPackageId json2ProductId = new Json2ProductPackageId(response);
            final List<Json2ProductPackageIdBean> json2ProductIdBeanList = json2ProductId.getProductIds();

            //mJson2ProductPackageIdBeanList = json2ProductIdBeanList;
            if (json2ProductIdBeanList == null)
            {
                toastMgr.builder.display(LOW_VERSION_TO_UPGRADE_APP, 1);
                mCallback.onGetPPkgFail(ERROR_CODE_LOW_VERSION);
                //这里需要修改
            }
            else
            {
                if (json2ProductIdBeanList.get(0).isHasData() == false)
                {
                    //没有产品包
                    mCallback.onGetPPkgFail(ERROR_CODE_NO_PRODUCT_PKG);

                }
                else
                {
                    //拿到数据了
                    //就去拿产品包对应的商品
                    httpGetProductPackageById(json2ProductIdBeanList);
                }
            }
        }
    }


    /**
     * 根据刚才得到的ppkgId bean list  去遍历list  拿到所有pkgid 的产品包内容
     *
     * GetProductPackageIdCallback调用此方法
     *
     * 通过产品包id 去拿产品包的信息
     * @param ids 产品包id
     */
    public void httpGetProductPackageById(List<Json2ProductPackageIdBean> ids)
    {

        int size = 0;
        size = ids.size();
        //取第一个
        String productPackageId = ids.get(0).getId();
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams(SQL_NAME_KEY, "clientSearchProductPackageProduct")
                .addParams(ARGS_NEED_TOTAL, "needTotal")
                .addParams(ARGG_PRODUCT_PACKAGE, productPackageId)
                .build()
                .executeObject(new GetProductPackageCallback(), ids, 0);

        L.i(TAG, TAG_GET_P_PKG_CONTENT_BY_PPID + "URL = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                + "sqlName=" + "clientSearchProductPackageProduct"
                + "&dataReqModel.args.productPackage=" + productPackageId
                + "&dataReqModel.args.needTotal=needTotal"
        );
    }

    /**
     * httpGetProductPackageById调用次回调
     *
     * 然后生成adapter 在listview里面去展示
     *
     */
    class GetProductPackageCallback extends MyObjectStringCallback {

        @Override
        public void onError(Call call, Object object, int position, Exception e) {
            toastMgr.builder.display(LOW_VERSION_TO_UPGRADE_APP, 1);
            mCallback.onGetPPkgFail(ERROR_CODE_LOW_VERSION);
        }

        @Override
        public void onResponse(String response, Object object, int position) {
            L.d(TAG,  TAG_GET_P_PKG_CONTENT_BY_PPID + "json = " + response);

            List<Json2ProductPackageIdBean> ids = (List<Json2ProductPackageIdBean>) object;

            synchronized (this)
            {
                //这里对应的改
                Json2ProductPackage json2ProductPackage = new Json2ProductPackage(response);
                final List<Json2ProductPackageBean> json2ProductPackageBeanList = json2ProductPackage.getProductPackage();

                if (json2ProductPackageBeanList == null)
                {
                    toastMgr.builder.display(LOW_VERSION_TO_UPGRADE_APP, 1);
                }
                else
                {

                    if (json2ProductPackageBeanList.get(0).isHasData() == false)
                    {
                        //没有产品包
                        mCallback.onGetPPkgFail(ERROR_CODE_NO_PRODUCT_PKG);
                    }
                    else
                    {
                        //拿到产品包  就去listview里面显示
                        for (Json2ProductPackageBean bean : json2ProductPackageBeanList)
                        {
                            //设置产品包的名字
                            bean.setPackageName(ids.get(0).getPackageName());
                            bean.setProductPackageId(ids.get(0).getId());
                            bean.setRepairService(mServiceId);
                        }
                        //
                        if (mCallback == null)
                        {
                            //不错认识事情
                        }
                        else
                        {
                            mCallback.onGetPPkgSucces(json2ProductPackageBeanList);
                        }
                    }
                }
            }
        }
    }


    /**
     * 调用成功或者失败的接口  用来回调
     */
    public interface GetProductPackageContent
    {
        void onGetPPkgSucces(List<Json2ProductPackageBean> json2ProductPackageBeanList);//得到的产品包列表
        void onGetPPkgFail(int errorCode);//失败的理由
    }


    /**
     * 根据从修改界面拿回来的数据, 去重新加载产品包  所有产品包
     * 通过产品包的id 去拿产品包  所有产品的id  拿到所有的产品包
     * @param ppList
     */
    public void httpGetProductPackageByIds(List<Json2ProductPackageIdBean> ppList)
    {

        int size = ppList.size();
        int index = 0;

        for (index = 0; index < size; index++)
        {
            String productPackageId = ppList.get(index).getId();
            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                    .addParams("sqlName", "clientSearchProductPackageProduct")
                    .addParams("dataReqModel.args.needTotal", "needTotal")
                    .addParams("dataReqModel.args.productPackage", productPackageId)
                    .build()
                    .executeObject(new GetProductPackagesCallback(), ppList.get(index), index);

            L.i("产品包", "获取产品包id url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                    + "sqlName=" + "clientSearchProductPackageProduct"
                    + "&dataReqModel.args.needTotal=needTotal"
                    + "&dataReqModel.args.productPackage=" + productPackageId
            );
        }
    }

    class GetProductPackagesCallback extends MyObjectStringCallback {

        @Override
        public void onError(Call call, Object object, int position, Exception e) {
            mCallback.onGetPPkgFail(ErrorCodes.ERROR_CODE_LOW_VERSION);
        }

        @Override
        public void onResponse(String response, Object object, int position) {


            synchronized (this)
            {
                Json2ProductPackageIdBean ids = (Json2ProductPackageIdBean) object;
                //这里对应的改
                Json2ProductPackage json2ProductPackage = new Json2ProductPackage(response);
                final List<Json2ProductPackageBean> json2ProductPackageBeanList = json2ProductPackage.getProductPackage();

                if (json2ProductPackageBeanList == null)
                {
                    mCallback.onGetPPkgFail(ErrorCodes.ERROR_CODE_SERVER_ERROR);
                }
                else
                {

                    if (json2ProductPackageBeanList.get(0).isHasData() == false)
                    {
                        //没有产品包
                        mCallback.onGetPPkgFail(ErrorCodes.ERROR_CODE_NO_PRODUCT_PKG);
                    }
                    else
                    {
                        //拿到产品包  就去listview里面显示
                        for (Json2ProductPackageBean bean : json2ProductPackageBeanList)
                        {
                            //设置产品包的名字
                            bean.setPackageName(ids.getPackageName());
                            bean.setProductPackageId(ids.getId());
                            bean.setRepairService(ids.getRepairService());
                        }
                        //
                        if (mCallback == null)
                        {
                            //不错认识事情
                        }
                        else
                        {
                            mCallback.onGetPPkgSucces(json2ProductPackageBeanList);
                        }
                    }
                }
            }
        }
    }















}
