package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.json.bean.Json2MyUserInfoBean;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.alterUserInfo.HttpReqAlterUserInfo;
import com.car.yubangapk.network.myHttpReq.alterUserInfo.HttpReqAlterUserInfoCallback;
import com.car.yubangapk.network.myHttpReq.alterUserInfo.HttpReqUploadUserPic;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.CustomProgressDialog;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * MyPersonalInfoActivity: 个人信息界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class MyPersonalInfoActivity extends BaseActivity{

    private Context mContext;

    private ImageView img_back;//返回
    private RelativeLayout my_personal_item_head_layout;//头像
    private CircleImageView mUserCirclePhoto;

    private RelativeLayout my_personal_item_nickname_layout;//昵称
    private TextView my_personal_item_name;

    private RelativeLayout my_personal_item_sex_layout;//性别
    private TextView my_personal_item_sex;
    private RelativeLayout my_personal_item_age_layout;//年龄
    private TextView my_personal_item_age;

    private RelativeLayout my_personal_item_phonenum_layout;//手机号码
    private TextView my_personal_item_phone_num;

    private RelativeLayout my_personal_item_level_layout;//等级
    private TextView my_personal_item_level;

    private RelativeLayout my_personal_item_industry_layout;//行业
    private TextView my_personal_item_industry;

    private RelativeLayout my_personal_item_occupation_layout;//职业
    private TextView my_personal_item_occupation;

    private RelativeLayout my_personal_item_real_name_certification_layout;//实名认证
    private TextView my_personal_item_real_name_certification;

    private CustomProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_personal_info);
        mContext = this;

        mProgress = new CustomProgressDialog(mContext);
        findViews();
        setUserInfo();
    }


    void setUserInfo() {
        Bundle bundle = getIntent().getExtras();
        Json2MyUserInfoBean userInfo = (Json2MyUserInfoBean) bundle.getSerializable("userInfo");

        //设置头像
        String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + userInfo.getPathCode() + "&fileReq.fileName=" + userInfo.getPhotoName();
        ImageLoaderTools.getInstance(mContext).displayImage(url, mUserCirclePhoto);

        my_personal_item_name.setText(userInfo.getUserName());
        my_personal_item_phone_num.setText(userInfo.getPhoneNum());
        my_personal_item_level.setText(userInfo.getCar());
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
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putBoolean("alter",isUserDataModified);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });//返回
        //头像点击 上传新头像
        my_personal_item_head_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, UploadFileSelectPicActivity.class);
                startActivityForResult(intent,REQUEST_ALTER_USER_PIC);

            }
        });//头像


        my_personal_item_nickname_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, AlterUserBaseInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", "username");
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_ALTER_USER_NAME);
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
                Intent intent = new Intent();
                intent.setClass(mContext, AlterUserBaseInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", "phonenum");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_ALTER_USER_PHONE_NUN);
            }
        });//手机号码


        my_personal_item_level_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, AlterUserBaseInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", "car");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_ALTER_USER_CAT_TYPE);
            }
        });//车型
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
                Intent intent = new Intent();
                intent.setClass(mContext, AlterUserPwdActivity.class);
                startActivity(intent);
            }
        });//
    }


    private int REQUEST_ALTER_USER_PIC = 11;//修改头像
    private int REQUEST_ALTER_USER_NAME = 12;//修改用户名
    private int REQUEST_ALTER_USER_CAT_TYPE = 13;//修改车型
    private int REQUEST_ALTER_USER_PHONE_NUN = 14;//修改电话号码

    private Bitmap mUserPics;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {


            if (requestCode == REQUEST_ALTER_USER_PIC)
            {
            //拿到图片的路径  解析成bitmap
                String imagepath = data.getStringExtra(UploadFileSelectPicActivity.KEY_PHOTO_PATH);
                Bitmap gettedImage = getSmallBitmap(imagepath);
                mUserPics = gettedImage;
                String[] name = imagepath.split("/");
                String imagename = name[name.length - 1];
                upLoadUserPic(imagepath);
            }
            else if (requestCode == REQUEST_ALTER_USER_NAME)
            {
                //拿到新的用户名, 去上传用户名
                Bundle bundle = data.getExtras();
                String info = bundle.getString("message");
                alterUserName(info);

            }
            else if (requestCode == REQUEST_ALTER_USER_CAT_TYPE)
            {
                Bundle bundle = data.getExtras();
                String info = bundle.getString("message");
                alterPhoneNum(info);
            }
            else if (requestCode == REQUEST_ALTER_USER_PHONE_NUN)
            {

            }
        }
        else
        {
            toastMgr.builder.display("您又有修改",1);
        }
    }

    /**
     * 修改用户名
     */
    private void alterUserName(String username) {
        HttpReqAlterUserInfo alterUserInfo = new HttpReqAlterUserInfo();
        alterUserInfo.setCallback(new HttpReqAlterUserInfoCallback() {
            @Override
            public void onFail(int errorCode, String message) {
                isUserDataModified = false;
            }

            @Override
            public void onSuccess(Object object, int type) {
                Json2LoginBean loginBean = (Json2LoginBean) object;
                //保存登陆信息
                Configs.putLoginedInfo(mContext,loginBean);
                isUserDataModified = true;
            }
        });
        String userid = Configs.getLoginedInfo(mContext).getUserid();
        alterUserInfo.alterUserBaseInfo(userid, username, null, "0");
    }


    private void alterPhoneNum(String phoneNum)
    {
        HttpReqAlterUserInfo alterUserInfo = new HttpReqAlterUserInfo();
        alterUserInfo.setCallback(new HttpReqAlterUserInfoCallback() {
            @Override
            public void onFail(int errorCode, String message) {
                isUserDataModified = false;
            }

            @Override
            public void onSuccess(Object object, int type) {
                Json2LoginBean loginBean = (Json2LoginBean) object;
                //保存登陆信息
                Configs.putLoginedInfo(mContext,loginBean);
                isUserDataModified = true;
            }
        });
        String userid = Configs.getLoginedInfo(mContext).getUserid();
        alterUserInfo.alterUserBaseInfo(userid, null, phoneNum, "0");
    }


    /**
     * 上传用户头像
     */
    private void upLoadUserPic(String filePath)
    {

        mProgress = mProgress.show(mContext, "正在上传照片...", false, null);
        HttpReqUploadUserPic uploadUserPic = new HttpReqUploadUserPic();
        uploadUserPic.setCallback(new UploadCallback());
        String userid = Configs.getLoginedInfo(mContext).getUserid();
        uploadUserPic.uploadPic(userid, filePath);
    }



    class UploadCallback implements HttpReqCallback
    {

        @Override
        public void onFail(int errorCode, String message) {
            isUserDataModified = false;
            mProgress.dismiss();
        }

        @Override
        public void onSuccess(Object object) {
            mProgress.dismiss();
            isUserDataModified = true;
            Json2LoginBean bean = (Json2LoginBean) object;
            mUserCirclePhoto.setImageBitmap(mUserPics);
            toastMgr.builder.display("修改成功",1);
        }
    }


    // 根据路径获得图片并压缩，返回bitmap用于显示
    public Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap1 = BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 压缩图片
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */

    //计算图片的缩放值
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    private boolean isUserDataModified = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
