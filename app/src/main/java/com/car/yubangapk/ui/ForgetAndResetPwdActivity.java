package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.car.yubangapk.view.AlertDialog;
import com.andy.android.yubang.R;

/**
 * ForgetAndResetPwdActivity: 忘记密码  但是要重置密码
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-25
 */
public class ForgetAndResetPwdActivity extends AppCompatActivity {

    private Context mContext;
    private ImageView img_back;//返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_and_reset_pwd);

        mContext = this;
        findViews();


    }

    private void findViews() {
        //返回按钮
        img_back = (ImageView) findViewById(R.id.img_back);
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

}
