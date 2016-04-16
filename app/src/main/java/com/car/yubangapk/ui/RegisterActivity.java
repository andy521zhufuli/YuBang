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
import com.car.yubangapk.json.bean.MsgType;
import com.car.yubangapk.json.bean.GetVerifyCodeBean;
import com.car.yubangapk.json.bean.VerifyCodeBean;
import com.car.yubangapk.json.formatJson.VerifyCode;
import com.car.yubangapk.network.okhttp.OkHttpUtils;

import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.TimerCount;
import com.car.yubangapk.utils.Validator;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.car.yubangapk.view.CustomProgressDialog;
import okhttp3.Call;


/**
 * RegisterActivity: 注册界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-25
 */
public class RegisterActivity extends BaseActivity {


    private static final String TAG = "RegisterActivity";
    private Context mContext;
    private static final String SMS_MODEL_TYPE = "1";
    private static final String SMS_SEND = "1";

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

    //
    String phoneNum;//电话号码
    String loginPwd;//登陆密码
    String driverName;//司机姓名
    String recommendPeople;//推荐人姓名
    String verifyCode;//输入的验证码


    private GetVerifyCodeBean mVerifyCodeBean;

    int phoneLength;//电话号码长度
    int verifyCodeLength;//验证码长度

    private boolean isAgreeProtocol = false;

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

