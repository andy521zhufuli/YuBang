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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.json.bean.OrderDetail.OrderAddress;
import com.car.yubangapk.json.bean.OrderDetail.OrderDetailInfo;
import com.car.yubangapk.json.bean.OrderDetail.OrderPackageModels;
import com.car.yubangapk.json.bean.OrderDetail.OrderPrice;
import com.car.yubangapk.json.bean.OrderDetail.OrderProductModels;
import com.car.yubangapk.json.formatJson.FormatJsonOrderDetail.Json2OrderDetail;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqGetMyOrderDetailInfo;
import com.car.yubangapk.ui.shoppingmallgoodsutil.Category;
import com.car.yubangapk.ui.shoppingmallgoodsutil.GoodsCategoryHelper;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * MyOrderInfoWaitSignActivity: 我的订单  代签收详情 待安装   已完成, 这三个界面是一样的    只是标题不一样
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class MyOrderDetailInfoActivity extends BaseActivity implements View.OnClickListener, HttpReqCallback{

    private String TAG = MyOrderDetailInfoActivity.class.getSimpleName();
    private Context mContext;

    private ImageView       img_back;
    private TextView        title;//头部标题
    private Button          order_detail_btn_top;//取消订单或者去评价



    private TextView        order_info_order_num;//订单号
    private TextView        order_total_price;//订单总价
    private TextView        order_word_time_cost;//工时费

    private TextView        textview_order_info_name_content;//姓名
    private TextView        textview_order_info_mobile_content;//电话
    private TextView        textview_order_info_car_num;//车牌号
    private TextView        textview_order_info_car_type;//车型
    private TextView        textview_order_info_install_shop;//安装门店
    private TextView        textview_order_info_install_time;//安装时间

    private TextView        guzhang_miaoshu;//故障描述
    private TextView        jiance_jieguo;//检测结果
    private TextView        weixiu_jianyi;//维修意见


    private ListView        order_detail_listview;//订单详情列表


    //最底部按钮
    private RelativeLayout  order_detail_bottom_layout_bar;//底部
    private TextView        tv_price;//总价
    private RelativeLayout  btn_service;//客服
    private RelativeLayout  btn_pay;//去支付或者确认安装
    private TextView        tv_pay;//显示文字



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
    public final static int WAIT_BUYER_TYPE = 2;//待付款
    public final static int WAIT_SHOP_INSTALL_TYPE = 3;//待安装";
    public final static int INSTALLED_TYPE = 4;//已安装
    public final static int WAIT_EVALUATE_TYPE = 5;//待评价
    public final static int DEAL_FAIL_TYPE = 6;//交易失败
    public final static int DEAL_SUCCESS_TYPE = 7;//交易成功


    private String mOrderId;
    private int mCurrentType = -1;

    /**
     * 设置界面标题
     * @param intent
     */
    private void setTitle(Intent intent)
    {
        Bundle bundle = intent.getExtras();
        int titleType = bundle.getInt(TITLE_TYPE);
        mCurrentType = titleType;

        if (WAIT_SIGN_TYPE == titleType)
        {
            title.setText("待签收");
            //不可见
            order_detail_btn_top.setVisibility(View.GONE);

            order_detail_bottom_layout_bar.setVisibility(View.GONE);
        }
        else if (WAIT_BUYER_TYPE == titleType)
        {
            title.setText("待付款");
            order_detail_btn_top.setVisibility(View.VISIBLE);
            order_detail_btn_top.setText("取消订单");
            //可见
            order_detail_bottom_layout_bar.setVisibility(View.VISIBLE);
            tv_pay.setText("确认付款");
        }
        else if (WAIT_SHOP_INSTALL_TYPE == titleType)
        {
            title.setText("待安装");
            //顶部不可见
            order_detail_btn_top.setVisibility(View.GONE);
            //可见
            order_detail_bottom_layout_bar.setVisibility(View.VISIBLE);
            tv_pay.setText("确认安装");
        }
        else if (INSTALLED_TYPE == titleType)
        {
            title.setText("已安装");
            //不可见
            order_detail_btn_top.setVisibility(View.GONE);
            order_detail_bottom_layout_bar.setVisibility(View.GONE);
        }
        else if (WAIT_EVALUATE_TYPE == titleType)
        {
            title.setText("待评价");
            order_detail_btn_top.setVisibility(View.VISIBLE);
            order_detail_btn_top.setText("评价订单");
            //不可见
            order_detail_bottom_layout_bar.setVisibility(View.GONE);
        }
        else if (DEAL_FAIL_TYPE == titleType)
        {
            title.setText("交易失败");
            //不可见
            order_detail_btn_top.setVisibility(View.GONE);
            order_detail_bottom_layout_bar.setVisibility(View.GONE);
        }
        else if (DEAL_SUCCESS_TYPE == titleType)
        {
            title.setText("交易成功");
            //不可见
            order_detail_btn_top.setVisibility(View.GONE);
            order_detail_bottom_layout_bar.setVisibility(View.GONE);
        }
        mOrderId = bundle.getString(ORDER_ID);
        String userid = Configs.getLoginedInfo(mContext).getUserid();
        if (null == userid || "".equals(userid))
        {
            toastMgr.builder.display("信息有误, 请重试!", 1);
            return;
        }

        getOrderDetail(userid, mOrderId);

    }

    /**
     * 去拿订单详情
     * @param userid
     * @param orderId
     */
    private void getOrderDetail(String userid, String orderId)
    {

        HttpReqGetMyOrderDetailInfo getMyOrderDetailInfo = new HttpReqGetMyOrderDetailInfo();
        getMyOrderDetailInfo.setCallback(this);
        getMyOrderDetailInfo.getMyOrderDetailInfo(userid, orderId);

    }

    /**
     * 绑定控件
     */
    private void findViews()
    {
        img_back = (ImageView) findViewById(R.id.img_back);
        title = (TextView) findViewById(R.id.title);
        order_detail_btn_top = (Button) findViewById(R.id.order_detail_btn_top);;//取消订单或者去评价

        order_info_order_num = (TextView) findViewById(R.id.order_info_order_num);//订单号
        order_total_price = (TextView) findViewById(R.id.order_total_price);//订单总价
        order_word_time_cost = (TextView) findViewById(R.id.order_word_time_cost);//工时费
        textview_order_info_name_content = (TextView) findViewById(R.id.textview_order_info_name_content);//姓名
        textview_order_info_mobile_content = (TextView) findViewById(R.id.textview_order_info_mobile_content);//电话
        textview_order_info_car_num = (TextView) findViewById(R.id.textview_order_info_car_num);//车牌号
        textview_order_info_car_type = (TextView) findViewById(R.id.textview_order_info_car_type);//车型
        textview_order_info_install_shop = (TextView) findViewById(R.id.textview_order_info_install_shop);//安装门店
        textview_order_info_install_time = (TextView) findViewById(R.id.textview_order_info_install_time);//安装时间


        guzhang_miaoshu = (TextView) findViewById(R.id.guzhang_miaoshu);//故障描述
        jiance_jieguo = (TextView) findViewById(R.id.jiance_jieguo);//检测结果
        weixiu_jianyi = (TextView) findViewById(R.id.weixiu_jianyi);//维修意见

        order_detail_listview = (ListView) findViewById(R.id.order_detail_listview);//订单详情列表


        order_detail_bottom_layout_bar = (RelativeLayout) findViewById(R.id.order_detail_bottom_layout_bar);//底部
        tv_price = (TextView) findViewById(R.id.tv_price);//总价
        btn_service = (RelativeLayout) findViewById(R.id.btn_service);//客服
        btn_pay = (RelativeLayout) findViewById(R.id.btn_pay);//去支付或者确认安装
        tv_pay = (TextView) findViewById(R.id.tv_pay);;//显示文字


        //监听
        img_back.setOnClickListener(this);//返回
        order_detail_btn_top.setOnClickListener(this);//顶部按钮
        btn_service.setOnClickListener(this);
        btn_pay.setOnClickListener(this);

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
            case R.id.order_detail_btn_top:
                toastMgr.builder.display(order_detail_btn_top.getText().toString(), 1);
                if (mCurrentType == WAIT_BUYER_TYPE)//待付款
                {

                }
                else if (mCurrentType == WAIT_EVALUATE_TYPE)//待评价
                {

                }
                break;
            case R.id.btn_service:
                //客服
                break;
            case R.id.btn_pay:
                //支付或者确认安装
                if (mCurrentType == WAIT_BUYER_TYPE)//待付款
                {

                }
                else if (mCurrentType == WAIT_SHOP_INSTALL_TYPE)//待安装
                {

                }
                break;
        }
    }


    /**
     * 获取订单详情失败
     * @param errorCode
     * @param message
     */
    @Override
    public void onFail(int errorCode, String message) {
        //提示要用户失败原因
        if (errorCode == 100)
        {
            NotLogin.gotoLogin(MyOrderDetailInfoActivity.this);
            return;
        }
        else
        {
            toastMgr.builder.display(message, 1);
        }
    }

    /**
     * 获取定案详情成功时候的回调
     * @param object
     */
    @Override
    public void onSuccess(Object object) {
        OrderDetailInfo orderDetailInfo = (OrderDetailInfo) object;
        //根据这个区显示
        //1.首先显示单号 价格
        setOrderPrice(orderDetailInfo.getOrderPrice());
        //2.显示订单信息  收货人  车牌号
        setOrderDetailPeopleInfo(orderDetailInfo.getOrderAddress(), orderDetailInfo);
        //3.显示维修信息  反馈信息
        setMaintanceInfo(orderDetailInfo);
        //4.显示订单详情  这里就是listview了
        setListviewDisplay(orderDetailInfo.getOrderPackageModels());

    }

    private void setListviewDisplay(List<OrderPackageModels> orderPackageModels) {

        List<Json2ProductPackageBean> productPackageBeans = new ArrayList<>();

        int packageSize = orderPackageModels.size();
        for (int i = 0; i < packageSize; i++) {

            OrderPackageModels orderPackageModel = orderPackageModels.get(i);



            List<OrderProductModels> orderProductModelsList = orderPackageModel.getOrderProductModels();

            int productSize = orderProductModelsList.size();
            for (int j = 0; j < productSize; j++)
            {
                Json2ProductPackageBean bean = new Json2ProductPackageBean();
                OrderProductModels productModel = orderProductModelsList.get(j);
                //这是产品包的类型的logo图片
                bean.setPackageName(orderPackageModel.getPackageName());
                bean.setPackageNamePathCode(orderPackageModel.getPathCode());
                bean.setPackageNamePhotoName(orderPackageModel.getPhotoName());

                bean.setPhotoName(productModel.getPhotoName());
                bean.setPathCode(productModel.getPathCode());
                bean.setProductShow(productModel.getDes());
                bean.setProductAmount(productModel.getNum());
                bean.setRetailPrice(productModel.getPrice());
                bean.setId(productModel.getProductId());
                bean.setProductName(productModel.getProductName());
                productPackageBeans.add(bean);
            }

        }

        List<Category> categoryLIst = GoodsCategoryHelper.productsToCategoryList(productPackageBeans);



        orderDetailAdapter = new OrderDetailListViewAdapter(categoryLIst, mContext);
        order_detail_listview.setAdapter(orderDetailAdapter);
        setListViewHeightBasedOnChildren(order_detail_listview);
    }

    private OrderDetailListViewAdapter orderDetailAdapter;

    private void setMaintanceInfo(OrderDetailInfo orderDetailInfo) {
        String guzhang = orderDetailInfo.getFaultDescription();
        if (Json2OrderDetail.UNDEFINED.equals(guzhang))
        {
            guzhang = "暂无维修信息";
        }
        String jiance_jieguo1= orderDetailInfo.getDetectionResult();
        if (Json2OrderDetail.UNDEFINED.equals(jiance_jieguo1))
        {
            jiance_jieguo1 = "暂无维修信息";
        }
        String weixiu_yijian = orderDetailInfo.getMaintenanceSuggestion();
        if (Json2OrderDetail.UNDEFINED.equals(weixiu_yijian))
        {
            weixiu_yijian = "暂无维修信息";
        }

        guzhang_miaoshu.setText(guzhang);//故障描述
        jiance_jieguo.setText(jiance_jieguo1);//检测结果
        weixiu_jianyi.setText(weixiu_yijian);//维修意见
    }

    private void setOrderDetailPeopleInfo(OrderAddress orderAddress, OrderDetailInfo orderDetailInfo) {

        textview_order_info_name_content.setText(orderAddress.getName());//姓名
        textview_order_info_mobile_content.setText(orderAddress.getPhone());//电话
        textview_order_info_car_num.setText(orderAddress.getCarNum());//车牌号
        textview_order_info_car_type.setText(orderDetailInfo.getCarName());//车型
        textview_order_info_install_shop.setText(orderDetailInfo.getShopName());//安装门店
        textview_order_info_install_time.setText(orderDetailInfo.getInstallTime());//安装时间

    }

    private void setOrderPrice(OrderPrice orderPrice) {
        order_info_order_num.setText(orderPrice.getOrderNum());//订单号
        order_total_price.setText(orderPrice.getTotalPrice() + "");//订单总价
        order_word_time_cost.setText(orderPrice.getInstallCoast() + "");//工时费
    }

    public class OrderDetailListViewAdapter extends BaseAdapter {

        List<Category> mpplist;
        int from;
        Context mPContext;

        private static final int TYPE_CATEGORY_ITEM = 0;
        private static final int TYPE_ITEM = 1;

        public OrderDetailListViewAdapter(List<Category> json2ProductPackageBeanList, Context context) {
            this.mpplist = json2ProductPackageBeanList;
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


    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }



}
