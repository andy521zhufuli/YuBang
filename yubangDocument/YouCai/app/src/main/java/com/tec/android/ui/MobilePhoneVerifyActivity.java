package com.tec.android.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethod;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.sales.vo.GetVerifyCodeResp;
import com.sales.vo.SendSmsResp;
import com.sales.vo.VerifySmsResp;
import com.tec.android.R;
import com.tec.android.network.GetVerifyCodeReqHttp;
import com.tec.android.network.SendSmsReqHttp;
import com.tec.android.network.VerifySmsReqHttp;
import com.tec.android.utils.TimerCount;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.CustomProgressDialog;

/**
 * 手机短信验证  个人中心  我的资料 绑定手机号码
 * 绑定手机界面  绑定好了要把数据写回去啊  别忘了
 */
public class MobilePhoneVerifyActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;

    private EditText    mobile_phone_dynamic_phone;             //输入手机号码
    private Button      mobile_phone_dynamic_get_dynamic_pswd;  //点击获取验证码
    private EditText    mobile_phone_dynamic_input_pswd;        //输入验证码
    private Button      mobile_phone_verify;                    //点击验证




    private final int GET_CODE_SUCCESS = 0X01;
    private final int GET_CODE_FAIL = 0X10;
    private final int VERIFY_CODE_SUCCESS = 0X02;
    private final int VERIFY_CODE_FAIL = 0X20;

    private int phoneLength = 0;
    private int codeLength = 0;



    private CustomProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mobile_phone_verify);

        mContext = this;
        //初始化控件
        findVies();


        mProgress = new CustomProgressDialog(mContext);
        //从哪里过来的
        String action = getIntent().getStringExtra("action");
        if (action.equals("bindPhone"))
        {
            //绑定手机
            if (getIntent().getStringExtra("from").equals("LoginActivity"))
            {
                //从登陆界面过来

            }
            else if (getIntent().getStringExtra("from").equals("MyInfoActivity"))
            {
                //从我的个人信息界面过来
            }
        }
        else if (action.equals("changePhone"))//不可能的 因为是另一个界面 算了 不该了
        {
            //更换手机
        }




    }

    /**
     * 初始化控件
     */
    private void findVies() {
        //输入的手机号码
        mobile_phone_dynamic_phone = (EditText) findViewById(R.id.mobile_phone_dynamic_phone);

        mobile_phone_dynamic_get_dynamic_pswd = (Button) findViewById(R.id.mobile_phone_dynamic_get_dynamic_pswd);
        //输入的验证码
        mobile_phone_dynamic_input_pswd = (EditText) findViewById(R.id.mobile_phone_dynamic_input_pswd);
        mobile_phone_verify = (Button) findViewById(R.id.mobile_phone_verify);
        mobile_phone_dynamic_get_dynamic_pswd.setEnabled(true);
        //设置监听器
        mobile_phone_dynamic_phone.setOnClickListener(this);
        mobile_phone_dynamic_get_dynamic_pswd.setOnClickListener(this);
        mobile_phone_dynamic_input_pswd.setOnClickListener(this);
        mobile_phone_verify.setOnClickListener(this);

        //监听电话号码变化
        mobile_phone_dynamic_phone.addTextChangedListener(watchPhoneNum);
        //监听输入的验证码
        mobile_phone_dynamic_input_pswd.addTextChangedListener(watchCode);

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //输入手机号  检测一旦输入正确  就把后面的发送验证码按钮颜色设置成可点击的
            case R.id.mobile_phone_dynamic_phone:
                mobile_phone_dynamic_input_pswd.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            //点击  发送验证码  细节控制
            case R.id.mobile_phone_dynamic_get_dynamic_pswd:
                String phoneNum1 = mobile_phone_dynamic_phone.getText().toString();
                toGetCode(phoneNum1);



                break;
            //验证码输入框
            case R.id.mobile_phone_dynamic_input_pswd:
                mobile_phone_dynamic_input_pswd.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            //验证按钮  点击就发送请求给后台
            case R.id.mobile_phone_verify:
                String code = mobile_phone_dynamic_input_pswd.getText().toString();
                verifyCode(code);
                break;
            default:
                break;
        }
    }

    /**
     * 验证用户输入的验证码是不是正确
     * @param code 用户输入的验证码
     */
    private void verifyCode(String code) {
        mProgress = mProgress.show(mContext, "", false, null);
        final Message msg = new Message();
        VerifySmsReqHttp verifySmsReqHttp = new VerifySmsReqHttp(mContext);
        verifySmsReqHttp.sendAndVerifySmsReqHttpJson(new VerifySmsReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                msg.what = VERIFY_CODE_SUCCESS;
                msg.obj = result;
                verifyCodeHandler.sendMessage(msg);
            }
        }, new VerifySmsReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                msg.what = VERIFY_CODE_FAIL;
                msg.obj = resultMsg;
                verifyCodeHandler.sendMessage(msg);
            }
        }, code);
    }


    /**
     * 点击按钮   获得验证码
     * @param phoneNum1
     */
    private void toGetCode(String phoneNum1) {
        mProgress = mProgress.show(mContext, "", false, null);
        TimerCount timerCount = new TimerCount(60000, 1000, mobile_phone_dynamic_get_dynamic_pswd);
        timerCount.start();
        //联网  去拿到验证码
        final Message msg = new Message();

//        SendSmsReqHttp sendSmsReqHttp = new SendSmsReqHttp(mContext);
//        sendSmsReqHttp.sendAndSendSmsReqJson(new SendSmsReqHttp.DoingSuccessCallback() {
//            @Override
//            public void onSuccess(String result) {
//                msg.what = GET_CODE_SUCCESS;
//                msg.obj = result;
//                verifyCodeHandler.sendMessage(msg);
//            }
//        }, new SendSmsReqHttp.DoingFailedCallback() {
//            @Override
//            public void onFail(String resultMsg) {
//                msg.what = GET_CODE_FAIL;
//                msg.obj = resultMsg;
//                verifyCodeHandler.sendMessage(msg);
//            }
//        }, phoneNum1);
        GetVerifyCodeReqHttp getVerifyCodeReqHttp = new GetVerifyCodeReqHttp(mContext);
        getVerifyCodeReqHttp.sendAndGetVerifyCodeReqJson(new GetVerifyCodeReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                msg.what = GET_CODE_SUCCESS;
                msg.obj = result;
                verifyCodeHandler.sendMessage(msg);
            }
        }, new GetVerifyCodeReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                msg.what = GET_CODE_FAIL;
                msg.obj = resultMsg;
                verifyCodeHandler.sendMessage(msg);
            }
        }, phoneNum1);
    }


    /**
     * 网络操作跟UI交互
     */
    private Handler verifyCodeHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                //获取验证码成功
                case GET_CODE_SUCCESS:
                    String json = (String) msg.obj;
                    SendSmsResp sendSmsResp = new Gson().fromJson(json, SendSmsResp.class);
                    sendSmsResp.getReturncode();
                    mProgress.dismiss();
                    break;
                //获取验证码失败
                case GET_CODE_FAIL:
                    mProgress.dismiss();
                    break;
                //验证获取的验证码成功
                case VERIFY_CODE_SUCCESS:
                    String resultJson  = (String) msg.obj;
                    VerifySmsResp verifySmsResp = new  Gson().fromJson(resultJson, VerifySmsResp.class);
                    int returnCode = verifySmsResp.getReturncode();
                    if (returnCode == 0)
                    {
                        toastMgr.builder.display("验证成功", 0);
                    }
                    else{
                        toastMgr.builder.display("验证失败", 0);
                    }
                    mProgress.dismiss();
                    break;
                //验证获取的验证码失败
                case VERIFY_CODE_FAIL:
                    mProgress.dismiss();
                    break;
            }
        }
    };


    /**
     * 监听输入的电话号码是不是正确
     */
    private TextWatcher watchPhoneNum = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            phoneLength = s.toString().length();
            if (phoneLength == 11)
            {
                mobile_phone_dynamic_get_dynamic_pswd.setEnabled(true);
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
     * 监听输入的验证码是不是正确 格式
     */
    private TextWatcher watchCode = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            codeLength = s.toString().length();
            if (codeLength == 6)
                mobile_phone_verify.setEnabled(true);
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
