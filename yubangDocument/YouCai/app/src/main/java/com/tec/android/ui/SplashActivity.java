package com.tec.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.sales.vo.AppConfigReq;
import com.tec.android.R;
import com.tec.android.network.GetBootImageUrlHttp;
import com.tec.android.utils.L;

/**
 * SplashActivity:App启动界面 启动显示图片 显示2s继续启动 欢迎图片 后台配置
 *
 * @author andyzhu
 * @version 1.0
 * @created 2015-07-30
 */

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    private Context mContext;

    private LinearLayout app_start_view;//启动界面的布局    背景

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_splash,null);
        LinearLayout splash = (LinearLayout) view.findViewById(R.id.app_start_view);
        setContentView(view);
        //日志
        L.i(TAG, "oncreate");

        mContext = this;

        app_start_view = (LinearLayout) findViewById(R.id.app_start_view);

        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        getBootImage();

    }


    public void getBootImage()
    {
        GetBootImageUrlHttp getBootImageHttp = new GetBootImageUrlHttp(mContext);
        getBootImageHttp.sendAndRecv(new GetBootImageUrlHttp.DoingSuccessCallback() {
                                         @Override
                                         public void onSuccess(String result) {
                                            //直接拿到bootimage URL
                                            // 从网络上下载图片
                                            // 保存在手机

                                         }
                                     }, new GetBootImageUrlHttp.DoingFailedCallback() {
                                         @Override
                                         public void onFail(String resultMsg) {

                                         }
                                     }
        );
    }





    /**
     * 检查是否需要换图片
     * 1.如果有新图片, 就加载新图片,
     * 2.如果没有新图片, 就加载默认的
     * @param view
     */
    private void check(LinearLayout view) {
//        String path = FileUtils.getAppCache(this, "welcomeback");
//        List<File> files = FileUtils.listPathFiles(path);
//        if (!files.isEmpty()) {
//            File f = files.get(0);
//            long time[] = getTime(f.getName());
//            long today = StringUtils.getToday();
//            if (today >= time[0] && today <= time[1]) {
//                view.setBackgroundDrawable(Drawable.createFromPath(f.getAbsolutePath()));
//            }
//        }
    }

    /**
     * 跳转到...主界面
     */
    private void redirectTo(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
