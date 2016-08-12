package com.tec.android.ui;

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
import android.widget.TextView;

import com.tec.android.R;
import com.tec.android.network.CommitCashReqHttp;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.CustomProgressDialog;

/**
 * 确认提现信息
 */
public class ConformGetCashInfoActivity extends BaseActivity {


    private Context mContext;
    private static final String TAG = "ConformGetCashInfoActivity";

    private TextView conform_get_cash_info_name_textview;       //姓名
    private TextView conform_get_cash_info_bank_textview;       //银行类别
    private TextView conform_get_cash_info_bankcard_textview;   //卡号
    private TextView conform_get_cash_info_money_textview;      //提现金额
    private Button   conform_get_cash_info_conform_button;      //确认提现  下一步是验证手机号码

    private ImageView title_back;//返回
    private String mCsahOutMoney;//用户提现金额

    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_conform_get_cash_info);
        mContext = this;
        findViews();

        mCsahOutMoney = getIntent().getStringExtra("money");


        //从上一个界面传过来的银行信息 这里获取 显示在响应的控件里面
//        conform_get_cash_info_name_textview.setText("");
//        conform_get_cash_info_bank_textview.setText("");
//        conform_get_cash_info_bankcard_textview.setText("卡号:");
//        conform_get_cash_info_money_textview.setText("￥" + mCsahOutMoney);



    }

    /**
     * 初始化控件
     */
    private void findViews() {
        conform_get_cash_info_name_textview = (TextView) findViewById(R.id.conform_get_cash_info_name_textview);
        conform_get_cash_info_bank_textview = (TextView) findViewById(R.id.conform_get_cash_info_bank_textview);
        conform_get_cash_info_bankcard_textview = (TextView) findViewById(R.id.conform_get_cash_info_bankcard_textview);
        conform_get_cash_info_money_textview = (TextView) findViewById(R.id.conform_get_cash_info_money_textview);
        conform_get_cash_info_conform_button = (Button) findViewById(R.id.conform_get_cash_info_conform_button);
        title_back = (ImageView) findViewById(R.id.title_back);


        //设置监听器
        conform_get_cash_info_conform_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认提现
                conformGetCash();
            }
        });

        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 确认提现请求
     */
    private void conformGetCash() {
        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog = mProgressDialog.show(mContext,"加载中...", false, null);
        CommitCashReqHttp commitCashReqHttp =  new CommitCashReqHttp(mContext);
        commitCashReqHttp.sendAndCommitCashReqHtml(new CommitCashReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                Message msg = new Message();
                msg.what = COMMIT_CASH_SUCCESS;
                msg.obj = result;
                commitGetCashHandler.sendMessage(msg);
            }
        }, new CommitCashReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                Message msg = new Message();
                msg.what = COMMIT_CASH_FAIL;
                msg.obj = resultMsg;
                commitGetCashHandler.sendMessage(msg);
            }
        });
    }


    private Handler commitGetCashHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                //提交成功
                case COMMIT_CASH_SUCCESS:
                    String html = (String) msg.obj;
                    toastMgr.builder.display("提交成功.", 0);
                    mProgressDialog.dismiss();;
                    mProgressDialog = null;
                    break;
                //提交失败
                case COMMIT_CASH_FAIL:
                    mProgressDialog.dismiss();;
                    mProgressDialog = null;
                    break;
                default:
                    break;
            }
        }
    };
    private static final int COMMIT_CASH_SUCCESS = 0X1;
    private static final int COMMIT_CASH_FAIL = 0X101;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //资源释放
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 从下一个界面返回的时候  就会触发这个
     */
    @Override
    protected void onResume() {
        super.onResume();
    }
}
