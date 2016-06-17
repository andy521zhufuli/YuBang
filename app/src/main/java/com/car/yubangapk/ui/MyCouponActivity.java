package com.car.yubangapk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.andy.android.yubang.R;
import com.car.yubangapk.ui.myrecommendpartner.MyRecommendedPartnerFirstFragmentActivityFragment;
import com.car.yubangapk.view.ScrollviewNavigationTabCoupon.coupon.CouponScrollTabView;
import com.car.yubangapk.view.ScrollviewNavigationTabCoupon.coupon.CouponScrollTabsAdapter;
import com.car.yubangapk.view.ScrollviewNavigationTabCoupon.coupon.CouponTabAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * MyCouponActivity: 我的优惠券
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-27
 */
public class MyCouponActivity extends FragmentActivity implements CouponScrollTabView.OnCouponNavItemClickListener {

    private Context         mContext;
    private ImageView       img_back;//返回

    private Fragment        firstFragment;
    private Fragment        secondFragment;
    private Fragment        threeFragment;
    private Fragment        fourFragment;

    CouponScrollTabView coupon_page_nav_tabs;
    private CouponTabAdapter tabsAdapter;//顶部导航的适配器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_coupon);

        mContext = this;
        findViews();

        mTabNameList = new ArrayList<>();
        mTabNameList.add("全部");
        mTabNameList.add("店铺优惠");
        mTabNameList.add("店铺红包");
        mTabNameList.add("已失效");
        initTabs(mTabNameList);
    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);//返回
        coupon_page_nav_tabs = (CouponScrollTabView) findViewById(R.id.my_coupon_nav_bar);//导航

        /**
         * 监听器
         */
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private List<String> mTabNameList;
    /**
     * s设置顶部tab的名字
     * @param mTabNameList
     */
    void initTabs(List<String> mTabNameList)
    {
        tabsAdapter = new CouponScrollTabsAdapter(this);
        for (String bean : mTabNameList)
        {
            tabsAdapter.add(bean);
        }
        coupon_page_nav_tabs.setAdapter(tabsAdapter);
    }


    /**实现切换不同的Fragment
     * @param i 点击的第几个按钮
     */
    private void select(int i)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (i)
        {
            case 0:
                if (firstFragment == null)
                {
                    firstFragment = new MyCouponFragmentFirst();
                    transaction.add(R.id.my_coupon_fragment_layout, firstFragment);
                } else
                {
                    transaction.show(firstFragment);
                }
                break;
            case 1:
                if (secondFragment == null)
                {
                    secondFragment = new MyCouponFragmentFirst();
                    transaction.add(R.id.my_coupon_fragment_layout, secondFragment);
                } else
                {
                    transaction.show(secondFragment);
                }
                break;
            case 2:
                if (threeFragment == null)
                {
                    threeFragment = new MyCouponFragmentFirst();
                    transaction.add(R.id.my_coupon_fragment_layout, threeFragment);
                } else
                {
                    transaction.show(threeFragment);
                }
                break;
            case 3:
                if (fourFragment == null)
                {
                    fourFragment = new MyCouponFragmentFirst();
                    transaction.add(R.id.my_coupon_fragment_layout, fourFragment);
                } else
                {
                    transaction.show(fourFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 用于每一显示不同的Fragment时候隐藏之前的所有可能显示的Fragment
     * @param transaction
     *          事物
     */
    private void hideFragment(FragmentTransaction transaction)
    {
        if (firstFragment != null)
        {
            transaction.hide(firstFragment);
        }
        if (secondFragment != null)
        {
            transaction.hide(secondFragment);
        }
        if (threeFragment != null)
        {
            transaction.hide(threeFragment);
        }
        if (fourFragment != null)
        {
            transaction.hide(fourFragment);
        }
    }

    /**
     *
     * @param tabAdapter
     * @param pos
     */
    @Override
    public void onTabItemClick(CouponTabAdapter tabAdapter, int pos) {
        select(pos);
    }
}
