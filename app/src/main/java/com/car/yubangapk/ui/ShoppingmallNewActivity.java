package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.swipetoloadlayout.OnRefreshListener;
import com.car.yubangapk.swipetoloadlayout.SwipeToLoadLayout;
import com.car.yubangapk.utils.toastMgr;

public class ShoppingmallNewActivity extends BaseActivity implements OnRefreshListener, View.OnClickListener{

    private SwipeToLoadLayout swipeToLoadLayout;
    private TextView          text1;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shoppingmall_new);

        mContext = this;
        findViews();

        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });

    }


    private void findViews()
    {
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);

        text1     = (TextView) findViewById(R.id.text1);
        text1.setOnClickListener(this);
    }


    @Override
    public void onRefresh() {
        toastMgr.builder.display("onrefresh",1);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.text1)
        {
            Intent intent = new Intent();
            intent.setClass(mContext,ListViewRefreshAcivity.class);
            startActivity(intent);
        }
    }
}
