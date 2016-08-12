package com.tec.android.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sales.vo.ShareResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.R;
import com.tec.android.configs.Configs;
import com.tec.android.network.ShareReqHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.CustomProgressDialog;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;
import java.util.List;

/**
 * 分享
 */
public class SocialShareActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;
    private static final String TAG = "SocialShareActivity";

    private final int GET_SHARE_PARAMS_SUCCESS = 0X01;
    private final int GET_SAHRE_PARAMS_FAIL    = 0X10;

    private ShareResp mShareResp;//拿回来的分享参数


    private RelativeLayout social_share_qq_share_textview;//qq分享
    private RelativeLayout social_share_wx_share_textview;//微信分享
    private RelativeLayout social_share_qqzone_share_textview;    //空间分享
    private RelativeLayout social_share_wx_friends_share_textview;//微信朋友圈分享
    private TextView social_share_text_cancle;//取消

    private String mGoodsID;

    private CustomProgressDialog customProgressDialog;//进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_social_share);

        mContext = this;

        mGoodsID = (String) getIntent().getExtras().get("goodid");

        customProgressDialog = new CustomProgressDialog(mContext);
        customProgressDialog = customProgressDialog.show(mContext, "加载中...", false, null);

        //调用预分享  拿到分享的一个参数
        ShareReqHttp shareReqHttp = new ShareReqHttp(mContext);
        shareReqHttp.sendAndGetShareReqParamsJson(new ShareReqHttp.DoingSuccessCallback() {
                                                      @Override
                                                      public void onSuccess(String result) {
                                                            //拿到分享参数ok
                                                          try {
                                                              //如果可以成功转成规定类型 ok
                                                              ShareResp shareResp = new Gson().fromJson(result, ShareResp.class);
                                                              Message m1 = new Message();
                                                              m1.what = GET_SHARE_PARAMS_SUCCESS;
                                                              m1.obj = result;
                                                              shareHandler.sendMessage(m1);
                                                          }catch (Exception e)
                                                          {
                                                               L.i("SocialShareActivity onSuccess result json Exception = " + e.toString());
                                                               customProgressDialog.dismiss();
                                                               toastMgr.builder.display("json转换出错", 0);
                                                               finish();
                                                          }

                                                      }
                                                  }, new ShareReqHttp.DoingFailedCallback() {
                                                      @Override
                                                      public void onFail(String resultMsg) {

                                                          Message m2 = new Message();
                                                          m2.what = GET_SAHRE_PARAMS_FAIL;
                                                          m2.obj = resultMsg;
                                                          shareHandler.sendMessage(m2);
                                                      }
                                                  },mGoodsID

        );

        findViews();
    }

    /**
     * 初始化控件
     */
    private void findViews() {
        social_share_qq_share_textview = (RelativeLayout) findViewById(R.id.social_share_qq_share_textview);
        social_share_wx_share_textview = (RelativeLayout) findViewById(R.id.social_share_wx_share_textview);
        social_share_qqzone_share_textview = (RelativeLayout) findViewById(R.id.social_share_qqzone_share_textview);
        social_share_wx_friends_share_textview = (RelativeLayout) findViewById(R.id.social_share_wx_friends_share_textview);
        social_share_text_cancle = (TextView) findViewById(R.id.social_share_text_cancle);
        //监听器
        social_share_qq_share_textview.setOnClickListener(this);
        social_share_wx_share_textview.setOnClickListener(this);
        social_share_qqzone_share_textview.setOnClickListener(this);
        social_share_wx_friends_share_textview.setOnClickListener(this);
        social_share_text_cancle.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Tencent mTencent = Tencent.createInstance(Configs.QQ_APP_ID, SocialShareActivity.this.getApplicationContext());
        switch (v.getId())
        {
            //qq分享
            case R.id.social_share_qq_share_textview:
                toastMgr.builder.display("qq share", 0);
                Bundle params = new Bundle();
                //
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                //选择是否分享的时候  标题分享的标题, 最长30个字符。
                params.putString(QQShare.SHARE_TO_QQ_TITLE, mShareResp.getShareGoodModel().getSharetitle());
                //选择是否分享的实惠   摘要分享的消息摘要，最长40个字。
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, mShareResp.getShareGoodModel().getSharecontent());
                //真正发送到qq好友上的url
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mShareResp.getShareGoodModel().getShareurl());
                //选择是否发送dialog上显示的图片   分享图片的URL或者本地路径
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,mShareResp.getShareGoodModel().getShareimgurl());
                //应用的名字手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "生活彩家");
                mTencent.shareToQQ(SocialShareActivity.this, params, qqShareListener);
                break;
            //微信分享
            case R.id.social_share_wx_share_textview:
                toastMgr.builder.display("微信 share", 0);


                break;
            //k
            case R.id.social_share_qqzone_share_textview:
                toastMgr.builder.display("空间 share", 0);
                Bundle params1 = new Bundle();
                //                                             QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT
                params1.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                //选择是否分享的时候  标题分享的标题, 最长30个字符。
                params1.putString(QzoneShare.SHARE_TO_QQ_TITLE, mShareResp.getShareGoodModel().getSharetitle());
                //选择是否分享的实惠   摘要分享的消息摘要，最长40个字。
                params1.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, mShareResp.getShareGoodModel().getSharecontent());
                //真正发送到qq好友上的url
                params1.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,  mShareResp.getShareGoodModel().getShareurl());
                //选择是否发送dialog上显示的图片   分享图片的URL或者本地路径
                List<String> list = new ArrayList<>();
                list.add(mShareResp.getShareGoodModel().getShareimgurl());
                params1.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, (ArrayList<String>) list);
                //应用的名字手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
                params1.putString(QQShare.SHARE_TO_QQ_APP_NAME, "生活彩家");
                mTencent.shareToQzone(SocialShareActivity.this, params1, qZoneShareListener);
                break;
            case R.id.social_share_wx_friends_share_textview:
                toastMgr.builder.display("朋友圈 share", 0);

                break;
            case R.id.social_share_text_cancle:
                toastMgr.builder.display("取消 share", 0);
                finish();
                break;
        }
    }


    /**
     * qq分享的回调
     */
    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            toastMgr.builder.display("QQShareActivityonCancel: ", 0);
            finish();
        }
        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            toastMgr.builder.display("QQShareActivity onComplete: " + response.toString(), 0);
            finish();
        }


        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            toastMgr.builder.display("QQShareActivityonError: " + e.errorMessage, 0);
            finish();
        }
    };


    /**
     * 空间分享的回调
     */
    IUiListener qZoneShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            toastMgr.builder.display("QQZoneShareActivity onCancel: ", 0);
            finish();
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            toastMgr.builder.display("QQZoneShareActivityon Error: " + e.errorMessage, 0);
            L.i("QQoneShareActivityon Error:" + e.errorMessage);
            finish();

        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            toastMgr.builder.display("QQZoneShareActivity onComplete: " + response.toString(), 0);
            finish();
        }
    };


    /**
     * 去服务器拿数据后的一些操作
     */
    private Handler shareHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                //拿到分享参数成功
                case GET_SHARE_PARAMS_SUCCESS:
                    customProgressDialog.dismiss();
                    String result = (String) msg.obj;
                    ShareResp shareResp = new Gson().fromJson(result, ShareResp.class);
                    mShareResp = shareResp;
                    break;
                //拿到分享信息失败
                case GET_SAHRE_PARAMS_FAIL:
                    customProgressDialog.dismiss();
                    toastMgr.builder.display("请检查您的网络", 0);
                    //一旦这里出现问题 就说明没有拿到参数, 就不能继续分享  否则 会报错
                    finish();
                    break;
            }
        }
    };
}
