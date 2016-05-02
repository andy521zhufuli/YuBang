package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.Base;
import com.car.yubangapk.network.myHttpReq.alterUserInfo.HttpReqAlterUserInfo;
import com.car.yubangapk.network.myHttpReq.alterUserInfo.HttpReqAlterUserInfoCallback;
import com.car.yubangapk.network.myHttpReq.alterUserInfo.HttpReqForgetPwd;
import com.car.yubangapk.utils.TimerCount;
import com.car.yubangapk.utils.Validator;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.andy.android.yubang.R;

/**
 * ForgetAndResetPwdActivity: 忘记密码  但是要重置密码
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-25
 */
public class ForgetAndResetPwdActivity extends BaseActivity implements HttpReqAlterUserInfoCallback{

    private static final String TAG = ForgetAndResetPwdActivity.class.getSimpleName();

    private Context mContext;
    private ImageView       img_back;//返回
    private EditText        forget_pwd_photo_edit;
    private EditText        forge_pwd_edit_pwd;
    private EditText        forget_pwd_verify_code_edittext;
    private Button          forget_pwd_btn_send_verify_code;
    private Button          forget_pwd_btn_reset_pwd;

    HttpReqForgetPwd forgetPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forget_and_reset_pwd);

        mContext = this;
        findViews();

    }

    private void findViews() {
        //返回按钮
        img_back = (ImageView) findViewById(R.id.img_back);
        forget_pwd_photo_edit = (EditText) findViewById(R.id.forget_pwd_photo_edit);
        forge_pwd_edit_pwd = (EditText) findViewById(R.id.forge_pwd_edit_pwd);
        forget_pwd_verify_code_edittext = (EditText) findViewById(R.id.forget_pwd_verify_code_edittext);
        forget_pwd_btn_send_verify_code = (Button) findViewById(R.id.forget_pwd_btn_send_verify_code);
        forget_pwd_btn_reset_pwd = (Button) findViewById(R.id.forget_pwd_btn_reset_pwd);



        forget_pwd_photo_edit.addTextChangedListener(uidWatcher);
        //点击发送验证码
        forget_pwd_btn_send_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVerifyCode();
            }
        });

        //根据得到的验证码  去重置密码
        forget_pwd_btn_reset_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPwd();
            }
        });



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 用户已经重置了密码
     * 提醒用户使用新密码重新登录
     */
    private void resetPwdOkPleaseGotoLogin()
    {
        AlertDialog alertDialog = new AlertDialog(mContext);
        alertDialog.builder()
                .setTitle("密码已经重置")
                .setMsg("请使用新密码重新登录!")
                .setCancelable(false)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //用户同意绑定手机  那就前往绑定手机界面
                        Intent bandingPhoneIntent = new Intent();
                        bandingPhoneIntent.setClass(mContext, LoginActivity.class);
                        //就是告诉这个界面  要绑定手机
//                        bandingPhoneIntent.putExtra("action", "bindPhone");
//                        bandingPhoneIntent.putExtra("from", "LoginActivity");
                        startActivity(bandingPhoneIntent);
                        finish();
                    }
                })
                .show();
    }


    private void getVerifyCode()
    {
        TimerCount timerCount = new TimerCount(60000, 1000, forget_pwd_btn_send_verify_code);
        timerCount.start();

        String phone = forget_pwd_photo_edit.getText().toString().trim();
        if ("".equals(phone) || phone == null)
        {
            toastMgr.builder.display("请输入手机号码", 1);
            return;
        }
        if (!Validator.isMobile(phone))
        {
            toastMgr.builder.display("请输入正确手机号码", 1);
            return;
        }
        forgetPwd = new  HttpReqForgetPwd();
        forgetPwd.setCallback(this);
        forgetPwd.getVerifyCode(phone);

    }


    private void resetPwd()
    {
        if (forgetPwd == null)
        {
            toastMgr.builder.display("请先获取验证码", 1);
            return;
        }
        String phone = forget_pwd_photo_edit.getText().toString().trim();
        String newPwd = forge_pwd_edit_pwd.getText().toString().trim();
        String code  = forget_pwd_verify_code_edittext.getText().toString().trim();
        if (newPwd == null || newPwd.equals(""))
        {
            toastMgr.builder.display("请输入密码", 1);
            return;
        }
        if (code == null || code.equals(""))
        {
            toastMgr.builder.display("请输入验证码", 1);
            return;
        }
        forgetPwd.resetPwd(phone, code, newPwd);

    }

    @Override
    public void onFail(int errorCode, String message) {
        toastMgr.builder.display(message, 1);
    }

    @Override
    public void onSuccess(Object object, int type) {
        //我还要先知道是不是验证码获取成功了
        if (type == 1)
        {
            toastMgr.builder.display("验证码发送成功", 1);
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder().setTitle("提示")
                    .setMsg("修改密码成功请重新登录")
                    .setCancelable(false)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();
        }
    }

    /**
     * 监听输入的手机号是不是完成了
     */
    private TextWatcher uidWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            int phoneNumLength = s.toString().length();
            //发送验证码按钮设置成enabled
            if (phoneNumLength >= 11)
            {
                forget_pwd_btn_send_verify_code.setEnabled(true);
                forget_pwd_btn_reset_pwd.setEnabled(true);
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
