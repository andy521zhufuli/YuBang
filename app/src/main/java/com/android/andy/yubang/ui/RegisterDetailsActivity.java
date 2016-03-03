package com.android.andy.yubang.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.andy.yubang.utils.toastMgr;
import com.andy.android.yubang.R;


/**
 * RegisterDetailsActivity: 注册详情界面
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class RegisterDetailsActivity extends BaseActivity {

    private Context mContext;

    private ImageView       img_back;//返回
    private RelativeLayout  car_info;//车辆信息
    private EditText        register_detail_select_car_num;//车牌号
    private ImageView       image_id_card;//上传身份证
    private ImageView       image_driver_photo;//上传车主照片
    private ImageView       image_car_photo;//上传车辆照片
    private ImageView       image_driver_license;//上传驾驶证
    private ImageView       image_driving_license;//上传行驶证
    private ImageView       image_taxi_license;//上传营运证
    private Button          btn_commit;//提交



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_details);

        mContext = this;
        
        findViews();

    }

    private void findViews()
    {
        img_back = (ImageView) findViewById(R.id.img_back  );//返回

        car_info = (RelativeLayout) findViewById(R.id.car_info  );//车辆信息

        register_detail_select_car_num = (EditText) findViewById(R.id.register_detail_select_car_num  );//车牌号

        image_id_card = (ImageView) findViewById(R.id.image_id_card  );//上传身份证

        image_driver_photo = (ImageView) findViewById(R.id.image_driver_photo  );//上传车主照片

        image_car_photo = (ImageView) findViewById(R.id.image_car_photo  );//上传车辆照片

        image_driver_license = (ImageView) findViewById(R.id.image_driver_license  );//上传驾驶证

        image_driving_license = (ImageView) findViewById(R.id.image_driving_license  );//上传行驶证

        image_taxi_license = (ImageView) findViewById(R.id.image_taxi_license  );//上传营运证

        btn_commit = (Button) findViewById(R.id.btn_commit  );//提交

        /**
         * 设置监听器
         */
        //返回
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //选择车辆信息
        car_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailChooseCarInfoActivity.class);
                startActivity(intent);

            }
        });
        //打开上传身份证界面
        image_id_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadIDCardActivity.class);
                startActivity(intent);
            }
        });
        //打开上传车主头像界面
        image_driver_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadDriverPhotoActivity.class);
                startActivity(intent);
            }
        });
        //打开上传车辆照片界面
        image_car_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadCarPhotoActivity.class);
                startActivity(intent);
            }
        });
        //打开上传驾驶证界面
        image_driver_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadDriverLicenseActivity.class);
                startActivity(intent);
            }
        });
        //打开上传行驶证界面
        image_driving_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadDrivingLicenseActivity.class);
                startActivity(intent);
            }
        });
        //打开上传运营证界面
        image_taxi_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadTaxiLicenseActivity.class);
                startActivity(intent);
            }
        });
        //都已经上传了  提交
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("提交成功", 0);
            }
        });
        //TODO 这里 应该是 在下一个界面上传  上传完了回到这个界面  在响应的图片上面打钩 最后所有都完成之后  提交 然后后台去处理
    }

}
