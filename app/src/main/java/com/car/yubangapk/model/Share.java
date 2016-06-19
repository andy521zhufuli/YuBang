package com.car.yubangapk.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.WxShareBean;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqShareCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqWXShare;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.ShareDialog;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by andy on 16/5/13.
 */
public class Share implements PlatformActionListener, HttpReqShareCallback
{


    Context mContext;
    Share mShare = null;
    String TAG = Share.class.getSimpleName();
    ShareDialog shareDialog;
    public Share(Context context)
    {
        this.mContext = context;
        ShareSDK.initSDK(mContext);
    }


    public void startShare()
    {
        shareDialog = new ShareDialog(mContext);
        shareDialog.setCancelButtonOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                shareDialog.dismiss();

            }
        });
        shareDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                if (item.get("ItemText").equals("微信好友")) {
                    toastMgr.builder.display("微信好友", 1);
                    getWxShareBean(SHARE_WX_FRIENDS);
                } else if (item.get("ItemText").equals("朋友圈")) {
                    getWxShareBean(SHARE_WX_MOMENTS);
                    toastMgr.builder.display("微信朋友圈", 1);
                } else if (item.get("ItemText").equals("QQ空间")) {
                    toastMgr.builder.display("QQ Zone", 1);
                    getWxShareBean(SHARE_QQ_ZOME);
                } else if (item.get("ItemText").equals("QQ")) {
                    toastMgr.builder.display("QQ", 1);
                    getWxShareBean(SHARE_QQ_FRIENDS);
                }
            }
        });
    }


    private void shareWXMoments(String title, String textcontent, String imageUrl, String url)
    {
        //2、设置分享内容
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE); //非常重要：一定要设置分享属性
        sp.setTitle(title);  //分享标题
        sp.setText(textcontent);   //分享文本
        sp.setImageUrl(imageUrl);//网络图片rul
        sp.setUrl(url);   //网友点进链接后，可以看到分享的详情
        //3、非常重要：获取平台对象
        Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
        wechatMoments.setPlatformActionListener(this); // 设置分享事件回调
        // 执行分享
        wechatMoments.share(sp);
    }


    private void getWxShareBean(int type)
    {
        HttpReqWXShare wxShare = new HttpReqWXShare(mContext);
        wxShare.setCallback(this, type);
        wxShare.getWxShare(Configs.getLoginedInfo(mContext).getUserid());
    }

    private void shareWXFriend(String title, String textcontent, String imageUrl, String url)
    {
        //2、设置分享内容
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
        sp.setTitle(title);  //分享标题
        sp.setText(textcontent);   //分享文本
        sp.setImageUrl(imageUrl);//网络图片rul
        sp.setUrl(url);   //网友点进链接后，可以看到分享的详情

        //3、非常重要：获取平台对象
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(this); // 设置分享事件回调
        // 执行分享
        wechat.share(sp);
    }

    private void shareQQFriends(String title, String textcontent, String imageUrl, String url)
    {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(title);
        sp.setText(textcontent);
        sp.setImageUrl(imageUrl);//网络图片rul
        sp.setTitleUrl(url);  //网友点进链接后，可以看到分享的详情
        //3、非常重要：获取平台对象
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(this);
        qq.share(sp);
    }

    private void shareQQZone(String title, String textcontent, String imageUrl, String url)
    {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(title);
        sp.setTitleUrl(url); // 标题的超链接
        sp.setText(textcontent);
        sp.setImageUrl(imageUrl);
        sp.setSite("驭帮");
        sp.setSiteUrl(url);

        Platform qzone = ShareSDK.getPlatform (QZone.NAME);
        qzone.setPlatformActionListener(this);
        qzone.share(sp);
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (platform.getName().equals(QZone.NAME))
        {//
            handler.sendEmptyMessage(1);
        }
        else if (platform.getName().equals(Wechat.NAME))
        {
            handler.sendEmptyMessage(2);
        }
        else if (platform.getName().equals(WechatMoments.NAME))
        {
            handler.sendEmptyMessage(3);
        } else if (platform.getName().equals(QQ.NAME))
        {
            handler.sendEmptyMessage(4);
        }
        L.d(TAG + "分享好友", "分享好友完成");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

        throwable.printStackTrace();
        Message msg = new Message();
        msg.what = 6;
        msg.obj = throwable.getMessage();
        handler.sendMessage(msg);
        if (i == 9) {
            toastMgr.builder.display("分享好友失败,您未安装微信, 请安装微信", 0);
        }

    }

    @Override
    public void onCancel(Platform platform, int i) {

        handler.sendEmptyMessage(5);
    }


    /**
     * 请求后台
     * @param errorCode
     * @param message
     */
    @Override
    public void onFail(int errorCode, String message) {
        //toastMgr.builder.display("服务器或者网络错误, 分享失败", 1);
        handler.sendEmptyMessage(7);
    }

    @Override
    public void onSuccess(Object object, int type) {
        WxShareBean wxShareBean = (WxShareBean) object;
        String title = wxShareBean.getTitle();
        String imageUrl = wxShareBean.getImageUrl();
        String url = wxShareBean.getUrl();
        String textcontent = wxShareBean.getDest();
        if (type == SHARE_WX_FRIENDS)
        {
            shareWXFriend(title, textcontent, imageUrl, url);
        }
        else if (type == SHARE_WX_MOMENTS)
        {
            shareWXMoments(title, textcontent, imageUrl, url);
        }
        else if (type == SHARE_QQ_FRIENDS)
        {
            shareQQFriends(title, textcontent, imageUrl, url);
        }
        else if (type == SHARE_QQ_ZOME)
        {
            shareQQZone(title, textcontent, imageUrl, url);
        }

    }


    int SHARE_WX_FRIENDS = 1;
    int SHARE_WX_MOMENTS = 2;
    int SHARE_QQ_FRIENDS = 3;
    int SHARE_QQ_ZOME    = 4;


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    toastMgr.builder.display("QQ空间分享成功", 1);
                    break;
                case 2:
                    toastMgr.builder.display("微信分享成功", 1);
                    break;
                case 3:
                    toastMgr.builder.display("朋友圈分享成功", 1);
                    break;
                case 4:
                    toastMgr.builder.display("QQ分享成功", 1);
                    break;

                case 5:
                    toastMgr.builder.display("取消分享", 1);
                    break;
                case 6:
                    toastMgr.builder.display("分享失败啊", 1);
                    L.d(TAG + "分享好友", "分享好友失败" + ",throwable" + (String)msg.obj);
                    break;
                case 7:
                    toastMgr.builder.display("网络错误, 分享失败",1);
                default:
                    break;
            }
            shareDialog.dismiss();

        }

    };
}
