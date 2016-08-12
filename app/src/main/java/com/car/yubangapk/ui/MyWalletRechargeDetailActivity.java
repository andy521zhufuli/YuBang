package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.view.CustomProgressDialog;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MyWalletRechargeDetailActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;
    private ImageView img_back;
    private PullToRefreshListView my_wallet_recharge_detail_refresh_listview;
    private LinearLayout nothing_layout;
    private TextView text_empty;


    private CustomProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_wallet_recharge_detail);

        mContext = this;
        mProgress = new CustomProgressDialog(mContext);
        findViews();
        //第一次请求
        firstRequestRechargeDetail();
    }

    /**
     *
     */
    private void findViews()
    {
        img_back = (ImageView) findViewById(R.id.img_back);
        my_wallet_recharge_detail_refresh_listview = (PullToRefreshListView) findViewById(R.id.my_wallet_recharge_detail_refresh_listview);
        nothing_layout = (LinearLayout) findViewById(R.id.nothing_layout);
        text_empty = (TextView) findViewById(R.id.text_empty);

        //监听器
        img_back.setOnClickListener(this);

        text_empty.setOnClickListener(this);

    }

    private void firstRequestRechargeDetail()
    {

    }


    @Override
    public void onClick(View v) {

    }
}
