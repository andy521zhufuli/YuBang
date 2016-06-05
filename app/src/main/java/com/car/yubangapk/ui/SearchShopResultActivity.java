package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2FirstPageShopBean;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqSearchShop;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

public class SearchShopResultActivity extends BaseActivity implements HttpReqCallback, PullToRefreshBase.OnRefreshListener2 {

    private Context mContext;
    private final String     TAG = "SearchShopResultActivity";
    ImageView img_back;//返回
    PullToRefreshListView search_result_pull_refresh_list;//
    HttpReqSearchShop mHttpReqSearchShop;
    SearchResultListViewAdapter     mListViewAdapter;

    boolean                         isFirstRequest = true;

    String                          mCarType;
    String                          mHotWord;
    String                          mUserid;
    int                             mCurrentPage = 1;
    double                          mLat;
    double                          mLon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_shop_result);


        mContext = this;

        findViews();

        Bundle bundle = getIntent().getExtras();
        //mCarType = bundle.getString("carType");
        mHotWord = bundle.getString("word");
        mLat = bundle.getDouble("latitude");
        mLon = bundle.getDouble("longitude");
        //mUserid = bundle.getString("userid");


        mHttpReqSearchShop = new HttpReqSearchShop();
        mHttpReqSearchShop.setCallback(this);


        //第一次请求
        getSearchResultByWord(mCurrentPage, 8, mHotWord);

    }


    private void findViews() {
        img_back = (ImageView) findViewById(R.id.img_back);//返回
        search_result_pull_refresh_list = (PullToRefreshListView) findViewById(R.id.search_result_pull_refresh_list);//

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void getSearchResultByWord(int page, int row, String word) {
        mHttpReqSearchShop.searchShop(page, row, word, mLat, mLon);
    }

    @Override
    public void onFail(int errorCode, String message) {
        toastMgr.builder.display(message, 1);
        if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
        {
            NotLogin.gotoLogin(SearchShopResultActivity.this);
        }
        search_result_pull_refresh_list.onRefreshComplete();
    }

    @Override
    public void onSuccess(Object object) {
        List<Json2FirstPageShopBean> json2FirstPageShopBeanList  = (List<Json2FirstPageShopBean>) object;
        mCurrentPage++;
        if (isFirstRequest)
        {
            initIndicator();
            mListViewAdapter = new SearchResultListViewAdapter(json2FirstPageShopBeanList);
            search_result_pull_refresh_list.setOnRefreshListener(this);
            search_result_pull_refresh_list.setAdapter(mListViewAdapter);

            isFirstRequest = false;
        }
        else
        {
            search_result_pull_refresh_list.onRefreshComplete();
            List<Json2FirstPageShopBean> list = mListViewAdapter.getList();
            for (Json2FirstPageShopBean row : json2FirstPageShopBeanList) {
                list.add(row);
            }
            mListViewAdapter.refresh(list);
        }
    }

    private void initIndicator()
    {
        ILoadingLayout startLabels = search_result_pull_refresh_list
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("松开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = search_result_pull_refresh_list.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("松开加载更多...");// 下来达到一定距离时，显示的提示
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getSearchResultByWord(mCurrentPage+1, 8, mHotWord);
    }


    class SearchResultListViewAdapter extends BaseAdapter
    {

        private List<Json2FirstPageShopBean> shopResultList;

        public SearchResultListViewAdapter(List<Json2FirstPageShopBean> list)
        {
            this.shopResultList = list;
        }

        public void refresh(List<Json2FirstPageShopBean> list)
        {
            this.shopResultList = list;
            notifyDataSetChanged();
        }

        public List<Json2FirstPageShopBean> getList()
        {
            return this.shopResultList;
        }


        @Override
        public int getCount() {
            return shopResultList.size();
        }

        @Override
        public Object getItem(int position) {
            return shopResultList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {

             /*
             * 1.手工创建对象 2.加载xml文件
             */
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(mContext, R.layout.item_search_shop_result, null);

                holder.im_shop_img                  = (ImageView) view.findViewById(R.id.im_shop_img);
                holder.shopbusinesstype             = (TextView) view.findViewById(R.id.shopbusinesstype);
                holder.tv_shop_name                 = (TextView) view.findViewById(R.id.tv_shop_name);
                holder.tv_shop_juli                 = (TextView) view.findViewById(R.id.tv_shop_juli);
                holder.tv_evaluate_text             = (TextView) view.findViewById(R.id.tv_evaluate_text);
                holder.tv_shop_points               = (TextView) view.findViewById(R.id.tv_shop_points);
                holder.tv_shop_address              = (TextView) view.findViewById(R.id.tv_shop_address);
                holder.zfb                          = (ImageView) view.findViewById(R.id.zfb);
                holder.wx                           = (ImageView) view.findViewById(R.id.wx);
                holder.yhk                          = (ImageView) view.findViewById(R.id.yhk);
                holder.xj                           = (ImageView) view.findViewById(R.id.xj);
                holder.search_shop_result_item      = (LinearLayout) view.findViewById(R.id.search_shop_result_item);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }
            String pathcode = shopResultList.get(position).getPathCode();
            String photoname = shopResultList.get(position).getShopPhoto();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;
            ImageLoaderTools.getInstance(mContext).displayImage(url, holder.im_shop_img);

            holder.tv_shop_name.setText(shopResultList.get(position).getShopName());

            holder.tv_shop_juli.setText(shopResultList.get(position).getDistance()+"米");

            holder.tv_evaluate_text.setText(shopResultList.get(position).getStar());
            holder.tv_shop_points.setText(shopResultList.get(position).getOrderNum() + "单");

            holder.tv_shop_address.setText(shopResultList.get(position).getShopAddress());

            holder.search_shop_result_item.setOnClickListener(new OnItemClick(shopResultList.get(position)));
            return view;
        }

        class ViewHolder
        {
            ImageView           im_shop_img;//店铺的照片

            TextView            shopbusinesstype;//店铺类型  快修店  这个没参数  不修改

            TextView            tv_shop_name;//店铺名字

            TextView            tv_shop_juli;//店铺距离当前位置距离

            TextView            tv_evaluate_text;//店铺评价分数

            TextView            tv_shop_points;//店铺接单数

            TextView            tv_shop_address;//店铺地址信息

            ImageView           zfb;//店铺支持的支付类型         支付宝

            ImageView           wx;//店铺支持的支付类型          微信

            ImageView           yhk;//店铺支持的支付类型         银行卡

            ImageView           xj;//店铺支持的支付类型          现金

            LinearLayout        search_shop_result_item;//
        }
    }

    class OnItemClick implements View.OnClickListener
    {
        Json2FirstPageShopBean row;

        public OnItemClick(Json2FirstPageShopBean _row)
        {
            this.row = _row;
        }

        @Override
        public void onClick(View view) {

            Bundle bundle = new Bundle();
            bundle.putSerializable("shopBean", row);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setClass(SearchShopResultActivity.this, FirstPageShopShowActivity.class);
            SearchShopResultActivity.this.startActivity(intent);
        }
    }

}
