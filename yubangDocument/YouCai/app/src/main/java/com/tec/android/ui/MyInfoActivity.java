package com.tec.android.ui;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.sales.vo.PostLoginResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.R;
import com.tec.android.configs.Configs;
import com.tec.android.network.GetAccountInfoHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.SPUtils;
import com.tec.android.view.CustomProgressDialog;

/**
 * 我的资料界面  姓名 id 还有电话
 *
 * 点击电话, 去设置手机号码, 接受短信验证码
 */
public class MyInfoActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;
    private TextView save;//保存
    private TextView my_info_modify_mobilephone_editview;


    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_info);

        mContext = this;

        //进来首先是获取个人信息

        getAccountInfo();

        findViews();
    }

    private void getAccountInfo() {
        //1. 先去网络请求, 先拿到goodslist的html String
        GetAccountInfoHttp getAccountInfoHttp = new GetAccountInfoHttp(mContext);
        //登陆的信息
        String postLoginRespJson = SPUtils.getUserInfo(mContext, Configs.LOGIN_INFO, null);
        PostLoginResp postLoginResp = (PostLoginResp) SalesMsgUtils.fromJson(postLoginRespJson, MSG_TYPES.MSG_POST_LOGIN, false);

        //2. 把String 放进webview .loadData()
        //3. 加载html(注意加载的时候 css JS这些文件怎么办)
        //4. webview.loadurl 的时候 要放在handler里面  因为LoadGoodsListReq是异步的   所以要等待
        getAccountInfoHttp.sendAndGetAccountInfoJson(new GetAccountInfoHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {

            }
        }, new GetAccountInfoHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {

            }
        }, postLoginResp, "11");

    }




    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    /**
     * 初始化控件
     */
    private void findViews() {
        save = (TextView) findViewById(R.id.save);
        my_info_modify_mobilephone_editview = (TextView) findViewById(R.id.my_info_modify_mobilephone_editview);


        //监听器
        save.setOnClickListener(this);
        my_info_modify_mobilephone_editview.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //保存
            case R.id.save:
                break;
            //点击绑定手机号码
            case R.id.my_info_modify_mobilephone_editview:
                /**
                 * 这里要判断用户是否已经绑定了手机号码
                 * 1. 没有绑定手机号码  点击去绑定手机号码
                 * 2. 已经绑定手机号码  点击去更换手机号码
                 */
                //加多一个判断, 是否已经绑定了手机

                //既然都来到了这个界面 肯定是已经登陆了
                String userInfoJson = SPUtils.getUserInfo(mContext, Configs.LOGIN_INFO,null);
                PostLoginResp postLoginResp = (PostLoginResp) SalesMsgUtils.fromJson(userInfoJson, MSG_TYPES.MSG_POST_LOGIN, false);
                if (postLoginResp.getNeedphone().equals("1"))
                {
                    //需要绑定手机
                    //启动绑定手机号码页面  去绑定手机号码
                    Intent intent = new Intent();
                    intent.setClass(mContext, MobilePhoneVerifyActivity.class);
                    intent.putExtra("action", "bindPhone");
                    intent.putExtra("from", "MyInfoActivity");
                    startActivity(intent);
                }
                else
                {
                    //已经绑定了手机, 那么点击这个  就是想去解绑, 然后重新绑定手机
                    Intent intent = new Intent();
                    intent.setClass(mContext, InputMobileVerificationCodeActivity.class);
                    //这里还要拿到当前手机号码  然后传递到下一个界面
                    intent.putExtra("from", "changePhone");
                    startActivity(intent);
                }


                break;
            default:
                break;
        }
    }
}
