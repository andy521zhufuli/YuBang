package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;
import com.car.yubangapk.json.formatJson.Json2ProductPackage;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.MyObjectStringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/5/12.
 *
 * 产皮包界面顶部点击修改产品包, 根据所选产品包的ids 拿到所选产品包的所有产品
 * 每个产品包的产品不是分次返回, 而是将所有的产品包遍历之后,
 * 拿到所有的产品包产品,
 * 所有都放在同一个list中, 将这个list返回
 * 这样就修复了, 如果选择多个产品包, 但是没有产品会先bug的问题
 */
public class HttpReqModifyPPkgByPkgIds
{

    private String TAG = HttpReqModifyPPkgByPkgIds.class.getSimpleName();
    private GetProductPackageContent mCallback;

    private int mPPkgSize = 0;

    private int mCurrentTime = 0;//当前是取ppkg中第几个




    /**
     * HttpReqProductPackageFromMallBannerShop  类new出来之后, 一定要设置回调  不然不能完成功能
     * @param callback
     */
    public void setInterface(GetProductPackageContent callback)
    {
        this.mCallback = callback;
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
        mPPkgSize = size;
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
            synchronized (this)
            {
                mCurrentTime++;
                if (mCurrentTime == mPPkgSize)
                {
                    mCallback.onGetPPkgFail(ErrorCodes.ERROR_CODE_NETWORK);
                    //刚好最后一个的时候出现了错误  那就提示网路错误 不加载选择的产品包
                    toastMgr.builder.display("网络错误", 1);
                }
            }

        }

        @Override
        public void onResponse(String response, Object object, int position) {

            synchronized (this)
            {
                mCurrentTime++;
                Json2ProductPackageIdBean ids = (Json2ProductPackageIdBean) object;
                //这里对应的改
                Json2ProductPackage json2ProductPackage = new Json2ProductPackage(response);
                final List<Json2ProductPackageBean> json2ProductPackageBeanList = json2ProductPackage.getProductPackage();

                if (json2ProductPackageBeanList == null)
                {
                    if(mCurrentTime == mPPkgSize)
                    {
                        mCallback.onGetPPkgFail(ErrorCodes.ERROR_CODE_SERVER_ERROR);
                    }
                }
                else
                {

                    if (json2ProductPackageBeanList.get(0).isHasData() == false)
                    {
                        //没有产品包
                        if (mCurrentTime == mPPkgSize)
                        {
                            if (mPruductListForAllPkgs.size() > 0)//说经已经拿到了产品包里面的产品, 就是有产品, 不是所有的产品包里面都是没产品的, 那么就返回, 给listview显示
                            {
                                mCallback.onGetPPkgSucces(mPruductListForAllPkgs);
                            }
                            else
                            {
                                //所选的产皮包,里面都是空的 那就就返回空
                                mCallback.onGetPPkgFail(ErrorCodes.ERROR_CODE_NO_PRODUCT_PKG);
                            }
                        }
                        toastMgr.builder.display("您选择的" + ids.getPackageName() + "产品包,没有相关的产品", 1);
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
                            mPruductListForAllPkgs.add(bean);//放进去
                        }
                        //
                        if (mCurrentTime == mPPkgSize)
                        {
                            mCallback.onGetPPkgSucces(mPruductListForAllPkgs);
                        }
                    }
                }
            }
        }
    }
    List<Json2ProductPackageBean> mPruductListForAllPkgs = new ArrayList<>();


}
