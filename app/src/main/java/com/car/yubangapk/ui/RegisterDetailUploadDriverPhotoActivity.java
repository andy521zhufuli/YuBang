package com.car.yubangapk.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.andy.android.yubang.R;

/**
 * RegisterDetailUploadDriverPhotoActivity: 注册详情  上传驾驶员照片 提交验证资料
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class RegisterDetailUploadDriverPhotoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_detail_upload_driver_photo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_detail_upload_driver_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
