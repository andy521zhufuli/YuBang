package com.android.andy.yubang.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
/**
 * MyCouponActivity: 我的优惠券
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-27
 */
public class MyCouponActivity extends BaseActivity {

    private Context         mContext;
    private ImageView       img_back;//返回
    private RelativeLayout  layout_order1;//全部优惠
    private TextView        TextView01;//全部优惠汉字
    private View            indicator1;//底部指示

    private RelativeLayout  layout_order2;//店铺优惠
    private TextView        TextView02;//店铺优惠汉字
    private View            indicator2;//底部指示

    private RelativeLayout  layout_order3;//店铺红包
    private TextView        TextView03;//店铺红包汉字
    private View            indicator3;//底部指示

    private ListView        my_coupon_all;//全部优惠
    private ListView        my_coupon_store_coupon;//店铺优惠
    private ListView        my_coupon_store_lucky_money;//店铺红包


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_coupon);

        mContext = this;

        findViews();


    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);//返回

        layout_order1 = (RelativeLayout) findViewById(R.id.layout_order1);//全部优惠

        TextView01 = (TextView) findViewById(R.id.TextView01);//全部优惠汉字

        indicator1 = findViewById(R.id.indicator1);//底部指示

        layout_order2 = (RelativeLayout) findViewById(R.id.layout_order2);//店铺优惠

        TextView02 = (TextView) findViewById(R.id.TextView02);//店铺优惠汉字

        indicator2 = findViewById(R.id.indicator2);//底部指示

        layout_order3 = (RelativeLayout) findViewById(R.id.layout_order3);//店铺红包

        TextView03 = (TextView) findViewById(R.id.TextView03);//店铺红包汉字

        indicator3 = findViewById(R.id.indicator3);//底部指示

        my_coupon_all = (ListView) findViewById(R.id.my_coupon_all);//全部优惠

        my_coupon_store_coupon = (ListView) findViewById(R.id.my_coupon_store_coupon);//店铺优惠

        my_coupon_store_lucky_money = (ListView) findViewById(R.id.my_coupon_store_lucky_money);//店铺红包

        /**
         * 监听器
         */
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layout_order1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //颜色改变
                //底部指示器改变
                indicator1.setVisibility(View.VISIBLE);
                indicator2.setVisibility(View.INVISIBLE);
                indicator3.setVisibility(View.INVISIBLE);

                TextView01.setTextColor(Color.parseColor("#ff0000"));
                TextView02.setTextColor(Color.parseColor("#4f4f4f"));
                TextView03.setTextColor(Color.parseColor("#4f4f4f"));

            }
        });
        layout_order2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //颜色改变
                //底部指示器改变
                indicator1.setVisibility(View.INVISIBLE);
                indicator2.setVisibility(View.VISIBLE);
                indicator3.setVisibility(View.INVISIBLE);
                TextView01.setTextColor(Color.parseColor("#4f4f4f"));
                TextView02.setTextColor(Color.parseColor("#ff0000"));
                TextView03.setTextColor(Color.parseColor("#4f4f4f"));
            }
        });
        layout_order3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //颜色改变
                //底部指示器改变
                indicator1.setVisibility(View.INVISIBLE);
                indicator2.setVisibility(View.INVISIBLE);
                indicator3.setVisibility(View.VISIBLE);
                TextView01.setTextColor(Color.parseColor("#4f4f4f"));
                TextView02.setTextColor(Color.parseColor("#4f4f4f"));
                TextView03.setTextColor(Color.parseColor("#ff0000"));
            }
        });

        //TODO listview的item还没画





    }


}
