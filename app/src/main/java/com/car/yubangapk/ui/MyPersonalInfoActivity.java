package com.car.yubangapk.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * MyPersonalInfoActivity: 个人信息界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class MyPersonalInfoActivity extends BaseActivity {

    private Context mContext;

    private ImageView           img_back;//返回
    private RelativeLayout      my_personal_item_head_layout;//头像
    private CircleImageView     mUserCirclePhoto;

    private RelativeLayout      my_personal_item_nickname_layout;//昵称
    private TextView            my_personal_item_name;

    private RelativeLayout      my_personal_item_sex_layout;//性别
    private TextView            my_personal_item_sex;
    private RelativeLayout      my_personal_item_age_layout;//年龄
    private TextView            my_personal_item_age;

    private RelativeLayout      my_personal_item_phonenum_layout;//手机号码
    private TextView            my_personal_item_phone_num;

    private RelativeLayout      my_personal_item_level_layout;//等级
    private TextView            my_personal_item_level;

    private RelativeLayout      my_personal_item_industry_layout;//行业
    private TextView            my_personal_item_industry;

    private RelativeLayout      my_personal_item_occupation_layout;//职业
    private TextView            my_personal_item_occupation;

    private RelativeLayout      my_personal_item_real_name_certification_layout;//实名认证
    private TextView            my_personal_item_real_name_certification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_personal_info);

        mContext = this;

        findViews();


    }

    private void findViews() {
        img_back = (ImageView) findViewById(R.id.img_back);//返回

        my_personal_item_head_layout = (RelativeLayout) findViewById(R.id.my_personal_item_head_layout);//头像
        mUserCirclePhoto = (CircleImageView) findViewById(R.id.my_personal_item_head_photo);//头像

        my_personal_item_nickname_layout = (RelativeLayout) findViewById(R.id.my_personal_item_nickname_layout);//昵称
        my_personal_item_name = (TextView) findViewById(R.id.my_personal_item_name);//

        my_personal_item_sex_layout = (RelativeLayout) findViewById(R.id.my_personal_item_sex_layout);//性别
        my_personal_item_sex = (TextView) findViewById(R.id.my_personal_item_sex);//

        my_personal_item_age_layout = (RelativeLayout) findViewById(R.id.my_personal_item_age_layout);//年龄
        my_personal_item_age = (TextView) findViewById(R.id.my_personal_item_age);//

        my_personal_item_phonenum_layout = (RelativeLayout) findViewById(R.id.my_personal_item_phonenum_layout);//手机号码
        my_personal_item_phone_num = (TextView) findViewById(R.id.my_personal_item_phone_num);//

        my_personal_item_level_layout = (RelativeLayout) findViewById(R.id.my_personal_item_level_layout);//等级
        my_personal_item_level = (TextView) findViewById(R.id.my_personal_item_level);//

        my_personal_item_industry_layout = (RelativeLayout) findViewById(R.id.my_personal_item_industry_layout);//行业
        my_personal_item_industry = (TextView) findViewById(R.id.my_personal_item_industry);//

        my_personal_item_occupation_layout = (RelativeLayout) findViewById(R.id.my_personal_item_occupation_layout);//职业
        my_personal_item_occupation = (TextView) findViewById(R.id.my_personal_item_occupation);//

        my_personal_item_real_name_certification_layout = (RelativeLayout) findViewById(R.id.my_personal_item_real_name_certification_layout);//实名认证
        my_personal_item_real_name_certification = (TextView) findViewById(R.id.my_personal_item_real_name_certification);//

        /**
         * 注册监听器
         */
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });//返回
        //头像点击 上传新头像
        my_personal_item_head_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });//头像
        my_personal_item_nickname_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });//昵称
        my_personal_item_sex_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });//性别
        my_personal_item_age_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });//年龄
        my_personal_item_phonenum_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });//手机号码
        my_personal_item_level_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });//等级
        my_personal_item_industry_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });//行业
        my_personal_item_occupation_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });//职业
        my_personal_item_real_name_certification_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });//实名认证
    }


}
