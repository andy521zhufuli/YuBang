package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2FirstPageShopBean;
import com.car.yubangapk.json.bean.Json2ShopServiceBean;
import com.car.yubangapk.json.formatJson.Json2FirstPageShop;
import com.car.yubangapk.json.formatJson.Json2ShopService;
import com.car.yubangapk.okhttp.OkHttpUtils;
import com.car.yubangapk.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

import java.util.List;

import okhttp3.Call;

/**
 * FirstPageShopShowActivity: 商铺展示
 * 1.首页要展示地图
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-03-01
 */
public class FirstPageShopShowActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;

    private LinearLayout   photo_show;//点击门店
    private LinearLayout   first_page_shop_no_service;//轮胎服务
    private LinearLayout   first_page_shop_show_baoyang_service;//保养服务
    private LinearLayout   first_page_shop_show_sales_activity;//优惠活动
    private LinearLayout   first_page_shop_show_customers_comments;//客户评价

    private ImageView      show_shop_photo;//店铺照片
    private TextView       show_shop_name;//店铺名字
    private RatingBar      shop_ratingBar_detai;//店铺星际
    private TextView       show_shop_distance;//店铺距离
    private ImageView      show_shop_tele;//电话
    private ImageView      show_shop_nav;//导航
    private TextView       show_shop_dan;//订单数目

    private ImageView      img_back;
   private ListView       shop_show_service_listview;//店铺服务的listview
    private ShopServiceListViewAdapter shopServiceAdapter;
    String shopBean;
    private List<Json2FirstPageShopBean> mShopBeanList;//店铺信息
    private List<Json2ShopServiceBean> mShopService;//店铺服务
    private Json2FirstPageShopBean mShopBean;
    int item;

    private CustomProgressDialog mProgressDialog;


    //店铺信息
    private String shopInfoId;//店铺id
    private String shopInfoShopAddress;
    private String shopInfoPhoneNum;
    private String shopInfoShopPhoto;
    private String shopInfoPathCode;
    private String shopInfoShopName;
    private String shopInfoCompany;
    private double shopInfoShopLatitude;
    private double shopInfoShopLongitude;
    private String shopInfoStar;
    private int    shopInfoOrder;
    private double shopInfoDistance;
    private long    orderNum;
    private String  mCarType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first_page_shop_show);

        mContext = this;


        mProgressDialog = new CustomProgressDialog(mContext);

        Intent intent = getIntent();
        shopBean = intent.getStringExtra("shopBean");
        //item = -1 代表最上面一个点击
        item = intent.getIntExtra("item", -2);


        Json2FirstPageShop json2FirstPageShop = new Json2FirstPageShop(shopBean);
        mShopBeanList = json2FirstPageShop.getFirstPageShop();








        findViews();

        //显示店铺信息  顶部图片之类
        showShopInfo(mShopBean, item);

        //item点击事件
        listviewShopServiceClicked();


        String carType = Configs.getLoginedInfo(mContext).getCarType();
        mCarType = carType;

        if (carType == null || carType.equals(""))
        {
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder()
                    .setCancelable(false)
                    .setTitle("提示")
                    .setMsg("您还没有添加车型,请添加车型")
                    .setPositiveButton("去添加", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setNegativeButton("先逛逛", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();
        }
        else
        {
            httpGetShopService(json2FirstPageShop.getFirstPageShop().get(0).getId(), Configs.getLoginedInfo(mContext).getCarType());
        }



    }

    /**
     * 显示店铺信息 item为上次点击的是哪一个  第一个  还是listview中的
     * @param mShopBean
     */
    private void showShopInfo(Json2FirstPageShopBean mShopBean, int _item)
    {

        if (_item == -1)//上一页的第一个
        {
            mShopBean = mShopBeanList.get(0);
            shopInfoId              = mShopBeanList.get(0).getId();
            shopInfoShopAddress     = mShopBeanList.get(0).getShopAddress();
            shopInfoPhoneNum        = mShopBeanList.get(0).getPhoneNum();
            shopInfoShopPhoto       = mShopBeanList.get(0).getShopPhoto();
            shopInfoPathCode        = mShopBeanList.get(0).getPathCode();
            shopInfoShopName        = mShopBeanList.get(0).getShopName();
            shopInfoCompany         = mShopBeanList.get(0).getCompany();
            shopInfoShopLatitude    = mShopBeanList.get(0).getShopLatitude();
            shopInfoShopLongitude   = mShopBeanList.get(0).getShopLongitude();
            shopInfoStar            = mShopBeanList.get(0).getStar();
            shopInfoOrder           = mShopBeanList.get(0).getOrder();
            shopInfoDistance        = mShopBeanList.get(0).getDistance();
            orderNum                = mShopBeanList.get(0).getOrderNum();
        }
        else if (_item >= 0)//listview中的item
        {
            _item = _item + 1;
            mShopBean = mShopBeanList.get(_item);
            shopInfoId              = mShopBeanList.get(_item).getId();
            shopInfoShopAddress     = mShopBeanList.get(_item).getShopAddress();
            shopInfoPhoneNum        = mShopBeanList.get(_item).getPhoneNum();
            shopInfoShopPhoto       = mShopBeanList.get(_item).getShopPhoto();
            shopInfoPathCode        = mShopBeanList.get(_item).getPathCode();
            shopInfoShopName        = mShopBeanList.get(_item).getShopName();
            shopInfoCompany         = mShopBeanList.get(_item).getCompany();
            shopInfoShopLatitude    = mShopBeanList.get(_item).getShopLatitude();
            shopInfoShopLongitude   = mShopBeanList.get(_item).getShopLongitude();
            shopInfoStar            = mShopBeanList.get(_item).getStar();
            shopInfoOrder           = mShopBeanList.get(_item).getOrder();
            shopInfoDistance        = mShopBeanList.get(_item).getDistance();
            orderNum                = mShopBeanList.get(_item).getOrderNum();
        }

        float star = Float.parseFloat(shopInfoStar);
        if (star >= (float)5.0)
            star = (float) 5.0;

        //显示信息
        String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + shopInfoPathCode + "&fileReq.fileName=" + shopInfoShopPhoto;
        L.d("marker shop photo url = " + url);
        ImageLoaderTools.getInstance(mContext).displayImage(url, show_shop_photo);
        show_shop_name.setText(shopInfoShopName);
       // shop_ratingBar_detai
        show_shop_distance.setText(shopInfoDistance + "米");
        shop_ratingBar_detai.setRating(star);
        show_shop_dan.setText(orderNum + "单");

    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);

        photo_show = (LinearLayout) findViewById(R.id.photo_show);//点击门店
        shop_show_service_listview = (ListView) findViewById(R.id.shop_show_service_listview);//
//        shop_show_service_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                toastMgr.builder.display("轮胎服务", 0);
//                //这里应该跳到对应的商品里面
//                //TODO 这里应该跳到对应的商品里面
//                gotoShoppingmallGoodsActivity(position);
//
//            }
//        });

        show_shop_photo         = (ImageView) findViewById(R.id.show_shop_photo);//店铺照片
        show_shop_name          = (TextView) findViewById(R.id.show_shop_name);//店铺名字
        shop_ratingBar_detai    = (RatingBar) findViewById(R.id.shop_ratingBar_detai);//店铺星际
        show_shop_distance      = (TextView) findViewById(R.id.show_shop_distance);//店铺距离

        show_shop_tele = (ImageView) findViewById(R.id.show_shop_tele);//电话
        show_shop_nav = (ImageView) findViewById(R.id.show_shop_nav);//导航

        show_shop_dan = (TextView) findViewById(R.id.show_shop_dan);//订单数



        first_page_shop_no_service = (LinearLayout) findViewById(R.id.first_page_shop_no_service);//轮如果没有服务的时候显示
//        first_page_shop_show_baoyang_service = (LinearLayout) findViewById(R.id.first_page_shop_show_baoyang_service);//保养服务
        first_page_shop_show_sales_activity = (LinearLayout) findViewById(R.id.first_page_shop_show_sales_activity);//优惠活动
        first_page_shop_show_customers_comments = (LinearLayout) findViewById(R.id.first_page_shop_show_customers_comments);//客户评价

        /**
         * 注册监听器
         */
        photo_show.setOnClickListener(this);
        img_back.setOnClickListener(this);
        show_shop_tele.setOnClickListener(this);
        show_shop_nav.setOnClickListener(this);
        first_page_shop_show_sales_activity.setOnClickListener(this);
        first_page_shop_show_customers_comments.setOnClickListener(this);

    }


    /**
     * 注册item点击事件
     */
    private void listviewShopServiceClicked()
    {

    }

    /**
     * 点击了之后, 就去商城产品包界面
     * @param position
     */
    private void gotoShoppingmallGoodsActivity(int position)
    {


        if (mCarType == null || mCarType.equals(""))
        {
            //这里会不会有个坑, 就是用户如果删除了应用, 重新安装登陆(已经注册已经有了车型)
            //这样会不会提示用户没有选择车型//TODO 大问题
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder()
                    .setTitle("提示").setMsg("您没有添加车型").setCancelable(false)
                    .setPositiveButton("去添加", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
        }
        else
        {

            String serviceId = mShopService.get(position).getId();
            Intent intent = new Intent();
//            intent.setClass(FirstPageShopShowActivity.this, ShoppingMallGoodsFromShopActivity.class);
            intent.setClass(FirstPageShopShowActivity.this, ShoppingMallGoodsActivity.class);
            //拿到的shopInfoId
            //拿到的车型mCarType
            Bundle bundle = new Bundle();
            bundle.putString("shopId", shopInfoId);
            bundle.putString(Configs.FROM, Configs.FROM_SHOP);
            bundle.putString("serviceId",serviceId);//店铺的id
            bundle.putString("mCarType", mCarType);//用户的车型
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    /**
     * 获取店铺店铺服务
     * @param id 店铺的id
     * @param carType 车类型
     */
    private void httpGetShopService(String id, String carType)
    {

        mProgressDialog = mProgressDialog.show(mContext,"正在加载店铺服务...", false, null);

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchShopService")
                .addParams("dataReqModel.args.needTotal", "needTotal")
                .addParams("dataReqModel.args.shop",id)
                .addParams("dataReqModel.args.carType",carType)
                .build()
                .execute(new GetShopCallback());

        L.i("FirstPageShopShowActivity", "获取门店url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                        + "sqlName=" + "clientSearchShopService"
                        + "&dataReqModel.args.shop=" + id
                        + "&dataReqModel.args.needTotal=needTotal"
                        + "&dataReqModel.args.carType=" + carType
        );
    }

    class GetShopCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请重试!", 1);
            mProgressDialog.dismiss();
        }

        @Override
        public void onResponse(String response) {
            L.d("获取门店 json = " + response);
            Json2ShopService json2ShopShow = new Json2ShopService(response);
            List<Json2ShopServiceBean> json2ShopServiceBeanjList = json2ShopShow.getShopShowData();
            mShopService = json2ShopServiceBeanjList;
            if (json2ShopServiceBeanjList == null)
            {
                toastMgr.builder.display("服务器错误",1);
                mProgressDialog.dismiss();
            }
            else
            {
                if (json2ShopServiceBeanjList.get(0).isHasData() == false)
                {
                    //里面没有数据, 说明该店铺没有服务可用
                    toastMgr.builder.display("该店铺没有服务可用", 1);
                    mProgressDialog.dismiss();

                    showShopServiceAndInfo(json2ShopServiceBeanjList, null, false);
                }
                else
                {
                    toastMgr.builder.display("店铺有诸多服务,请选择", 1);
                    mProgressDialog.dismiss();
                    showShopServiceAndInfo(json2ShopServiceBeanjList, null, true);
                }
            }
        }
    }


    /**
     * 从拿到的数据里面显示该店铺有的服务 以及店铺的信息
     * @param shopServices 店铺的服务
     * @param shopInfos 店铺的信息
     */
    private void showShopServiceAndInfo(List<Json2ShopServiceBean> shopServices, String shopInfos, boolean hasShopService)
    {
        if (shopInfos != null)
        {
            //显示店铺顶部信息
        }
        if (shopServices != null)
        {
            if (hasShopService == true)
            {
                first_page_shop_no_service.setVisibility(View.GONE);
                shop_show_service_listview.setVisibility(View.VISIBLE);
                shopServiceAdapter = new ShopServiceListViewAdapter(shopServices);
                shop_show_service_listview.setAdapter(shopServiceAdapter);

            }
            else
            {
                first_page_shop_no_service.setVisibility(View.VISIBLE);
                shop_show_service_listview.setVisibility(View.GONE);

            }
        }
    }

    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent();
        switch (view.getId())
        {
            //店面点击
            case R.id.photo_show:

                intent.setClass(FirstPageShopShowActivity.this, FirstPageShopBigPhotoShowActivity.class);
                startActivity(intent);
                break;

            //游湖活动
            case R.id.first_page_shop_show_sales_activity:
                //TODO 这里应该跳到对应的商品里面
                toastMgr.builder.display("暂无优惠活动",1);
//                intent.setClass(FirstPageShopShowActivity.this, ShoppingMallGoodsActivity.class);
//                startActivity(intent);
                break;
            //客户评价
            case R.id.first_page_shop_show_customers_comments:
                toastMgr.builder.display( "暂无客户评论" , 0);
                //TODO 这里应该跳到对应的商品里面
//                intent.setClass(FirstPageShopShowActivity.this, ShoppingMallGoodsActivity.class);
//                startActivity(intent);
                break;
            case R.id.img_back:
                finish();
                break;
            //电话
            case R.id. show_shop_tele:
                new AlertDialog(mContext).builder()
                        .setTitle("打电话给店主吗").setCancelable(false)
                        .setPositiveButton("现在打", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + shopInfoPhoneNum));
                                mContext.startActivity(intent);
                            }
                        })
                        .setNegativeButton("考虑下", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .show();
                break;
            case R.id.show_shop_nav:
                break;

        }
        intent = null;
    }



    /**
    * 适配器的定义,要继承BaseAdapter
    */
    public class ShopServiceListViewAdapter extends BaseAdapter {


        List<Json2ShopServiceBean> json2ShopServiceBeanjList;

        public ShopServiceListViewAdapter(List<Json2ShopServiceBean> _json2ShopServiceBeanjList)
        {
            this.json2ShopServiceBeanjList = _json2ShopServiceBeanjList;
        }

        @Override
        public int getCount() {
            return json2ShopServiceBeanjList.size();
        }

        @Override
        public Object getItem(int position) {
            return json2ShopServiceBeanjList.get(position);
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
            view = View.inflate(mContext, R.layout.item_shop_service_item, null);
            LinearLayout first_page_shop_service = (LinearLayout) view.findViewById(R.id.first_page_shop_show_wheel_service);
            first_page_shop_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    gotoShoppingmallGoodsActivity(position);
                }
            });


            ImageView item_shop_service_logo = (ImageView) view.findViewById(R.id.item_shop_service_logo);

            TextView item_shop_service_name  = (TextView) view.findViewById(R.id.item_shop_service_name);

            item_shop_service_name.setText(json2ShopServiceBeanjList.get(position).getServiceName());
            return view;
        }


    }




}
