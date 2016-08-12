package com.tec.android.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.sales.vo.AppVersionResp;
import com.sales.vo.ExitLoginReq;
import com.sales.vo.ExitLoginResp;
import com.sales.vo.PreLoginResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.configs.Configs;
import com.tec.android.network.AppVersionReqHttp;
import com.tec.android.network.NetWorkHelper;
import com.tec.android.network.PostLoginReqHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.SPUtils;
import com.tec.android.utils.SystemInfoUtils;
import com.tec.android.utils.toastMgr;

import com.tec.android.R;
import com.tec.android.view.AlertDialog;
import com.tec.android.view.CustomProgressDialog;
import com.tencent.tauth.Tencent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * SettingsActivity: 设置界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2015-07-31
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "SettingsActivity";
    private static final int GET_APPID_SUCCESS = 0X01;
    private static final int GET_APPID_FAIL = 0X100;
    private static final int LOGINOUT_SUCCESS = 0X02;
    private static final int LOGINOUT_FAIL = 0X101;
    private static final int CHECK_VERSION_SUCCESS = 0X03;
    private static final int CHECK_VERSION_FAIL = 0X103;
    private static final int DOWNLOAD_SUCCESS = 0X04;

    private Context mContext;

    private RelativeLayout check_new_version_layout;
    private RelativeLayout connect_service_layout;
    private RelativeLayout about_us_layout;
    private ImageView title_back;
    private Button button_quit;//退出登陆
    private CustomProgressDialog mProgressDialog;//进度对话框


    private Tencent mTencent;

    /**
     * 这是测试登陆功能的
     */
    private Button justtest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        mContext = this;

        //初始化控件
        findViews();
    }

    /**
     * 初始化控件
     */
    private void findViews() {
        check_new_version_layout = (RelativeLayout) findViewById(R.id.check_new_version_layout);

        connect_service_layout = (RelativeLayout) findViewById(R.id.connect_service_layout);


        about_us_layout = (RelativeLayout) findViewById(R.id.about_us_layout);

        title_back = (ImageView) findViewById(R.id.title_back);

        button_quit = (Button) findViewById(R.id.button_quit);
        //监听器
        //返回
        title_back.setOnClickListener(this);
        //关于
        about_us_layout.setOnClickListener(this);
        //联系我们
        connect_service_layout.setOnClickListener(this);
        //检查新版本
        check_new_version_layout.setOnClickListener(this);
        //退出登陆
        button_quit.setOnClickListener(this);
        /**
         * 测试登陆的按钮  完事就删除
         */
        justtest = (Button) findViewById(R.id.justtest);
        justtest.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //检查新版本
            case R.id.check_new_version_layout:
                checkAppVersion();
                toastMgr.builder.display(TAG + "已经是最新版本", 0);
                break;
            //联系客服
            case R.id.connect_service_layout:
                //这里去往一个新界面  这个界面就是一个webview

                toastMgr.builder.display(TAG + "联系客服", 0);
                break;
            //关于我们
            case R.id.about_us_layout:
                toastMgr.builder.display(TAG + "关于我们", 0);
                Intent intent = new Intent();
                intent.setClass(mContext, AboutActivity.class);
                startActivity(intent);
                break;
            //返回
            case R.id.title_back:
                toastMgr.builder.display(TAG + "返回", 0);
                finish();
                break;
            //退出登陆
            case R.id.button_quit:
                //TODO 请求后台
                //退出要联网, 要告诉用户等待
                mProgressDialog = new CustomProgressDialog(mContext);
                mProgressDialog = mProgressDialog.show(mContext, "请稍后...", false, null);
                //并且本地标记退出登陆
                preloginOut();
                SPUtils.putUserInfo(mContext, Configs.IS_USER_LOGINED, Configs.NOT_LOGINED);
                break;
            /**
             * 测试登陆的按钮  完事就删除
             */
            case R.id.justtest:
                Intent intent1 = new Intent();
                intent1.setClass(mContext, ReturnOfGoodsActivity.class);
                startActivityForResult(intent1, 111);
//                Intent receiverIntent = new Intent("loginoutsuccess");
//                mContext.sendBroadcast(receiverIntent);
                break;
        }
    }

    /**
     * 检查当前app的版本
     */
    private void checkAppVersion() {
        int currentAppVersion = SystemInfoUtils.getCurrentVersion(mContext);
        AppVersionReqHttp appVersionReqHttp = new AppVersionReqHttp(mContext);
        appVersionReqHttp.sendAndCheckAppVersionReqJson(new AppVersionReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                Message msg = new Message();
                msg.what = CHECK_VERSION_SUCCESS;
                msg.obj = result;
                loginOutActivityHelperHandler.sendMessage(msg);

            }
        }, new AppVersionReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                Message msg = new Message();
                msg.what = CHECK_VERSION_FAIL;
                msg.obj = resultMsg;
                loginOutActivityHelperHandler.sendMessage(msg);
            }
        }, currentAppVersion + "");


    }


    /**
     * 预先退出 先拿到appid函数
     */
    private void preloginOut()
    {
        getAPPID();
    }

    /**
     * preLogin  预登陆 向后台拿appid
     */
    public void getAPPID()
    {

        PostLoginReqHttp postLoginReqHttp= new PostLoginReqHttp(mContext);
        //退出登陆  好像不需要去拿appid  直接后台退出登陆就可以了  然后删除本地sp数据
        postLoginReqHttp.exitLoginReqJson(new PostLoginReqHttp.DoingSuccessCallback() {
              @Override
              public void onSuccess(String result) {

              }
          }, new PostLoginReqHttp.DoingFailedCallback() {
              @Override
              public void onFail(String resultMsg) {

              }
          }, null
        );
        postLoginReqHttp.sendAndPreLoginReqJson(new PostLoginReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                Message msg = new Message();
                msg.what = GET_APPID_SUCCESS;
                msg.obj = result;
                loginOutActivityHelperHandler.sendMessage(msg);
            }
        }, new PostLoginReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                Message msg = new Message();
                msg.what = GET_APPID_FAIL;
                msg.obj = resultMsg;
                loginOutActivityHelperHandler.sendMessage(msg);
            }
        });
    }


    /**
     * 退出登陆的交互
     */
    private Handler loginOutActivityHelperHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                //APPID成功拿到
                case GET_APPID_SUCCESS:
                    PreLoginResp preLoginResp = (PreLoginResp) SalesMsgUtils.fromJson((String) msg.obj, MSG_TYPES.MSG_PRE_LOGIN, false);
                    // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
                    if (preLoginResp.getQqAppID() == null || preLoginResp.getQqAppID().equals(""))
                    {
                        //拿到的id是空得 就是说明后台有问题
                        //而且都返回这个json了  那一定是后台有问题
                        //提示用户, 系统故障, 请稍后再试
                        toastMgr.builder.display("系统故障, 请稍后再试...", 1);
                    }
                    mTencent = Tencent.createInstance(preLoginResp.getQqAppID(), SettingsActivity.this.getApplicationContext());
                    mTencent.logout(mContext);
                    loginOut();
                    L.i("getAPPIDHandler qq APPID = " + preLoginResp.getQqAppID());
                    break;
                //APPid没拿到  提示用户网络有问题
                case GET_APPID_FAIL:

                    break;
                //退出成功
                case LOGINOUT_SUCCESS:
                    String json1 = (String) msg.obj;
                    ExitLoginResp resp = new Gson().fromJson(json1, ExitLoginResp.class);
