package com.android.andy.yubang.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.andy.yubang.utils.toastMgr;
import com.andy.android.yubang.R;
/**
 * ShoppingMallGoodsActivity: 商城产品
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class ShoppingMallGoodsActivity extends BaseActivity {

    private Context mContext;
    private ListView shoppingmall_goods_listview;

    private RelativeLayout btn_pay;//去结算
    private RelativeLayout btn_service;//客服
    //编辑商品
    private TextView tv_modify_goods;//编辑
    private LinearLayout    productitem_changge_before;//商品信息
    private LinearLayout    productitem_changge_after;//编辑商品之后
    private boolean         isModified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_mall_goods);

        mContext = this;

        findViews();


        shoppingmall_goods_listview.setAdapter(new TestAdapter());


    }

    private void findViews() {

        shoppingmall_goods_listview = (ListView) findViewById(R.id.shoppingmall_goods_listview);
        tv_modify_goods = (TextView) findViewById(R.id.tv_modify_goods);
        btn_pay = (RelativeLayout) findViewById(R.id.btn_pay);
        btn_service = (RelativeLayout) findViewById(R.id.btn_service);



        /**
         * 监听器
         */
        //编辑商品
        tv_modify_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                //一开始是编辑状态
                if (isModified == false)
                {
                    //还没编辑 已经点击  那就去编辑
                    productitem_changge_before.setVisibility(View.GONE);
                    productitem_changge_after.setVisibility(View.VISIBLE);
                    isModified = true;
                    tv_modify_goods.setText("保存");
                }
                else
                {
                    productitem_changge_after.setVisibility(View.GONE);
                    productitem_changge_before.setVisibility(View.VISIBLE);
                    isModified = false;
                    tv_modify_goods.setText("编辑");
                }

                Intent intent = new Intent();
                //intent.setClass()
                //startActivity(intent);
            }
        });
        //去结算
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ShoppingMallGoodsActivity.this, ShoppingMallConformOrderActivity.class);
                startActivity(intent);
            }
        });
        //联系客服
        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(ShoppingMallGoodsActivity.this, ShoppingMallConformOrderActivity.class);
//                startActivity(intent);
                toastMgr.builder.display("联系客服", 0);
            }
        });

    }


    private String[] strings = {"保养维护", "电子电路", "发动机件", "底盘配件", "车架配件", "拖架配件" };
    /**
     * 适配器的定义,要继承BaseAdapter
     */
    public class TestAdapter extends BaseAdapter {

        public TestAdapter() {
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return strings[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            /*
             * 1.手工创建对象 2.加载xml文件
             */
            view = View.inflate(mContext, R.layout.item_test_listview_data, null);
            productitem_changge_before = (LinearLayout) view.findViewById(R.id.productitem_changge_before);
            productitem_changge_after  = (LinearLayout) view.findViewById(R.id.productitem_changge_after);
//            TextView species = (TextView) view.findViewById(R.id.item_search_recommend_textview);
//            species.setText(strings[position]);
            return view;
        }
    }


}
