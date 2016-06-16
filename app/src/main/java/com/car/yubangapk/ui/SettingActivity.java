package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2FirstPageTabsBean;
import com.car.yubangapk.json.bean.sysconfigs.Json2AppConfigs;
import com.car.yubangapk.json.formatJson.formatSysconfigs.Json2SYSConfigs;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqLogout;
import com.car.yubangapk.network.okhttp.cookie.store.PersistentCookieStore;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;
import com.car.yubangapk.view.ScrollNavigationTab.ScrollTabView;
import com.car.yubangapk.view.ScrollNavigationTab.ScrollTabsAdapter;
import com.car.yubangapk.view.ScrollNavigationTab.TabAdapter;
import com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager.ScrollTabView1;
import com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager.ScrollTabsAdapter1;
import com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager.TabAdapter1;

/**
 * SettingActivity: 设置界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-28
 */
public class SettingActivity extends BaseActivity {

    Context     mContext;
    TextView    tv_version;
    LinearLayout    layout_about_version;
    ImageView       img_back;
    Button          btn_logout;
    CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);

        mContext = this;
        progressDialog = new CustomProgressDialog(mContext);
        findViews();

        getAndSetVersion();


    }

    private void getAndSetVersion() {
        tv_version.setText(getVersion());


    }


    void findViews()
    {
        tv_version = (TextView) findViewById(R.id.tv_version);
        layout_about_version = (LinearLayout) findViewById(R.id.layout_about_version);
        img_back = (ImageView) findViewById(R.id.img_back);

        btn_logout = (Button) findViewById(R.id.btn_logout);

        layout_about_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkVersion();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    /**
     * 退出登录
     */
    private void logout() {
        progressDialog = progressDialog.show(mContext, "正在退出...", false, null);
        HttpReqLogout logout = new HttpReqLogout(mContext);
        logout.setCallback(new HttpReqCallback() {
            @Override
            public void onFail(int errorCode, String message) {
                progressDialog.dismiss();
                if (errorCode == 100) {
                    toastMgr.builder.display("服务器重启, 需要重新登录", 1);
                    NotLogin.gotoLogin(SettingActivity.this);
                }
            }

            @Override
            public void onSuccess(Object object) {
                progressDialog.dismiss();
                SPUtils.putUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);
                //清除现存的cookie 下次请求的时候  就会有新的cookie
                PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
                cookieStore.removeAll();
                //去到我的界面
                toastMgr.builder.display("退出登录成功!", 1);
                Bundle bundle = new Bundle();
                bundle.putString("otherActivity", "logout");
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this, MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        logout.logout(Configs.getLoginedInfo(mContext).getUserid());
    }






    private void checkVersion() {

        Json2SYSConfigs configs = new Json2SYSConfigs(mContext);
        Json2AppConfigs appConfigs = configs.getAppConfigs();
        if (appConfigs == null)
        {
            toastMgr.builder.display("暂无新版本!", 1);
            return;
        }
        else
        {
            String version = appConfigs.getSys().getCzVersion();
            if (!version.equals(getVersionNew()))
            {
                AlertDialog alertDialog = new AlertDialog(mContext);
                alertDialog.builder().setTitle("更新提醒")
                        .setMsg("您有新版本可以下载")
                        .setCancelable(true)
                        .setPositiveButton("立即更新", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                updateAPp();
                            }
                        })
                        .setNegativeButton("下次再说", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .show();
            }
            else
            {
                toastMgr.builder.display("您当前为最新版", 1);
            }
        }
    }

    private void updateAPp() {
        Intent intent = new Intent();
        intent.setClass(mContext, UpgradeAppWebviewActivity.class);
        startActivity(intent);
    }


    public String getVersion()
    {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
        return "V" + version;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
            }
    }

    public String getVersionNew()
    {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
