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
import android.widget.Button;
import android.widget.EditText;

import com.tec.android.R;
import com.tec.android.network.GetVerifyCodeReqHttp;
import com.tec.android.network.SendSmsReqHttp;
import com.tec.android.utils.TimerCount;
import com.tec.android.view.CustomProgressDialog;

/**
 * 输入手机验证码 不输入手机号   只有验证码  文档38页
 */
public class InputMobileVerificationCodeActivity extends BaseActivity implements View.OnClickListener{

    private static final int GET_CODE_SUCCESS = 0x01;
    private static final int GET_CODE_FAIL = 0x101;
    private Context mContext;
    private static final String TAG = "InputMobileVerificationCodeActivity";

    private EditText input_mobile_phone_dynamic_code_edittext;      //输入验证码的text
    private Button   input_mobile_phone_get_dynamic_code_button;    //点击获取验证码
    private Button   input_mobile_phone_dynamic_commit_button;      //提交验证码信息给后台

    private int codeLength = 0;

    private CustomProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_input_mobile_verification_code);
        mContext = this;
        mProgress = new CustomProgressDialog(mContext);
        findViews();

        //一进来肯定就保证了手机号码是拿到的

    }

    /**
     * 初始化控件
     */
    private void findViews() {
        input_mobile_phone_dynamic_code_edittext = (EditText) findViewById(R.id.input_mobile_phone_dynamic_code_edittext);
        input_mobile_phone_get_dynamic_code_button = (Button) findViewById(R.id.input_mobile_phone_get_dynamic_code_button);
        input_mobile_phone_dynamic_commit_button = (Button) findViewById(R.id.input_mobile_phone_dynamic_commit_button);

        //设置监听器
        input_mobile_phone_get_dynamic_code_button.setOnClickListener(this);
        input_mobile_phone_dynamic_commit_button.setOnClickListener(this);
        input_mobile_phone_dynamic_code_edittext.setOnClickListener(this);

    }


    private void sendSms(String phone)
    {
        SendSmsReqHttp sendSmsReqHttp = new SendSmsReqHttp(mContext);
        sendSmsReqHttp.sendAndSendSmsReqJson(new SendSmsReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj =result;
                sendSmsHandler.sendMessage(msg);
            }
        }, new SendSmsReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                Message msg = new Message();
                msg.what = 101;
                msg.obj =resultMsg;
                sendSmsHandler.sendMessage(msg);
            }
        }, phone);
    }


    /**
     * handler
     */
    private Handler sendSmsHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                //成功根据已经绑定的手机号拿到验证码
                case GET_CODE_SUCCESS:
                    break;
                //失败  根据已经绑定的手机号拿到验证码 失败
                case GET_CODE_FAIL:
                    break;
                default:
                    break;
            }
        }
    };




    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //输入验证码的输入框
            case R.id.input_mobile_phone_dynamic_code_edittext:
                //设置输入法为纯数字
                input_mobile_phone_dynamic_code_edittext.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            //获取验证码点击  点完了之后提示60秒内不能再点
            case R.id.input_mobile_phone_get_dynamic_code_button:
                //一些业务流程

                toGetCode("");
                break;
            //提交验证码点击  一开始是灰色  只有输完验证码才是红色
            case R.id.input_mobile_phone_dynamic_commit_button:
                //一些业务流程
                break;
            default:
                break;
        }
    }


    /**
     * 点击按钮   获得验证码
     * @param phoneNum1
     */
    private void toGetCode(String phoneNum1) {
        mProgress = mProgress.show(mContext, "", false, null);
        TimerCount timerCount = new TimerCount(60000, 1000, input_mobile_phone_get_dynamic_code_button);
        timerCount.start();
        //联网  去拿到验证码
        final Message msg = new Message();

        SendSmsReqHttp sendSmsReqHttp = new SendSmsReqHttp(mContext);
        sendSmsReqHttp.sendAndSendSmsReqJson(new SendSmsReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                msg.what = GET_CODE_SUCCESS;
                msg.obj = result;
                sendSmsHandler.sendMessage(msg);
            }
        }, new SendSmsReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                msg.what = GET_CODE_FAIL;
                msg.obj = resultMsg;
                sendSmsHandler.sendMessage(msg);
            }
        }, phoneNum1);
    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    /**
     * 监听输入的验证码是不是正确 格式
     */
    private TextWatcher watchCode = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            codeLength = s.toString().length();
            if (codeLength == 6)
                input_mobile_phone_dynamic_commit_button.setEnabled(true);
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
