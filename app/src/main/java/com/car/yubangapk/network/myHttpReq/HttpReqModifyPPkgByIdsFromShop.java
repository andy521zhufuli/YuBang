package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;
import com.car.yubangapk.json.bean.Json2ShopServiceBean;
import com.car.yubangapk.json.formatJson.Json2ProductPackageId;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.MyObjectStringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/5/12.
 */
public class HttpReqModifyPPkgByIdsFromShop
{


    private String TAG = HttpReqModifyPPkgByPkgIds.class.getSimpleName();
    private GetProductPackageContent mCallback;

    private int mPPkgSize = 0;

    private int mCurrentTime = 0;//当前是取ppkg中第几个


    /**
     * 调用成功或者失败的接口  用来回调
     */
    public interface GetProductPackageContent
    {
        void onGetPPkgSucces(List<Json2ProductPackageIdBean> json2ProductPackageIdBeanList);//得到的产品包列表
        void onGetPPkgFail(int errorCode);//失败的理由
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
     * 从修改界面返回来, 确定是门店里面服务的数据
     * @param ppList
     */
    public void httpGetShopServiceByIds(List<Json2ShopServiceBean> ppList, String mCarType)
    {
        int size = ppList.size();
        mPPkgSize = size;
        int index = 0;
        for (index = 0; index < size; index++)
        {
            String repairservice = ppList.get(index).getId();
            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                    .addParams("sqlName", "clientSearchCarRepairServiceProductPackage")
                    .addParams("dataReqModel.args.needTotal", "needTotal")
                    .addParams("dataReqModel.args.carType", mCarType)
                    .addParams("dataReqModel.args.repairService", repairservice)
                    .build()
                    .executeObject(new GetProductPackageIdFromShopModify(), ppList, index);

            L.i("c产品包", "获取产品包id url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                            + "sqlName=" + "clientSearchCarRepairServiceProductPackage"
                            + "&dataReqModel.args.needTotal=needTotal"
                            + "&dataReqModel.args.repairService=" + repairservice
            );
        }
    }
    private class GetProductPackageIdFromShopModify extends MyObjectStringCallback
    {
        @Override
        public void onError(Call call, Object object, int position, Exception e) {
            synchronized (this)
            {
                mCurrentTime++;
                if (mCurrentTime == mPPkgSize)
                {
                    //刚好最后一个的时候出现了错误  那就提示网路错误 不加载选择的产品包
                    mCallback.onGetPPkgFail(ErrorCodes.ERROR_CODE_NETWORK);
                }
            }

        }
        @Override
        public void onResponse(String response, Object object, int position) {
            //从门店过来
            List<Json2ShopServiceBean> ppList = (List<Json2ShopServiceBean>) object;
            synchronized (this)
            {

                mCurrentTime++;
                Json2ProductPackageId jppid = new Json2ProductPackageId(response);
                List<Json2ProductPackageIdBean> beans = jppid.getProductIds();
                if (beans == null)
                {
                    if (mCurrentTime == mPPkgSize)
                    {
                        toastMgr.builder.display("您当前版本太低,请升级版本", 1);
                    }

                }
                else
                {
                    for (Json2ProductPackageIdBean bean : beans)
                    {
                        if (bean.isHasData() == true)
                        {
                            bean.setRepairService(ppList.get(position).getId());
                            mPPkgIdsList.add(bean);
                        }
                        else
                        {
                            toastMgr.builder.display("您选择的其中一个产品包无对应产品", 1);
                        }
                    }
                }
                if (mCurrentTime == mPPkgSize)
                {

                    mCallback.onGetPPkgSucces(mPPkgIdsList);
                }
            }

        }
    }


    List<Json2ProductPackageIdBean> mPPkgIdsList = new ArrayList<>();

}
