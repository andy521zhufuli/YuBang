package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.AddressBean;
import com.car.yubangapk.json.bean.Json2AddressBean;
import com.car.yubangapk.network.myHttp.HttpReqAddress;
import com.car.yubangapk.network.myHttp.httpReqInterface;
import com.car.yubangapk.utils.String2UTF8;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.Warn.UpdateApp;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

/**
 * 添加收货人信息
 *
 * 16/04/16
 */
public class ShoppingmallAddReceivePeopleInfoActivity extends BaseActivity implements OnClickListener {


    private static final String TAG = ShoppingmallAddReceivePeopleInfoActivity.class.getSimpleName();
    private Context mContext;
    private ImageView       img_back;
    private TextView        title_name;//标题名字
    private LinearLayout    ll_address_tishi;//顶部没有收货人提示信息
    private EditText        edit_address_name;//姓名
    private EditText        edit_address_phone;//姓名

    private LinearLayout    ll_address_location;//所在区域
    private TextView        tv_address_location;//选择的区域 显示在tv上
    private EditText        edit_address_address;//详细收货地址
    private LinearLayout    ll_check_address_default;//选择默认收货地址
    private CheckBox        checkbox_select;//默认收货地址 选择
    private Button          btn_save;//保存

    private AddressBean     mAddressBean;
    private String          mFrom;

    private String mName;
    private String mPHone;

    private CustomProgressDialog mProgress ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shoppingmall_add_receive_people_info);

        mContext = this;

        findViews();

        mProgress = new CustomProgressDialog(mContext);

        AddressBean address;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
             mFrom = bundle.getString(ShoppingmallChooseReceiveAddressActivity.JUMP_METHOD);
            if ("modify".equals(mFrom))
            {
                title_name.setText("修改收货人信息");
                address = (AddressBean) bundle.getSerializable("address");
                mAddressBean = address;
                //给界面设置姓名个电话
                edit_address_name.setText(address.getName());
                edit_address_phone.setText(address.getPhone());
                ll_address_tishi.setVisibility(View.GONE);
            }
            else if ("add".equals(mFrom))
            {
                //do nothing
                title_name.setText("添加收货人信息");
                ll_address_tishi.setVisibility(View.VISIBLE);
            }

        }


    }

    /**
     * 绑定控件
     */
    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);

        title_name = (TextView) findViewById(R.id.title_name);


        ll_address_tishi = (LinearLayout) findViewById(R.id.ll_address_tishi);//顶部没有收货人提示信息

        edit_address_name = (EditText) findViewById(R.id.edit_address_name);//姓名

        edit_address_phone = (EditText) findViewById(R.id.edit_address_phone);//电话

        ll_address_location = (LinearLayout) findViewById(R.id.ll_address_location);//所在区域

        tv_address_location = (TextView) findViewById(R.id.tv_address_location);//选择的区域 显示在tv上

        edit_address_address = (EditText) findViewById(R.id.edit_address_address);//详细收货地址

        ll_check_address_default = (LinearLayout) findViewById(R.id.ll_check_address_default);//选择默认收货地址

        checkbox_select = (CheckBox) findViewById(R.id.checkbox_select);//默认收货地址 选择

        btn_save = (Button) findViewById(R.id.btn_save);//保存


        img_back.setOnClickListener(this);

        btn_save.setOnClickListener(this);//保存


    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                setResult(Activity.RESULT_CANCELED, null);
                finish();
                break;
            case R.id.btn_save:
                //保存
                saveAddress();
                break;
        }
    }



    private void saveAddress()
    {

        mProgress = mProgress.show(mContext, "正在保存", false, null);

        getNameAndPhone();

        if (mName.equals("") || mPHone.equals("")) {
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder().setTitle("提示")
                    .setCancelable(true)
                    .setMsg("信息输入不完整")
                    .setPositiveButton("重新输入", new OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();
            return;
        }

        if ("modify".equals(mFrom))
        {
            mName = String2UTF8.getUTF8String(mName);
            mPHone = String2UTF8.getUTF8String(mPHone);

            HttpReqAddress httpReqAddress = new HttpReqAddress(mAddressBean.getCUserid(),"1", mName, mPHone, mAddressBean.getId() );
            httpReqAddress.setCallback(new httpReqInterface() {
                @Override
                public void onGetAddressSucces(Json2AddressBean addressBean) {
                    mProgress.dismiss();
                    setResult(Activity.RESULT_OK, null);
                    finish();
                }

                @Override
                public void onGetAddressFail(int errorCode) {
                    mProgress.dismiss();
                    if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
                    {
                        UpdateApp.gotoUpdateApp(mContext);
                    }
                    else if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
                    {
                        toastMgr.builder.display("网络错误" ,1);
                    }
                    else if (errorCode == ErrorCodes.ERROR_CODE_NO_ADDRESS)
                    {
                        toastMgr.builder.display("没有收货信息信息" ,1);
                    }
                    else if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
                    {
                        toastMgr.builder.display("没有登录" ,1);
                        NotLogin.gotoLogin(ShoppingmallAddReceivePeopleInfoActivity.this);
                    }
                    else if (errorCode == ErrorCodes.ERROR_CODE_SERVER)
                    {
                        toastMgr.builder.display("服务器错误" ,1);
                    }


                }
            });
            httpReqAddress.modifyAddress();
        }
        else
        {
            mName = String2UTF8.getUTF8String(mName);
            mPHone = String2UTF8.getUTF8String(mPHone);

            String userid = Configs.getLoginedInfo(mContext).getUserid();

            HttpReqAddress httpReqAddress = new HttpReqAddress(userid,"0", mName, mPHone, null );
            httpReqAddress.setCallback(new httpReqInterface() {
                @Override
                public void onGetAddressSucces(Json2AddressBean addressBean) {
                    mProgress.dismiss();
                    setResult(Activity.RESULT_OK, null);
                    finish();
                }

                @Override
                public void onGetAddressFail(int errorCode) {
                    mProgress.dismiss();
                    if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
                    {
                        UpdateApp.gotoUpdateApp(mContext);
                    }
                    else if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
                    {
                        toastMgr.builder.display("网络错误" ,1);
                    }
                    else if (errorCode == ErrorCodes.ERROR_CODE_NO_ADDRESS)
                    {
                        toastMgr.builder.display("没有收货信息信息" ,1);
                    }
                    else if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
                    {
                        toastMgr.builder.display("没有登录" ,1);
                        NotLogin.gotoLogin(ShoppingmallAddReceivePeopleInfoActivity.this);
                    }
                    else if (errorCode == ErrorCodes.ERROR_CODE_SERVER)
                    {
                        toastMgr.builder.display("服务器错误" ,1);
                    }
                    else if (errorCode == ErrorCodes.ERROR_CODE_SERVER_ERROR)
                    {
                        toastMgr.builder.display("服务器错误" ,1);
                    }


                }
            });
            httpReqAddress.addAddress();
        }



    }


    private void getNameAndPhone()
    {
        String name = edit_address_name.getText().toString().trim();

        String phone = edit_address_phone.getText().toString().trim();


        mName = name;
        mPHone= phone;



    }




}
