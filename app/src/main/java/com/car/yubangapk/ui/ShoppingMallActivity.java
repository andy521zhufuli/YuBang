package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.car.yubangapk.banner.FlashView;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.banner.constants.EffectConstants;
import com.car.yubangapk.banner.listener.FlashViewListener;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.FormatJson;
import com.car.yubangapk.json.bean.BannerAd;
import com.car.yubangapk.json.bean.ShoppingmallPicBean;
import com.car.yubangapk.json.bean.ShoppingmallSpeciesePicBean;
import com.car.yubangapk.okhttp.OkHttpUtils;
import com.car.yubangapk.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * ShoppingMallActivity: 商城界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class ShoppingMallActivity extends BaseActivity implements View.OnClickListener{


    //banner广告点击
    List<BannerAd> mBannerAdList = null;//全局的banner广告变量  保存广告相关信息
    //商城图片  中部分类
    List<ShoppingmallSpeciesePicBean> mShoppingmallSpeciesPicList = null;//种类
    List<ImageView>           mMiddleSpeciesList = null;
    //商城图片  底部6个大分类 大图片
    List<ShoppingmallPicBean>   mShoppingmallBottomPicList = null;//中部分类以下的
    Map<String,List<ImageView>> mShoppingmallBottomPicMap = null;
    List<ImageView>             mShoppingmallBottomImageviewList = null;


    // 定义图片的资源
    private String[] strings = {"保养维护", "电子电路", "发动机件", "底盘配件", "车架配件", "拖架配件" };

    //实现点击种类scroll自动滚动
    private int pointYs[];
    //是不是已经调用了measure方法
    private boolean hasMeasure = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_mall);

        mContext = this;

        findViews();

        //将需要设置图片的所有空间添加到list  或者  map  里面去
        addImageviewTOListMap();


        //广告轮播点击监听器设置
        shoppingmall_flashview_banner.setOnPageClickListener(new FlashViewListener() {
            @Override
            public void onClick(int position) {
                toastMgr.builder.display("position"+position + "clicked" , 0);
                if (mBannerAdList == null || mBannerAdList.size() == 0)
                {

                }
                else
                {
                    BannerAd bannerAd = mBannerAdList.get(position);
                    toastMgr.builder.display(bannerAd.getAdvertisementName() + bannerAd.getSkipType(), 0);
                    bannerAd.getLink();

                    String skipType = bannerAd.getSkipType();
                    if (skipType.equals(Configs.SKIP_TYPE_WEB))
                    {
                        //网页

                    }
                    else if (skipType.equals(Configs.SKIP_TYPE_PRODUCT_PACKAGE))
                    {
                        //产品包
                    }
                    else if (skipType.equals(Configs.SKIP_TYPE_LOGIC_SERVICE))
                    {
                        //逻辑服务
                    }
                }
            }
        });






        //广告轮播 最开始的广告轮播
//        imageUrls = new ArrayList<String>();
//        imageUrls.add("http://www.juzi2.com/uploads/allimg/130619/1_130619193218_1.jpg");
//        imageUrls.add("http://a.hiphotos.baidu.com/zhidao/pic/item/4034970a304e251f4dd80e61a786c9177f3e5378.jpg");
//        imageUrls.add("http://f.hiphotos.baidu.com/zhidao/pic/item/9e3df8dcd100baa12801ad224710b912c8fc2e7e.jpg");
//        imageUrls.add("drawable://" + R.mipmap.banner01);
//        imageUrls.add("drawable://" + R.mipmap.banner02);
//        imageUrls.add("drawable://" + R.mipmap.banner03);
//
//
//        shoppingmall_flashview_banner.setImageUris(imageUrls);
//        shoppingmall_flashview_banner.setEffect(EffectConstants.DEFAULT_EFFECT);//更改图片切换的动画效果

        //实现点击种类scroll自动滚动
        pointYs = new int[8];
        getScrollPoints();

        /**
         * 去拿轮播图片
         */
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchAd")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .addParams("dataReqModel.args.position","2")//2代表banner广告
                .build().execute(new MyBannerAdCallback());
        L.i(TAG, "banner url = " + Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_GETDATA + "?" + "sqlName=clientSearchAd&dataReqModel.args.needTotal=needTotal&dataReqModel.args.position=2" );
        /**
         * 去拿中间8个的图片
         */
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchLogicalService")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .build()
                .execute(new MallSpecieseCallback());
        L.i(TAG, "Species url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?" + "sqlName=clientSearchLogicalService&dataReqModel.args.needTotal=clientSearchLogicalService");

