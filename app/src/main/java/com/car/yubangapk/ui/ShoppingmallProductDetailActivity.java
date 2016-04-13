package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;

import org.w3c.dom.Text;

/**
 * ShoppingmallProductDetailActivity: 商品详情页面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */


public class ShoppingmallProductDetailActivity extends AppCompatActivity {

    private Context mContext;
    private static final String TAG = ShoppingmallProductDetailActivity.class.getName();


    private ImageView           img_back;//返回
    private ImageView           product_pic;//图片  默认取第一张显示
    private TextView            product_details_name;//商品的名字
    private TextView            product_price;//商品的价格
    private TextView            product_selled_count;//商品已售数量
    private TextView            product_comment_num;//商品评价人数
    private RelativeLayout      product_detail_layout;//产品详情
    private RelativeLayout      product_detail_parameter;//产品详情的产品参数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_shoppingmall_product_detail);
        mContext = this;

        findViews();

    }

    /**
     * 绑定控件
     */
    private void findViews() {
        img_back = (ImageView) findViewById(R.id.img_back);//返回

        product_pic = (ImageView) findViewById(R.id.product_pic);;//图片  默认取第一张显示

        product_details_name = (TextView) findViewById(R.id.product_details_name);;//商品的名字

        product_price = (TextView) findViewById(R.id.product_price);;//商品的价格

        product_selled_count = (TextView) findViewById(R.id.product_selled_count);;//商品已售数量

        product_comment_num = (TextView) findViewById(R.id.product_comment_num);;//商品评价人数

        product_detail_layout = (RelativeLayout) findViewById(R.id.product_detail_layout);;//产品详情

        product_detail_parameter = (RelativeLayout) findViewById(R.id.product_detail_parameter);;//产品详情的产品参数

    }
}
