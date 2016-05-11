package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqGetMyOrderDetailInfo;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * DiscoverActivity: 发现界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class DiscoverActivity extends BaseActivity implements View.OnClickListener{


    private Context mContext;
    private static final String TAG = "DiscoverActivity";

    private LinearLayout discover_activity_recommend_partners_wallet_layout;//推荐合伙人
    private LinearLayout discover_activity_nearby_layout;//附近的
    private LinearLayout iwant_get_cash_account_alipay_wallet_layout;//物流

    private LinearLayout iwant_get_cash_account_alipay_account_layout;//保险
    private LinearLayout iwant_get_cash_account_alipay_account_layout1;//油卡

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_discover);
        mContext = this;

        findViews();

        ShareSDK.initSDK(mContext);
    }

    private void findViews() {

        discover_activity_recommend_partners_wallet_layout = (LinearLayout) findViewById(R.id.discover_activity_recommend_partners_wallet_layout);//推荐合伙人
        discover_activity_nearby_layout = (LinearLayout) findViewById(R.id.discover_activity_nearby_layout);//附近的
        iwant_get_cash_account_alipay_wallet_layout = (LinearLayout) findViewById(R.id.iwant_get_cash_account_alipay_wallet_layout);//物流
        iwant_get_cash_account_alipay_account_layout = (LinearLayout) findViewById(R.id.iwant_get_cash_account_alipay_account_layout);//保险
        iwant_get_cash_account_alipay_account_layout1 = (LinearLayout) findViewById(R.id.iwant_get_cash_account_alipay_account_layout1);//油卡
        /**
         * 注册监听器
         */
        discover_activity_recommend_partners_wallet_layout.setOnClickListener(this);//推荐合伙人
        discover_activity_nearby_layout.setOnClickListener(this);//附近的
        iwant_get_cash_account_alipay_wallet_layout.setOnClickListener(this);//物流
        iwant_get_cash_account_alipay_account_layout.setOnClickListener(this);//保险
        iwant_get_cash_account_alipay_account_layout1.setOnClickListener(this);//油卡

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            //推荐合伙人
            case R.id.discover_activity_recommend_partners_wallet_layout:

                intent = new Intent();
                intent.setClass(DiscoverActivity.this, DiscoverRecommendPartnerActivity.class);
                startActivity(intent);
                intent = null;
                break;
            //附近的
            case R.id.discover_activity_nearby_layout:
                intent = new Intent();
                intent.setClass(DiscoverActivity.this, DiscoverNearbyActivity.class);
                startActivity(intent);
                intent = null;
                break;
            //物流
            case R.id.iwant_get_cash_account_alipay_wallet_layout:
                toastMgr.builder.display("第一期不用做", 0);
                break;
            //保险
            case R.id.iwant_get_cash_account_alipay_account_layout:
                toastMgr.builder.display("第一期不用做", 0);
                break;
            //油卡
            case R.id.iwant_get_cash_account_alipay_account_layout1:

                //这里测试分享
                //testGetOrderInfo();
                //shareQQ();
                //wxMoment();
                //wxFriends();
                //oneKeyShare();
                shareWx();

                break;
        }
    }

    private void testGetOrderInfo() {

        HttpReqGetMyOrderDetailInfo orderDetailInfo = new HttpReqGetMyOrderDetailInfo();
        orderDetailInfo.setCallback(new HttpReqCallback() {
            @Override
            public void onFail(int errorCode, String message)
            {

            }

            @Override
            public void onSuccess(Object object)
            {

            }
        });
        orderDetailInfo.getMyOrderDetailInfo(null, null);

    }

    private void qqZone()
    {
        QZone.ShareParams sp = new QZone.ShareParams();
        sp.setTitle("测试分享的标题");
        sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
        sp.setText("测试分享的文本");
        sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
        sp.setSite("发布分享的网站名称");
        sp.setSiteUrl("发布分享网站的地址");

        Platform qzone = ShareSDK.getPlatform (QZone.NAME);
        qzone.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                toastMgr.builder.display("分享空间完成", 0);
                L.d(TAG + "分享空间", "分享空间完成");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                toastMgr.builder.display("分享空间失败", 0);
                L.d(TAG + "分享空间", "分享空间失败" + "i " + i + ",throwable" + throwable.toString());
                if (i == 9) {
                    toastMgr.builder.display("分享空间失败,", 0);
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                toastMgr.builder.display("分享空间取消", 0);
                L.d(TAG + "分享空间", "分享空间取消");
            }
        }); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp);
    }

    private void oneKeyShare()
    {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle("我是分享标题");  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");  //最多40个字符

        // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
        //oks.setImagePath(Environment.getExternalStorageDirectory() + "/meinv.jpg");//确保SDcard下面存在此张图片

        //网络图片的url：所有平台
        oks.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul

        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ空间使用
        oks.setTitleUrl("http://www.baidu.com");  //网友点进链接后，可以看到分享的详情

        // 启动分享GUI
        oks.show(this);

    }


    private void wxMoment()
    {

        //2、设置分享内容
       Platform.ShareParams sp = new Platform.ShareParams();
       sp.setShareType(Platform.SHARE_WEBPAGE); //非常重要：一定要设置分享属性
       sp.setTitle("我是分享标题");  //分享标题
       sp.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");   //分享文本
       sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片ru
       sp.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

        //3、非常重要：获取平台对象
        Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);

        wechatMoments.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                toastMgr.builder.display("分享朋友圈完成", 0);
                L.d(TAG + "分享朋友圈", "分享朋友圈完成");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                toastMgr.builder.display("分享朋友圈失败", 0);
                L.d(TAG + "分享朋友圈", "分享朋友圈失败" + "i " + i + ",throwable" + throwable.toString());
                if (i == 9) {
                    toastMgr.builder.display("分享朋友圈失败,您未安装微信, 请安装微信", 0);
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                toastMgr.builder.display("分享朋友圈取消", 0);
                L.d(TAG + "分享朋友圈", "分享朋友圈取消");
            }
        });
        wechatMoments.share(sp);
    }

    private void wxFriends()
    {
        Wechat.ShareParams wechatSp = new Wechat.ShareParams();
        wechatSp.setShareType(Platform.SHARE_IMAGE);
        wechatSp.setShareType(Platform.SHARE_WEBPAGE); //非常重要：一定要设置分享属性
        wechatSp.setTitle("朋友圈Image");

        Platform wxFriends = ShareSDK.getPlatform(Wechat.NAME);
        wxFriends.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                toastMgr.builder.display("分享微信好友完成", 0);
                L.d(TAG + "分享好友", "分享好友完成");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                toastMgr.builder.display("分享好友失败", 0);
                L.d(TAG + "分享好友", "分享好友失败" + "i " + i + ",throwable" + throwable.toString());
                if (i == 9) {
                    toastMgr.builder.display("分享好友失败,您未安装微信, 请安装微信", 0);
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                toastMgr.builder.display("分享好友取消", 0);
                L.d(TAG + "分享好友", "分享好友取消");
            }
        });
        wxFriends.share(wechatSp);
    }


    private void shareMsg()
    {
        ShortMessage.ShareParams sp = new ShortMessage.ShareParams();
        sp.setTitle("123");
        Platform msg = ShareSDK.getPlatform(ShortMessage.NAME);
        msg.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                toastMgr.builder.display("分享短信完成", 0);
                L.d(TAG + "分享短信", "分享短信完成");
             }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                toastMgr.builder.display("分享短信失败", 0);
                L.d(TAG + "分享短信", "分享短信失败" + "i " + i + ",throwable" + throwable.toString());
                if (i == 9) {
                    toastMgr.builder.display("分享短信失败,您未安装微信, 请安装微信", 0);
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                toastMgr.builder.display("分享短信取消", 0);
                L.d(TAG + "分享短信", "分享短信取消");
            }
        });
        msg.share(sp);
    }



    private void shareWx()
    {
        //2、设置分享内容
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
        sp.setTitle("我是分享标题");  //分享标题
        sp.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");   //分享文本
        sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul
        sp.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

        //3、非常重要：获取平台对象
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                toastMgr.builder.display("分享微信好友完成", 0);
                L.d(TAG + "分享好友", "分享好友完成");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                toastMgr.builder.display("分享好友失败", 0);
                L.d(TAG + "分享好友", "分享好友失败" + "i " + i + ",throwable" + throwable.toString());
                if (i == 9) {
                    toastMgr.builder.display("分享好友失败,您未安装微信, 请安装微信", 0);
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                toastMgr.builder.display("分享好友取消", 0);
                L.d(TAG + "分享好友", "分享好友取消");
            }
        }); // 设置分享事件回调
        // 执行分享
        wechat.share(sp);
    }


    private void shareQQ()
    {
//        QQ.ShareParams qqSp = new QQ.ShareParams();
//        qqSp.setTitle("123");
//        Platform qq = ShareSDK.getPlatform(QQ.NAME);
//        qq.setPlatformActionListener(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                toastMgr.builder.display("分享qq完成", 0);
//                L.d(TAG + "分享qq", "分享qq完成");
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                toastMgr.builder.display("分享qq失败", 0);
//                L.d(TAG + "分享qq", "分享qq失败" + "i " + i + ",throwable" + throwable.toString());
//                if (i == 9) {
//                    toastMgr.builder.display("分享qq失败,您", 0);
//                }
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                toastMgr.builder.display("分享qq取消", 0);
//                L.d(TAG + "分享qq", "分享qq取消");
//            }
//        });
//        qq.share(qqSp);


        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("我是分享标题");
        sp.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");
        sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul
        sp.setTitleUrl("http://www.baidu.com");  //网友点进链接后，可以看到分享的详情
        //3、非常重要：获取平台对象
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                handler.sendEmptyMessage(4);
                L.d(TAG + "分享qq", "分享qq完成");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                handler.sendEmptyMessage(6);
                L.d(TAG + "分享qq", "分享qq失败" + "i " + i + ",throwable" + throwable.toString());

            }

            @Override
            public void onCancel(Platform platform, int i) {
                handler.sendEmptyMessage(5);

                L.d(TAG + "分享qq", "分享qq取消");
            }
        }); // 设置分享事件回调
        // 执行分享
        qq.share(sp);
    }



    Handler handler = new Handler() {

            @Override
        public void handleMessage(Message msg) {
             switch (msg.what) {
                     case 1:
                             Toast.makeText(getApplicationContext(), "微博分享成功", Toast.LENGTH_LONG).show();
                             break;

                     case 2:
                             Toast.makeText(getApplicationContext(), "微信分享成功", Toast.LENGTH_LONG).show();
                             break;
                     case 3:
                         toastMgr.builder.display("分享qq完成", 0);
                             break;
                     case 4:
                             Toast.makeText(getApplicationContext(), "QQ分享成功", Toast.LENGTH_LONG).show();
                             break;

                     case 5:
                             Toast.makeText(getApplicationContext(), "取消分享", Toast.LENGTH_LONG).show();
                             break;
                     case 6:
                         toastMgr.builder.display("分享qq失败", 0);
                             break;

                     default:
                             break;
                 }
}

};


}
