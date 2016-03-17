package com.android.andy.yubang.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.andy.yubang.banner.FlashView;
import com.android.andy.yubang.banner.constants.EffectConstants;
import com.android.andy.yubang.utils.L;
import com.android.andy.yubang.utils.toastMgr;
import com.android.andy.yubang.view.ForbiddenScrollGridView;
import com.andy.android.yubang.R;

import java.util.ArrayList;

/**
 * ShoppingMallActivity: 商城界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class ShoppingMallActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;
    private final static String TAG = "ShoppingMallActivity";

    private FlashView shoppingmall_flashview_banner;
    private ArrayList<String> imageUrls;

    private ImageView shoppingmall_search_imageview;//搜索按钮

    private ScrollView scrollView;//

    //种类
    private ImageView speciese_01;
    private ImageView speciese_02;
    private ImageView speciese_03;
    private ImageView speciese_04;
    private ImageView speciese_05;
    private ImageView speciese_06;
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

        //广告轮播
        imageUrls = new ArrayList<String>();
//        imageUrls.add("http://www.juzi2.com/uploads/allimg/130619/1_130619193218_1.jpg");
//        imageUrls.add("http://a.hiphotos.baidu.com/zhidao/pic/item/4034970a304e251f4dd80e61a786c9177f3e5378.jpg");
//        imageUrls.add("http://f.hiphotos.baidu.com/zhidao/pic/item/9e3df8dcd100baa12801ad224710b912c8fc2e7e.jpg");
        imageUrls.add("drawable://" + R.mipmap.banner01);
        imageUrls.add("drawable://" + R.mipmap.banner02);
        imageUrls.add("drawable://" + R.mipmap.banner03);


        shoppingmall_flashview_banner.setImageUris(imageUrls);
        shoppingmall_flashview_banner.setEffect(EffectConstants.DEFAULT_EFFECT);//更改图片切换的动画效果

        //实现点击种类scroll自动滚动
        pointYs = new int[6];
        getScrollPoints();


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
        speciese_01 = (ImageView) findViewById(R.id.speciese_01);
        speciese_02 = (ImageView) findViewById(R.id.speciese_02);
        speciese_03 = (ImageView) findViewById(R.id.speciese_03);
        speciese_04 = (ImageView) findViewById(R.id.speciese_04);
        speciese_05 = (ImageView) findViewById(R.id.speciese_05);
        speciese_06 = (ImageView) findViewById(R.id.speciese_06);
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


}
