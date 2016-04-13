package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2ChangeableProductBean;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;
import com.car.yubangapk.json.bean.Json2ShopServiceBean;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;
import com.car.yubangapk.json.formatJson.Json2ProductPackage;
import com.car.yubangapk.json.formatJson.Json2ProductPackageId;
import com.car.yubangapk.json.formatJson.Json2ShopService;
import com.car.yubangapk.json.formatJson.Json2ShoppingmallBottomPics;
import com.car.yubangapk.okhttp.OkHttpUtils;
import com.car.yubangapk.okhttp.callback.MyPPStringCallback;
import com.car.yubangapk.okhttp.callback.MyStringCallback;
import com.car.yubangapk.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * ShoppingMallGoodsActivity: 商城产品包
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class ShoppingMallGoodsFromShopActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = ShoppingMallGoodsFromShopActivity.class.getName();

    private Context mContext;
    private ListView shoppingmall_goods_listview;

    //返回
    private ImageView       img_back; //返回
    private RelativeLayout btn_pay;//去结算
    private RelativeLayout btn_service;//客服
    //编辑商品
    private TextView tv_modify_goods;//编辑
    private LinearLayout    productitem_changge_before;//商品信息
    private LinearLayout    productitem_changge_after;//编辑商品之后
    private boolean         isModified = false;
    private TextView        tv_price;//商品总价
    private LinearLayout    shoppingmall_goods_modify_layout;//修改

    private TextView        modifyable_product_count;//可修改的数目显示


    private CustomProgressDialog mProgressDialog;
    String mRepairServiceServiceId;
    String mCarType;

    private ProductPackageAdapter goodsAdapter;
    private List<Json2ProductPackageBean> mJson2ProductPackageBeanList;//商城过来  保存的数据
    private List<Json2ShopServiceBean>    mJson2ShopServiceBeanList;
    private String mResponse;

    private String mRepairService;
    //传递给可修改的那一页
    private List<Json2ShoppingmallBottomPicsBean> mModifyableItemShoppingmallBottomPicBeanList;


    String mFrom;
    String mShopInfoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_mall_goods);

        mContext = this;

        findViews();

        mProgressDialog  = new  CustomProgressDialog(mContext);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
        {
            //不是从首页店铺 门店跳过来的

        }
        else
        {

            mFrom = bundle.getString(Configs.FROM);
            //从首页门店过来  就要获取产品包
            String serviceId = bundle.getString(Configs.serviceId);//服务id
            String carType = bundle.getString(Configs.mCarType);//车主车类型
            //这个是从上一个界面传过来的 为了去修改界面  备用
            mResponse = bundle.getString("repairService");

            mRepairService = serviceId;

            //去拿产品包的id  拿到产品包的id时候  再去根据产品包的id获取产品包的内容
            mRepairServiceServiceId = serviceId;
            mCarType = carType;
            if (Configs.FROM_SHOPPINGMALL.equals(mFrom))
            {
                //从商城来
                httpGetLogicalService(serviceId, mFrom);
            }
            else
            {
                //从店铺来
                mShopInfoID = bundle.getString("shopId");
                httpGetLogicalService(serviceId, mFrom);

            }

            //都需要调用这句话, 来展示产品包
            httpGetProductPackageId(serviceId, carType);
        }






    }


    /**
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
    private void httpGetLogicalService(String serviceId, String mFrom)
    {
        if (mFrom.equals(Configs.FROM_SHOPPINGMALL))
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
        else
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



    }

    public class LogicalServiceCallback extends MyStringCallback
    {


        @Override
        public void onError(Call call, int position, Exception e) {
            toastMgr.builder.display("网络连接有问题, 请教检查您的网络设置", 1);
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
                toastMgr.builder.display("版本太低, 请更新app", 1);

            }
            else
            {

                for (Json2ShoppingmallBottomPicsBean bean : beans)
                {
                    if (bean.isHasData() == true)
                    {

                        if (mFrom.equals(Configs.FROM_SHOPPINGMALL))
                        {
                            //有数据  拿到了数据
                            httpGetRepairServiceListByLogicalService(bean.getLogicalService());
                        }
                        else
                        {
                            httpGetShopServiceListByLogicalService(bean.getLogicalService(), mShopInfoID);
                        }


                    }
                    else
                    {
                        toastMgr.builder.display("服务器异常,请返回重新加载!", 0);
                        break;
                    }
                }
            }
        }
    }


    /**
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
            toastMgr.builder.display("网络错误, 请返回重新加载...", 1);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG,"通过logicalService获取的 json = " +  response);

            Json2ShopService  shopBena = new Json2ShopService(response);
            List<Json2ShopServiceBean> beans = shopBena.getShopShowData();
            if (beans == null)
            {
                toastMgr.builder.display("请升级app", 1);
                return;
            }
            else
            {
                for (Json2ShopServiceBean bean : beans)
                {
                    if (bean.isHasData() == true)
                    {
                        mModifyableSHopItemList.add(bean);
                        modifyable_product_count.setText("1个项目需要保养(共" + mModifyableSHopItemList.size() + "个项目)");
                    }
                }
            }



        }
    }

    /**
     *
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

    class GetRepairServiceListCallback extends MyStringCallback
    {
        @Override
        public void onError(Call call, int position, Exception e) {
            toastMgr.builder.display("网络连接有问题, 请教检查您的网络设置", 1);
            //这时候点击任何一个都会提示不能点击  不能进入到下一个页面
        }

        @Override
        public void onResponse(String response, int position) {
            L.i(TAG, "中部以下的图片json = " + response);
            L.i(TAG, "中部以下的图片position = " + position);
            Json2ShoppingmallBottomPics json2ShoppingmallBottomPics = new Json2ShoppingmallBottomPics(response);
            List<Json2ShoppingmallBottomPicsBean> beans = json2ShoppingmallBottomPics.getShoppingmallBottomPics();


            mModifyableItemShoppingmallBottomPicBeanList = beans;

            if (beans == null)
            {
                //提示更新app
                toastMgr.builder.display("版本太低, 请更新app", 1);

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
                        toastMgr.builder.display("服务器异常!", 0);
                    }

                }


            }
        }
    }


    /**
     * 根据参数去获取产品包id
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

    private List<Json2ProductPackageIdBean> mModifyableItemList = new ArrayList<>();
    private List<Json2ShopServiceBean> mModifyableSHopItemList = new ArrayList<>();
    class GetProductPackageIdsCallback extends MyStringCallback {


        @Override
        public void onError(Call call, int position, Exception e) {
            toastMgr.builder.display("服务器错误", 1);

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
                    toastMgr.builder.display("您当前版本太低,请升级版本", 1);

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
                            modifyable_product_count.setText("1个项目需要保养(共" + mModifyableItemList.size() + "个项目)");
                        }
                    }
                }
            }
        }
    }




    /**
     * 根据参数去获取产品包id  只选第一个作为显示
     * @param serviceId 门店id
     * @param carType 车主车类型
     */
    private void httpGetProductPackageId(String serviceId, String carType) {

        mProgressDialog = mProgressDialog.show(mContext,"正在加载店铺服务...", false, null);

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchCarRepairServiceProductPackage")
                .addParams("dataReqModel.args.needTotal", "needTotal")
                .addParams("dataReqModel.args.carType", carType)
                .addParams("dataReqModel.args.repairService",serviceId)
                .build()
                .execute(new GetProductPackageIdCallback());

        L.i("FirstPageShopShowActivity", "获取产品包id url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                        + "sqlName=" + "clientSearchCarRepairServiceProductPackage"
                        + "&dataReqModel.args.carType=" + carType
                        + "&dataReqModel.args.needTotal=needTotal"
                        + "&dataReqModel.args.repairService=" + serviceId
        );

    }

    private class GetProductPackageIdCallback extends StringCallback{

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("服务器错误", 1);
            mProgressDialog.dismiss();
            //这里应该在布局文件里面写多一个  就是提示用户 没有相关产品包
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "产品包id json = " + response);

            Json2ProductPackageId json2ProductId = new Json2ProductPackageId(response);
            final List<Json2ProductPackageIdBean> json2ProductIdBeanList = json2ProductId.getProductIds();

            mJson2ProductPackageIdBeanList = json2ProductIdBeanList;

            if (json2ProductIdBeanList == null)
            {
                toastMgr.builder.display("您当前版本太低,请升级版本", 1);
                //这里需要修改
                tv_modify_goods.setClickable(false);
            }
            else
            {
                if (json2ProductIdBeanList.get(0).isHasData() == false)
                {
                    //没有产品包
                    toastMgr.builder.display("对不起, 没有相关产品包",1);
                    AlertDialog alertDialog = new AlertDialog(mContext);
                    alertDialog.builder().setTitle("提示")
                            .setCancelable(false)
                            .setMsg("没有相关产品包")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //就关掉这个界面????
                                    ShoppingMallGoodsFromShopActivity.this.finish();
                                }
                            })
                            .show();

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
     * 通过产品包id 去拿产品包的信息
     * @param ids 产品包id
     */
    private void httpGetProductPackageById(List<Json2ProductPackageIdBean> ids)
    {

        int size = 0;
        size = ids.size();
        //取第一个
        String productPackageId = ids.get(0).getId();

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchProductPackageProduct")
                .addParams("dataReqModel.args.needTotal", "needTotal")
                .addParams("dataReqModel.args.productPackage", productPackageId)
                .build()
                .executeProcudtPkg(new GetProductPackageCallback(), ids.get(0).getPackageName());

        L.i("FirstPageShopShowActivity", "获取产品包url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                        + "sqlName=" + "clientSearchProductPackageProduct"
                        + "&dataReqModel.args.productPackage=" + productPackageId
                        + "&dataReqModel.args.needTotal=needTotal"
        );
    }
    class GetProductPackageCallback extends MyPPStringCallback{

        @Override
        public void onError(Call call, String packageName, Exception e) {
            toastMgr.builder.display("您当前版本太低,请升级版本", 1);
            //TODO 这里需要删除
            tv_modify_goods.setClickable(false);
        }

        @Override
        public void onResponse(String response, String packageName) {
            L.d(TAG, "产品包 json = " + response);
            mProgressDialog.dismiss();


            synchronized (this)
            {
                //这里对应的改
                Json2ProductPackage json2ProductPackage = new Json2ProductPackage(response);
                final List<Json2ProductPackageBean> json2ProductPackageBeanList = json2ProductPackage.getProductPackage();

                if (json2ProductPackageBeanList == null)
                {
                    toastMgr.builder.display("您当前版本太低,请升级版本", 1);
                }
                else
                {
                    if (json2ProductPackageBeanList.get(0).isHasData() == false)
                    {
                        //没有产品包
                        toastMgr.builder.display("对不起, 没有相关产品包",1);
                        AlertDialog alertDialog = new AlertDialog(mContext);
                        alertDialog.builder().setTitle("提示")
                                .setCancelable(false)
                                .setMsg("没有相关产品包")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .show();

                    }
                    else
                    {
                        //拿到产品包  就去listview里面显示
                        for (Json2ProductPackageBean bean : json2ProductPackageBeanList)
                        {
                            bean.setPackageName(packageName);
                        }
                        goodsAdapter = new ProductPackageAdapter(json2ProductPackageBeanList,FROM_SHOPPINGMALL);
                        mJson2ProductPackageBeanList = json2ProductPackageBeanList;
                        shoppingmall_goods_listview.setAdapter(goodsAdapter);
                    }
                }
            }
        }
    }



    /**
     * 根据从修改界面拿回来的数据, 去重新加载产品包  所有产品包
     * 通过产品包的id 去拿产品包  所有产品的id  拿到所有的产品包
     * @param ppList
     */
    private void httpGetProductPackageByIds(List<Json2ProductPackageIdBean> ppList)
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
                    .executeProcudtPkg(new GetProductPackagesCallback(), ppList.get(index).getPackageName());

            L.i("FirstPageShopShowActivity", "获取产品包id url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                    + "sqlName=" + "clientSearchCarRepairServiceProductPackage"
                    + "&dataReqModel.args.needTotal=needTotal"
                    + "&dataReqModel.args.productPackage=" + productPackageId
            );
        }
    }



    private void httpGetShopServiceByIds(List<Json2ProductPackageIdBean> ppList)
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
                    .executeProcudtPkg(new GetProductPackagesCallback(), ppList.get(index).getPackageName());

            L.i("FirstPageShopShowActivity", "获取产品包id url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                    + "sqlName=" + "clientSearchCarRepairServiceProductPackage"
                    + "&dataReqModel.args.needTotal=needTotal"
                    + "&dataReqModel.args.productPackage=" + productPackageId
            );
        }
    }


    private  int FROM_SHOPPINGMALL = 1;//从商品界面过来
    private static final int FROM_MODIFY       = 2;//从修改界面过来

    class GetProductPackagesCallback extends MyPPStringCallback {

        @Override
        public void onError(Call call, String packageName, Exception e) {
            toastMgr.builder.display("您当前版本太低,请升级版本", 1);
            //TODO 这里需要删除
            tv_modify_goods.setClickable(false);
        }

        @Override
        public void onResponse(String response, String packageName) {
            L.d(TAG, "产品包 json = " + response);
            mProgressDialog.dismiss();
            synchronized (this)
            {
                if (mFrom.equals(Configs.FROM_SHOPPINGMALL))
                {
                    //从商城过来
                    Json2ProductPackage json2ProductPackage = new Json2ProductPackage(response);
                    final List<Json2ProductPackageBean> json2ProductPackageBeanList = json2ProductPackage.getProductPackage();

                    if (json2ProductPackageBeanList == null)
                    {
                        toastMgr.builder.display("您当前版本太低,请升级版本", 1);
                    }
                    else
                    {

                        for (Json2ProductPackageBean bean : json2ProductPackageBeanList)
                        {
                            bean.setPackageName(packageName);
                        }

                        if (json2ProductPackageBeanList.get(0).isHasData() == false)
                        {
                            if (GET_IDS_TIMES == mGetModifyProductPkgCount)
                            {
                                //所有的id都遍历了 但是还是没有产品包
                                //没有产品包
                                toastMgr.builder.display("对不起, 没有相关产品包",1);
                                AlertDialog alertDialog = new AlertDialog(mContext);
                                alertDialog.builder().setTitle("提示")
                                        .setCancelable(false)
                                        .setMsg("没有相关产品包")
                                        .setPositiveButton("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .show();
                            }

                        }
                        else
                        {
                            if (GET_IDS_TIMES == 0)
                            {
                                //清楚现有的产皮包
                                mJson2ProductPackageBeanList.clear();
                            }

                            if (mGetModifyProductPkgCount == 1)//只选了一个产品包  那就直接显示出来
                            {
                                int size1 = json2ProductPackageBeanList.size();
                                for (int i= 0; i < size1; i++)
                                {
                                    mJson2ProductPackageBeanList.add(json2ProductPackageBeanList.get(i));
                                }
                                //goodsAdapter = new ProductPackageAdapter(json2ProductPackageBeanList);
                                //mJson2ProductPackageBeanList = json2ProductPackageBeanList;
                                goodsAdapter.refresh(mJson2ProductPackageBeanList);
                                countTotalPrice(mJson2ProductPackageBeanList);
                                GET_IDS_TIMES = 0;
                                return;
                            }
                            else
                            {
                                if (GET_IDS_TIMES  == mGetModifyProductPkgCount - 1)
                                {

                                    int size1 = json2ProductPackageBeanList.size();
                                    for (int i= 0; i < size1; i++)
                                    {
                                        mJson2ProductPackageBeanList.add(json2ProductPackageBeanList.get(i));
                                    }

                                    //所有的都遍历完了 就可以去显示了  listview显示
                                    //goodsAdapter = new ProductPackageAdapter(json2ProductPackageBeanList);
                                    //mJson2ProductPackageBeanList = json2ProductPackageBeanList;

                                    goodsAdapter.refresh(mJson2ProductPackageBeanList);
                                    countTotalPrice(mJson2ProductPackageBeanList);
                                    GET_IDS_TIMES = 0;
                                    return;
                                }
                                else
                                {
                                    //这里不知道显示的对不对
                                    int size1 = json2ProductPackageBeanList.size();
                                    for (int i= 0; i < size1; i++)
                                    {
                                        mJson2ProductPackageBeanList.add(json2ProductPackageBeanList.get(i));
                                    }

                                }
                            }



                        }
                    }
                }
                else
                {
                    //从门店过来
                }



                GET_IDS_TIMES++;

            }
        }
    }




    private void findViews() {

        //返回
        img_back = (ImageView) findViewById(R.id.img_back);
        shoppingmall_goods_listview = (ListView) findViewById(R.id.shoppingmall_goods_listview);
        tv_modify_goods = (TextView) findViewById(R.id.tv_modify_goods);
        btn_pay = (RelativeLayout) findViewById(R.id.btn_pay);
        btn_service = (RelativeLayout) findViewById(R.id.btn_service);
        tv_price = (TextView) findViewById(R.id.tv_price);
        modifyable_product_count = (TextView) findViewById(R.id.modifyable_product_count);



        shoppingmall_goods_modify_layout = (LinearLayout) findViewById(R.id.shoppingmall_goods_modify_layout);

        shoppingmall_goods_modify_layout.setOnClickListener(this);


        img_back.setOnClickListener(this);
        /**
         * 监听器
         */
        //编辑商品
        tv_modify_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO

                isModifyList = true;

                //一开始是编辑状态
                if (isModified == false)
                {
                    //还没编辑 已经点击  那就去编辑

                    isModified = true;
                    tv_modify_goods.setText("保存");
                    goodsAdapter.refresh(mJson2ProductPackageBeanList);
                    shoppingmall_goods_listview.deferNotifyDataSetChanged();
                }
                else
                {

                    isModified = false;
                    tv_modify_goods.setText("编辑");
                    goodsAdapter.refresh(mJson2ProductPackageBeanList);
                    shoppingmall_goods_listview.deferNotifyDataSetChanged();

                }

                Intent intent = new Intent();
                //intent.setClass()
                //startActivity(intent);
            }
        });
        //去结算
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ShoppingMallGoodsFromShopActivity.this, ShoppingMallConformOrderActivity.class);
                startActivity(intent);
            }
        });
        //联系客服
        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ShoppingMallGoodsFromShopActivity.this, ClientServiceActivity.class);
                startActivity(intent);
                toastMgr.builder.display("联系客服", 0);
            }
        });

    }


    private String[] strings = {"保养维护", "电子电路", "发动机件", "底盘配件", "车架配件", "拖架配件" };

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.shoppingmall_goods_modify_layout:
                modifyLayoutClicked(mResponse, mCarType);
                break;
        }
    }

    /**
     * 顶部修改点击
     */
    private void modifyLayoutClicked(String response,String carType)
    {
        Intent intent = new Intent();
        intent.setClass(mContext, ShoppingmallProductModifyFromShopActivity.class);
        Bundle bundle = new Bundle();
        if (mModifyableItemList.size() == 0 && mModifyableSHopItemList.size() == 0)
        {
            warnNoModifyItem();
            return;
        }

        if(mFrom.equals(Configs.FROM_SHOPPINGMALL))
        {
            bundle.putString(Configs.FROM, Configs.FROM_SHOPPINGMALL);
            bundle.putSerializable("shoppingmallBottomPicBeanList", (Serializable) mModifyableItemShoppingmallBottomPicBeanList);
        }
        else
        {
            bundle.putString(Configs.FROM, Configs.FROM_SHOP);
            bundle.putSerializable("shopServiceBean", (Serializable) mModifyableSHopItemList);
        }


        bundle.putString("response", response);
        bundle.putString(Configs.mCarType, carType);
        intent.putExtras(bundle);
        startActivityForResult(intent,MODIFY_PRODUCT_PKG_Request);

    }

    /**
     * 提示用户 您好没有添加车型  麻痹的麻溜的去添加车型
     */
    private void warnNoModifyItem()
    {
        AlertDialog alertDialog = new AlertDialog(mContext);
        alertDialog.builder()
                .setCancelable(false)
                .setTitle("提示")
                .setMsg("没有可修改项目")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })

                .show();

    }




    /**
     * 适配器的定义,要继承BaseAdapter
     */
    public class ProductPackageAdapter extends BaseAdapter{

        List<Json2ProductPackageBean> mpplist;
        int from;

        public ProductPackageAdapter(List<Json2ProductPackageBean> json2ProductPackageBeanList) {
        }

        public ProductPackageAdapter(List<Json2ProductPackageBean> json2ProductPackageBeanList, int _from) {
            this.mpplist = json2ProductPackageBeanList;
            this.from = _from;
        }


        public void refresh(List<Json2ProductPackageBean> json2ProductPackageBeanList) {
            mpplist = json2ProductPackageBeanList;
            notifyDataSetChanged();
            L.d("refresh");
        }


        @Override
        public int getCount() {
            return mpplist.size();
        }

        @Override
        public Object getItem(int position) {
            return mpplist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            /*
             * 1.手工创建对象 2.加载xml文件
             */
            final ViewHolder holder;
            if (view == null)
            {
                holder =  new ViewHolder();
                view = View.inflate(mContext, R.layout.item_test_listview_data, null);
                productitem_changge_before = (LinearLayout) view.findViewById(R.id.productitem_changge_before);
                productitem_changge_after  = (LinearLayout) view.findViewById(R.id.productitem_changge_after);


                holder.produte_pic = (ImageView) view.findViewById(R.id.produte_pic);
                holder.productitem_changge_before = (LinearLayout) view.findViewById(R.id.productitem_changge_before);
                holder.maintenance_produte_name = (TextView) view.findViewById(R.id.maintenance_produte_name);
                holder.maintenance_img_1 = (TextView) view.findViewById(R.id.maintenance_hecheng_1);
                holder.produte_price = (TextView) view.findViewById(R.id.produte_price);
                holder.produte_count = (TextView) view.findViewById(R.id.produte_count);
                holder.maintenance_hecheng_1 = (TextView) view.findViewById(R.id.maintenance_hecheng_1);

                holder.productitem_changge_after = (LinearLayout) view.findViewById(R.id.productitem_changge_after);
                holder.jiancount = (Button) view.findViewById(R.id.jiancount);
                holder.jiacount = (Button) view.findViewById(R.id.jiacount);
                holder.count_tx = (TextView) view.findViewById(R.id.count_tx);
                holder.delete_bt = (RelativeLayout) view.findViewById(R.id.delete_bt);
                holder.change_bt = (RelativeLayout) view.findViewById(R.id.change_bt);


                holder.product_service_left_image = (ImageView) view.findViewById(R.id.product_service_left_image);;//保养服务分类图片
                holder.product_service_name = (TextView) view.findViewById(R.id.product_service_name);;//保养服务分类名字
                holder.product_service_title_layout = (LinearLayout) view.findViewById(R.id.product_service_title_layout);;//分类布局

                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }
            String pathcode = mpplist.get(position).getPathCode();
            String photoname = mpplist.get(position).getPhotoName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;


            if (position == 0)
            {
                lastItemTitle = mpplist.get(position).getPackageName();
            }
            else
            {
                nowItemTitle = mpplist.get(position).getPackageName();
                if (nowItemTitle.equals(lastItemTitle))
                {
                    holder.product_service_title_layout.setVisibility(View.GONE);
                }
            }




            if (from == 1)
            {
                mJson2ProductPackageIdBeanList.get(0).getPackageName();
            }


            if (isModifyList == false)
            {
                L.d(TAG, "产品包图片加载url = " + url);
                ImageLoaderTools.getInstance(mContext).displayImage(url, holder.produte_pic);
            }


            if (tv_modify_goods.getText().toString().equals("保存"))
            {
                holder.productitem_changge_before.setVisibility(View.GONE);
                holder.productitem_changge_after.setVisibility(View.VISIBLE);
            }
            else if (tv_modify_goods.getText().toString().equals("编辑"))
            {
                holder.productitem_changge_after.setVisibility(View.GONE);
                holder.productitem_changge_before.setVisibility(View.VISIBLE);
            }

            holder.maintenance_produte_name.setText(mpplist.get(position).getProductName());
            holder.produte_price.setText(mpplist.get(position).getRetailPrice() + "");

            holder.maintenance_hecheng_1.setText(mpplist.get(position).getProductShow());

            holder.produte_count.setText(mpplist.get(position).getProductAmount() + "");
            holder.produte_price.setText(mpplist.get(position).getRetailPrice() + "");
            holder.produte_price.setText(mpplist.get(position).getRetailPrice() + "");
            holder.count_tx.setText(mpplist.get(position).getProductAmount() + "");

            holder.product_service_name.setText(mpplist.get(position).getPackageName());


            //加商品
            holder.jiacount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String num = holder.produte_count.getText().toString();
                    int num1 = Integer.parseInt(num);
                    num1++;
                    mJson2ProductPackageBeanList.get(position).setProductAmount(num1);
                    holder.produte_count.setText("" + num1);
                    holder.count_tx.setText("" + num1);

                    countTotalPrice(mJson2ProductPackageBeanList);
                }
            });
            //减商品
            holder.jiancount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String numJian = holder.produte_count.getText().toString();
                    int num2 = Integer.parseInt(numJian);
                    if (num2 == 1)
                    {
                        AlertDialog alertDialog = new AlertDialog(mContext);
                        alertDialog.builder()
                                .setTitle("提示")
                                .setMsg("您确定要删除商品吗?")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .show();
                        toastMgr.builder.display("请点击删除来删除商品",1);
                        return;
                    }
                    num2--;
                    mJson2ProductPackageBeanList.get(position).setProductAmount(num2);
                    holder.produte_count.setText("" + num2);
                    holder.count_tx.setText("" + num2);

                    countTotalPrice(mJson2ProductPackageBeanList);

                }
            });
            holder.productitem_changge_before.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toastMgr.builder.display("商品详情",1);

                }
            });


            holder.delete_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    toastMgr.builder.display("删除商品", 1);
                    mJson2ProductPackageBeanList.remove(position);
                    goodsAdapter.refresh(mJson2ProductPackageBeanList);

                }
            });
            holder.change_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toastMgr.builder.display("更换商品", 1);
                    Intent intent = new Intent();
                    intent.setClass(mContext, ShoppingmallProductChangeActivity.class);
                    String categoryid = mpplist.get(position).getCategory();


                    Bundle bundle = new Bundle();
                    bundle.putInt("position",position);
                    bundle.putSerializable("product", mpplist.get(position));
                    bundle.putString(Configs.categoryId, categoryid);
                    intent.putExtras(bundle);
                    ShoppingMallGoodsFromShopActivity.this.startActivityForResult(intent, CHANGE_PRODUCT_Request);
                }
            });

            L.d("会不会重新刷新");

            return view;
        }

        class ViewHolder
        {
            ImageView       produte_pic;//左侧图片

            LinearLayout    productitem_changge_before;//商品名字  价格等等
            TextView        maintenance_produte_name;//产品名字
            TextView        maintenance_img_1;//半合成
            TextView        produte_price;//产品价格
            TextView        produte_count;//产品数量
            TextView        maintenance_hecheng_1;//半合成

            LinearLayout    productitem_changge_after;//商品数量删除 加减 更换
            Button          jiancount;//减商品数量
            Button          jiacount;//加商品数量
            TextView        count_tx;//商品数量  加减中间的
            RelativeLayout  delete_bt;//商品删除
            RelativeLayout  change_bt;//商品更换

            ImageView       product_service_left_image;//保养服务分类图片
            TextView        product_service_name;//保养服务分类名字
            LinearLayout    product_service_title_layout;//分类布局
        }
    }


    class ProductPkgClick implements View.OnClickListener
    {
        private List<Json2ProductPackageBean> mpplist;
        private ProductPackageAdapter.ViewHolder holder;


        public ProductPkgClick(List<Json2ProductPackageBean> list, ProductPackageAdapter.ViewHolder hold)
        {
            this.mpplist = list;
            this.holder = hold;
        }

        @Override
        public void onClick(View view) {




        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHANGE_PRODUCT_Request)
        {
            if (resultCode == Activity.RESULT_OK)
            {

                Bundle bundle = data.getExtras();
                Json2ChangeableProductBean bean = (Json2ChangeableProductBean) bundle.getSerializable("changedProductBean");
                int _position = bundle.getInt("position");

                mJson2ProductPackageBeanList.get(_position).setCategory(bean.getCategory());
                mJson2ProductPackageBeanList.get(_position).setPathCode(bean.getPathCode());
                mJson2ProductPackageBeanList.get(_position).setCategoryName(bean.getCategoryName());
                mJson2ProductPackageBeanList.get(_position).setPhotoName(bean.getPhotoName());
                mJson2ProductPackageBeanList.get(_position).setProductCode(bean.getProductCode());
                mJson2ProductPackageBeanList.get(_position).setProductName(bean.getProductName());
                mJson2ProductPackageBeanList.get(_position).setProductShow(bean.getProductShow());
                mJson2ProductPackageBeanList.get(_position).setRetailPrice(bean.getRetailPrice());


                goodsAdapter.refresh(mJson2ProductPackageBeanList);

                countTotalPrice(mJson2ProductPackageBeanList);

            }
            else if (resultCode == Activity.RESULT_CANCELED)
            {
                toastMgr.builder.display("您没有更改产品",1);
            }
        }
        else if (requestCode == MODIFY_PRODUCT_PKG_Request)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Bundle bundle = data.getExtras();
                String from1 = bundle.getString(Configs.FROM);

                if (from1.equals(Configs.FROM_SHOPPINGMALL))
                {

                    List<Json2ProductPackageIdBean> productPackageIdBeanList = (List<Json2ProductPackageIdBean>) bundle.getSerializable("bean");
                    mGetModifyProductPkgCount = productPackageIdBeanList.size();
                    if (mGetModifyProductPkgCount == 0)
                    {
                        toastMgr.builder.display("您没有选择",1);
                        return;
                    }
                    //根据这个  再去请求,  显示所有产品包
                    httpGetProductPackageByIds(productPackageIdBeanList);
                    FROM_SHOPPINGMALL = 2;
                }
                else
                {

                }



            }
            else
            {
                toastMgr.builder.display("您没有修改产品",1);
            }
        }
    }








    /**
     * 计算商品总价
     * @param beans
     */
    private void countTotalPrice(List<Json2ProductPackageBean> beans)
    {
        int size = beans.size();
        double l_total_size = 0;
        for (Json2ProductPackageBean bean : beans)
        {
            int num = bean.getProductAmount();
            double price = bean.getRetailPrice();
            l_total_size  = l_total_size + ((double)num) * price;
        }
        setTotalPrice(l_total_size);
    }


    private void setTotalPrice(double price)
    {
        tv_price.setText("￥" + price + "");
    }

    private boolean isModifyList = false;//判断是不是点击编辑保存,
    private int productNum;
    private double productPrice;
    private double totalPrice;

    private static int CHANGE_PRODUCT_Request = 1;//更换
    private static int CHANGE_PRODUCT_Result = 2;
    private static int MODIFY_PRODUCT_PKG_Request = 3;//修改

    int listviewItemLoadTimes = 0;//列表商品是否加载完成  加载的次数

    private  int GET_IDS_TIMES = 0;//从修改界面返回之后, 去后台拿了多少次数据
    private  int mGetModifyProductPkgCount;//从修改界面返回来之后, 添加了多少个选项


    private List<Json2ProductPackageIdBean> mJson2ProductPackageIdBeanList;

    private String lastItemTitle = "";
    private String nowItemTitle = "";
}
