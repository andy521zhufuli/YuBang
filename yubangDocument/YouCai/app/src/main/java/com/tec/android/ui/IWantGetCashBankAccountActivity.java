package com.tec.android.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tec.android.R;
import com.tec.android.network.GetCashAccountListReqHttp;
import com.tec.android.view.CustomProgressDialog;

import cn.trinea.android.common.util.DownloadManagerPro;


/**
 * 我要提现到银行卡账户  用户填写银行卡信息
 * 姓名
 * 卡号
 * 银行
 * 用户也可以选择已有账户
 */
public class IWantGetCashBankAccountActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;
    private static final String TAG = "IWantGetCashBankAccountActivity";
    private CustomProgressDialog mProgressDialog;


    private EditText iwant_get_cash_bank_account_name_editview;         //银行卡户主名字
    private EditText iwant_get_cash_bank_account_bankcar_num_editview;  //银行卡卡号
    private EditText iwant_get_cash_bank_account_bank_editview;         //哪个银行
    private Button iwant_get_cash_bank_account_nextstep_button;       //下一步

    private ImageView iwant_get_cash_bank_account_name_list;//点击获取用户使用的账户列表
    private ImageView iwant_get_cash_bank_account_banklist;//支持的银行列表

    private String mCashOutMoney;//用户提现金额
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_iwant_get_cash_bank_account);

        mCashOutMoney = getIntent().getStringExtra("money");

        mContext = this;

        findViews();

    }

    /**
     * 初始化控件
     */
    private void findViews() {
        iwant_get_cash_bank_account_name_editview = (EditText) findViewById(R.id.iwant_get_cash_bank_account_name_editview);
        iwant_get_cash_bank_account_bankcar_num_editview = (EditText) findViewById(R.id.iwant_get_cash_bank_account_bankcar_num_editview);
        iwant_get_cash_bank_account_bank_editview = (EditText) findViewById(R.id.iwant_get_cash_bank_account_bank_editview);
        iwant_get_cash_bank_account_nextstep_button = (Button) findViewById(R.id.iwant_get_cash_bank_account_nextstep_button);
        //已有用户列表
        iwant_get_cash_bank_account_name_list = (ImageView) findViewById(R.id.iwant_get_cash_bank_account_name_list);
        //支持的银行卡
        iwant_get_cash_bank_account_banklist = (ImageView) findViewById(R.id.iwant_get_cash_bank_account_banklist);

        //设置监听器
        iwant_get_cash_bank_account_nextstep_button.setOnClickListener(this);
        iwant_get_cash_bank_account_name_list.setOnClickListener(this);
        iwant_get_cash_bank_account_banklist.setOnClickListener(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 点击  交互
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //下一步
            case R.id.iwant_get_cash_bank_account_nextstep_button:
                gotoNextStep();
                break;
            //已有账户的列表
            case R.id.iwant_get_cash_bank_account_name_list:
                gotoNameList();
                break;
            //支持银行卡的列表
            case R.id.iwant_get_cash_bank_account_banklist:
                gotoBankList();
                break;
            default:
                break;

        }
    }

    /**
     * 去到支持银行列表界面
     */
    private void gotoBankList() {
        Intent intent = new Intent();
        intent.setClass(mContext, IWantGetCashBankListActivity.class);
        startActivityForResult(intent, GET_BANK_NAME);
    }

    private static final int GET_BANK_NAME = 0x11;
    private static final int GET_ACCOUNT_LIST_ITEM = 0x12;

    /**
     * 去到选择已有账户列表界面
     */
    private void gotoNameList() {
        Intent intent = new Intent();
        intent.setClass(mContext, IWantGetCashNameListActivity.class);
        startActivityForResult(intent, GET_ACCOUNT_LIST_ITEM);
    }

    /**
     * 去往下一步
     */
    private void gotoNextStep() {
        //搜集用户的账户信息  作为参数发送给后台, 然后取回信息 跳转到下一个界面
        String bank_user_name = iwant_get_cash_bank_account_name_editview.getText().toString().trim();
        String bank_card_num = iwant_get_cash_bank_account_bankcar_num_editview.getText().toString().trim();
        String bank_name = iwant_get_cash_bank_account_bank_editview.getText().toString().trim();

        //跳转到确认提现账户信息界面
        Intent intent = new Intent();
        intent.putExtra("money", mCashOutMoney);
        intent.setClass(mContext, ConformGetCashInfoActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            //返回的是拿支持的银行列表的  某一个银行
            if (requestCode == GET_BANK_NAME)
            {
                Bundle bundle = data.getExtras();
                String bandkName = bundle.getString("bankname");
                iwant_get_cash_bank_account_bank_editview.setText(bandkName);


            }
            else if (requestCode == GET_ACCOUNT_LIST_ITEM)
            {
                Bundle bundle = data.getBundleExtra("cashnamelistitem");

                //返回的是  拿到了以后账户的所有信息  这里把它重新写到控件上
                String accountname = bundle.getString("accountname");
                String accountid = bundle.getString("accountid");
                String bankname = bundle.getString("bankname");
                String accounttype = bundle.getString("accounttype");
                iwant_get_cash_bank_account_bank_editview.setText(bankname);
                iwant_get_cash_bank_account_name_editview.setText(accountname);
                iwant_get_cash_bank_account_bankcar_num_editview.setText(accountid);
            }
        }
    }


    //    /**
//     *
//     */
//    private Handler UIHandler = new Handler()
//    {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what)
//            {
//                //成功已有账户列表
//                case NAME_LIST_SUCCESS:
//                    mProgressDialog.dismiss();
//                    break;
//                case NAME_LIST_FAIL:
//                    mProgressDialog.dismiss();
//                    break;
//                case BANK_LIST_SUCCESS:
//                    mProgressDialog.dismiss();
//                    break;
//                case BANK_LIST_FAIL:
//                    mProgressDialog.dismiss();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    private static final int NAME_LIST_SUCCESS = 0X1;
//    private static final int NAME_LIST_FAIL = 0X101;
//    private static final int BANK_LIST_SUCCESS = 0X2;
//    private static final int BANK_LIST_FAIL = 0X102;
}
