package com.car.yubangapk.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.json.bean.Json2FirstPageTabsBean;
import com.car.yubangapk.utils.toastMgr;
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

    TextView    tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);


        tv_version = (TextView) findViewById(R.id.tv_version);




        tv_version.setText(getVersion());

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

}
