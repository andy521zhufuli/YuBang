package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2MyUserInfoBean;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqGetUserInfo;
import com.car.yubangapk.ui.myordersfragment.MyOrdersActivity;
import com.car.yubangapk.ui.myrecommendpartner.MyRecommendedPartnerActivity;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.car.yubangapk.view.CustomProgressDialog;

import cn.trinea.android.common.service.impl.RemoveTypeBitmapLarge;
import de.hdodenhof.circleimageview.CircleImageView;

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
    private RelativeLayout layout_not_logined;//还没登陆
    private RelativeLayout layout_logined;//已经登陆



    private CircleImageView user_icon;//
    private TextView        tv_user_name;//
    private TextView        tv_car_type;//
    private TextView        tv_phone_num;//
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


    private CustomProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my);

        findViews();

        mContext = this;

        String from = getIntent().getStringExtra("from");

        mProgress = new CustomProgressDialog(mContext);

        if (from != null) {
            //就是说明从登陆过来的
            layout_not_logined.setVisibility(View.GONE);//还没登陆
            layout_logined.setVisibility(View.VISIBLE);//已经登陆
            //就去后台拿数据
        }
        String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);
        if (loinged.equals(Configs.LOGINED))
        {
            layout_not_logined.setVisibility(View.VISIBLE);//还没登陆
            layout_logined.setVisibility(View.GONE);//已经登陆

        }
        else
        {

        }

    }

    private void getUserInfo() {
        mProgress = mProgress.show(mContext, "加载中...", false, null);
        String userid = Configs.getLoginedInfo(mContext).getUserid();
        HttpReqGetUserInfo getUserInfo = new HttpReqGetUserInfo();
        getUserInfo.setListener(new GetInfo());
        getUserInfo.getUserInfo(userid);
    }


    private int GET_USER_INFO_STATUS = 0;//0 代表成功, 100代表未登录, 200 代表 服务器错误, 300 其他错误

    class GetInfo implements HttpReqCallback
    {
        @Override
        public void onFail(int errorCode, String message) {
            mProgress.dismiss();
            if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
            {
                layout_not_logined.setVisibility(View.VISIBLE);//还没登陆
                layout_logined.setVisibility(View.GONE);//已经登陆
                GET_USER_INFO_STATUS = 100;
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_SERVER_ERROR)
            {
                toastMgr.builder.display(message, 1);
                GET_USER_INFO_STATUS = 200;
            }
            else
            {
                toastMgr.builder.display(message, 1);
                GET_USER_INFO_STATUS = 300;
            }
        }

        @Override
        public void onSuccess(Object object) {
            GET_USER_INFO_STATUS = 0;
            mProgress.dismiss();
            Json2MyUserInfoBean userInfoBean = (Json2MyUserInfoBean) object;
            mUserInfo = userInfoBean;
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + userInfoBean.getPathCode() + "&fileReq.fileName=" + userInfoBean.getPhotoName();
            ImageLoaderTools.getInstance(mContext).displayImage(url, user_icon);
            tv_user_name.setText(userInfoBean.getUserName());
            tv_car_type.setText(userInfoBean.getCar());
            tv_phone_num.setText(userInfoBean.getPhoneNum());

        }
    }

    private Json2MyUserInfoBean mUserInfo;

    @Override
    protected void onResume() {
        super.onResume();
        String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);


        if (loinged.equals(Configs.LOGINED))
        {
            //就是说明已经登录了
            layout_not_logined.setVisibility(View.GONE);//还没登陆
            layout_logined.setVisibility(View.VISIBLE);//已经登陆
            getUserInfo();
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

        my_layout_mine_order= (LinearLayout) findViewById(R.id.my_layout_order_coupon);          //我的订单

        my_layout_mine_privilege= (LinearLayout) findViewById(R.id.my_layout_mine_privilege);      //我的特权

        my_layout_mine_publish= (LinearLayout) findViewById(R.id.my_layout_mine_publish);        //我的发布

        my_layout_mine_member= (LinearLayout) findViewById(R.id.my_layout_mine_member);         //我的会员

        my_layout_i_want_share= (LinearLayout) findViewById(R.id.my_layout_i_want_share);        //我要分享

        my_layout_mine_setting= (LinearLayout) findViewById(R.id.my_layout_mine_setting);        //设置

        layout_not_logined = (RelativeLayout) findViewById(R.id.layout_not_logined);;//还没登陆
        layout_logined = (RelativeLayout) findViewById(R.id.layout_logined);//已经登陆


        user_icon = (CircleImageView) findViewById(R.id.user_icon);//头像;//
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);//用户名;//
        tv_car_type = (TextView) findViewById(R.id.tv_car_type);//车型;//
        tv_phone_num = (TextView) findViewById(R.id.tv_phone_num);//电话;//


        /**
         * 设置监听器
         */
        //已经登陆  点击进入详情
        layout_logined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUserInfo == null || mUserInfo.isHasData() == false)
                {
                    toastMgr.builder.display("信息有误,或者网络不可用!", 1);
                    return;
                }
                if (GET_USER_INFO_STATUS != 0)
                {
                    toastMgr.builder.display("获取用户信息出错!", 1);
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(MyActivity.this, MyPersonalInfoActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("userInfo", mUserInfo);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_USER_INFO);
            }
        });
        //登陆注册
        personal_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent();
                intent.setClass(MyActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //我推荐的合伙人
        my_layout_partner_i_recommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);
                if (!loinged.equals(Configs.LOGINED))
                {
                    toastMgr.builder.display("您还没登陆,请先去登陆",1);
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, MyRecommendedPartnerActivity.class);
//                intent.setClass(MyActivity.this, RegisterDetailsActivity.class);
                startActivity(intent);
            }
        });
        //我的钱包
        my_layout_mine_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);
                if (!loinged.equals(Configs.LOGINED))
                {
                    toastMgr.builder.display("您还没登陆,请先去登陆",1);
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, MyWwalletActivity.class);
                startActivity(intent);
            }
        });
        //我的订单
        my_layout_mine_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);
                if (!loinged.equals(Configs.LOGINED))
                {
                    toastMgr.builder.display("您还没登陆,请先去登陆",1);
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, MyOrdersActivity.class);
                startActivity(intent);
            }
        });
        //我的特权
        my_layout_mine_privilege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);
                if (!loinged.equals(Configs.LOGINED))
                {
                    toastMgr.builder.display("您还没登陆,请先去登陆",1);
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, ListViewRefreshAcivity.class);
                startActivity(intent);
            }
        });
        //我的发布
        my_layout_mine_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);
                if (!loinged.equals(Configs.LOGINED))
                {
                    toastMgr.builder.display("您还没登陆,请先去登陆",1);
                    return;
                }
                toastMgr.builder.display("not finish", 0);
            }
        });
        //我的会员
        my_layout_mine_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);
                if (!loinged.equals(Configs.LOGINED))
                {
                    toastMgr.builder.display("您还没登陆,请先去登陆",1);
                    return;
                }
                toastMgr.builder.display("删除 不需要做", 0);
            }
        });
        //我要分享
        my_layout_i_want_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);
                if (!loinged.equals(Configs.LOGINED))
                {
                    toastMgr.builder.display("您还没登陆,请先去登陆",1);
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, MyQRCodeActivity.class);
                startActivity(intent);
            }
        });
        //我的设置
        my_layout_mine_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);
                if (!loinged.equals(Configs.LOGINED))
                {
                    toastMgr.builder.display("您还没登陆,请先去登陆",1);
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            boolean altered = bundle.getBoolean("alter");
            if (altered == true)
            {
                getUserInfo();
            }
            else {

            }
        }
    }

    private int REQUEST_USER_INFO = 0X11;

}
