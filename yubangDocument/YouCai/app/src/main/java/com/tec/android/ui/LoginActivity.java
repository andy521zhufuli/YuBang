package com.tec.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sales.vo.PostLoginResp;
import com.sales.vo.PreLoginResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.R;
import com.tec.android.configs.Configs;
import com.tec.android.network.PostLoginReqHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.SPUtils;
import com.tec.android.utils.bean.QQLoginBean;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.AlertDialog;
import com.tec.android.view.CustomProgressDialog;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.security.Policy;


/**
 * LoginActivity: 登录界面  微信 qq登录
 *
 * @author andyzhu
 * @version 1.0
 * @created 2015-07-31
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{


    private Context mContext;



    private final int POST_LOGIN_SUCCESS = 0X01;
    private final int GET_APPID_SUCCESS = 0X02;
    private final int GET_USER_INFO_SUCCESS = 0X03;
    private final int GET_QQ_CODE_SUCCESS = 0X04;

    private final int GET_QQ_CODE_CANCLE = 0x15;
    private final int POST_LOGIN_FAIL = 0X11;
    private final int GET_APPID_FAIL = 0X12;
    private final int GET_USER_INFO_FAIL = 0X13;
    private final int GET_QQ_CODE_FAIL = 0X14;

    private static final String TAG = "LoginActivity";
    private TextView login_by_qq;
    private TextView login_by_weixin;

    public static Tencent mTencent;

    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        /**
         * 登陆流程:
         * 1.findViews(); 初始化登陆按钮
         * 2.getAPPID(); 通过PreLoginReq  去后台拿APPID
         *      2.1 postLoginReqHttp.sendAndPreLoginReqJson 拿到服务器但会数据  拿到json 穿到getAppidHandler里面
         *      2.2 getAppidHandler处理  初始化mTencent
         * 3.用户点击qq登陆
         *      3.1 QQ登陆 loginByQQ
         *      3.2 qq的回调函数成功调用 得到返回的json  注意  是jsonobject   要tostring一下
         *      3.3 将json转成qq对象   调用PostLoginReq  拿到用户信息
         *      3.4 成功拿到后台的用户信息 调用handler  更新UI
         * 4.用户点击微信登陆
         *      4.1 QQ登陆 loginByQQ
         *      4.2 qq的回调函数成功调用 得到返回的json  注意  是jsonobject   要tostring一下
         *      4.3 将json转成qq对象   调用PostLoginReq  拿到用户信息
         *      4.4 成功拿到后台的用户信息 调用handler  更新UI
         */
        mContext = this;

        mProgressDialog = new CustomProgressDialog(mContext);
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        //mTencent = Tencent.createInstance(Configs.QQ_APP_ID,this.getApplicationContext());
        findViews();

        //预登陆  拿到appid
        getAPPID();
    }

    /**
     * 初始化控件
     */
    private void findViews() {
        login_by_qq = (TextView) findViewById(R.id.login_by_qq);
        login_by_weixin = (TextView) findViewById(R.id.login_by_weixin);

        login_by_weixin.setOnClickListener(this);
        login_by_qq.setOnClickListener(this);
    }

    /**
     * preLogin  预登陆 向后台拿appid
     */
    public void getAPPID()
    {
        PostLoginReqHttp postLoginReqHttp= new PostLoginReqHttp(mContext);
        postLoginReqHttp.sendAndPreLoginReqJson(new PostLoginReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                Message msg = new Message();
                msg.what = GET_APPID_SUCCESS;
                msg.obj = result;
                loginActivityHelperHandler.sendMessage(msg);
            }
        }, new PostLoginReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                Message msg = new Message();
                msg.what = GET_APPID_FAIL;
                msg.obj = resultMsg;
                loginActivityHelperHandler.sendMessage(msg);
            }
        });
    }

    /**
     * Onclick  监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //通过QQ登录
            case R.id.login_by_qq:
                loginByQQ();
                break;
            //通过微信登录
            case R.id.login_by_weixin:
                loginByWeixin();
                break;
            default:
                break;
        }
    }

    /**
     * QQ登陆的按钮
     * 按照文档要求, 应该是先去服务器拿到APPID
     */
    private void loginByQQ()
    {

        //qq的回调函数
        IUiListener listener = new BaseUiListener() {
            @Override
            protected void doComplete(JSONObject values) {
                L.i("mTencent login-----> json = " + values.toString());
                mProgressDialog = mProgressDialog.show(mContext, "", false, null);
                Message msg = new Message();
                msg.what = GET_QQ_CODE_SUCCESS;
                msg.obj = values;
                loginActivityHelperHandler.sendMessage(msg);
            }

            @Override
            public void onCancel() {
                super.onCancel();
                mProgressDialog = mProgressDialog.show(mContext, "", false, null);
                Message msg = new Message();
                msg.what = GET_QQ_CODE_CANCLE;
                loginActivityHelperHandler.sendMessage(msg);
            }

            @Override
            public void onError(UiError e) {
                super.onError(e);
            }
        };
        mTencent.login(this, "all", listener);
        //mTencent.loginServerSide(this, "get_user_info,all", listener);
    }

    /**
     * 微信登陆按钮
     * 还没做
     */
    private void loginByWeixin()
    {
        toastMgr.builder.display("微信登陆", 0);
    }

    /**
     * 腾讯的登录回调函数
     */
    class BaseUiListener implements IUiListener
    {

        protected void doComplete(JSONObject values) {
            String string = values.toString();
            L.i("QQ login result = " + string);
            L.i("doComplete");
        }

        @Override
        public void onComplete(Object o) {

            JSONObject value = (JSONObject)o;
            doComplete(value);
            L.i("onComplete value = " + value.toString());
        }

        @Override
        public void onError(UiError e) {
            L.i("onerror");
        }
        @Override
        public void onCancel() {

            L.i("onCancel");
        }
    }

    /**
     * 处理整个界面的handler
     *
     */
    private Handler loginActivityHelperHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                //用户点击qq登录按钮  并且调用qq界面 成功返回
                case GET_QQ_CODE_SUCCESS:

                    //这里的obj是qq传回来的json  是JsonObject格式  是一个对象 不是string
                    String json = (String) msg.obj.toString();
                    QQLoginBean qqLoginBean = new Gson().fromJson(json, new QQLoginBean().getClass());
                    PostLoginReqHttp postLoginReqHttp = new PostLoginReqHttp(mContext);
                    postLoginReqHttp.sendAndPostLoginReqJson(
                            new PostLoginReqHttp.DoingSuccessCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    Message message = new Message();
                                    message.what = POST_LOGIN_SUCCESS;
                                    message.obj = result;
                                    loginActivityHelperHandler.sendMessage(message);

                                }
                            },
                            new PostLoginReqHttp.DoingFailedCallback() {
                                @Override
                                public void onFail(String resultMsg) {
                                    Message message1 = new Message();
                                    message1.what = POST_LOGIN_FAIL;
                                    message1.obj = resultMsg;
                                    loginActivityHelperHandler.sendMessage(message1);
                                }
                            },
                            qqLoginBean
                    );

                    break;
                //登陆成功
                case POST_LOGIN_SUCCESS:
                    //这个对象是服务器返回的json对象 是个字符串来的
                    String postLoginJson = (String) msg.obj;
                    PostLoginResp postLoginResp = (PostLoginResp) SalesMsgUtils.fromJson(postLoginJson, MSG_TYPES.MSG_POST_LOGIN, false);
                    mProgressDialog.dismiss();
                    postLoginSuccess(postLoginResp, postLoginJson);



                    break;
                //拿到appid成功
                case GET_APPID_SUCCESS:
                    //
                    PreLoginResp preLoginResp = (PreLoginResp)SalesMsgUtils.fromJson((String)msg.obj, MSG_TYPES.MSG_PRE_LOGIN, false);

                    // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
                    if (preLoginResp.getQqAppID() == null || preLoginResp.getQqAppID().equals(""))
                    {
                        //拿到的id是空得 就是说明后台有问题
                        //而且都返回这个json了  那一定是后台有问题
                        //提示用户, 系统故障, 请稍后再试
                        toastMgr.builder.display("系统故障, 请稍后再试...", 1);
                        LoginActivity.this.finish();
                    }
                    mTencent = Tencent.createInstance(preLoginResp.getQqAppID(), LoginActivity.this.getApplicationContext());
                    L.i("getAPPIDHandler qq APPID = " + preLoginResp.getQqAppID());
                    toastMgr.builder.display("getAPPIDHandler qq APPID = " + preLoginResp.getQqAppID(), 0);
                    break;
                //拿到用户信息成功
                case GET_USER_INFO_SUCCESS:
                    break;
                //登陆失败
                case POST_LOGIN_FAIL:
                    String error = (String) msg.obj;
                    mProgressDialog.dismiss();
                    toastMgr.builder.display("登陆失败, 请重新登陆", 0);
                    break;
                //拿到appid失败
                case GET_APPID_FAIL:
                    String errorAPPID = (String) msg.obj;
                    //传过来的是null 就是从网上获取的就是null 提示服务器错误
                    if (errorAPPID == null)
                    {
                        toastMgr.builder.display("服务器错误,请稍后再试!," ,0);
                        finish();
                    }
                    else if (errorAPPID != null)
                    {

                    }

                    break;
                //拿到用户信息失败
                case GET_USER_INFO_FAIL:
                    break;
                case GET_QQ_CODE_FAIL:
                    break;
                //用户取消qq登录
                case GET_QQ_CODE_CANCLE:
                    mProgressDialog.dismiss();
                    break;
            }
        }
    };

    /**
     * 成功登陆了
     * 1.成功登陆信息写到sp里面保存
     * 2.判断用户是不是需要绑定手机
     * @param postLoginResp
     */
    private void postLoginSuccess(PostLoginResp postLoginResp, String postLoginJson) {
        //登陆成功标志位
        SPUtils.putUserInfo(mContext, Configs.IS_USER_LOGINED,Configs.LOGINED);
        //当用户成功登陆的时候  会拿到后台发来的信息   这个信息保存在这里
        SPUtils.putUserInfo(mContext, Configs.LOGIN_INFO, postLoginJson);

        //判断用户是不是已经绑定了手机了
        if (postLoginResp.getNeedphone().equals("1"))
        {
            toastMgr.builder.display("您还没绑定手机哦", 0);
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder()
                    .setTitle("提示")
                    .setMsg("您还未绑定手机哦,绑定手机可以获得好友的收益哦,快去绑定手机!")
                    .setCancelable(false)
                    .setNegativeButton("暂不绑定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //用户不绑定手机  那就去往个人中心界面
                            //用户已经绑定了手机了  直接去个人中心
                            toastMgr.builder.display("提示用户登陆成功", 0);
                            //去往个人中心界面
                            Intent intent = new Intent();
                            intent.setClass(mContext, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("otherActivity", "LOGIN_SUCCESS");
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setPositiveButton("立即绑定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //用户同意绑定手机  那就前往绑定手机界面
                            Intent bandingPhoneIntent = new Intent();
                            bandingPhoneIntent.setClass(mContext, MobilePhoneVerifyActivity.class);
                            //就是告诉这个界面  要绑定手机
                            bandingPhoneIntent.putExtra("action", "bindPhone");
                            bandingPhoneIntent.putExtra("from", "LoginActivity");
                            startActivity(bandingPhoneIntent);
                            finish();
                        }
                    })
                    .show();

        }
        else
        {
            //用户已经绑定了手机了  直接去个人中心
            toastMgr.builder.display("提示用户登陆成功", 0);
            //去往个人中心界面
            Intent intent = new Intent();
            intent.setClass(mContext, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("otherActivity", "LOGIN_SUCCESS");
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }



    }


    public  boolean ready(Context context) {
        if (mTencent == null) {
            return false;
        }
        boolean ready = mTencent.isSessionValid()
                && mTencent.getQQToken().getOpenId() != null;
        if (!ready) {
            Toast.makeText(context, "login and get openId first, please!",
                    Toast.LENGTH_SHORT).show();
        }
        return ready;
    }


    /**
     * 拉起的qq界面返回本界面时候回调函数
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.RESULT_LOGIN) {
                mTencent.handleLoginData(data, new BaseUiListener());
                Log.d(TAG, "-->onActivityResult handle logindata");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}




