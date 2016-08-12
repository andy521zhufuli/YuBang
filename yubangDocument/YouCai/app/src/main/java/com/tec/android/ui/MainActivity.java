package com.tec.android.ui;

import com.google.gson.Gson;
import com.tec.android.app.AppManager;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.tec.android.R;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;
import com.tec.android.utils.SPUtils;
import com.tec.android.utils.bean.GoodsInShoppingCarBean;
import com.tec.android.utils.bean.ShoppingcarSPBean;
import com.tec.android.utils.toastMgr;

import org.w3c.dom.Text;

import java.util.List;

/**
 * MainActivity: index界面 显示导航 添加到index里面来   即主界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2015-07-30
 */
public class MainActivity extends TabActivity {

    private static final String TAG = "MainActivity";
    private Context mContext;

    private TabHost mTabHost;
    private ImageView image_first_page;//首页
    private ImageView main_activity_footer__shoppingcar;//购物车
    private ImageView image_me; //我的
    private TextView main_activity_footer_shoppingcar_number;//购物车数量

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


        IntentFilter intentFilter = new IntentFilter("main.count.goods.num");
        mContext.registerReceiver(mainActivityCountShoppingcarGoodsNum, intentFilter);

        //初始化的时候就要显示  购物车里面商品的个数显示 右上角
        countGoodsNumInShoppingcar();
    }


    /**
     * 计算购物车商品数量的代码
     * 广播
     */
    private BroadcastReceiver mainActivityCountShoppingcarGoodsNum = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            countGoodsNumInShoppingcar();
        }
    };



    /**
     * 拿到购物车信息  用户本地购物车是否有商品
     *
     * @return
     */
    public String getShoppingCarInfo() {
        String shoppingcarInfo = (String) SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
        if (shoppingcarInfo == null || shoppingcarInfo.equals("{\"goodsInShoppingCarBeans\":[]}"))
            return null;
        else
            return shoppingcarInfo;
    }

    /**
     * 判断购物车里面一共多少个商品了
     */
    private void countGoodsNumInShoppingcar()
    {
        String shoppingcarInfo = getShoppingCarInfo();
        if (shoppingcarInfo != null) {
            //购物车里面有商品

            int numCount = 0;
            //设置显示
            main_activity_footer_shoppingcar_number.setVisibility(View.VISIBLE);

            String goodsJson = SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
            ShoppingcarSPBean shoppingcarGoods = new Gson().fromJson(goodsJson, ShoppingcarSPBean.class);
            List<GoodsInShoppingCarBean> goodsList = shoppingcarGoods.getGoodsInShoppingCarBeans();
            int length = goodsList.size();
            for (int i = 0; i < length; i++)
            {
                //取得所有商品的总数
                numCount += goodsList.get(i).getNum();
            }
            main_activity_footer_shoppingcar_number.setText(numCount + "");
        }
        else
        {
            //购物车里面没有商品
            main_activity_footer_shoppingcar_number.setVisibility(View.GONE);
        }
    }


    /**
     * 初始化底部导航的Tab
     */
    private void initTab() {
        mTabHost = getTabHost();
        // 发现
        mTabHost.addTab(mTabHost.newTabSpec("FirstPage")
                .setIndicator("FirstPage")
                .setContent(new Intent(this, GoodsListActivity.class)));
        // 拍照
        mTabHost.addTab(mTabHost.newTabSpec("ShoppingCar").setIndicator("ShoppingCar")
                .setContent(new Intent(this, ShoppingCarActivity.class).putExtra("from", "main")));
        // 我的
        mTabHost.addTab(mTabHost.newTabSpec("My").setIndicator("My")
                .setContent(new Intent(this, MyActivity.class)));
    }

    /**
     * 初始化控件
     */
    public void findViews()
    {

        image_first_page = (ImageView) findViewById(R.id.image_firstpage_foot);
        main_activity_footer__shoppingcar = (ImageView) findViewById(R.id.main_activity_footer__shoppingcar);
        image_me = (ImageView) findViewById(R.id.image_me_foot);
        main_activity_footer_shoppingcar_number = (TextView) findViewById(R.id.main_activity_footer_shoppingcar_number);
    }

    /**
     * 初始化监听
     */
    public void setListener()
    {
        image_first_page.setOnClickListener(new clickListener_first_page());
        main_activity_footer__shoppingcar.setOnClickListener(new clickListener_shoppingcar());
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
            // TODO Auto-generated method stub
            mTabHost.setCurrentTabByTag("FirstPage");
            image_first_page.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_all_dark_press));
            main_activity_footer__shoppingcar.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_shoppingcar_bnormal));
            image_me.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_my_normal));
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
            // TODO Auto-generated method stub
            mTabHost.setCurrentTabByTag("ShoppingCar");
            image_first_page.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_all_dark_normal));
            main_activity_footer__shoppingcar.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_shoppingcar_perss));
            image_me.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_my_normal));
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
            // TODO Auto-generated method stub
            mTabHost.setCurrentTabByTag("My");
            image_first_page.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_all_dark_normal));
            main_activity_footer__shoppingcar.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_shoppingcar_bnormal));
            image_me.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_my_press));
        }

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
        /**拿到从其他activity传来的参数, 判断应该显示哪个页面
         * 首页
         * 购物车
         * 个人中心
         * */
        String activityToShow = (String) bundle.get("otherActivity");
        //正常启动
        if (activityToShow == null)
        {

        }
        //购物车没有商品, 跳转到首页
        else if (activityToShow.equals("SHOPPINGCAR_NO_GOODS"))
        {
            mTabHost.setCurrentTabByTag("FirstPage");
            image_first_page.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_all_dark_press));
            main_activity_footer__shoppingcar.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_shoppingcar_bnormal));
            image_me.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_my_normal));
        }
        else if (activityToShow.equals("LOGIN_SUCCESS"))
        {
            //这里不能判断 就正常启动行了
        }
        else if (activityToShow.equals("LOGINOUT_SUCCESS"))//退出了
        {
            //到个人中心界面
            mTabHost.setCurrentTabByTag("My");
            image_first_page.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_all_dark_normal));
            main_activity_footer__shoppingcar.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_shoppingcar_bnormal));
            image_me.setImageDrawable(getResources().getDrawable(
                    R.mipmap.tabhost_my_press));
        }

        //购物车里面的商品显示  多少个
        countGoodsNumInShoppingcar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //日志
        L.i(TAG, "onDestroy");
        // 结束Activity&从堆栈中移除
        unregisterReceiver(mainActivityCountShoppingcarGoodsNum);
        AppManager.getAppManager().finishActivity(this);
    }


}
