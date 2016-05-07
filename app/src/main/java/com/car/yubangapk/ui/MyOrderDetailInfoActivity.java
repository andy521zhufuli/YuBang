package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.ui.shoppingmallgoodsutil.Category;
import com.car.yubangapk.ui.shoppingmallgoodsutil.GoodsCategoryHelper;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;

import java.util.List;

/**
 * MyOrderInfoWaitSignActivity: 我的订单  代签收详情 待安装   已完成, 这三个界面是一样的    只是标题不一样
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class MyOrderDetailInfoActivity extends BaseActivity implements View.OnClickListener{

    private String TAG = MyOrderDetailInfoActivity.class.getSimpleName();
    private Context mContext;

    private ImageView       img_back;
    private TextView        title;//头部标题


    private TextView        order_info_order_num;//订单号
    private TextView        order_total_price;//订单总价
    private TextView        order_word_time_cost;//工时费
    private TextView        textview_order_info_name_content;//姓名
    private TextView        textview_order_info_mobile_content;//电话
    private TextView        textview_order_info_car_num;//车牌号
    private TextView        textview_order_info_car_type;//车型
    private TextView        textview_order_info_install_shop;//安装门店
    private TextView        textview_order_info_install_time;//安装时间
    private ListView        order_detail_listview;//订单详情列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_order_info_wait_sign);

        mContext = this;

        findViews();

        setTitle(getIntent());



    }

    public final static String TITLE_TYPE = "titleType";
    public final static String ORDER_ID = "orderId";
    public final static int WAIT_SIGN_TYPE = 1;//等待签收
    public final static int WAIT_INSTALL_TYPE = 2;//等待安装
    public final static int COMPELETED_TYPE = 3;//已完成
    private String mOrderId;

    /**
     * 设置界面标题
     * @param intent
     */
    private void setTitle(Intent intent)
    {
        Bundle bundle = intent.getExtras();
        int titleType = bundle.getInt(TITLE_TYPE);

        if (WAIT_SIGN_TYPE == titleType)
        {
            title.setText("待签收");
            mOrderId = bundle.getString(ORDER_ID);
        }
        else if (WAIT_INSTALL_TYPE == titleType)
        {
            title.setText("待安装");
        }
        else if (COMPELETED_TYPE == titleType)
        {
            title.setText("已完成");
        }
    }

    /**
     * 绑定控件
     */
    private void findViews()
    {
        img_back = (ImageView) findViewById(R.id.img_back);
        title = (TextView) findViewById(R.id.title);

        order_info_order_num = (TextView) findViewById(R.id.order_info_order_num);//订单号
        order_total_price = (TextView) findViewById(R.id.order_total_price);//订单总价
        order_word_time_cost = (TextView) findViewById(R.id.order_word_time_cost);//工时费
        textview_order_info_name_content = (TextView) findViewById(R.id.textview_order_info_name_content);//姓名
        textview_order_info_mobile_content = (TextView) findViewById(R.id.textview_order_info_mobile_content);//电话
        textview_order_info_car_num = (TextView) findViewById(R.id.textview_order_info_car_num);//车牌号
        textview_order_info_car_type = (TextView) findViewById(R.id.textview_order_info_car_type);//车型
        textview_order_info_install_shop = (TextView) findViewById(R.id.textview_order_info_install_shop);//安装门店
        textview_order_info_install_time = (TextView) findViewById(R.id.textview_order_info_install_time);//安装时间

        order_detail_listview = (ListView) findViewById(R.id.order_detail_listview);//订单详情列表


        //监听
        img_back.setOnClickListener(this);

    }




    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                finish();
                break;
        }
    }

    public class OrderDetailListViewAdapter extends BaseAdapter {

        List<Category> mpplist;
        int from;
        Context mPContext;

        private static final int TYPE_CATEGORY_ITEM = 0;
        private static final int TYPE_ITEM = 1;

        public OrderDetailListViewAdapter(List<Category> json2ProductPackageBeanList, int _from, Context context) {
            this.mpplist = json2ProductPackageBeanList;
            this.from = _from;
            this.mPContext = context;
            //mProductPkgBeanListToConformOrderPage = categoryToJsonProductPackageGoods(json2ProductPackageBeanList);
        }


        public void refresh(List<Category> json2ProductPackageBeanList) {
            mpplist = json2ProductPackageBeanList;
            //mProductPkgBeanListToConformOrderPage = categoryToJsonProductPackageGoods(json2ProductPackageBeanList);
            notifyDataSetChanged();
            L.d("refresh");
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            // 异常情况处理
            if (null == mpplist || position <  0|| position > getCount()) {
                L.d("item view type = " + TYPE_ITEM + "TYPE_ITEM");
                return TYPE_ITEM;
            }


            int categroyFirstIndex = 0;

            for (Category category : mpplist) {
                int size = category.getItemCount();
                // 在当前分类中的索引值
                int categoryIndex = position - categroyFirstIndex;
                if (categoryIndex == 0) {
                    L.d("item view type = " + TYPE_CATEGORY_ITEM + "TYPE_CATEGORY_ITEM");
                    return TYPE_CATEGORY_ITEM;
                }

                categroyFirstIndex += size;
            }

            L.d("item view type = " + TYPE_ITEM + "TYPE_ITEM");

            return TYPE_ITEM;
        }

        @Override
        public int getCount() {
            int count = 0;
            if (null != mpplist) {
                //  所有分类中item的总和是ListVIew  Item的总个数
                for (Category category : mpplist) {
                    count += category.getItemCount();
                }
            }
            L.d("count = " + count);
            return count;
        }


        @Override
        public Object getItem(int position) {
            // 异常情况处理
            if (null == mpplist || position <  0|| position > getCount()) {
                return null;
            }

            // 同一分类内，第一个元素的索引值
            int categroyFirstIndex = 0;

            for (Category category : mpplist) {
                int size = category.getItemCount();
                // 在当前分类中的索引值
                int categoryIndex = position - categroyFirstIndex;
                // item在当前分类内
                if (categoryIndex < size) {
                    return  category.getItem( categoryIndex );
                }
                // 索引移动到当前分类结尾，即下一个分类第一个元素索引
                categroyFirstIndex += size;
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            /**
             * 1.手工创建对象 2.加载xml文件
             */

            final ViewHolder holder;
            if (view == null)
            {
                holder =  new ViewHolder();
                view = View.inflate(mContext, R.layout.item_comform_order_product_detail_item, null);
                holder.product_service_name = (TextView) view.findViewById(R.id.product_service_name);;//保养服务分类名字
                holder.product_service_title_layout = (LinearLayout) view.findViewById(R.id.product_service_title_layout);;//分类布局


                holder.produte_pic = (ImageView) view.findViewById(R.id.produte_pic);
                holder.maintenance_produte_name = (TextView) view.findViewById(R.id.maintenance_produte_name);
                holder.produte_price = (TextView) view.findViewById(R.id.produte_price);
                holder.produte_count = (TextView) view.findViewById(R.id.produte_count);
                holder.maintenance_hecheng_1 = (TextView) view.findViewById(R.id.maintenance_hecheng_1);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }
            //设置分类
            int itemViewType = getItemViewType(position);
            if(itemViewType == TYPE_CATEGORY_ITEM)
            {
                holder.product_service_title_layout.setVisibility(View.VISIBLE);
                String packageName = ((Json2ProductPackageBean)getItem(position)).getPackageName();
                holder.product_service_name.setText(packageName);
            }
            else
            {
                holder.product_service_title_layout.setVisibility(View.GONE);
            }
            String productName = ((Json2ProductPackageBean)getItem(position)).getProductName();
            holder.maintenance_produte_name.setText(productName);
            double price = ((Json2ProductPackageBean)getItem(position)).getRetailPrice();
            holder.produte_price.setText("￥" + price + "");

            String show = ((Json2ProductPackageBean)getItem(position)).getProductShow();
            holder.maintenance_hecheng_1.setText(show);

            holder.produte_count.setText("x" + ((Json2ProductPackageBean) getItem(position)).getProductAmount() + "");
            holder.produte_price.setText("￥" + ((Json2ProductPackageBean) getItem(position)).getRetailPrice() + "");
            holder.produte_price.setText("￥" + ((Json2ProductPackageBean) getItem(position)).getRetailPrice() + "");

            //以上是设置分类的显示 每个item的显示



            String pathcode = ((Json2ProductPackageBean)getItem(position)).getPathCode();
            String photoname = ((Json2ProductPackageBean)getItem(position)).getPhotoName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;
            ImageLoaderTools.getInstance(mPContext).displayImage(url, holder.produte_pic);




            return view;
        }





        class ViewHolder
        {
            LinearLayout    product_service_title_layout;//
            TextView        product_service_name; //产品包分类名字

            ImageView       produte_pic; //左侧图片
            TextView        maintenance_produte_name;//产品名字
            TextView        maintenance_hecheng_1;//半合成
            TextView        produte_price;//产品价格
            TextView        produte_count;//产品数量

        }
    }





}
