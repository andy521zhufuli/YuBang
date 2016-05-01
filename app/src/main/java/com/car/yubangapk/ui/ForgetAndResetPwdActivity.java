package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.Base;
import com.car.yubangapk.network.myHttpReq.alterUserInfo.HttpReqAlterUserInfo;
import com.car.yubangapk.utils.TimerCount;
import com.car.yubangapk.view.AlertDialog;
import com.andy.android.yubang.R;

/**
 * ForgetAndResetPwdActivity: 忘记密码  但是要重置密码
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-25
 */
public class ForgetAndResetPwdActivity extends BaseActivity {

    private static final String TAG = ForgetAndResetPwdActivity.class.getSimpleName();

    private Context mContext;
    private ImageView       img_back;//返回
    private EditText        forget_pwd_photo_edit;
    private EditText        forge_pwd_edit_pwd;
    private EditText        forget_pwd_verify_code_edittext;
    private Button          forget_pwd_btn_send_verify_code;
    private Button          forget_pwd_btn_reset_pwd;



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
    }

}
