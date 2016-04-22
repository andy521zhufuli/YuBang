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
import com.car.yubangapk.configs.BannerSkipType;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.FormatJson;
import com.car.yubangapk.json.bean.BannerAd;
import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;
import com.car.yubangapk.json.bean.ShoppingmallPicBean;
import com.car.yubangapk.json.bean.ShoppingmallSpeciesePicBean;
import com.car.yubangapk.json.formatJson.Json2ShoppingmallBottomPics;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqGetLogicalService;
import com.car.yubangapk.network.myHttpReq.HttpReqGetShoppingmallBanner;
import com.car.yubangapk.network.myHttpReq.HttpReqGetShoppingmallBannerInterface;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.MyStringCallback;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.Warn.UpdateApp;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.car.yubangapk.view.AlertDialog;

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

        //设置banner的点击监听
        setBannerClickedListener();

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

        //去拿轮播图片
        httpGetBannerPics();
        //去拿中间8个的图片
        httpGetMiddleSpeciesPics();
    }


    /**
     * 网络连接去拿banner的图片
     */
    private void httpGetBannerPics()
    {
        HttpReqGetShoppingmallBanner getShoppingmallBanner = new HttpReqGetShoppingmallBanner();
        getShoppingmallBanner.setCallback(new HttpReqGetShoppingmallBannerInterface() {
            @Override
            public void onSuccess(List<BannerAd> bannerad) {
                List<String> bannerAdUrl = new ArrayList<>();
                //赋值给全局变量
                mBannerAdList = bannerad;
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
            public void onFail(int errorCode, String message) {
                List<String> bannerAdUrl = new ArrayList<>();
                String url = "drawable://" + R.mipmap.banner03;
                bannerAdUrl.add(url);
                shoppingmall_flashview_banner.setImageUris(bannerAdUrl);
                shoppingmall_flashview_banner.setEffect(EffectConstants.DEFAULT_EFFECT);//更改图片切换的动画效果
                toastMgr.builder.display("网络未连接, 请检查您的网络设置.", 1);
            }
        });
        getShoppingmallBanner.getBannerPics();
    }

    /**
     * 去拿中间分类的小图标以及信息  LogicalService
     */
    private void httpGetMiddleSpeciesPics()
    {
        HttpReqGetLogicalService getLogicalService = new HttpReqGetLogicalService();
        getLogicalService.setCallback(new HttpReqCallback() {
            @Override
            public void onFail(int errorCode, String message) {
                if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
                {
                    UpdateApp.gotoUpdateApp(mContext);
                }
                else if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
                {
                    toastMgr.builder.display("网络连接有问题, 请教检查您的网络设置",1);
                }
            }

            @Override
            public void onSuccess(Object object) {
                List<ShoppingmallSpeciesePicBean> picList;
                picList = (List<ShoppingmallSpeciesePicBean>) object;
                mShoppingmallSpeciesPicList = picList;
                loadSpeciesImage(mShoppingmallSpeciesPicList);
            }
        });
        getLogicalService.getLogicalService();
    }


    /**
     * 中间部分图片加载
     */
    private void loadSpeciesImage(List<ShoppingmallSpeciesePicBean> picList)
    {
        int size = mMiddleSpeciesList.size();
        for (int i = 0; i < size - 1; i++)
        {
            ShoppingmallSpeciesePicBean shoppingmallSpeciesePicBean = picList.get(i);
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
                    .executeMy(new MallBottomCallback(),i);
            L.i(TAG, "service all kinds bottom " + i  + " url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                    + "sqlName=clientSearchRepairService&dataReqModel.args.needTotal=needTotal&dataReqModel.args.logicalService="+id);
        }
    }


    /**
     * 为顶部banner设置监听
     */
    private void setBannerClickedListener()
    {
        //广告轮播点击监听器设置
        shoppingmall_flashview_banner.setOnPageClickListener(new FlashViewListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                toastMgr.builder.display("position"+position + "clicked" , 0);
                if (mBannerAdList == null || mBannerAdList.size() == 0)
                {
                    toastMgr.builder.display("服务器错误,没有数据",1);
                }
                else
                {
                    BannerAd bannerAd = mBannerAdList.get(position);
                    toastMgr.builder.display(bannerAd.getAdvertisementName() + "skipType" + bannerAd.getSkipType(), 0);
                    String link = bannerAd.getLink();



                    String skipType = bannerAd.getSkipType();
                    if (BannerSkipType.SKIP_TYPE_WEB.equals(skipType))
                    {
                        //网页
                        intent.setClass(mContext, AdWebViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("link", link);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                    else if (BannerSkipType.SKIP_TYPE_PRODUCT_PACKAGE.equals(skipType))
                    {
                        //产品包
                        intent.setClass(mContext, ShoppingMallGoodsActivity.class);
                        Bundle bundle = new Bundle();
                        //传的就是repairService
                        bundle.putString(Configs.serviceId,link);
                        String carType = Configs.getLoginedInfo(mContext).getCarType();
                        if ("".equals(carType) || carType == null)
                        {
                            bannerClickWarnNoCarType();
                            return;
                        }
                        bundle.putString(Configs.FROM, Configs.FROM_SHOPPINGMALL);
                        bundle.putString(Configs.mCarType, carType);
                        intent.putExtras(bundle);
                        startActivity(intent);


                    }
                    else if (BannerSkipType.SKIP_TYPE_LOGIC_SERVICE.equals(skipType))
                    {
                        //逻辑服务
                        toastMgr.builder.display("没有相关商品", 1);
                    }
                }
            }
        });
    }


    /**
     * 中间分类8个
     */
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
        mMiddleSpeciesList.add(speciese_08);

        //中部以下
        mShoppingmallBottomPicList = new ArrayList<>();
        mShoppingmallBottomPicMap = new HashMap<>();
        mShoppingmallBottomImageviewList = new ArrayList<>();
        //后期做加载图片的时候再做



    }



    /**
     * 细节分类 没给7个图标 还不算广告
     * 中部以下的图标获取
     */
    public class MallBottomCallback extends MyStringCallback
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
                if (beans.get(0).isHasData() == true)
                {
                    //有数据  拿到了数据

                    setBottomPics(position, beans, response);
                }
                else
                {
                    toastMgr.builder.display("服务器异常!", 0);
                }
            }
        }

    }


    /**
     * 拿到了图片 去给下面的控件设置图片
     */
    private synchronized void setBottomPics(int position, List<Json2ShoppingmallBottomPicsBean> beanList,String response)
    {
        switch (position)
        {
            case 0://保养维护
                mBAOYANGWEIHUList = beanList;
                mBAOYANGWEIHUString = response;
                //先去去显示图片
                setBottomPicsBaoyang(beanList);
                break;
            case 1://电子电路
                mDIANZIDIANLUList = beanList;
                mDIANZIDIANLUString = response;
                setBottomPicsDianziDianlu(beanList);
                break;
            case 2://发动机件
                mFADONGJIJIANList = beanList;
                mFADONGJIJIANString = response;
                setBottomPicsFaDongjijian(beanList);
                break;
            case 3://打黄油
                mDAHUANGYOUList = beanList;
                setBottomPicsDaHuangyou(beanList);
                break;
            case 4://底盘配件
                mDIPANPEIJIANList = beanList;
                mDIPANPEIJIANString = response;
                setBottomPicsDiPan(beanList);
                break;
            case 5://车架配件
                mCHEJIAPEIJIANList = beanList;
                mCHEJIAPEIJIANString = response;
                setBottomPicsCheJia(beanList);
                break;
            case 6://托架配件
                mTUOJIAPEIJIANList = beanList;
                mTUOJIAPEIJIANString = response;
                break;
            case 7://更多
                mGEBGDUOList = beanList;
                mGEBGDUOString = response;
                //TODO
                break;



        }

    }

    /**
     * 保养维护 设置图片
     */
    private void setBottomPicsBaoyang(List<Json2ShoppingmallBottomPicsBean> beanList)
    {



        List<ImageView> baoyangweihuList = new ArrayList<>();
        baoyangweihuList.add(main_product1_01);
        baoyangweihuList.add(main_product1_02);
        baoyangweihuList.add(main_product1_03);
        baoyangweihuList.add(main_product1_04);
        baoyangweihuList.add(main_product1_05);
        baoyangweihuList.add(main_product1_06);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < 6; i++)
        {
            Json2ShoppingmallBottomPicsBean bean = beanList.get(i);
            String id;
            String logicalService;
            String pathCode;
            String photoName;
            String serviceCode;
            int repairServiceSort;
            String serviceName;
            id = bean.getId();
            logicalService = bean.getLogicalService();
            pathCode = bean.getPathCode();

            photoName = bean.getPhotoName();

            serviceCode = bean.getServiceCode();
            repairServiceSort = bean.getRepairServiceSort();
            serviceName = bean.getServiceName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathCode + "&fileReq.fileName=" + photoName;
            urls.add(url);
        }

        for (int i = 0; i < 6; i++)
        {
            ImageLoaderTools.getInstance(mContext).displayImage(urls.get(i),baoyangweihuList.get(i));
        }

    }


    /**
     * 电子电路 设置图片
     * @param beanList
     */
    private void setBottomPicsDianziDianlu(List<Json2ShoppingmallBottomPicsBean> beanList)
    {
        List<ImageView> baoyangweihuList = new ArrayList<>();
        baoyangweihuList.add(main_product2_01);
        baoyangweihuList.add(main_product2_02);
        baoyangweihuList.add(main_product2_03);
        baoyangweihuList.add(main_product2_04);
        baoyangweihuList.add(main_product2_05);
        baoyangweihuList.add(main_product2_06);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < 6; i++)
        {
            Json2ShoppingmallBottomPicsBean bean = beanList.get(i);
            String id;
            String logicalService;
            String pathCode;
            String photoName;
            String serviceCode;
            int repairServiceSort;
            String serviceName;
            id = bean.getId();
            logicalService = bean.getLogicalService();
            pathCode = bean.getPathCode();

            photoName = bean.getPhotoName();

            serviceCode = bean.getServiceCode();
            repairServiceSort = bean.getRepairServiceSort();
            serviceName = bean.getServiceName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathCode + "&fileReq.fileName=" + photoName;
            urls.add(url);
        }

        for (int i = 0; i < 6; i++)
        {
            ImageLoaderTools.getInstance(mContext).displayImage(urls.get(i), baoyangweihuList.get(i));
        }
    }


    /**
     * 发动机件 设置图片
     * @param beanList
     */
    private void setBottomPicsFaDongjijian(List<Json2ShoppingmallBottomPicsBean> beanList)
    {
        List<ImageView> baoyangweihuList = new ArrayList<>();
        baoyangweihuList.add(main_product3_01);
        baoyangweihuList.add(main_product3_02);
        baoyangweihuList.add(main_product3_03);
        baoyangweihuList.add(main_product3_04);
        baoyangweihuList.add(main_product3_05);
        baoyangweihuList.add(main_product3_06);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < 6; i++)
        {
            Json2ShoppingmallBottomPicsBean bean = beanList.get(i);
            String id;
            String logicalService;
            String pathCode;
            String photoName;
            String serviceCode;
            int repairServiceSort;
            String serviceName;
            id = bean.getId();
            logicalService = bean.getLogicalService();
            pathCode = bean.getPathCode();

            photoName = bean.getPhotoName();

            serviceCode = bean.getServiceCode();
            repairServiceSort = bean.getRepairServiceSort();
            serviceName = bean.getServiceName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathCode + "&fileReq.fileName=" + photoName;
            urls.add(url);
        }

        for (int i = 0; i < 6; i++)
        {
            ImageLoaderTools.getInstance(mContext).displayImage(urls.get(i),baoyangweihuList.get(i));
        }
    }
    /**
     * 打黄油 设置图片
     * @param beanList
     */
    private void setBottomPicsDaHuangyou(List<Json2ShoppingmallBottomPicsBean> beanList)
    {
        List<ImageView> baoyangweihuList = new ArrayList<>();
        baoyangweihuList.add(main_product4_01);
        baoyangweihuList.add(main_product4_02);
        baoyangweihuList.add(main_product4_03);
        baoyangweihuList.add(main_product4_04);
        baoyangweihuList.add(main_product4_05);
        baoyangweihuList.add(main_product4_06);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < 6; i++)
        {
            Json2ShoppingmallBottomPicsBean bean = beanList.get(i);
            String id;
            String logicalService;
            String pathCode;
            String photoName;
            String serviceCode;
            int repairServiceSort;
            String serviceName;
            id = bean.getId();
            logicalService = bean.getLogicalService();
            pathCode = bean.getPathCode();

            photoName = bean.getPhotoName();

            serviceCode = bean.getServiceCode();
            repairServiceSort = bean.getRepairServiceSort();
            serviceName = bean.getServiceName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathCode + "&fileReq.fileName=" + photoName;
            urls.add(url);
        }

        for (int i = 0; i < 6; i++)
        {
            ImageLoaderTools.getInstance(mContext).displayImage(urls.get(i),baoyangweihuList.get(i));
        }
    }


    /**
     * 底盘 设置图片
     * @param beanList
     */
    private void setBottomPicsDiPan(List<Json2ShoppingmallBottomPicsBean> beanList)
    {
        List<ImageView> baoyangweihuList = new ArrayList<>();
        baoyangweihuList.add(main_product5_01);
        baoyangweihuList.add(main_product5_02);
        baoyangweihuList.add(main_product5_03);
        baoyangweihuList.add(main_product5_04);
        baoyangweihuList.add(main_product5_05);
        baoyangweihuList.add(main_product5_06);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < 6; i++)
        {
            Json2ShoppingmallBottomPicsBean bean = beanList.get(i);
            String id;
            String logicalService;
            String pathCode;
            String photoName;
            String serviceCode;
            int repairServiceSort;
            String serviceName;
            id = bean.getId();
            logicalService = bean.getLogicalService();
            pathCode = bean.getPathCode();

            photoName = bean.getPhotoName();

            serviceCode = bean.getServiceCode();
            repairServiceSort = bean.getRepairServiceSort();
            serviceName = bean.getServiceName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathCode + "&fileReq.fileName=" + photoName;
            urls.add(url);
        }

        for (int i = 0; i < 6; i++)
        {
            ImageLoaderTools.getInstance(mContext).displayImage(urls.get(i),baoyangweihuList.get(i));
        }
    }

    /**
     * 车架 设置图片
     * @param beanList
     */
    private void setBottomPicsCheJia(List<Json2ShoppingmallBottomPicsBean> beanList)
    {
        List<ImageView> baoyangweihuList = new ArrayList<>();
        baoyangweihuList.add(main_product6_01);
        baoyangweihuList.add(main_product6_02);
        baoyangweihuList.add(main_product6_03);
        baoyangweihuList.add(main_product6_04);
        baoyangweihuList.add(main_product6_05);
        baoyangweihuList.add(main_product6_06);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < 6; i++)
        {
            Json2ShoppingmallBottomPicsBean bean = beanList.get(i);
            String id;
            String logicalService;
            String pathCode;
            String photoName;
            String serviceCode;
            int repairServiceSort;
            String serviceName;
            id = bean.getId();
            logicalService = bean.getLogicalService();
            pathCode = bean.getPathCode();

            photoName = bean.getPhotoName();

            serviceCode = bean.getServiceCode();
            repairServiceSort = bean.getRepairServiceSort();
            serviceName = bean.getServiceName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathCode + "&fileReq.fileName=" + photoName;
            urls.add(url);
        }

        for (int i = 0; i < 6; i++)
        {
            ImageLoaderTools.getInstance(mContext).displayImage(urls.get(i),baoyangweihuList.get(i));
        }
    }

    /**
     * 点击分类跳转到对应分类
     */
    private void getScrollPoints() {
        ViewTreeObserver observer = shoppingmall_more_01.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (hasMeasure) {

                } else {
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
        main_product1_01     = (ImageView) findViewById(R.id.main_product1_01);//主打产品
        main_product1_02     = (ImageView) findViewById(R.id.main_product1_02);
        main_product1_03     = (ImageView) findViewById(R.id.main_product1_03);
        main_product1_04     = (ImageView) findViewById(R.id.main_product1_04);
        main_product1_05     = (ImageView) findViewById(R.id.main_product1_05);
        main_product1_06     = (ImageView) findViewById(R.id.main_product1_06);

        //电子电路
        shoppingmall_more_02 = (ImageView) findViewById(R.id.shoppingmall_more_02);//更多
        main_product2_01     = (ImageView) findViewById(R.id.main_product2_01);//主打产品
        main_product2_02     = (ImageView) findViewById(R.id.main_product2_02);
        main_product2_03     = (ImageView) findViewById(R.id.main_product2_03);
        main_product2_04     = (ImageView) findViewById(R.id.main_product2_04);
        main_product2_05     = (ImageView) findViewById(R.id.main_product2_05);
        main_product2_06     = (ImageView) findViewById(R.id.main_product2_06);

        //发动机件
        shoppingmall_more_03 = (ImageView) findViewById(R.id.shoppingmall_more_03);//更多
        main_product3_01     = (ImageView) findViewById(R.id.main_product3_01);//主打产品
        main_product3_02     = (ImageView) findViewById(R.id.main_product3_02);
        main_product3_03     = (ImageView) findViewById(R.id.main_product3_03);
        main_product3_04     = (ImageView) findViewById(R.id.main_product3_04);
        main_product3_05     = (ImageView) findViewById(R.id.main_product3_05);
        main_product3_06     = (ImageView) findViewById(R.id.main_product3_06);
        //底盘配件
        shoppingmall_more_04 = (ImageView) findViewById(R.id.shoppingmall_more_04);//更多
        main_product4_01     = (ImageView) findViewById(R.id.main_product4_01);//主打产品
        main_product4_02     = (ImageView) findViewById(R.id.main_product4_02);
        main_product4_03     = (ImageView) findViewById(R.id.main_product4_03);
        main_product4_04     = (ImageView) findViewById(R.id.main_product4_04);
        main_product4_05     = (ImageView) findViewById(R.id.main_product4_05);
        main_product4_06     = (ImageView) findViewById(R.id.main_product4_06);
        //车架配件
        shoppingmall_more_05 = (ImageView) findViewById(R.id.shoppingmall_more_05);//更多
        main_product5_01     = (ImageView) findViewById(R.id.main_product5_01);//主打产品
        main_product5_02     = (ImageView) findViewById(R.id.main_product5_02);
        main_product5_03     = (ImageView) findViewById(R.id.main_product5_03);
        main_product5_04     = (ImageView) findViewById(R.id.main_product5_04);
        main_product5_05     = (ImageView) findViewById(R.id.main_product5_05);
        main_product5_06     = (ImageView) findViewById(R.id.main_product5_06);
        //拖架配件
        shoppingmall_more_06 = (ImageView) findViewById(R.id.shoppingmall_more_06);//更多
        main_product6_01     = (ImageView) findViewById(R.id.main_product6_01);//主打产品
        main_product6_02     = (ImageView) findViewById(R.id.main_product6_02);
        main_product6_03     = (ImageView) findViewById(R.id.main_product6_03);
        main_product6_04     = (ImageView) findViewById(R.id.main_product6_04);
        main_product6_05     = (ImageView) findViewById(R.id.main_product6_05);
        main_product6_06     = (ImageView) findViewById(R.id.main_product6_06);

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
        setClickListener();


    }

    @Override
    public void onClick(View view) {
        final Intent intent = new Intent();
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
            //保养维护
            case R.id.main_product1_01:
                repairServiceItemClick(1, 1);
                break;
            case R.id.main_product1_02:
                repairServiceItemClick(1, 2);
                break;
            case R.id.main_product1_03:
                repairServiceItemClick(1, 3);
                break;
            case R.id.main_product1_04:
                repairServiceItemClick(1, 4);
                break;
            case R.id.main_product1_05:
                repairServiceItemClick(1, 5);
                break;
            case R.id.main_product1_06:
                repairServiceItemClick(1, 6);
                break;
            //电子电路
            case R.id.shoppingmall_more_02://更多
                toastMgr.builder.display("更多精彩", 0);
                break;
            case R.id.main_product2_01:

                repairServiceItemClick(2, 1);

                break;
            case R.id.main_product2_02:
                repairServiceItemClick(2, 2);
                break;
            case R.id.main_product2_03:
                repairServiceItemClick(2, 3);
                break;
            case R.id.main_product2_04:

                repairServiceItemClick(2, 4);
                break;
            case R.id.main_product2_05:
                repairServiceItemClick(2, 5);
                break;
            case R.id.main_product2_06:
                repairServiceItemClick(2, 6);
                break;


            //发动机配件
            case R.id.shoppingmall_more_03://更多
                toastMgr.builder.display("更多精彩", 0);
                break;
            case R.id.main_product3_01:

                repairServiceItemClick(3,1);

                break;
            case R.id.main_product3_02:
                repairServiceItemClick(3, 2);


                break;
            case R.id.main_product3_03:
                repairServiceItemClick(3, 3);

                break;
            case R.id.main_product3_04:
                repairServiceItemClick(3, 4);


                break;
            case R.id.main_product3_05:
                repairServiceItemClick(3, 5);

                break;
            case R.id.main_product3_06:
                repairServiceItemClick(3, 6);

                break;

            //底盘配件
            case R.id.shoppingmall_more_04://更多
                toastMgr.builder.display("更多精彩", 0);
                break;
            case R.id.main_product4_01:

                repairServiceItemClick(4, 1);
                break;
            case R.id.main_product4_02:
                repairServiceItemClick(4, 2);
                break;
            case R.id.main_product4_03:
                repairServiceItemClick(4, 3);
                break;
            case R.id.main_product4_04:
                repairServiceItemClick(4, 4);
                break;
            case R.id.main_product4_05:
                repairServiceItemClick(4, 5);
                break;
            case R.id.main_product4_06:
                repairServiceItemClick(4, 6);
                break;

            //车架配件
            case R.id.shoppingmall_more_05://更多
                toastMgr.builder.display("更多精彩", 0);
                break;
            case R.id.main_product5_01:

                repairServiceItemClick(5, 1);
                break;
            case R.id.main_product5_02:
                repairServiceItemClick(5, 2);
                break;
            case R.id.main_product5_03:
                repairServiceItemClick(5, 3);
                break;
            case R.id.main_product5_04:
                repairServiceItemClick(5, 4);
                break;
            case R.id.main_product5_05:
                repairServiceItemClick(5, 5);
                break;
            case R.id.main_product5_06:
                repairServiceItemClick(5, 6);
                break;

            //车架
            case R.id.shoppingmall_more_06://更多
                toastMgr.builder.display("更多精彩", 0);
                break;
            case R.id.main_product6_01:

                repairServiceItemClick(6, 1);
                break;
            case R.id.main_product6_02:
                repairServiceItemClick(6, 2);
                break;
            case R.id.main_product6_03:
                repairServiceItemClick(6, 3);
                break;
            case R.id.main_product6_04:
                repairServiceItemClick(6, 4);
                break;
            case R.id.main_product6_05:
                repairServiceItemClick(6, 5);
                break;
            case R.id.main_product6_06:
                repairServiceItemClick(6, 6);
                break;

        }

    }

    /**
     * 提示用户 您好没有添加车型  麻痹的麻溜的去添加车型
     */
    private void warnNoCarType()
    {
        AlertDialog alertDialog = new AlertDialog(mContext);
        alertDialog.builder()
                .setCancelable(false)
                .setTitle("提示")
                .setMsg("您还没有添加车型,请添加车型")
                .setPositiveButton("去添加", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gotoAddRegisterDetailAddCarType();
                    }
                })
                .setNegativeButton("先逛逛", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .show();
    }


    /**
     * banner点击的时候
     * 发现没有车型
     */
    private void bannerClickWarnNoCarType()
    {
        AlertDialog alertDialog = new AlertDialog(mContext);
        alertDialog.builder()
                .setCancelable(false)
                .setTitle("提示")
                .setMsg("您还没有添加车型,请添加车型")
                .setPositiveButton("去添加", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gotoAddRegisterDetailAddCarType();
                    }
                })
                .show();
    }

    /**
     * 去添加车型
     */
    private void gotoAddRegisterDetailAddCarType() {
        Intent intent1 = new Intent();
        intent1.setClass(mContext, RegisterDetailsActivity.class);
        Bundle bundle1 = new Bundle();
        Json2LoginBean json2LoginBean = Configs.getLoginedInfo(mContext);
        String id = json2LoginBean.getUserid();
        bundle1.putString("userid",id);
        intent1.putExtras(bundle1);
        startActivity(intent1);
    }


    /**
     * 底下的repairService点击 跳转到产品包界面
     * @param type
     * @param item
     */
    private void repairServiceItemClick(int type, int item)
    {
        Intent intent =  new Intent();
        intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
        Json2ShoppingmallBottomPicsBean json2ShoppingmallBottomPicsBean6 = null;
        Bundle bundle6 = new Bundle();


        bundle6.putString(Configs.FROM,Configs.FROM_SHOPPINGMALL);

        if (type == 1)
        {

            if (mBAOYANGWEIHUList == null || mBAOYANGWEIHUList.size() == 0)
            {
                toastMgr.builder.display("服务器异常,没有数据", 1);

                return;
            }
            bundle6.putString("repairService",mBAOYANGWEIHUString);

            if (item == 1)
            {
                json2ShoppingmallBottomPicsBean6 = mBAOYANGWEIHUList.get(BAOYANGWEIHU);
            }
            else if (item == 2)
            {
                json2ShoppingmallBottomPicsBean6 = mBAOYANGWEIHUList.get(DIANZIDIANLU);
            }
            else if (item == 3)
            {
                json2ShoppingmallBottomPicsBean6 = mBAOYANGWEIHUList.get(FADONGJIJIAN);
            }
            else if (item == 4)
            {
                json2ShoppingmallBottomPicsBean6 = mBAOYANGWEIHUList.get(DAHUANGYOU);
            }
            else if(item == 5)
            {
                json2ShoppingmallBottomPicsBean6 = mBAOYANGWEIHUList.get(DIPANPEIJIAN);
            }
            else if (item == 6)
            {
                json2ShoppingmallBottomPicsBean6 = mBAOYANGWEIHUList.get(CHEJIAPEIJIAN);
            }


        }
        else if (type == 2)
        {
            if (mDIANZIDIANLUList == null || mDIANZIDIANLUList.size() == 0)
            {
                toastMgr.builder.display("服务器异常,没有数据", 1);

                return;
            }
            bundle6.putString("repairService",mDIANZIDIANLUString);
            if (item == 1)
            {
                json2ShoppingmallBottomPicsBean6 = mDIANZIDIANLUList.get(BAOYANGWEIHU);
            }
            else if (item == 2)
            {
                json2ShoppingmallBottomPicsBean6 = mDIANZIDIANLUList.get(DIANZIDIANLU);
            }
            else if (item == 3)
            {
                json2ShoppingmallBottomPicsBean6 = mDIANZIDIANLUList.get(FADONGJIJIAN);
            }
            else if (item == 4)
            {
                json2ShoppingmallBottomPicsBean6 = mDIANZIDIANLUList.get(DAHUANGYOU);
            }
            else if (item == 5)
            {
                json2ShoppingmallBottomPicsBean6 = mDIANZIDIANLUList.get(DIPANPEIJIAN);
            }
            else if (item == 6)
            {
                json2ShoppingmallBottomPicsBean6 = mDIANZIDIANLUList.get(CHEJIAPEIJIAN);
            }
        }
        else if(type == 3)
        {

            if (mFADONGJIJIANList == null || mFADONGJIJIANList.size() == 0)
            {
                toastMgr.builder.display("服务器异常,没有数据", 1);

                return;
            }
            bundle6.putString("repairService",mFADONGJIJIANString);

            if (item == 1)
            {
                json2ShoppingmallBottomPicsBean6 = mFADONGJIJIANList.get(BAOYANGWEIHU);
            }
            else if (item == 2)
            {
                json2ShoppingmallBottomPicsBean6 = mFADONGJIJIANList.get(DIANZIDIANLU);
            }
            else if (item == 3)
            {
                json2ShoppingmallBottomPicsBean6 = mFADONGJIJIANList.get(FADONGJIJIAN);
            }
            else if (item == 4)
            {
                json2ShoppingmallBottomPicsBean6 = mFADONGJIJIANList.get(DAHUANGYOU);
            }
            else if (item == 5)
            {
                json2ShoppingmallBottomPicsBean6 = mFADONGJIJIANList.get(DIPANPEIJIAN);
            }
            else if (item == 6)
            {
                json2ShoppingmallBottomPicsBean6 = mFADONGJIJIANList.get(CHEJIAPEIJIAN);
            }
        }
        else if(type == 4)
        {

            if (mDAHUANGYOUList == null || mDAHUANGYOUList.size() == 0)
            {
                toastMgr.builder.display("服务器异常,没有数据", 1);

                return;
            }
            bundle6.putString("repairService",mDAHUANGYOUString);
            if (item == 1)
            {
                json2ShoppingmallBottomPicsBean6 = mDAHUANGYOUList.get(BAOYANGWEIHU);
            }
            else if (item == 2)
            {
                json2ShoppingmallBottomPicsBean6 = mDAHUANGYOUList.get(DIANZIDIANLU);
            }
            else if (item == 3)
            {
                json2ShoppingmallBottomPicsBean6 = mDAHUANGYOUList.get(FADONGJIJIAN);
            }
            else if (item == 4)
            {
                json2ShoppingmallBottomPicsBean6 = mDAHUANGYOUList.get(DAHUANGYOU);
            }
            else if (item == 5)
            {
                json2ShoppingmallBottomPicsBean6 = mDAHUANGYOUList.get(DIPANPEIJIAN);
            }
            else if (item == 6)
            {
                json2ShoppingmallBottomPicsBean6 = mDAHUANGYOUList.get(CHEJIAPEIJIAN);
            }
        }
        else if(type == 5)
        {

            if (mDIPANPEIJIANList == null || mDIPANPEIJIANList.size() == 0)
            {
                toastMgr.builder.display("服务器异常,没有数据", 1);

                return;
            }
            bundle6.putString("repairService",mDIPANPEIJIANString);
            if (item == 1)
            {
                json2ShoppingmallBottomPicsBean6 = mDIPANPEIJIANList.get(BAOYANGWEIHU);
            }
            else if (item == 2)
            {
                json2ShoppingmallBottomPicsBean6 = mDIPANPEIJIANList.get(DIANZIDIANLU);
            }
            else if (item == 3)
            {
                json2ShoppingmallBottomPicsBean6 = mDIPANPEIJIANList.get(FADONGJIJIAN);
            }
            else if (item == 4)
            {
                json2ShoppingmallBottomPicsBean6 = mDIPANPEIJIANList.get(DAHUANGYOU);
            }
            else if (item == 5)
            {
                json2ShoppingmallBottomPicsBean6 = mDIPANPEIJIANList.get(DIPANPEIJIAN);
            }
            else if (item == 6)
            {
                json2ShoppingmallBottomPicsBean6 = mDIPANPEIJIANList.get(CHEJIAPEIJIAN);
            }
        }
        else if(type == 6)
        {

            if (mCHEJIAPEIJIANList == null || mCHEJIAPEIJIANList.size() == 0)
            {
                toastMgr.builder.display("服务器异常,没有数据", 1);

                return;
            }
            bundle6.putString("repairService",mCHEJIAPEIJIANString);
            if (item == 1)
            {
                json2ShoppingmallBottomPicsBean6 = mCHEJIAPEIJIANList.get(BAOYANGWEIHU);
            }
            else if (item == 2)
            {
                json2ShoppingmallBottomPicsBean6 = mCHEJIAPEIJIANList.get(DIANZIDIANLU);
            }
            else if (item == 3)
            {
                json2ShoppingmallBottomPicsBean6 = mCHEJIAPEIJIANList.get(FADONGJIJIAN);
            }
            else if (item == 4)
            {
                json2ShoppingmallBottomPicsBean6 = mCHEJIAPEIJIANList.get(DAHUANGYOU);
            }
            else if (item == 5)
            {
                json2ShoppingmallBottomPicsBean6 = mCHEJIAPEIJIANList.get(DIPANPEIJIAN);
            }
            else if (item == 6)
            {
                json2ShoppingmallBottomPicsBean6 = mCHEJIAPEIJIANList.get(CHEJIAPEIJIAN);
            }
        }



        String serviceId6 = json2ShoppingmallBottomPicsBean6.getId();
        String carType6 = Configs.getLoginedInfo(mContext).getCarType();
        mCarType = carType6;

        if (carType6 == null || carType6.equals(""))
        {
            warnNoCarType();
        }
        else
        {

            bundle6.putString("serviceId", serviceId6);
            bundle6.putString("mCarType", mCarType);

            intent.putExtras(bundle6);
            startActivity(intent);

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

    //是不是通过网络拿到了bottom的图片  这样点击的时候才知道跳转的参数
    private boolean isBottomPicGetted = false;
    private String  mCarType;//用户选的车型

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
    private ImageView  main_product1_01;//主打产品
    private ImageView  main_product1_02;
    private ImageView  main_product1_03;
    private ImageView  main_product1_04;
    private ImageView  main_product1_05;
    private ImageView  main_product1_06;

    //电子电路
    private ImageView shoppingmall_more_02;
    private ImageView  main_product2_01;
    private ImageView  main_product2_02;
    private ImageView  main_product2_03;
    private ImageView  main_product2_04;
    private ImageView  main_product2_05;
    private ImageView  main_product2_06;

    //发动机件
    private ImageView shoppingmall_more_03;
    private ImageView  main_product3_01;
    private ImageView  main_product3_02;
    private ImageView  main_product3_03;
    private ImageView  main_product3_04;
    private ImageView  main_product3_05;
    private ImageView  main_product3_06;

    //底盘配件
    private ImageView shoppingmall_more_04;
    private ImageView main_product4_01;
    private ImageView main_product4_02;
    private ImageView main_product4_03;
    private ImageView main_product4_04;
    private ImageView main_product4_05;
    private ImageView main_product4_06;

    //车架配件
    private ImageView shoppingmall_more_05;
    private ImageView main_product5_01;
    private ImageView main_product5_02;
    private ImageView main_product5_03;
    private ImageView main_product5_04;
    private ImageView main_product5_05;
    private ImageView main_product5_06;


    //托架配件
    private ImageView shoppingmall_more_06;
    private ImageView main_product6_01;
    private ImageView main_product6_02;
    private ImageView main_product6_03;
    private ImageView main_product6_04;
    private ImageView main_product6_05;
    private ImageView main_product6_06;

    private LinearLayout shoppingmall01;
    private LinearLayout shoppingmall02;
    private LinearLayout shoppingmall03;
    private LinearLayout shoppingmall04;
    private LinearLayout shoppingmall05;
    private LinearLayout shoppingmall06;

    private static int BAOYANGWEIHU  = 0;//保养维护
    private static int DIANZIDIANLU  = 1;//电子电路
    private static int FADONGJIJIAN  = 2;//发动机件
    private static int DAHUANGYOU    = 3;//打黄油
    private static int DIPANPEIJIAN  = 4;//底盘配件
    private static int CHEJIAPEIJIAN = 5;//车架配件
    private static int TUOJIAPEIJIAN = 6;//托架配件
    private static int GEBGDUO       = 7;//更多


    private List<Json2ShoppingmallBottomPicsBean> mBAOYANGWEIHUList;        //保养维护保存获取的图片信息
    private List<Json2ShoppingmallBottomPicsBean> mDIANZIDIANLUList;        //电子电路保存获取的图片信息
    private List<Json2ShoppingmallBottomPicsBean> mFADONGJIJIANList;        //发动机件保存获取的图片信息
    private List<Json2ShoppingmallBottomPicsBean> mDAHUANGYOUList;          //打黄油保存获取的图片信息
    private List<Json2ShoppingmallBottomPicsBean> mDIPANPEIJIANList;        //底盘配件保存获取的图片信息
    private List<Json2ShoppingmallBottomPicsBean> mCHEJIAPEIJIANList;       //车架配件保存获取的图片信息
    private List<Json2ShoppingmallBottomPicsBean> mTUOJIAPEIJIANList;       //托架配件保存获取的图片信息
    private List<Json2ShoppingmallBottomPicsBean> mGEBGDUOList;             //更多保存获取的图片信息

    private String mBAOYANGWEIHUString;
    private String mDIANZIDIANLUString;
    private String mFADONGJIJIANString;
    private String mDAHUANGYOUString;
    private String mDIPANPEIJIANString;
    private String mCHEJIAPEIJIANString;
    private String mTUOJIAPEIJIANString;
    private String mGEBGDUOString;




    //banner广告点击
    List<BannerAd> mBannerAdList = null;//全局的banner广告变量  保存广告相关信息

}
