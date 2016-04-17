package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;

/**
 * FirstPageActivity: 首页界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class MyActivity extends BaseActivity {


    private Context mContext;
    /**
     * 未登录的时候显示的东西
     */
    private Button personal_login_button;//登陆注册按钮
    private RelativeLayout  layout_not_logined;//还没登陆
    private RelativeLayout  layout_logined;//已经登陆
    /**
     * 登陆完了之后显示的
     */

    /**
     * 长条选项
     */
    private LinearLayout my_layout_partner_i_recommended;//我推荐的合伙人
    private LinearLayout my_layout_mine_wallet;         //我的钱包
    private LinearLayout my_layout_mine_order;          //我的订单
    private LinearLayout my_layout_mine_privilege;      //我的特权
    private LinearLayout my_layout_mine_publish;        //我的发布
    private LinearLayout my_layout_mine_member;         //我的会员
    private LinearLayout my_layout_i_want_share;        //我要分享
    private LinearLayout my_layout_mine_setting;        //设置


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my);

        findViews();

        mContext = this;

        String from = getIntent().getStringExtra("from");

        if (from != null)
        {
            //就是说明从登陆过来的
            layout_not_logined.setVisibility(View.GONE);//还没登陆
            layout_logined.setVisibility(View.VISIBLE);//已经登陆
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);

        toastMgr.builder.display("logined?  = " + loinged, 1);

        if (loinged.equals(Configs.LOGINED))
        {

            //就是说明从登陆过来的
            layout_not_logined.setVisibility(View.GONE);//还没登陆
            layout_logined.setVisibility(View.VISIBLE);//已经登陆
        }
        else if (loinged.equals(Configs.NOTLOGINED))
        {
            //就是说明没登陆
            layout_not_logined.setVisibility(View.VISIBLE);//还没登陆
            layout_logined.setVisibility(View.GONE);//已经登陆
        }
    }

    private void findViews() {

        personal_login_button = (Button) findViewById(R.id.personal_login_button);//登陆注册按钮

        my_layout_partner_i_recommended = (LinearLayout) findViewById(R.id.my_layout_partner_i_recommended);//我推荐的合伙人

        my_layout_mine_wallet= (LinearLayout) findViewById(R.id.my_layout_mine_wallet);         //我的钱包

        my_layout_mine_order= (LinearLayout) findViewById(R.id.my_layout_mine_order);          //我的订单

        my_layout_mine_privilege= (LinearLayout) findViewById(R.id.my_layout_mine_privilege);      //我的特权

        my_layout_mine_publish= (LinearLayout) findViewById(R.id.my_layout_mine_publish);        //我的发布

        my_layout_mine_member= (LinearLayout) findViewById(R.id.my_layout_mine_member);         //我的会员

        my_layout_i_want_share= (LinearLayout) findViewById(R.id.my_layout_i_want_share);        //我要分享

        my_layout_mine_setting= (LinearLayout) findViewById(R.id.my_layout_mine_setting);        //设置

        layout_not_logined = (RelativeLayout) findViewById(R.id.layout_not_logined);;//还没登陆
        layout_logined = (RelativeLayout) findViewById(R.id.layout_logined);//已经登陆

        /**
         * 设置监听器
         */
        //已经登陆  点击进入详情
        layout_logined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, MyPersonalInfoActivity.class);
                startActivity(intent);
            }
        });
        //登陆注册
        personal_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent();
//                intent.setClass(MyActivity.this, RegisterDetailChooseCarInfoActivity.class);
//                intent.setClass(MyActivity.this, RegisterDetailsActivity.class);
                intent.setClass(MyActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //我推荐的合伙人
        my_layout_partner_i_recommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //intent.setClass(MyActivity.this, MyRecommendedPartnerActivity.class);
                intent.setClass(MyActivity.this, RegisterDetailsActivity.class);
                startActivity(intent);
            }
        });
        //我的钱包
        my_layout_mine_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, MyWwalletActivity.class);
                startActivity(intent);
            }
        });
        //我的订单
        my_layout_mine_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, MyOrdersActivity.class);
                startActivity(intent);
            }
        });
        //我的特权
        my_layout_mine_privilege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, MyPrivilegeActivity.class);
                startActivity(intent);
            }
        });
        //我的发布
        my_layout_mine_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("not finish", 0);
            }
        });
        //我的会员
        my_layout_mine_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("删除 不需要做", 0);
            }
        });
        //我要分享
        my_layout_i_want_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, MyQRCodeActivity.class);
                startActivity(intent);
            }
        });
        //我的设置
        my_layout_mine_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