        if (isAgreeProtocol ==false)
        {
            register_btn_check.setChecked(false);
        }

    }

    private void findViews() {

        img_back                                = (ImageView) findViewById(R.id.img_back);//返回

        register_phone_num_uid_edittext         = (EditText) findViewById(R.id.register_phone_num_uid_edittext);//注册本人手机号码

        register_pwd_edittext                   = (EditText) findViewById(R.id.register_pwd_edittext);//注册密码

        register_driver_name_edittext           = (EditText) findViewById(R.id.register_driver_name_edittext);//司机姓名

        register_recommend_ren_name_edittext    = (EditText) findViewById(R.id.register_recommend_ren_name_edittext);//司机姓名

        register_btn_check                      = (CheckBox) findViewById(R.id.register_btn_check);//我同意

        register_driver_service_protocol        = (TextView) findViewById(R.id.register_driver_service_protocol);//车主协议

        register_btn_sign_up                    = (Button) findViewById(R.id.register_btn_sign_up);//注册按钮

        register_verify_code_edittext           = (EditText) findViewById(R.id.register_verify_code_edittext);//验证码输入框

        register_btn_send_verify_code           = (Button) findViewById(R.id.register_btn_send_verify_code);//发送验证码按钮
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
                if (register_btn_check.isChecked() == true)
                {
                    isAgreeProtocol = false;
                }
                else
                {
                    isAgreeProtocol = true;
                }
            }
        });//我同意
        /**
         * 点击弹出协议
         */
        register_driver_service_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("车主协议", 0);
                //TODO 车主协议
            }
        });//车主协议
        /**
         * 立即注册  也就是下一步
         */
        register_btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isAgreeProtocol == true)
                {
                    toastMgr.builder.display("请先阅读驭帮车主服务协议,并且同意协议!", 1);
                    return;
                }

                String code = register_verify_code_edittext.getText().toString().trim();
                boolean isCOde = Validator.isVerifyCode(code);
                if (isCOde == false)
                {
                    toastMgr.builder.display("验证码格式不对,请重新输入!", 1);
                    return;
                }

                submitVerificationCode(mPhone, mVerifyCodeBean, code);



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
                boolean isPhone = Validator.isMobile(mPhone);
                if (isPhone == false) {
                    toastMgr.builder.display("请输入正确的手机号码!", 1);
                    return;
                }
                toGetVerifyCode(phoneNum);
                toastMgr.builder.display("发送验证码", 0);
            }
        });//发送验证码按钮




    }


    private void getUserInfo()
    {
        phoneNum = register_phone_num_uid_edittext.getText().toString().trim();;//电话号码
        loginPwd = register_pwd_edittext.getText().toString().trim();//登陆密码
        driverName = register_driver_name_edittext.getText().toString().trim();//司机姓名
        recommendPeople = register_recommend_ren_name_edittext.getText().toString().trim();//推荐人姓名
        verifyCode = register_verify_code_edittext.getText().toString().trim();//输入的验证码
    }




    /**
     * 通过mob sdk获取短信验证码
     * @param phoneNum
     */
    private void toGetVerifyCode(String phoneNum) {

        TimerCount timerCount = new TimerCount(60000, 1000, register_btn_send_verify_code);
        timerCount.start();


        OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_SEND_VERIFY_MSG)
                .addParams("smsReqModel.phoneNum", phoneNum)
                .addParams("smsReqModel.type", SMS_MODEL_TYPE)
                .addParams("smsReqModel.actionCode", MsgType.SEND_MESSAGE)
                .build().execute(new GetVerifyCodeCallback());
        L.d(TAG, "获取验证码url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_SEND_VERIFY_MSG
                + "?smsReqModel.phoneNum=18620647064" + "&smsReqModel.type=" + SMS_MODEL_TYPE
                + "smsReqModel.actionCode" + MsgType.SEND_MESSAGE
                );



    }

    class GetVerifyCodeCallback extends StringCallback{

        @Override
        public void onError(Call call, Exception e) {
            //获取验证码失败
            toastMgr.builder.display("验证码发送失败,请重试", 1);
        }

        @Override
        public void onResponse(String response) {
            //获取验证码成功
            //解析json
            L.d(TAG, "注册 接收验证码 json = " + response);
            VerifyCode verifyCode = new VerifyCode(response);
            GetVerifyCodeBean verifyCodeBean = verifyCode.getVerifyCodeObj();
            mVerifyCodeBean = verifyCodeBean;
            toastMgr.builder.display(verifyCodeBean.getMessage(), 1);

        }
    }



    /**
     * 提交短信验证码
     */
    private void submitVerificationCode(String phone, GetVerifyCodeBean mVerifyCodeBean, String code)
    {


        register_btn_sign_up.setClickable(false);

        String inputCode = register_verify_code_edittext.getText().toString().trim();
        code = inputCode;

        getUserInfo();

        phoneNum = register_phone_num_uid_edittext.getText().toString().trim();;//电话号码
        loginPwd = register_pwd_edittext.getText().toString().trim();//登陆密码
        driverName = register_driver_name_edittext.getText().toString().trim();//司机姓名
        recommendPeople = register_recommend_ren_name_edittext.getText().toString().trim();//推荐人手机号
        boolean isRecommendPeopele = Validator.isMobile(recommendPeople);
        if (isRecommendPeopele == false)
        {
            if (recommendPeople.equals("") || recommendPeople == null)
            {
                toastMgr.builder.display("推荐人手机号码没有填写!", 1);
                recommendPeople = "";
            }
            else
            {
                toastMgr.builder.display("推荐人手机号码填写格式错误!", 1);
                return;
            }

        }
        verifyCode = register_verify_code_edittext.getText().toString().trim();//输入的验证码


        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_REGISTER)
                .addParams("userReq.name", driverName)
                .addParams("userReq.type", SMS_MODEL_TYPE)
                .addParams("userReq.code", code)
                .addParams("userReq.phoneNum", phone)
                .addParams("userReq.passWorld", loginPwd)
                .addParams("userReq.recMan", recommendPeople)
                .build()
                .execute(new VerifyCodeCallback());

        L.d(TAG, "验证验证码url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_REGISTER
                        + "?userReq.name" +  driverName
                        + "smsReqModel.phoneNum=" + phone
                        + "&smsReqModel.type=" + SMS_MODEL_TYPE
                        + "&userReq.code=" + code
                        + "&userReq.passWorld=" + loginPwd
                        + "&userReq.recMan=" + recommendPeople
        );


    }


    class VerifyCodeCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误", 1);
            register_btn_sign_up.setClickable(true);

        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "验证验证码url = " + response);
            VerifyCode verifyCode = new VerifyCode(response);
            VerifyCodeBean verifyCodeBean = verifyCode.verifyCodeObj();
            register_btn_sign_up.setClickable(true);

            String userid = verifyCodeBean.getUserid();
            int returnCode = verifyCodeBean.getReturnCode();
            if (returnCode == 0)
            {
                //验证成功
                toastMgr.builder.display(verifyCodeBean.getMessage(), 1);
                Intent intent=  new Intent();
                intent.setClass(RegisterActivity.this, RegisterDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid", userid);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
            else
            {
                toastMgr.builder.display(verifyCodeBean.getReturneMsg() + "  "  + verifyCodeBean.getMessage(), 1);
            }
        }
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
            if (verifyCodeLength >= 4)
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


/**
 * 获取验证码
 http://192.168.1.50:8080/carService/sendSms?smsReqModel.phoneNum=18620647064&smsReqModel.type=1&smsReqModel.actionCode=0

 验证验证码
 http://192.168.1.50:8080/carService/client/user/register?userReq.name=%E9%B9%8F%E5%93%A5&userReq.type=1&userReq.code=8626&userReq.phoneNum=18620647064&userReq.passWorld=123456789&userReq.recMan=18620647064
 */
