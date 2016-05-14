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

import com.car.yubangapk.network.myHttpReq.HttpReqModifyPPkgByIdsFromShop;
import com.car.yubangapk.network.myHttpReq.HttpReqModifyPPkgByPkgIds;
import com.car.yubangapk.ui.shoppingmallgoodsutil.Category;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2ChangeableProductBean;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;
import com.car.yubangapk.json.bean.Json2ShopServiceBean;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;
import com.car.yubangapk.json.formatJson.Json2ProductPackage;
import com.car.yubangapk.json.formatJson.Json2ProductPackageId;
import com.car.yubangapk.network.myHttpReq.HttpReqGetShoppingmallGoodsModifiableCountFromShoppingmall;
import com.car.yubangapk.network.myHttpReq.HttpReqModifiableCountFromShopCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqModifiableCountFromShoppingmallCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqProductPackageFromMallBannerShop;
import com.car.yubangapk.network.myHttpReq.HttpReqShoppingmallGoodsModifiableCountFromShop;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.MyObjectStringCallback;
import com.car.yubangapk.ui.shoppingmallgoodsutil.GoodsCategoryHelper;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.Warn.NoProductPackage;
import com.car.yubangapk.utils.Warn.UpdateApp;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
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
public class ShoppingMallGoodsActivity extends BaseActivity implements View.OnClickListener,
        HttpReqProductPackageFromMallBannerShop.GetProductPackageContent
{

    private static final String TAG = ShoppingMallGoodsActivity.class.getName();

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
    private ProductPackageAdapter1 goodsAdapter1;

    private List<Json2ProductPackageBean> mJson2ProductPackageBeanList;//商城过来  保存的数据
    private List<Json2ShopServiceBean>    mJson2ShopServiceBeanList;
//    private String mResponse;

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
            String carType =   bundle.getString(Configs.mCarType);//车主车类型

            mRepairService = serviceId;

            //去拿产品包的id  拿到产品包的id时候  再去根据产品包的id获取产品包的内容
            mRepairServiceServiceId = serviceId;
            mCarType = carType;
            if (Configs.FROM_SHOPPINGMALL.equals(mFrom))
            {
                //从商城来  这里是去显示有多少个可修改  可供选择的项目 最顶部
                httpGetLogicalService(serviceId, mFrom, carType);
            }
            else
            {
                //从店铺来  这里是去显示有多少个可修改  可供选择的项目 最顶部
                mShopInfoID = bundle.getString("shopId");
                httpGetLogicalService(serviceId, mFrom, carType);

            }

            //都需要调用这句话, 来展示产品包
            HttpReqProductPackageFromMallBannerShop req = new HttpReqProductPackageFromMallBannerShop(serviceId, carType, mContext);
            req.setInterface(this);
            req.getProductPackageId();
            mProgressDialog = mProgressDialog.show(mContext, "正在加载...",false, null);

        }
    }

    /**
     * 根据上一个界面(商城 banner 门店)的serviceId 去拿对应产品包id 再根据id 去拿产品包内容成功后的回调
     *
     * 就是去listview显示
     *
     * @param json2ProductPackageBeanList
     */

    @Override
    public void onGetPPkgSucces(List<Json2ProductPackageBean> json2ProductPackageBeanList) {
        List<Category> data = getListViewData(json2ProductPackageBeanList);

        goodsAdapter1 = new ProductPackageAdapter1(data,FROM_SHOPPINGMALL, mContext);
        mJson2ProductPackageBeanList = json2ProductPackageBeanList;
        shoppingmall_goods_listview.setAdapter(goodsAdapter1);
        mProgressDialog.dismiss();
    }

    private List<Category> getListViewData(List<Json2ProductPackageBean> json2ProductPackageBeanList)
    {
        return GoodsCategoryHelper.productsToCategoryList(json2ProductPackageBeanList);
    }

    /**
     * 根据上一个界面(商城 banner 门店)的serviceId 去拿对应产品包id 再根据id 去拿产品包内容失败后的回调
     *
     * 给出失败的原因
     *
     * @param errorCode
     */
    @Override
    public void onGetPPkgFail(int errorCode) {
        mProgressDialog.dismiss();
        if (ErrorCodes.ERROR_CODE_LOW_VERSION == errorCode)
        {
            //提示去升级
            UpdateApp.gotoUpdateApp(mContext);
        }
        else if (ErrorCodes.ERROR_CODE_NETWORK == errorCode)
        {
            //提示网络错误
            toastMgr.builder.display("网络错误", 1);
        }
        else if (ErrorCodes.ERROR_CODE_NO_PRODUCT_PKG == errorCode)
        {
            //提示没有相关产品包
            NoProductPackage.tishiNoPPkg(ShoppingMallGoodsActivity.this);
        }
        else if (ErrorCodes.ERROR_CODE_SERVER == errorCode)
        {
            //其实服务器错误
            toastMgr.builder.display("服务器错误", 1);
        }
        else {

            toastMgr.builder.display("errorcode = " + errorCode, 1);
        }
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
        if (mFrom.equals(Configs.FROM_SHOPPINGMALL))
        {
            HttpReqGetShoppingmallGoodsModifiableCountFromShoppingmall req = new HttpReqGetShoppingmallGoodsModifiableCountFromShoppingmall();
            req.setCallbac(new GetModifiableNumFromMall());
            req.getModifiableCount(serviceId, mFrom, carType);
        }
        else
        {
            HttpReqShoppingmallGoodsModifiableCountFromShop req = new HttpReqShoppingmallGoodsModifiableCountFromShop();
            req.setCallbac(new GetModifiableNumFromShop());
            req.getModifiableCount(serviceId, mFrom, carType, mShopInfoID);
        }
    }
    class GetModifiableNumFromMall implements HttpReqModifiableCountFromShoppingmallCallback
    {

        @Override
        public void onFail(int errorCode, String message) {
            mProgressDialog.dismiss();
            if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
            {
                toastMgr.builder.display(message, 1);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_NO_DATA)
            {
                toastMgr.builder.display(message, 1);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
            {
                toastMgr.builder.display(message, 1);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_SERVER_ERROR)
            {
                toastMgr.builder.display(message, 1);
            }else if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
            {
                UpdateApp.gotoUpdateApp(mContext);
            }
        }

        @Override
        public void onSuccess(List<Json2ProductPackageIdBean> modifyableItemList, List<Json2ShoppingmallBottomPicsBean> modifyableItemShoppingmallBottomPicBeanList) {
            mProgressDialog.dismiss();
            mModifyableItemList = modifyableItemList;
            mModifyableItemShoppingmallBottomPicBeanList = modifyableItemShoppingmallBottomPicBeanList;
            modifyable_product_count.setText("1个项目需要保养(共" + mModifyableItemList.size() + "个项目)");
        }
    }
    class GetModifiableNumFromShop implements HttpReqModifiableCountFromShopCallback
    {

        @Override
        public void onFail(int errorCode, String message) {
            if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
            {
                toastMgr.builder.display(message, 1);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_NO_DATA)
            {
                toastMgr.builder.display(message, 1);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
            {
                toastMgr.builder.display(message, 1);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_SERVER_ERROR)
            {
                toastMgr.builder.display(message, 1);
            }else if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
            {
                UpdateApp.gotoUpdateApp(mContext);
            }
        }

        @Override
        public void onSuccess(List<Json2ShopServiceBean> modifyableSHopItemList) {
            mModifyableSHopItemList = modifyableSHopItemList;
            modifyable_product_count.setText("1个项目需要保养(共" + mModifyableSHopItemList.size() + "个项目)");
        }
    }

    private List<Json2ProductPackageIdBean> mModifyableItemList = new ArrayList<>();
    private List<Json2ShopServiceBean> mModifyableSHopItemList = new ArrayList<>();

    /**
     * 根据从修改界面拿回来的数据, 去重新加载产品包  所有产品包
     * 通过产品包的id 去拿产品包  所有产品的id  拿到所有的产品包
     * @param ppList
     */
    private void httpGetProductPackageByIds(List<Json2ProductPackageIdBean> ppList)
    {
        HttpReqModifyPPkgByPkgIds req = new HttpReqModifyPPkgByPkgIds();
        req.setInterface(new GetPPIdsCallbac1());
        req.httpGetProductPackageByIds(ppList);



    }

    class GetPPIdsCallbac1 implements HttpReqModifyPPkgByPkgIds.GetProductPackageContent
    {

        @Override
        public void onGetPPkgSucces(List<Json2ProductPackageBean> json2ProductPackageBeanList) {
            mJson2ProductPackageBeanList.clear();

            mJson2ProductPackageBeanList = json2ProductPackageBeanList;

            List<Category> cLst = getListViewData(mJson2ProductPackageBeanList);
            goodsAdapter1.refresh(getListViewData(mJson2ProductPackageBeanList));
            countTotalPrice(categoryToJsonProductPackageGoods(cLst));

        }

        @Override
        public void onGetPPkgFail(int errorCode)
        {

        }
    }

    /**
     * 从修改界面返回来, 确定是门店里面服务的数据
     * @param ppList
     */
    private void httpGetShopServiceByIds(List<Json2ShopServiceBean> ppList)
    {
        HttpReqModifyPPkgByIdsFromShop req = new HttpReqModifyPPkgByIdsFromShop();
        req.setInterface(new HttpReqModifyPPkgByIdsFromShop.GetProductPackageContent() {
            @Override
            public void onGetPPkgSucces(List<Json2ProductPackageIdBean> json2ProductPackageIDBeanList) {
                httpGetProductPackageByIds(json2ProductPackageIDBeanList);
            }

            @Override
            public void onGetPPkgFail(int errorCode) {

            }
        });
        req.httpGetShopServiceByIds(ppList, mCarType);
    }


    private  int FROM_SHOPPINGMALL = 1;//从商品界面过来
    private static final int FROM_MODIFY       = 2;//从修改界面过来

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
                    List<Category> categories = GoodsCategoryHelper.productsToCategoryList(mJson2ProductPackageBeanList);
                    goodsAdapter1.refresh(categories);
                    shoppingmall_goods_listview.deferNotifyDataSetChanged();
                }
                else
                {
                    isModified = false;
                    tv_modify_goods.setText("编辑");
                    List<Category> categories = GoodsCategoryHelper.productsToCategoryList(mJson2ProductPackageBeanList);
                    goodsAdapter1.refresh(categories);
                    shoppingmall_goods_listview.deferNotifyDataSetChanged();
                }

            }
        });
        //去结算
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ShoppingMallGoodsActivity.this, ShoppingMallConformOrderActivity.class);
                Bundle bundle = new Bundle();
                List<String> repairServices = new ArrayList<String>();
                repairServices.add(mRepairService);
                //就是商城首页 保养维护底下对应的6个图片的repairService
                if (mFrom.equals(Configs.FROM_SHOPPINGMALL))
                {
                    bundle.putStringArrayList("repairServices", (ArrayList<String>) repairServices);
                    bundle.putString("from", Configs.FROM_SHOPPINGMALL);
                }
                else
                {
                    bundle.putStringArrayList("shopServiceBean", (ArrayList<String>) repairServices);
                    bundle.putString("from", Configs.FROM_SHOP);//门店
                }

                bundle.putSerializable("productPackageList", (Serializable) mProductPkgBeanListToConformOrderPage);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //联系客服
        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ShoppingMallGoodsActivity.this, ClientServiceActivity.class);
                startActivity(intent);
                toastMgr.builder.display("联系客服", 0);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.shoppingmall_goods_modify_layout:
                modifyLayoutClicked(null, mCarType);
                break;
        }
    }

    /**
     * 顶部修改点击
     *
     */
    private void modifyLayoutClicked(String response,String carType)
    {
        Intent intent = new Intent();
        intent.setClass(mContext, ShoppingmallProductModifyActivity.class);
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



    public class ProductPackageAdapter1 extends BaseAdapter{

        List<Category> mpplist;
        int from;
        Context mPContext;

        private static final int TYPE_CATEGORY_ITEM = 0;
        private static final int TYPE_ITEM = 1;

        public ProductPackageAdapter1(List<Category> json2ProductPackageBeanList) {
        }

        public ProductPackageAdapter1(List<Category> json2ProductPackageBeanList, int _from, Context context) {
            this.mpplist = json2ProductPackageBeanList;
            this.from = _from;
            this.mPContext = context;
            mProductPkgBeanListToConformOrderPage = categoryToJsonProductPackageGoods(json2ProductPackageBeanList);

            mJson2ProductPackageBeanList = null;
            mJson2ProductPackageBeanList = mProductPkgBeanListToConformOrderPage;
        }


        public void refresh(List<Category> json2ProductPackageBeanList) {
            mpplist = json2ProductPackageBeanList;
            mProductPkgBeanListToConformOrderPage = categoryToJsonProductPackageGoods(json2ProductPackageBeanList);

            mJson2ProductPackageBeanList = null;
            mJson2ProductPackageBeanList = mProductPkgBeanListToConformOrderPage;
            notifyDataSetChanged();
            L.d("refresh");
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            // 异常情况处理
            if (null == mpplist || position <  0|| position > getCount()) {
                L.d("item view type = " + TYPE_ITEM + "TYPE_ITEM");
                return TYPE_ITEM;
            }


            int categroyFirstIndex = 0;

            for (Category category : mpplist) {
                int size = category.getItemCount();
                // 在当前分类中的索引值
                int categoryIndex = position - categroyFirstIndex;
                if (categoryIndex == 0) {
                    L.d("item view type = " + TYPE_CATEGORY_ITEM + "TYPE_CATEGORY_ITEM");
                    return TYPE_CATEGORY_ITEM;
                }

                categroyFirstIndex += size;
            }

            L.d("item view type = " + TYPE_ITEM + "TYPE_ITEM");

            return TYPE_ITEM;
        }

        @Override
        public int getCount() {
            int count = 0;
            if (null != mpplist) {
                //  所有分类中item的总和是ListVIew  Item的总个数
                for (Category category : mpplist) {
                    count += category.getItemCount();
                }
            }
            L.d("count = " + count);
            return count;
        }


        @Override
        public Object getItem(int position) {
            // 异常情况处理
            if (null == mpplist || position <  0|| position > getCount()) {
                return null;
            }

            // 同一分类内，第一个元素的索引值
            int categroyFirstIndex = 0;

            for (Category category : mpplist) {
                int size = category.getItemCount();
                // 在当前分类中的索引值
                int categoryIndex = position - categroyFirstIndex;
                // item在当前分类内
                if (categoryIndex < size) {
                    return  category.getItem( categoryIndex );
                }

                // 索引移动到当前分类结尾，即下一个分类第一个元素索引
                categroyFirstIndex += size;
            }

            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            /**
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
            //设置分类
            int itemViewType = getItemViewType(position);
            if(itemViewType == TYPE_CATEGORY_ITEM)
            {
                holder.product_service_title_layout.setVisibility(View.VISIBLE);
                String packageName = ((Json2ProductPackageBean)getItem(position)).getPackageName();
                holder.product_service_name.setText(packageName);
            }
            else
            {
                holder.product_service_title_layout.setVisibility(View.GONE);
            }
            String productName = ((Json2ProductPackageBean)getItem(position)).getProductName();
            holder.maintenance_produte_name.setText(productName);
            final double price = ((Json2ProductPackageBean)getItem(position)).getRetailPrice();
            holder.produte_price.setText("￥" + price + "");

            String show = ((Json2ProductPackageBean)getItem(position)).getProductShow();
            holder.maintenance_hecheng_1.setText(show);

            holder.produte_count.setText("x" + ((Json2ProductPackageBean)getItem(position)).getProductAmount() + "");
            holder.produte_price.setText("￥" + ((Json2ProductPackageBean)getItem(position)).getRetailPrice() + "");
            holder.produte_price.setText("￥" + ((Json2ProductPackageBean)getItem(position)).getRetailPrice() + "");
            holder.count_tx.setText("x" + ((Json2ProductPackageBean)getItem(position)).getProductAmount() + "");
            //以上是设置分类的显示 每个item的显示

            //这里是显示增加和删除
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


            //加商品
            holder.jiacount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String num = holder.produte_count.getText().toString();
                    int num1 = Integer.parseInt(num);
                    num1++;
                    mJson2ProductPackageBeanList.get(position).setProductAmount(num1);
                    holder.produte_count.setText("x" + num1);
                    holder.count_tx.setText("x" + num1);

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
                    holder.produte_count.setText("x" + num2);
                    holder.count_tx.setText("x" + num2);

                    countTotalPrice(mJson2ProductPackageBeanList);

                }
            });
            holder.productitem_changge_before.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toastMgr.builder.display("商品详情", 1);
                    String productId = mJson2ProductPackageBeanList.get(position).getId();
                    gotoProductDetailPage(productId);

                }
            });


            holder.delete_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    toastMgr.builder.display("删除商品", 1);
                    mJson2ProductPackageBeanList.remove(position);
                    List<Category> categories = GoodsCategoryHelper.productsToCategoryList(mJson2ProductPackageBeanList);
                    goodsAdapter1.refresh(categories);

                }
            });
            //更换商品
            holder.change_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toastMgr.builder.display("更换商品", 1);
                    Intent intent = new Intent();
                    intent.setClass(mContext, ShoppingmallProductChangeActivity.class);
                    String categoryid = ((Json2ProductPackageBean)getItem(position)).getCategory();


                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    //bundle.putSerializable("product", mpplist.get(position));
                    bundle.putString(Configs.categoryId, categoryid);
                    intent.putExtras(bundle);
                    ShoppingMallGoodsActivity.this.startActivityForResult(intent, CHANGE_PRODUCT_Request);
                }
            });



            String pathcode = ((Json2ProductPackageBean)getItem(position)).getPathCode();
            String photoname = ((Json2ProductPackageBean)getItem(position)).getPhotoName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;
            ImageLoaderTools.getInstance(mPContext).displayImage(url, holder.produte_pic);



            countTotalPrice(categoryToJsonProductPackageGoods(mpplist));
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

    /**
     * 去产品详情页
     * @param productId
     */
    private void gotoProductDetailPage(String productId) {
        Intent intent = new Intent();
        intent.setClass(mContext, ShoppingmallProductDetailActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("productId", productId);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 产品包分类 到 产品包列表  方便计算价格
     * @param listData
     * @return
     */
    private List<Json2ProductPackageBean> categoryToJsonProductPackageGoods(List<Category> listData)
    {
        return GoodsCategoryHelper.categoryListToProducts(listData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //更换商品
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
                List<Category> categories = GoodsCategoryHelper.productsToCategoryList(mJson2ProductPackageBeanList);
                goodsAdapter1.refresh(categories);
                countTotalPrice(mJson2ProductPackageBeanList);
            }
            else if (resultCode == Activity.RESULT_CANCELED)
            {
                toastMgr.builder.display("您没有更改产品",1);
            }
        }
        //修改可选的产品包
        else if (requestCode == MODIFY_PRODUCT_PKG_Request)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Bundle bundle = data.getExtras();
                String from1 = mFrom;

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
                    List<Json2ShopServiceBean> json2ShopServiceBean = (List<Json2ShopServiceBean>) bundle.getSerializable("bean");
                    mGetModifyProductPkgCount = json2ShopServiceBean.size();
                    if (mGetModifyProductPkgCount == 0)
                    {
                        toastMgr.builder.display("您没有选择",1);
                        return;
                    }
                    httpGetShopServiceByIds(json2ShopServiceBean);
                    FROM_SHOPPINGMALL = 2;
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

    private String[] strings = {"保养维护", "电子电路", "发动机件", "底盘配件", "车架配件", "拖架配件" };

    private List<Json2ProductPackageBean> mProductPkgBeanListToConformOrderPage;
}
