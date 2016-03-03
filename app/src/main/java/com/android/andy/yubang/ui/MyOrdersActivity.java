package com.android.andy.yubang.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;

/**
 * MyOrdersActivity: 我的订单
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-27
 */
public class MyOrdersActivity extends BaseActivity {

    private Context mContext;

    private ImageView       img_back;//返回

    private RelativeLayout  my_order_layout_1;//实物订单
    private RelativeLayout  my_order_layout_2;//物流订单
    private RelativeLayout  my_order_layout_3;//已取消订单

    private TextView        order1;
    private TextView        order2;
    private TextView        order3;

    private View            indicator1;
    private View            indicator2;
    private View            indicator3;

    private ListView        my_order_listview;//列表
    private ProgressBar     my_order_progressbar;//进度条


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        mContext =this;

        findViews();

    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);//返回
        my_order_layout_1 = (RelativeLayout) findViewById(R.id.my_order_layout_1);//实物订单
        my_order_layout_2 = (RelativeLayout) findViewById(R.id.my_order_layout_2);//物流订单
        my_order_layout_3 = (RelativeLayout) findViewById(R.id.my_order_layout_3);//已取消订单
        order1 = (TextView) findViewById(R.id.order1);
        order2 = (TextView) findViewById(R.id.order2);
        order3 = (TextView) findViewById(R.id.order3);
        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);

        my_order_listview = (ListView) findViewById(R.id.my_order_listview);;//列表

        my_order_progressbar = (ProgressBar) findViewById(R.id.my_order_progressbar);;//进度条
        /**
         * 注册监听
         */

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /**
         * 实物订单
         */
        my_order_layout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goods_orders();
            }
        });

        /**
         * 物流订单
         */
        my_order_layout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logistics_orders();
            }
        });

        /**
         * 已取消订单
         */
        my_order_layout_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canceled_orders();
            }
        });
    }




    /**
     * 实物订单
     */
    private void goods_orders() {

    }

    /**
     * 物流订单
     */
    private void logistics_orders() {

    }

    /**
     * 已取消订单
     */
    private void canceled_orders() {

    }

}
