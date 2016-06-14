package com.car.yubangapk.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andy.android.yubang.R;
import com.car.yubangapk.adapter.GuidePageVIewPagerAdapter;
import com.car.yubangapk.utils.APPUtils;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;

public class GuideActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{


    Context         mContext;
    Button          main_button;
    ViewPager       guide_activity_viewpager;
    //定义一个ArrayList来存放View
    private         ArrayList<View> views;
    GuidePageVIewPagerAdapter vpAdapter;

    //引导图片资源
    private static final int[] pics = {R.mipmap.guide_page_03,R.mipmap.guide_page_02,R.mipmap.guide_page_01};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        mContext = this;

        findViews();
        initData();

    }

    /**
     * 绑定控件
     */
    private void findViews() {
        guide_activity_viewpager = (ViewPager) findViewById(R.id.guide_activity_viewpager);
        main_button = (Button) findViewById(R.id.main_button);

        views = new ArrayList<View>();
        main_button.setOnClickListener(this);
    }


    /**
     * 初始化数据
     */
    private void initData(){
        //定义一个布局并设置参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //初始化引导图片列表
        for(int i=0; i<pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            //防止图片不能填满屏幕
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            views.add(iv);
        }
        vpAdapter = new GuidePageVIewPagerAdapter(views);
        //设置数据
        guide_activity_viewpager.setAdapter(vpAdapter);
        //设置监听
        guide_activity_viewpager.setOnPageChangeListener(this);

    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.main_button:
                toMainPage();
                break;
        }
    }

    /**
     * 去主界面
     */
    private void toMainPage() {
        Intent intent = new Intent();
        intent.setClass(mContext, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * 禁用返回按键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //toastMgr.builder.display(position + "", 1);
        if (position == pics.length - 1)
        {
            //这里应该
            SPUtils.put(mContext, SplashActivity.KEY_GUIDE_ACTIVITY, false);
            String version = APPUtils.getVersion(mContext);
            SPUtils.put(mContext, SplashActivity.KEY_APP_VERSION, version);
            main_button.setVisibility(View.VISIBLE);
        }
        else
        {
            main_button.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
