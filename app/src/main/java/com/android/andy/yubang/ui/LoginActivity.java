package com.android.andy.yubang.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.andy.yubang.utils.toastMgr;
import com.andy.android.yubang.R;

/**
 * LoginActivity: 登陆界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-25
 */
public class LoginActivity extends BaseActivity {

    private Context mContext;
    private final static String TAG = "LoginActivity";

    private CheckBox login_checkbox;
    private TextView tv_remember_pwd;//下次自动登陆
    private TextView tv_forget_psw;//忘记密码
    private Button   btn_login;//登陆
    private TextView tv_quick_sign_up;//立即注册

    private EditText edit_uid;//手机号

    private int phoneNumLength;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        mContext = this;

        findViews();


        
    }

    private void findViews() {
        login_checkbox      = (CheckBox) findViewById(R.id.login_checkbox);
        tv_remember_pwd      = (TextView) findViewById(R.id.tv_remember_pwd);
        tv_forget_psw       = (TextView) findViewById(R.id.tv_forget_psw);
        btn_login           = (Button) findViewById(R.id.btn_login);
        tv_quick_sign_up    = (TextView) findViewById(R.id.tv_quick_sign_up);
        edit_uid            = (EditText) findViewById(R.id.edit_uid);
        /**
         * 设置监听器
         */


        edit_uid.addTextChangedListener(uidWatcher);

        //选择框
        login_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("记住密码", 0);
            }
        });
        //记住密码
        tv_remember_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("记住密码", 0);
            }
        });
        //忘记密码
        tv_forget_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("忘记密码", 0);
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ForgetAndResetPwdActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("登陆成功", 0);
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("otherActivity","login");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //立即注册
        tv_quick_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("立即注册", 0);
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }




    /**
     * 监听输入的验证码是不是完成了
     */
    private TextWatcher uidWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            toastMgr.builder.display("s", 0);
            phoneNumLength = s.toString().length();
            //发送验证码按钮设置成enabled
            if (phoneNumLength > 8)
            {
                btn_login.setEnabled(true);
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
