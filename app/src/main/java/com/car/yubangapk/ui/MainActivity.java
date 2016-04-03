package com.car.yubangapk.ui;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.car.yubangapk.app.AppManager;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;

/**
 * MainActivity: index界面 显示导航 添加到index里面来   即主界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class MainActivity extends TabActivity {

    private static final String TAG = "MainActivity";
    private Context mContext;

    private TabHost mTabHost;
    private ImageView image_first_page;//首页
    private ImageView main_activity_footer__shoppingmall;//购物车
    private ImageView image_discover;//发现
    private ImageView image_me; //我的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //日志
        L.i(TAG, "oncreate");

        mContext = this;
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);


        this.initTab();
        // 初始化控件
        this.findViews();

        // 初始化监听
        this.setListener();
    }






    /**
     * 初始化底部导航的Tab
     */
    private void initTab() {
        mTabHost = getTabHost();
        // 首页
        mTabHost.addTab(mTabHost.newTabSpec("FirstPage")
                .setIndicator("FirstPage")
                .setContent(new Intent(this, FirstPageActivity.class)));
        //商城
        mTabHost.addTab(mTabHost.newTabSpec("ShoppingMall").setIndicator("ShoppingMall")
                .setContent(new Intent(this, ShoppingMallActivity.class)));
        //发现
        mTabHost.addTab(mTabHost.newTabSpec("Discover").setIndicator("Discover")
                .setContent(new Intent(this, DiscoverActivity.class)));
        // 我的
        mTabHost.addTab(mTabHost.newTabSpec("My").setIndicator("My")
                .setContent(new Intent(this, MyActivity.class)));
    }

    /**
     * 初始化控件
     */
    public void findViews()
    {
        //首页
        image_first_page = (ImageView) findViewById(R.id.image_firstpage_foot);
        //商城
        main_activity_footer__shoppingmall = (ImageView) findViewById(R.id.main_activity_footer__shoppingmall);
        //发现
        image_discover = (ImageView) findViewById(R.id.image_discover_foot);
        //我的
        image_me = (ImageView) findViewById(R.id.image_me_foot);
    }

    /**
     * 初始化监听
     */
    public void setListener()
    {
        //首页
        image_first_page.setOnClickListener(new clickListener_first_page());
        //商城
        main_activity_footer__shoppingmall.setOnClickListener(new clickListener_shoppingcar());
        //发现
        image_discover.setOnClickListener(new clickListener_discover());
        //我的
        image_me.setOnClickListener(new clickListener_me());
    }

    /**
     * 底部导航按下时的图片的切换
     * 首页
     */
    private class clickListener_first_page implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            setDisplayFirstPage();

//            mTabHost.setCurrentTabByTag("FirstPage");
//            image_first_page.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_all_dark_press));
//            main_activity_footer__shoppingmall.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_shoppingcar_bnormal));
//            image_discover.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_discover_normal));
//            image_me.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_my_normal));
        }

    }

    /**
     * 底部导航按下时的图片的切换
     * 购物车
     */
    private class clickListener_shoppingcar implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            setDisplayShoppingPage();
//            mTabHost.setCurrentTabByTag("ShoppingMall");
//            image_first_page.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_all_dark_normal));
//            main_activity_footer__shoppingmall.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_shoppingcar_perss));
//            image_discover.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_discover_normal));
//            image_me.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_my_normal));
        }

    }

    /**
     * 底部导航按下时的图片的切换
     * 发现
     */
    private class clickListener_discover implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            setDisplayDiscoverPage();
//            mTabHost.setCurrentTabByTag("Discover");
//            image_first_page.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_all_dark_normal));
//            main_activity_footer__shoppingmall.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_shoppingcar_bnormal));
//            image_discover.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_discover_press));
//            image_me.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_my_normal));
        }
    }
    /**
     * 底部导航按下时的图片的切换
     * 我的
     */
    private class clickListener_me implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            setDisplayMyPage();
//            mTabHost.setCurrentTabByTag("My");
//            image_first_page.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_all_dark_normal));
//            main_activity_footer__shoppingmall.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_shoppingcar_bnormal));
//            image_discover.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_discover_normal));
//            image_me.setImageDrawable(getResources().getDrawable(
//                    R.mipmap.tabhost_my_press));
        }

    }


    private void setDisplayFirstPage()
    {
        mTabHost.setCurrentTabByTag("FirstPage");
        image_first_page.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_all_dark_press));
        main_activity_footer__shoppingmall.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_shoppingcar_bnormal));
        image_discover.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_discover_normal));
        image_me.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_my_normal));
    }

    private void setDisplayShoppingPage()
    {
        mTabHost.setCurrentTabByTag("ShoppingMall");
        image_first_page.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_all_dark_normal));
        main_activity_footer__shoppingmall.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_shoppingcar_perss));
        image_discover.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_discover_normal));
        image_me.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_my_normal));
    }

    private void setDisplayDiscoverPage()
    {
        mTabHost.setCurrentTabByTag("Discover");
        image_first_page.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_all_dark_normal));
        main_activity_footer__shoppingmall.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_shoppingcar_bnormal));
        image_discover.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_discover_press));
        image_me.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_my_normal));
    }

    private void setDisplayMyPage()
    {
        mTabHost.setCurrentTabByTag("My");
        image_first_page.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_all_dark_normal));
        main_activity_footer__shoppingmall.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_shoppingcar_bnormal));
        image_discover.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_discover_normal));
        image_me.setImageDrawable(getResources().getDrawable(
                R.mipmap.tabhost_my_press));
    }


    /**
     * MainActivity 设置了SingleTask   当从其他页面跳过来的时候 它task栈上面所有activity出栈
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        toastMgr.builder.display("onNewIntent called ", 0);
        setIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return;
        /**拿到从其他activity传来的参数, 判断应该显示哪个页面
         * 首页
         * 商城
         * 发现
         * 个人中心
         * */
        String activityToShow = (String) bundle.get("otherActivity");
        //正常启动
        if (activityToShow == null)
        {

        }
        //购物车没有商品, 跳转到首页
        else if (activityToShow.equals("login"))
        {
            toastMgr.builder.display("login 首页", 0);
            setDisplayMyPage();
            //设置为已经登陆
            //SPUtils.putUserInfo(mContext, "LoginOrNot", Configs.LOGINED);

        }
//        else if (activityToShow.equals("LOGIN_SUCCESS"))
//        {
//            //这里不能判断 就正常启动行了
//        }
//        else if (activityToShow.equals("LOGINOUT_SUCCESS"))//退出了
//        {
//
//        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //日志
        L.i(TAG, "onDestroy");
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }




}