//                    if (resp.getReturncode() == 1)
//                    {
//                        if (resp.getErrmsg().equals("失败"))
//                        {
//
//                        }
//                        toastMgr.builder.display("服务器错误", 0);
//                        mProgressDialog.dismiss();
//                        break;
//                    }
                    //退出成功, 删除登陆的信息
                    //登陆成功标志位
                    SPUtils.putUserInfo(mContext, Configs.IS_USER_LOGINED, Configs.NOT_LOGINED);
                    //用户退出 删除保存的用户id 以及accesstoken
                    SPUtils.deleteUserInfo(mContext, Configs.LOGIN_INFO);
                    //关闭进度条
                    mProgressDialog.dismiss();

                    //提示用户成功退出  然后就到首页去
                    toastMgr.builder.display("成功退出", 0);
                    Intent intent = new Intent();
                    intent.setClass(mContext, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("otherActivity", "LOGINOUT_SUCCESS");
                    intent.putExtras(bundle);
                    startActivity(intent);

                    //很对  就是在删除之后   发送广播   重新加载个人中心界面
                    Intent receiverIntent = new Intent("loginoutsuccess");
                    mContext.sendBroadcast(receiverIntent);
                    finish();
                    break;
                //退出失败
                case LOGINOUT_FAIL:
                    //关闭进度条
                    mProgressDialog.dismiss();
                    break;

                //检查版本成功
                case CHECK_VERSION_SUCCESS:
                    String json = (String) msg.obj;
                    AppVersionResp appVersionResp = (AppVersionResp) SalesMsgUtils.fromJson(json, MSG_TYPES.MSG_APP_VERSION, false);
                    int returnCode = appVersionResp.getReturncode();
                    if (returnCode == 0)
                    {
                        //最新的版本url
                        String latestVersionUrl = appVersionResp.getLatestVersionUrl();
                        if (latestVersionUrl != null && !latestVersionUrl.equals(""))
                        {
                            //询问用户是不是更新
                            askUserUpdateOrNot(Configs.SERVER_IP_ADDRESS + latestVersionUrl);
                        }
                        else
                        {
                            toastMgr.builder.display("您当前已经是最新版!", 0);
                        }

                    }
                    break;
                //检查版本失败
                case CHECK_VERSION_FAIL:
                    break;
                case DOWNLOAD_SUCCESS:
                    //更新新版本  安装新版本
                    CustomProgressDialog dialog = (CustomProgressDialog) msg.obj;
                    dialog.dismiss();
                    //下载完成, 问用户是不是立即安装
                    AlertDialog installAlertDialog = new AlertDialog(mContext);
                    installAlertDialog.builder()
                            .setCancelable(false)
                            .setTitle("恭喜, 下载完成")
                            .setPositiveButton("立即安装", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    update();
                                }
                            })
                            .show();

                    break;
            }
        }
    };

    /**
     * 询问用户是不是更新到最新版本
     * @param latestVersionUrl
     */
    private void askUserUpdateOrNot(final String latestVersionUrl) {
        AlertDialog updateAlertDialog = new AlertDialog(mContext);
        updateAlertDialog.builder()
                .setCancelable(false)
                .setTitle("软件更新")
                .setMsg("有新版本更新lalala...")
                .setNegativeButton("残忍拒绝", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("立刻更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoDownloadAndUpdate(latestVersionUrl);
                    }
                })
                .show();
    }

    /**
     * 去下载并且更新最新版
     * @param latestVersionUrl
     */
    private void gotoDownloadAndUpdate(final String latestVersionUrl) {

        //首先判断 有没有网络 , 没有网络提示用户先打开网路
        //先去检查网络, 如果网络连接可用, 就去服务器拿数据, 如果网络不可用, 那就提示用户网络没打开  网络不可用,
        CustomProgressDialog downloadProgress = new CustomProgressDialog(mContext);

        boolean isNetworkAvailable = NetWorkHelper.isNetStateConnected(mContext);
        if (isNetworkAvailable == true)
        {
            //判断是不是wifi
            boolean wifiState = NetWorkHelper.isWifiConnected(mContext);
            if (!wifiState)//wifi 没连接  提示用户
            {
                AlertDialog updateAlertDialog = new AlertDialog(mContext);
                updateAlertDialog.builder()
                        .setCancelable(false)
                        .setTitle("提醒")
                        .setMsg("您的wifi没有连接, 软件可能比较大, 是否确认下载")
                        .setNegativeButton("不下载", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                return;
                            }
                        })
                        .setPositiveButton("立即下载", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            }
        }
        downloadProgress = downloadProgress.show(mContext, "下载中...", false, null);
        //根据url下载
        final CustomProgressDialog finalDownloadProgress = downloadProgress;
        Thread downThread =  new Thread() {
            public void run() {

                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(latestVersionUrl);
                HttpResponse response;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    long length = entity.getContentLength();
                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    if (is != null) {
                        File file = new File(
                                Environment.getExternalStorageDirectory(),
                                "shenghuocajia.apk");
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int count = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            count += ch;
                            if (length > 0) {
                            }
                        }
                    }
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    Message msg = new Message();
                    msg.what = DOWNLOAD_SUCCESS;
                    msg.obj = finalDownloadProgress;
                    loginOutActivityHelperHandler.sendMessage(msg);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        //开始下载
        downThread.start();


    }


    /**
     * 安安装新版本
     */
    private void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "shenghuocajia.apk")),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


    /**
     * 退出
     */
    private void loginOut()
    {
        PostLoginReqHttp postLoginReqHttp= new PostLoginReqHttp(mContext);
        //退出登陆  好像不需要去拿appid  直接后台退出登陆就可以了  然后删除本地sp数据
        postLoginReqHttp.exitLoginReqJson(new PostLoginReqHttp.DoingSuccessCallback() {
                                              @Override
                                              public void onSuccess(String result) {
                                                  Message msg = new Message();
                                                  msg.what = LOGINOUT_SUCCESS;
                                                  msg.obj = result;

                                                  loginOutActivityHelperHandler.sendMessage(msg);
                                              }
                                          }, new PostLoginReqHttp.DoingFailedCallback() {
                                              @Override
                                              public void onFail(String resultMsg) {
                                                  Message msg = new Message();
                                                  msg.what = LOGINOUT_FAIL;
                                                  msg.obj = resultMsg;
                                                  loginOutActivityHelperHandler.sendMessage(msg);
                                              }
                                          }, null
        );
    }


    /**
     * 测试 选择照片
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            String imagepath = data.getStringExtra(UploadFileSelectPicActivity.KEY_PHOTO_PATH);
        }
    }
}
