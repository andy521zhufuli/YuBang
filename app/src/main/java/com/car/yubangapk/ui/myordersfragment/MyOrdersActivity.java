package com.car.yubangapk.ui.myordersfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;


import com.andy.android.yubang.R;
import com.car.yubangapk.view.ScrollNavigationTab.PagerAdapter;
import com.car.yubangapk.view.ScrollNavigationTab.ScrollTabsAdapter;
import com.car.yubangapk.view.ScrollNavigationTab.TabAdapter;
import com.car.yubangapk.view.ScrollNavigationTab.ScrollTabView;
import com.umeng.analytics.MobclickAgent;

/**
 * MyOrdersActivity: 我的订单
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-27
 */
public class MyOrdersActivity extends FragmentActivity{

    private Context mContext;

    private ImageView       img_back;//返回
    private ScrollTabView   my_order_tab;//
    private ViewPager       my_orders_viewpager;
    private TabAdapter      tabsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        mContext =this;

        findViews();

        initTabs();
        initViewPager();

    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);//返回
        my_order_tab = (ScrollTabView) findViewById(R.id.my_order_tab);//
        my_orders_viewpager = (ViewPager) findViewById(R.id.my_orders_viewpager);
        /**
         * 注册监听
         */

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void initTabs()
    {
        tabsAdapter = new ScrollTabsAdapter(this);
        tabsAdapter.add(ALL_ORDER);
        tabsAdapter.add(WAIT_SIGN);
        tabsAdapter.add(WAIT_BUYER);
        tabsAdapter.add(WAIT_SHOP_INSTALL);
        tabsAdapter.add(INSTALLED);
        tabsAdapter.add(WAIT_EVALUATE);
        tabsAdapter.add(DEAL_SUCCESS);
        tabsAdapter.add(DEAL_FAIL);
        my_order_tab.setAdapter(tabsAdapter);
    }

    void initViewPager() {

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        MyOrdersFragment2 f1 = new MyOrdersFragment2(ALL_ORDER);
        pagerAdapter.addFragment(f1);

        MyOrdersFragment2 f2 = new MyOrdersFragment2(WAIT_SIGN);
        pagerAdapter.addFragment(f2);

        MyOrdersFragment2 f3 = new MyOrdersFragment2(WAIT_BUYER);
        pagerAdapter.addFragment(f3);

        MyOrdersFragment2 f4 = new MyOrdersFragment2(WAIT_SHOP_INSTALL);
        pagerAdapter.addFragment(f4);

        MyOrdersFragment2 f5 = new MyOrdersFragment2(INSTALLED);
        pagerAdapter.addFragment(f5);

        MyOrdersFragment2 f6 = new MyOrdersFragment2(WAIT_EVALUATE);
        pagerAdapter.addFragment(f6);
        MyOrdersFragment2 f7 = new MyOrdersFragment2(DEAL_FAIL);
        pagerAdapter.addFragment(f7);

        MyOrdersFragment2 f8 = new MyOrdersFragment2(DEAL_SUCCESS);
        pagerAdapter.addFragment(f8);

        my_orders_viewpager.setAdapter(pagerAdapter);

        my_order_tab.setViewPager(my_orders_viewpager);
    }

    public static final String ALL_ORDER = "全部订单";
    public static final String WAIT_SIGN = "待签收";
    public static final String WAIT_BUYER = "待付款";

    public static final String WAIT_SHOP_INSTALL = "待安装";
    public static final String INSTALLED = "已安装";
    public static final String WAIT_EVALUATE = "待评价";

    public static final String DEAL_SUCCESS = "交易成功";
    public static final String DEAL_FAIL = "交易失败";

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
