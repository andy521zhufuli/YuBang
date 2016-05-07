package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.andy.android.yubang.R;

/**
 * ShoppingmallConformOrderProductDetailActivity: 确认订单时候 点击商品缩略  进入订单里面商品详情
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-29
 */
public class ShoppingmallConformOrderProductDetailActivity extends BaseActivity {


    private static final String TAG = ShoppingmallConformOrderProductDetailActivity.class.getName();

    private Context mContext;

    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shoppingmall_conform_order_product_detail);


        mContext = this;

        findViews();
    }

    /**
     * 绑定控件
     */
    private void findViews() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
