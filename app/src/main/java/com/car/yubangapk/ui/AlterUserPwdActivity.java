package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.GetVerifyCodeBean;
import com.car.yubangapk.network.myHttpReq.alterUserInfo.HttpReqAlterUserInfo;
import com.car.yubangapk.network.myHttpReq.alterUserInfo.HttpReqAlterUserInfoCallback;
import com.car.yubangapk.utils.TimerCount;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;

public class AlterUserPwdActivity extends BaseActivity implements View.OnClickListener{


    static final String TAG = AlterUserPwdActivity.class.getSimpleName();
    Context             mContext;

    ImageView           img_back;
    EditText            alter_pwd_old_pwd_edittext;
    EditText            alter_pwd_new_pwd_edittext;
    EditText            alter_pwd_confirm_new_pwd_edittext;
    EditText            alter_pwd_verify_code_edittext;
    Button              alter_pwd_btn_send_verify_code;
    Button              alter_pwd_btn_alter;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alter_user_pwd);

        mContext = this;

        findViews();

    }

    /**
     * 绑定控件
     */
    private void findViews() {


        img_back = (ImageView) findViewById(R.id.img_back);
        alter_pwd_old_pwd_edittext              = (EditText) findViewById(R.id.alter_pwd_old_pwd_edittext);
        alter_pwd_new_pwd_edittext              = (EditText) findViewById(R.id.alter_pwd_new_pwd_edittext);
        alter_pwd_confirm_new_pwd_edittext      = (EditText) findViewById(R.id.alter_pwd_confirm_new_pwd_edittext);
        alter_pwd_verify_code_edittext          = (EditText) findViewById(R.id.alter_pwd_verify_code_edittext);

        alter_pwd_btn_send_verify_code = (Button) findViewById(R.id.alter_pwd_btn_send_verify_code);

        alter_pwd_btn_alter = (Button) findViewById(R.id.alter_pwd_btn_alter);


        img_back.setOnClickListener(this);
        alter_pwd_btn_send_verify_code.setOnClickListener(this);
        alter_pwd_btn_alter.setOnClickListener(this);

    }



    private void getVerifyCode()
    {
        TimerCount timerCount = new TimerCount(60000, 1000, alter_pwd_btn_send_verify_code);
        timerCount.start();
        String userid = Configs.getLoginedInfo(mContext).getUserid();
        HttpReqAlterUserInfo alterUserInfo = new HttpReqAlterUserInfo();
        alterUserInfo.setCallback(new GetUserInfoCallback());
        actionType = 1;
        alterUserInfo.alterUserPwd(userid, null, null, null, null, null, "1");
    }

    class GetUserInfoCallback implements HttpReqAlterUserInfoCallback
    {

        @Override
        public void onFail(int errorCode, String message) {

        }

        @Override
        public void onSuccess(Object object, int type) {
            if (actionType == 0)
            {
                GetVerifyCodeBean bean = (GetVerifyCodeBean) object;
                toastMgr.builder.display(bean.getMessage(), 1);
                //密码已经修改成功, 请重新登
                reLoadingAfterAlterPwd();
            }
            else if (actionType == 1)
            {
                toastMgr.builder.display("验证码发送成功", 1);
                alter_pwd_btn_alter.setEnabled(true);
            }

        }
    }

    /**
     * 修改密码之后  重新登录
     */
    private void reLoadingAfterAlterPwd() {
        AlertDialog alertDialog = new AlertDialog(mContext);
        alertDialog.builder().setTitle("重新登录")
                .setMsg("修改密码成功,请重新登录")
                .setPositiveButton("登录", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.alter_pwd_btn_send_verify_code:
                getVerifyCode();
                break;
            case R.id.alter_pwd_btn_alter:
                alterPwd();
                break;
        }
    }





    private void alterPwd() {
        String oldPwd = alter_pwd_old_pwd_edittext.getText().toString();
        String newPwd = alter_pwd_new_pwd_edittext.getText().toString();
        String confirmNewPwd = alter_pwd_confirm_new_pwd_edittext.getText().toString();
        String verfifyCode = alter_pwd_verify_code_edittext.getText().toString();

        if (oldPwd.equals("") || newPwd.equals("") || confirmNewPwd.equals("") || verfifyCode.equals(""))
        {
            toastMgr.builder.display("请完善信息再修改密码",1);
            return;
        }
        if (!newPwd.equals(confirmNewPwd))
        {
            toastMgr.builder.display("两次输入密码不一致, 请重新输入",1);
            return;
        }

        String userid = Configs.getLoginedInfo(mContext).getUserid();
        HttpReqAlterUserInfo alterUserInfo = new HttpReqAlterUserInfo();
        alterUserInfo.setCallback(new GetUserInfoCallback());
        actionType = 0;
        alterUserInfo.alterUserPwd(userid, null, null, newPwd, oldPwd, verfifyCode, "0");

    }


    private int actionType = -1;  //0 为修改密码,  1为获取验证码
}
