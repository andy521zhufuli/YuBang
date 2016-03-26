package com.car.yubangapk.ui;

import android.os.Bundle;
import android.view.Window;

import com.andy.android.yubang.R;
/**
 * RegisterDetailUploadIDCardActivity: 注册详情界面 上传身份证
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class RegisterDetailUploadIDCardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_detail_upload_idcard);


    }


}
