package com.car.yubangapk.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.TimerCount;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.car.yubangapk.view.CustomProgressDialog;

import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * RegisterActivity: 注册界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-25
 */
public class RegisterActivity extends BaseActivity {

    private Context mContext;

    private ImageView img_back;//返回
    private EditText register_phone_num_uid_edittext;//注册本人手机号码
    private EditText register_pwd_edittext;//注册密码
    private EditText register_driver_name_edittext;//司机姓名
    private EditText register_recommend_ren_name_edittext;//司机姓名
    private CheckBox register_btn_check;//我同意
    private TextView register_driver_service_protocol;//车主协议
    private Button   register_btn_sign_up;//注册按钮
    private EditText register_verify_code_edittext;//验证码输入框
    private Button   register_btn_send_verify_code;//发送验证码按钮

    int phoneLength;//电话号码长度
    int verifyCodeLength;//验证码长度

    //进度狂
    private CustomProgressDialog mProgress;
    String mPhone = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        mContext = this;

        mProgress = new CustomProgressDialog(mContext);

        findViews();

    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);//返回

        register_phone_num_uid_edittext = (EditText) findViewById(R.id.register_phone_num_uid_edittext);//注册本人手机号码

        register_pwd_edittext = (EditText) findViewById(R.id.register_pwd_edittext);//注册密码

        register_driver_name_edittext = (EditText) findViewById(R.id.register_driver_name_edittext);//司机姓名

        register_recommend_ren_name_edittext = (EditText) findViewById(R.id.register_recommend_ren_name_edittext);//司机姓名

        register_btn_check = (CheckBox) findViewById(R.id.register_btn_check);//我同意

        register_driver_service_protocol = (TextView) findViewById(R.id.register_driver_service_protocol);//车主协议

        register_btn_sign_up = (Button) findViewById(R.id.register_btn_sign_up);//注册按钮

        register_verify_code_edittext = (EditText) findViewById(R.id.register_verify_code_edittext);//验证码输入框

        register_btn_send_verify_code = (Button) findViewById(R.id.register_btn_send_verify_code);//发送验证码按钮
        /**
         * 设置监听器
         */
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });//返回



        //监听电话号码输入情况
        register_phone_num_uid_edittext.addTextChangedListener(watchPhoneNum);
        //监听验证码输入情况
        register_verify_code_edittext.addTextChangedListener(watchVerifyCode);

        /**
         * 是否同意协议
         */
        register_btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });//我同意
        /**
         * 点击弹出协议
         */
        register_driver_service_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("车主协议", 0);
            }
        });//车主协议
        /**
         * 立即注册  也就是下一步
         */
        register_btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitVerificationCode("+86", mPhone, "");


                toastMgr.builder.display("下一步", 0);
                Intent intent=  new Intent();
                intent.setClass(RegisterActivity.this, RegisterDetailsActivity.class);
                startActivity(intent);
            }
        });//注册按钮
        /**
         * 发送验证码
         */
        register_btn_send_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = register_phone_num_uid_edittext.getText().toString().trim();
                mPhone = phoneNum;
                toGetVerifyCode(phoneNum);
                toastMgr.builder.display("发送验证码", 0);
            }
        });//发送验证码按钮




    }

    /**
     * 提交短信验证码
     */
    private void submitVerificationCode(String country, String phone, String code) {

        mProgress = mProgress.show(mContext, "正在验证...", false, null);

        String inputCode = register_verify_code_edittext.getText().toString().trim();
        code = inputCode;
        SMSSDK.submitVerificationCode(country,phone,code);

    }

    /**
     * 通过mob sdk获取短信验证码
     * @param phoneNum
     */
    private void toGetVerifyCode(String phoneNum) {

        TimerCount timerCount = new TimerCount(60000, 1000, register_btn_send_verify_code);
        timerCount.start();

        mProgress = mProgress.show(mContext, "正在获取验证码...", false, null);

        SMSSDK.initSDK(mContext, Configs.SMS_APPKEY, Configs.SMS_APPSECRET, true);
        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        L.d("RegisterActivity","提交验证码成功" );
                        mProgress.dismiss();
                        //提交验证码成功
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        L.d("RegisterActivity","获取验证码成功" );
                        mProgress.dismiss();
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                        L.d("RegisterActivity","返回支持发送验证码的国家列表" );
                        List<String> contries = (List<String>) data;
                        mProgress.dismiss();

                    }
                }else{
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh);
        SMSSDK.getSupportedCountries();

        SMSSDK.getVerificationCode("+86","18620647064");


    }


    /**
     * 监听输入的电话号码是不是正确
     */
    private TextWatcher watchPhoneNum = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            phoneLength = s.toString().length();
            //发送验证码按钮设置成enabled
            if (phoneLength == 11)
            {
                register_btn_send_verify_code.setEnabled(true);
            }
            else
            {
                register_btn_send_verify_code.setEnabled(false);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };

    /**
     * 监听输入的验证码是不是完成了
     */
    private TextWatcher watchVerifyCode = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            verifyCodeLength = s.toString().length();
            //发送验证码按钮设置成enabled
            if (verifyCodeLength >= 6)
            {
                register_btn_sign_up.setEnabled(true);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };


}