//        OkHttpUtils.post()
//                .url("http://192.168.1.7:8080/carService/getFile")
//                .addParams("fileReq.pathCode","0")
//                .addParams("fileReq.fileName","bde07f22-0380-4f2b-9ca4-8e094dfbce6b.png")
//                .build()
//                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "bde07f22-0380-4f2b-9ca4-8e094dfbce6b.png") {
//
//                    @Override
//                    public void inProgress(float progress, long total) {
//                        Log.e(TAG, "onError :");
//                    }
//
//                    @Override
//                    public void onBefore(Request request) {
//                        super.onBefore(request);
//                    }
//
//                    @Override
//                    public void inProgress(float progress) {
//                        Log.e(TAG, "onError :");
//                    }
//
//
//                    @Override
//                    public void onError(Call call, Exception e) {
//                        Log.e(TAG, "onError :" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(File file) {
//                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
//                    }
//                });
//




    }

    private void addImageviewTOListMap()
    {
        //中部分类快捷方式
        mMiddleSpeciesList = new ArrayList<>();
        mMiddleSpeciesList.add(speciese_01);
        mMiddleSpeciesList.add(speciese_02);
        mMiddleSpeciesList.add(speciese_03);
        mMiddleSpeciesList.add(speciese_04);
        mMiddleSpeciesList.add(speciese_05);
        mMiddleSpeciesList.add(speciese_06);
        mMiddleSpeciesList.add(speciese_07);
        mMiddleSpeciesList.add(speciese_07);

        //中部以下
        mShoppingmallBottomPicList = new ArrayList<>();
        mShoppingmallBottomPicMap = new HashMap<>();
        mShoppingmallBottomImageviewList = new ArrayList<>();
        //后期做加载图片的时候再做



    }

    /**
     * 轮播图片
     */
    public class MyBannerAdCallback extends StringCallback
    {
        @Override
        public void onBefore(Request request)
        {
            super.onBefore(request);
            L.i(TAG + "http MyStringCallback loading");
        }

        @Override
        public void onAfter()
        {
            super.onAfter();
            L.i(TAG + "http MyStringCallback onAfter");

        }

        @Override
        public void onError(Call call, Exception e)
        {

            L.i(TAG + "http MyStringCallback error "  + e.getMessage());
            List<String> bannerAdUrl = new ArrayList<>();
            String url = "drawable://" + R.mipmap.banner03;
            bannerAdUrl.add(url);
            shoppingmall_flashview_banner.setImageUris(bannerAdUrl);
            shoppingmall_flashview_banner.setEffect(EffectConstants.DEFAULT_EFFECT);//更改图片切换的动画效果
            toastMgr.builder.display("网络未连接, 请检查您的网络设置.", 1);
        }

        @Override
        public void onResponse(String response)
        {
            L.i(TAG + "http MyStringCallback onResponse " + response);
            FormatJson formatJson = new FormatJson(response);
            List<BannerAd> bannerad = formatJson.getBannerAdImageList();
            //赋值给全局变量
            mBannerAdList = bannerad;
            if (bannerad == null)
            {
                //解析json错误  肯定是服务器返回给我的有错误
                toastMgr.builder.display("服务器返回错误",0);
                return;
            }
            List<String> bannerAdUrl = new ArrayList<>();
            String url;
            int size = bannerad.size();
            if (size > 0)
            {
                //里面有数据
                for (int i= 0; i< size; i++)
                {
                    BannerAd ad = bannerad.get(i);
                    url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + ad.getPathCode() + "&fileReq.fileName=" + ad.getPhotoName();
                    bannerAdUrl.add(url);
                    L.d(TAG,url);
                }

            }
            else
            {
                //里面没数据   就显示一张默认的图片
                url = "drawable://" + R.mipmap.banner03;
            }


            shoppingmall_flashview_banner.setImageUris(bannerAdUrl);
            shoppingmall_flashview_banner.setEffect(EffectConstants.DEFAULT_EFFECT);//更改图片切换的动画效果
        }

        @Override
        public void inProgress(float progress)
        {
            L.i(TAG + "http MyStringCallback inProgress " + progress);
        }
    }


    /**
     * 中部图标
     */
    public class MallSpecieseCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络连接有问题, 请教检查您的网络设置",1);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG + "种类 json", response);
            FormatJson formatJson = new FormatJson(response);
            List<ShoppingmallSpeciesePicBean> picList;
            picList = formatJson.getShoppingMallSpeciesePic();
            if (picList == null)
            {
                toastMgr.builder.display("服务器错误,请稍后再试,", 1);
                return;
            }
            mShoppingmallSpeciesPicList = picList;
            //TODO
            //中部分类图片加载
            loadSpeciesImage();
        }

        @Override
        public void inProgress(float progress) {
            super.inProgress(progress);
            L.d(TAG + "MallSpecieseCallback  progress" + "  " + progress * 100);
        }
    }

    /**
     * 中部以下的图标获取
     */
    public class MallBottomCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络连接有问题, 请教检查您的网络设置",1);
        }

        @Override
        public void onResponse(String response) {
            L.i(TAG, "中部以下的图片json = " + response);
        }
    }

    /**
     * 中间部分图片加载
     */
    private void loadSpeciesImage()
    {
        int size = mMiddleSpeciesList.size();
        for (int i= 0; i < size - 1; i++)
        {
            ShoppingmallSpeciesePicBean shoppingmallSpeciesePicBean = mShoppingmallSpeciesPicList.get(i);
            String pathcode = shoppingmallSpeciesePicBean.getPathCode();
            String photoname = shoppingmallSpeciesePicBean.getPhotoName();
            String id        = shoppingmallSpeciesePicBean.getId();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;
            L.d(TAG,"商城中间图片加载url = " + url);
            ImageLoaderTools.getInstance(mContext).displayImage(url, mMiddleSpeciesList.get(i));

            //在此同时, 去拿对应的6个图片
            /**
             * 去拿中间以下6个的图片
             */
            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_GETDATA)
                    .addParams("sqlName", "clientSearchRepairService")
                    .addParams("dataReqModel.args.needTotal","needTotal")
                    .addParams("dataReqModel.args.logicalService",id)
                    .build()
                    .execute(new MallBottomCallback());
            L.i(TAG, "service all kinds bottom url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?" + "sqlName=clientSearchRepairService&dataReqModel.args.needTotal=needTotal&dataReqModel.args.logicalService="+id);
        }
    }


    private void getScrollPoints() {
        ViewTreeObserver observer = shoppingmall_more_01.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (hasMeasure)
                {

                }
                else
                {
                    int height = shoppingmall_more_01.getMeasuredHeight();
                    ViewGroup.LayoutParams params = shoppingmall_more_01.getLayoutParams();
                    L.d(TAG + "shoppingmall_more_01 pos" + shoppingmall01.getTop());
                    L.d(TAG + "shoppingmall_more_01 pos" + shoppingmall02.getTop());
                    L.d(TAG + "shoppingmall_more_01 pos" + shoppingmall03.getTop());
                    L.d(TAG + "shoppingmall_more_01 pos" + shoppingmall04.getTop());
                    L.d(TAG + "shoppingmall_more_01 pos" + shoppingmall05.getTop());
                    //还要增加两个 因为有8个选项
                    //TODO
                    pointYs[0] = shoppingmall01.getTop();
                    pointYs[1] = shoppingmall02.getTop();
                    pointYs[2] = shoppingmall03.getTop();
                    pointYs[3] = shoppingmall04.getTop();
                    pointYs[4] = shoppingmall05.getTop();
                    pointYs[5] = shoppingmall06.getTop();
                    hasMeasure = true;
                }
                return true;
            }
        });
    }

    /**
     * 找到控件位置 绑定
     */
    private void findViews() {



        //轮播广告
        shoppingmall_flashview_banner = (FlashView) findViewById(R.id.shoppingmall_flashview_banner);

        //搜索
        shoppingmall_search_imageview = (ImageView) findViewById(R.id.shoppingmall_search_imageview);

        scrollView = (ScrollView) findViewById(R.id.scrollView);


        //种类
        speciese_01 = (ImageView) findViewById(R.id.speciese_01);//保养维护
        speciese_02 = (ImageView) findViewById(R.id.speciese_02);//电子电路
        speciese_03 = (ImageView) findViewById(R.id.speciese_03);//发动机件
        speciese_04 = (ImageView) findViewById(R.id.speciese_04);//打黄油
        speciese_05 = (ImageView) findViewById(R.id.speciese_05);//底盘配件
        speciese_06 = (ImageView) findViewById(R.id.speciese_06);//车架配件
        speciese_07 = (ImageView) findViewById(R.id.speciese_07);//托架配件
        speciese_08 = (ImageView) findViewById(R.id.speciese_08);//更多


        //保养维护
        shoppingmall_more_01 = (ImageView) findViewById(R.id.shoppingmall_more_01);//更多
        main_product1_01     = (TextView) findViewById(R.id.main_product1_01);//主打产品
        main_product1_02     = (TextView) findViewById(R.id.main_product1_02);
        main_product1_03     = (TextView) findViewById(R.id.main_product1_03);
        main_product1_04     = (TextView) findViewById(R.id.main_product1_04);
        main_product1_05     = (TextView) findViewById(R.id.main_product1_05);
        main_product1_06     = (TextView) findViewById(R.id.main_product1_06);

        //电子电路
        shoppingmall_more_02 = (ImageView) findViewById(R.id.shoppingmall_more_02);//更多
        main_product2_01     = (TextView) findViewById(R.id.main_product2_01);//主打产品
        main_product2_02     = (TextView) findViewById(R.id.main_product2_02);
        main_product2_03     = (TextView) findViewById(R.id.main_product2_03);
        main_product2_04     = (TextView) findViewById(R.id.main_product2_04);
        main_product2_05     = (TextView) findViewById(R.id.main_product2_05);
        main_product2_06     = (TextView) findViewById(R.id.main_product2_06);

        //发动机件
        shoppingmall_more_03 = (ImageView) findViewById(R.id.shoppingmall_more_03);//更多
        main_product3_01     = (TextView) findViewById(R.id.main_product3_01);//主打产品
        main_product3_02     = (TextView) findViewById(R.id.main_product3_02);
        main_product3_03     = (TextView) findViewById(R.id.main_product3_03);
        main_product3_04     = (TextView) findViewById(R.id.main_product3_04);
        main_product3_05     = (TextView) findViewById(R.id.main_product3_05);
        main_product3_06     = (TextView) findViewById(R.id.main_product3_06);
        //底盘配件
        shoppingmall_more_04 = (ImageView) findViewById(R.id.shoppingmall_more_04);//更多
        main_product4_01     = (TextView) findViewById(R.id.main_product4_01);//主打产品
        main_product4_02     = (TextView) findViewById(R.id.main_product4_02);
        main_product4_03     = (TextView) findViewById(R.id.main_product4_03);
        main_product4_04     = (TextView) findViewById(R.id.main_product4_04);
        main_product4_05     = (TextView) findViewById(R.id.main_product4_05);
        main_product4_06     = (TextView) findViewById(R.id.main_product4_06);
        //车架配件
        shoppingmall_more_05 = (ImageView) findViewById(R.id.shoppingmall_more_05);//更多
        main_product5_01     = (TextView) findViewById(R.id.main_product5_01);//主打产品
        main_product5_02     = (TextView) findViewById(R.id.main_product5_02);
        main_product5_03     = (TextView) findViewById(R.id.main_product5_03);
        main_product5_04     = (TextView) findViewById(R.id.main_product5_04);
        main_product5_05     = (TextView) findViewById(R.id.main_product5_05);
        main_product5_06     = (TextView) findViewById(R.id.main_product5_06);
        //拖架配件
        shoppingmall_more_06 = (ImageView) findViewById(R.id.shoppingmall_more_06);//更多
        main_product6_01     = (TextView) findViewById(R.id.main_product6_01);//主打产品
        main_product6_02     = (TextView) findViewById(R.id.main_product6_02);
        main_product6_03     = (TextView) findViewById(R.id.main_product6_03);
        main_product6_04     = (TextView) findViewById(R.id.main_product6_04);
        main_product6_05     = (TextView) findViewById(R.id.main_product6_05);
        main_product6_06     = (TextView) findViewById(R.id.main_product6_06);

        shoppingmall01 = (LinearLayout) findViewById(R.id.shoppingmall01);
        shoppingmall02 = (LinearLayout) findViewById(R.id.shoppingmall02);
        shoppingmall03 = (LinearLayout) findViewById(R.id.shoppingmall03);
        shoppingmall04 = (LinearLayout) findViewById(R.id.shoppingmall04);
        shoppingmall05 = (LinearLayout) findViewById(R.id.shoppingmall05);
        shoppingmall06 = (LinearLayout) findViewById(R.id.shoppingmall06);



        //搜索点击后  跳转到搜索界面
        shoppingmall_search_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ShoppingMallActivity.this, SearchActivity.class);
                mContext.startActivity(intent);
            }
        });

        main_product1_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
            }
        });


        setClickListener();


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId())
        {
            //种类选择
            case R.id.speciese_01:
                scrollView.smoothScrollTo(0, pointYs[0]);
                break;
            case R.id.speciese_02:
                scrollView.smoothScrollTo(0, pointYs[1]);
                break;
            case R.id.speciese_03:
                scrollView.smoothScrollTo(0, pointYs[2]);
                break;
            case R.id.speciese_04:
                scrollView.smoothScrollTo(0, pointYs[3]);
                break;
            case R.id.speciese_05:
                scrollView.smoothScrollTo(0, pointYs[4]);
                break;
            case R.id.speciese_06:
                scrollView.smoothScrollTo(0, pointYs[5]);
                break;
            //这里也是紧接着A修改
            //TODO
            case R.id.speciese_07:
                scrollView.smoothScrollTo(0, pointYs[5]);
                break;
            case R.id.speciese_08:
                scrollView.smoothScrollTo(0, pointYs[5]);
                break;
            //保养维护
            case R.id.shoppingmall_more_01://更多
                toastMgr.builder.display("更多精彩", 0);
                break;
            case R.id.main_product1_01:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product1_02:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product1_03:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product1_04:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product1_05:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product1_06:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;

            //电子电路
            case R.id.shoppingmall_more_02://更多
                toastMgr.builder.display("更多精彩", 0);
                break;
            case R.id.main_product2_01:

                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product2_02:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product2_03:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product2_04:

                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product2_05:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product2_06:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;


            //发动机配件
            case R.id.shoppingmall_more_03://更多
                toastMgr.builder.display("更多精彩", 0);
                break;
            case R.id.main_product3_01:

                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product3_02:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product3_03:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product3_04:

                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product3_05:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product3_06:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;

            //底盘配件
            case R.id.shoppingmall_more_04://更多
                toastMgr.builder.display("更多精彩", 0);
                break;
            case R.id.main_product4_01:

                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product4_02:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product4_03:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product4_04:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product4_05:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product4_06:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;

            //车架配件
            case R.id.shoppingmall_more_05://更多
                toastMgr.builder.display("更多精彩", 0);
                break;
            case R.id.main_product5_01:

                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product5_02:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product5_03:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product5_04:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product5_05:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product5_06:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;

            //托架配件
            case R.id.shoppingmall_more_06://更多
                toastMgr.builder.display("更多精彩", 0);
                break;
            case R.id.main_product6_01:

                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product6_02:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product6_03:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product6_04:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product6_05:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_product6_06:
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;

        }

    }
    /**
     * 设置所有监听
     */
    private void setClickListener() {

        speciese_01.setOnClickListener(this);
        speciese_02.setOnClickListener(this);
        speciese_03.setOnClickListener(this);
        speciese_04.setOnClickListener(this);
        speciese_05.setOnClickListener(this);
        speciese_06.setOnClickListener(this);
        speciese_07.setOnClickListener(this);
        speciese_08.setOnClickListener(this);
        //保养维护
        shoppingmall_more_01.setOnClickListener(this);
        main_product1_01.setOnClickListener(this);
        main_product1_02.setOnClickListener(this);
        main_product1_03.setOnClickListener(this);
        main_product1_04.setOnClickListener(this);
        main_product1_05.setOnClickListener(this);
        main_product1_06.setOnClickListener(this);
        //电子电路
        shoppingmall_more_02.setOnClickListener(this);
        main_product2_01.setOnClickListener(this);
        main_product2_02.setOnClickListener(this);
        main_product2_03.setOnClickListener(this);
        main_product2_04.setOnClickListener(this);
        main_product2_05.setOnClickListener(this);
        main_product2_06.setOnClickListener(this);
//发动机件
        shoppingmall_more_03.setOnClickListener(this);
        main_product3_01.setOnClickListener(this);
        main_product3_02.setOnClickListener(this);
        main_product3_03.setOnClickListener(this);
        main_product3_04.setOnClickListener(this);
        main_product3_05.setOnClickListener(this);
        main_product3_06.setOnClickListener(this);
//底盘配件
        shoppingmall_more_04.setOnClickListener(this);
        main_product4_01.setOnClickListener(this);
        main_product4_02.setOnClickListener(this);
        main_product4_03.setOnClickListener(this);
        main_product4_04.setOnClickListener(this);
        main_product4_05.setOnClickListener(this);
        main_product4_06.setOnClickListener(this);
//车架配件
        shoppingmall_more_05.setOnClickListener(this);
        main_product5_01.setOnClickListener(this);
        main_product5_02.setOnClickListener(this);
        main_product5_03.setOnClickListener(this);
        main_product5_04.setOnClickListener(this);
        main_product5_05.setOnClickListener(this);
        main_product5_06.setOnClickListener(this);
//拖架配件
        shoppingmall_more_06.setOnClickListener(this);
        main_product6_01.setOnClickListener(this);
        main_product6_02.setOnClickListener(this);
        main_product6_03.setOnClickListener(this);
        main_product6_04.setOnClickListener(this);
        main_product6_05.setOnClickListener(this);
        main_product6_06.setOnClickListener(this);
    }




    /*
     * 适配器的定义,要继承BaseAdapter
     */
    public class ImageAdapter extends BaseAdapter {

        public ImageAdapter() {
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return strings[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            /*
             * 1.手工创建对象 2.加载xml文件
             */
            view = View.inflate(mContext, R.layout.item_shoppingmall_species_gridview, null);
            TextView species = (TextView) view.findViewById(R.id.item_shoppingmall_species_textview);
            species.setText(strings[position]);
            return view;
        }
    }




    private Context mContext;
    private final static String TAG = "ShoppingMallActivity";

    private FlashView shoppingmall_flashview_banner;
    private ArrayList<String> imageUrls;

    private ImageView shoppingmall_search_imageview;//搜索按钮

    private ScrollView scrollView;//

    //种类
    private ImageView speciese_01;//保养维护
    private ImageView speciese_02;//电子电路
    private ImageView speciese_03;//发动机件
    private ImageView speciese_04;//打黄油
    private ImageView speciese_05;//底盘配件
    private ImageView speciese_06;//车架配件
    private ImageView speciese_07;//托架配件
    private ImageView speciese_08;//更多
    //保养维护
    private ImageView shoppingmall_more_01;//更多
    private TextView  main_product1_01;//主打产品
    private TextView  main_product1_02;
    private TextView  main_product1_03;
    private TextView  main_product1_04;
    private TextView  main_product1_05;
    private TextView  main_product1_06;

    //电子电路
    private ImageView shoppingmall_more_02;
    private TextView  main_product2_01;
    private TextView  main_product2_02;
    private TextView  main_product2_03;
    private TextView  main_product2_04;
    private TextView  main_product2_05;
    private TextView  main_product2_06;

    //发动机件
    private ImageView shoppingmall_more_03;
    private TextView  main_product3_01;
    private TextView  main_product3_02;
    private TextView  main_product3_03;
    private TextView  main_product3_04;
    private TextView  main_product3_05;
    private TextView  main_product3_06;

    //底盘配件
    private ImageView shoppingmall_more_04;
    private TextView  main_product4_01;
    private TextView  main_product4_02;
    private TextView  main_product4_03;
    private TextView  main_product4_04;
    private TextView  main_product4_05;
    private TextView  main_product4_06;

    //车架配件
    private ImageView shoppingmall_more_05;
    private TextView  main_product5_01;
    private TextView  main_product5_02;
    private TextView  main_product5_03;
    private TextView  main_product5_04;
    private TextView  main_product5_05;
    private TextView  main_product5_06;


    //托架配件
    private ImageView shoppingmall_more_06;
    private TextView  main_product6_01;
    private TextView  main_product6_02;
    private TextView  main_product6_03;
    private TextView  main_product6_04;
    private TextView  main_product6_05;
    private TextView  main_product6_06;

    private LinearLayout shoppingmall01;
    private LinearLayout shoppingmall02;
    private LinearLayout shoppingmall03;
    private LinearLayout shoppingmall04;
    private LinearLayout shoppingmall05;
    private LinearLayout shoppingmall06;

}
