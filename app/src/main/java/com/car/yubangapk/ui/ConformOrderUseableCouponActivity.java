package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.json.bean.CouponsBean;

import java.nio.BufferUnderflowException;
import java.util.List;

/**
 * 确认订单可用优惠券
 */
public class ConformOrderUseableCouponActivity extends BaseActivity {

    Context mContext;

    ListView    conform_order_useable_coupon_listview;//
    ImageView   img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_conform_order_useable_coupon);

        this.mContext = this;

        Bundle bundle  = getIntent().getExtras();
        List<CouponsBean> coupons = (List<CouponsBean>) bundle.getSerializable("coupons");



        findViews();

        CouponsBean bean = new CouponsBean();
        bean.setCouponName("不使用");
        coupons.add(bean);
        CouponListAdapter adapter = new CouponListAdapter(coupons);
        conform_order_useable_coupon_listview.setAdapter(adapter);

    }

    /**
     * 绑定
     */
    private void findViews() {
        conform_order_useable_coupon_listview = (ListView) findViewById(R.id.conform_order_useable_coupon_listview);
        img_back = (ImageView) findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }



    /**
     * 优惠券列表适配
     */
    private class CouponListAdapter extends BaseAdapter
    {
        List<CouponsBean> coupons;

        public CouponListAdapter(List<CouponsBean> coupons)
        {
            this.coupons = coupons;

        }

        @Override
        public int getCount() {
            return coupons.size();
        }

        @Override
        public Object getItem(int i) {
            return coupons.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {

            final ViewHolder holder;
            if (view == null)
            {
                holder = new ViewHolder();
                view = View.inflate(mContext, R.layout.item_conform_order_coupon_list_item, null);
                holder.conform_order_coupon_layout = (LinearLayout) view.findViewById(R.id.conform_order_coupon_layout);
                holder.coupon_name = (TextView) view.findViewById(R.id.coupon_name);
                holder.coupon_check_imageview = (ImageView) view.findViewById(R.id.coupon_check_imageview);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }

            if (coupons.get(position).getCouponName().equals("不使用"))
            {
                holder.coupon_name.setText("不使用");
            }
            else
            {
                holder.coupon_name.setText(coupons.get(position).getRegulationReach()+"元");
            }


            holder.coupon_check_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_02));

            holder.conform_order_coupon_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.coupon_check_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_01));

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("coupon", coupons.get(position));
                    intent.putExtras(bundle);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
            });



            return view;
        }

        class ViewHolder
        {
            LinearLayout    conform_order_coupon_layout;
            TextView        coupon_name;
            ImageView       coupon_check_imageview;

        }
    }
}
