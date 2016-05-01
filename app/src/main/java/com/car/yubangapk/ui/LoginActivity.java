package com.car.yubangapk.ui;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.json.formatJson.Json2Login;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

import okhttp3.Call;

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
    private EditText edit_psw;//密码

    private ImageView img_back;

    private int phoneNumLength;

    private CustomProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        mContext = this;

        findViews();

        mProgressDialog = new CustomProgressDialog(mContext);


        
    }

    private void findViews() {
        login_checkbox      = (CheckBox) findViewById(R.id.login_checkbox);
        tv_remember_pwd      = (TextView) findViewById(R.id.tv_remember_pwd);
        tv_forget_psw       = (TextView) findViewById(R.id.tv_forget_psw);
        btn_login           = (Button) findViewById(R.id.btn_login);
        tv_quick_sign_up    = (TextView) findViewById(R.id.tv_quick_sign_up);//
        edit_uid            = (EditText) findViewById(R.id.forget_pwd_photo_edit);//用户名  手机号
        edit_psw            = (EditText) findViewById(R.id.forge_pwd_edit_pwd);//密码

        img_back            = (ImageView) findViewById(R.id.img_back);//返回

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
                login();
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
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 登陆处理
     */
    private void login() {

        mProgressDialog = mProgressDialog.show(mContext, "正在登陆中...", false, null);

        String phone = edit_uid.getText().toString().trim();
        String pwd = edit_psw.getText().toString().trim();
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS+Configs.IP_ADDRESSS_ACTION_LOGIN)
                .addParams("userReq.phoneNum", phone)
                .addParams("userReq.passWorld", pwd)
                .build().execute(new LoginCallback());


        L.d(TAG, "验证验证码url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_LOGIN
                        + "?userReq.phoneNum=" + phone
                        + "&userReq.passWorld=" + pwd
        );

    }


    class LoginCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("登陆失败, 网络错误", 1);
            mProgressDialog.dismiss();
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "登陆返回json = " + response);
            Json2Login json2Login = new Json2Login(response);
            mProgressDialog.dismiss();
            Json2LoginBean json2LoginBean = json2Login.getLoginObj();
            if (json2LoginBean == null)
            {
                toastMgr.builder.display("服务器返回错误", 1);
                return;
            }
            //{"userid":"","carType":"","name":"住福利","status":"5","isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS","message":"登陆成功"}



            final String userid = json2LoginBean.getUserid();
            boolean isJson = json2LoginBean.isJson();
            boolean isReturnStr = json2LoginBean.isReturnStr();
            int returnCode = json2LoginBean.getReturnCode();
            String returneMsg = json2LoginBean.getReturneMsg();
            String  message = json2LoginBean.getMessage();
            String carType = json2LoginBean.getCarType();
            String name = json2LoginBean.getName();
            String status = json2LoginBean.getStatus();

            //将登录信息保存起来  SP里面
            Configs.putLoginedInfo(mContext, json2LoginBean);

            if (returnCode == 0)//成功
            {
                if ("0".equals(status))
                {
                    //用户未审核处在正在审核
                    toastMgr.builder.display("您正在审核中,请等待审核...",1);
                    //状态为已经登陆
                    SPUtils.putUserInfo(mContext, Configs.LoginOrNot, Configs.LOGINED);
                    return;
                }
                else if ("1".equals(status))
                {
                    //审核通过 可以登陆
                    toastMgr.builder.display(json2LoginBean.getMessage(), 1);
                    //设置已经登录
                    SPUtils.putUserInfo(mContext, Configs.LoginOrNot, Configs.LOGINED);
                    //


                    //已登录  判断是不是添加了车型
                    AlertDialog alertDialog = new AlertDialog(mContext);
                    if (carType == null || carType.equals("")) {
                        //没有 登录
                        alertDialog.builder().setTitle("提示")
                                .setMsg("您还未添加车型,请添加!")
                                .setPositiveButton("去添加", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent();
                                        intent.setClass(mContext, RegisterDetailsActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("userid", userid);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                })
                                .show();

                    }

                    //这里是直接我的界面   onnew Intent里面去判断
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("otherActivity","login");
                    bundle.putString("userid", userid);//根据这个区请求网络
                    intent.putExtras(bundle);
                    startActivity(intent);
                    SPUtils.putUserInfo(mContext, Configs.LoginOrNot, Configs.LOGINED);
                    finish();
                }
                else if ("2".equals(status))
                {
                    //审核不通过  联系客服
                    toastMgr.builder.display("审核不通过, 请联系客服!", 1);
                    AlertDialog alertDialog = new AlertDialog(mContext);
                    alertDialog.builder().setCancelable(false)
                            .setTitle("警告")
                            .setMsg("您审核未通过,请联系客服!")
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //TODO
                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //TODO 联系客服界面
                                }
                            })
                            .show();

                }
                else if ("3".equals(status))
                {
                    //已经忽略
                    toastMgr.builder.display("已经忽略, 请联系客服!", 1);
                    AlertDialog alertDialog = new AlertDialog(mContext);
                    alertDialog.builder().setCancelable(false)
                            .setTitle("警告")
                            .setMsg("您审核未通过,请联系客服!")
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //TODO
                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //TODO 联系客服界面
                                }
                            })
                            .show();
                }
                else if ("4".equals(status))
                {
                    //已经屏蔽
                    toastMgr.builder.display("已经屏蔽, 请联系客服!", 1);
                    AlertDialog alertDialog = new AlertDialog(mContext);
                    alertDialog.builder().setCancelable(false)
                            .setTitle("警告")
                            .setMsg("您审核未通过,请联系客服!")
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //TODO
                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //TODO 联系客服界面
                                }
                            })
                            .show();
                }
                else if ("5".equals(status))
                {
                    //审核不通过, 不可以登陆 重新上传
                    toastMgr.builder.display("未添加相片",1);
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, RegisterDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userid", userid);//根据这个区请求网络
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }

            }
            else if (returnCode == -2)
            {
                //审核不通过, 不可以登陆 重新上传
                toastMgr.builder.display("您还没注册", 1);


                AlertDialog alertDialog = new AlertDialog(mContext);
                alertDialog.builder().setCancelable(false)
                        .setTitle("提示")
                        .setMsg("您还未注册,请注册!")
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //TODO
                            }
                        })
                        .setPositiveButton("注册", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this, RegisterActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();

            }
            else
            {
                toastMgr.builder.display(json2LoginBean.getMessage(), 1);
            }

        }
    }


    /**
     * 监听输入的手机号是不是完成了
     */
    private TextWatcher uidWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            phoneNumLength = s.toString().length();
            //发送验证码按钮设置成enabled
            if (phoneNumLength >= 11)
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
