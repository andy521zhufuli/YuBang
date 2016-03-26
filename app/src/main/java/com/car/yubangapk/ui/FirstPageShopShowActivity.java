package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;

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
    private LinearLayout   first_page_shop_show_wheel_service;//轮胎服务
    private LinearLayout   first_page_shop_show_baoyang_service;//保养服务
    private LinearLayout   first_page_shop_show_sales_activity;//优惠活动
    private LinearLayout   first_page_shop_show_customers_comments;//客户评价

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first_page_shop_show);

        mContext = this;

        findViews();

    }

    private void findViews() {

        photo_show = (LinearLayout) findViewById(R.id.photo_show);//点击门店
        first_page_shop_show_wheel_service = (LinearLayout) findViewById(R.id.first_page_shop_show_wheel_service);//轮胎服务
        first_page_shop_show_baoyang_service = (LinearLayout) findViewById(R.id.first_page_shop_show_baoyang_service);//保养服务
        first_page_shop_show_sales_activity = (LinearLayout) findViewById(R.id.first_page_shop_show_sales_activity);//优惠活动
        first_page_shop_show_customers_comments = (LinearLayout) findViewById(R.id.first_page_shop_show_customers_comments);//客户评价

        /**
         * 注册监听器
         */
        photo_show.setOnClickListener(this);
        first_page_shop_show_wheel_service.setOnClickListener(this);
        first_page_shop_show_baoyang_service.setOnClickListener(this);
        first_page_shop_show_sales_activity.setOnClickListener(this);
        first_page_shop_show_customers_comments.setOnClickListener(this);

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
            //轮胎服务
            case R.id.first_page_shop_show_wheel_service:
                toastMgr.builder.display( "轮胎服务" , 0);
                //这里应该跳到对应的商品里面
                //TODO 这里应该跳到对应的商品里面
                intent.setClass(FirstPageShopShowActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            //保养服务
            case R.id.first_page_shop_show_baoyang_service:
                toastMgr.builder.display( "保养服务" , 0);
                //TODO 这里应该跳到对应的商品里面
                intent.setClass(FirstPageShopShowActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            //游湖活动
            case R.id.first_page_shop_show_sales_activity:
                toastMgr.builder.display( "优惠活动" , 0);
                //TODO 这里应该跳到对应的商品里面
                intent.setClass(FirstPageShopShowActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;
            //客户评价
            case R.id.first_page_shop_show_customers_comments:
                toastMgr.builder.display( "客户评论" , 0);
                //TODO 这里应该跳到对应的商品里面
                intent.setClass(FirstPageShopShowActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
                break;

        }
        intent = null;
    }
}
