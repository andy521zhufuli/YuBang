package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2RecommendShopDetailInfoBean;
import com.car.yubangapk.location.GetLocationData;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqRecommendDetailInfo;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.Warn.UpdateApp;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.CustomProgressDialog;

public class DiscoverAddPartnerDetailInfoActivity extends BaseActivity implements View.OnClickListener{


    private static final String TAG = "DiscoverAddPartnerDetailInfoActivity";
    private Context mContext;


    ImageView           img_back;
    EditText            recommend_contact_uid_edittext;//联系人
    EditText            recommend_phone_edittext;//联系电话
    RelativeLayout      recommend_location_layout;//定位布局
    TextView            recommend_location_text;//定位后信息显示
    EditText            recommend_shop_address_edittext;//店铺地址
    EditText            recommend_shop_name_edittext;//店铺名字
    Button              recommend_btn_next_step;//下一步


    CustomProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_discover_add_partner_detail_info);

        mContext = this;

        findViews();

        mProgress = new CustomProgressDialog(mContext);


    }

    /**
     * 绑定控件
     */
    private void findViews() {
        img_back = (ImageView) findViewById(R.id.img_back);
        recommend_contact_uid_edittext = (EditText) findViewById(R.id.recommend_contact_uid_edittext);//联系人
        recommend_phone_edittext = (EditText) findViewById(R.id.recommend_phone_edittext);//联系电话
        recommend_location_layout = (RelativeLayout) findViewById(R.id.recommend_location_layout);//定位布局
        recommend_location_text = (TextView) findViewById(R.id.recommend_location_text);//定位后信息显示
        recommend_shop_address_edittext = (EditText) findViewById(R.id.recommend_shop_address_edittext);//店铺地址
        recommend_shop_name_edittext = (EditText) findViewById(R.id.recommend_shop_name_edittext);//名字
        recommend_btn_next_step = (Button) findViewById(R.id.recommend_btn_next_step);//下一步


        //设置监听器
        img_back.setOnClickListener(this);
        recommend_location_layout.setOnClickListener(this);
        recommend_btn_next_step.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.recommend_location_layout:
                location();
                break;
            case R.id.recommend_btn_next_step:
                nextStep();
                break;
        }
    }

    /**
     * 保存,提交, 进入下一步
     */
    private void nextStep()
    {
        getInputInfo();


        if (isLocationSuccess == false)
        {
            toastMgr.builder.display("您没有定位,不可以上传信息!", 1);
            return;
        }
        if (mContactName == "" || mContactName.equals("") || mContactName == null)
        {
            toastMgr.builder.display("您没有填写联系人!", 1);
            return;
        }
        if (mContactPhone == "" || mContactPhone.equals("") || mContactPhone == null)
        {
            toastMgr.builder.display("您未填写联系人电话!", 1);
            return;
        }
        if (mAddress.equals(""))
        {
            toastMgr.builder.display("您未填地址!", 1);
            return;
        }

        if (shopName.equals(""))
        {
            toastMgr.builder.display("您未填店名!", 1);
            return;
        }

        mProgress = mProgress.show(mContext, "正在上传信息...", false, null);


        HttpReqRecommendDetailInfo putDetailInfo = new HttpReqRecommendDetailInfo();
        putDetailInfo.setListener(new PutInfo());
        putDetailInfo.putRecommendShopInfo(mUserid, mContactName, mContactPhone, mLongitude, mLatitude, mProvince, mCity, mDistrict, mAddress, shopName);
    }

    class PutInfo implements HttpReqCallback
    {

        @Override
        public void onFail(int errorCode, String message) {
            mProgress.dismiss();
            if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
            {
                UpdateApp.gotoUpdateApp(mContext);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
            {
                toastMgr.builder.display("网络错误", 1);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
            {
                NotLogin.gotoLogin(DiscoverAddPartnerDetailInfoActivity.this);
            }
            else
            {
                toastMgr.builder.display(message, 1);
            }
        }

        @Override
        public void onSuccess(Object object) {
            mProgress.dismiss();
            Json2RecommendShopDetailInfoBean shopDetailInfoBean = (Json2RecommendShopDetailInfoBean) object;

            toastMgr.builder.display("恭喜您, 上传成功, 请进入下一步", 1);
        }
    }


    String mUserid;
    String mContactName = "";
    String mContactPhone = "";
    double mLongitude ;
    double mLatitude;
    String mProvince;
    String mCity;
    String mDistrict;
    String mAddress = "";
    String mRecommendedId;
    String shopName = "";
    private void getInputInfo()
    {
        mUserid = Configs.getLoginedInfo(mContext).getUserid();
        mRecommendedId = mUserid;
        mContactName = recommend_contact_uid_edittext.getText().toString();
        mContactPhone = recommend_phone_edittext.getText().toString();
        shopName = recommend_shop_name_edittext.getText().toString();
        mAddress = recommend_shop_address_edittext.getText().toString();
    }



    private boolean isLocationSuccess = false;
    /**
     * 点击定位
     */
    private void location()
    {
        mProgress = mProgress.show(mContext, "正在获取定位信息...", false, null);
        GetLocationData locationData = new GetLocationData(mContext);
        locationData.setCallback(new GetLocationData.LocationCallback() {
            @Override
            public void onLocationFail(String message) {
                toastMgr.builder.display(message, 1);
                recommend_location_text.setText("自动定位失败");
                isLocationSuccess = false;
                mProgress.dismiss();
            }

            @Override
            public void onLocationSuccess(double lon, double lat, String province, String city, String district) {
                mProgress.dismiss();
                toastMgr.builder.display("定位成功", 1);
                isLocationSuccess = true;
                mLongitude = lon;
                mLatitude = lat;
                mProvince = province;
                mCity = city;
                mDistrict = district;
                recommend_location_text.setText(mProvince + "-" + mCity + "-" + mDistrict);
            }
        });
        locationData.startLocation();
    }








}
