package com.car.yubangapk.ui.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2MyOrderBean;
import com.car.yubangapk.json.bean.MyOrderBean;
import com.car.yubangapk.network.myHttpReq.HttpGetOrders;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.swipetoloadlayout.SwipeToLoadLayout;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersFragment2 extends Fragment {
	TextView tv;
	String text;

    HttpGetOrders mGetOrders;


	private OrderListAdapter mAdapter;
    SwipeToLoadLayout swipeToLoadLayout;
    PullToRefreshListView my_order_listview;

    List<MyOrderBean> mMyOrderList = new ArrayList<>();

    private String mType;


    String ALL_ORDER = "全部订单";
    String WAIT_BUYER = "待买家付款";
    String WAIT_SHOP_CONFIRM = "待店家确认";
    String WAIT_SHOP_INSTALL = "待店家安装";
    String WAIT_BUYER_CONFIRM = "待买家确认";
    String DEAL_SUCCESS = "交易成功";
    String DEAL_FAIL = "交易失败";

    String mUserId ;

    Context mContext;
    public MyOrdersFragment2()
    {

    }


	public MyOrdersFragment2(String type){
        this.mType = type;
        mGetOrders = new HttpGetOrders();
        mGetOrders.setCallback(new GetOrders());

	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
        this.mContext = context;
        mUserId = Configs.getLoginedInfo(mContext).getUserid();
	}

	@Override
	public void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lay4,container,false);

        my_order_listview = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);



        my_order_listview
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                        L.e("TAG", "onPullDownToRefresh");
                        //这里写下拉刷新的任务
                        new GetDataTask().execute();
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                        L.e("TAG", "onPullUpToRefresh");
                        //这里写上拉加载更多的任务
                        new GetDataTask().execute();
                    }
                });
        firstGetOrder();//进来 首次加载订单 加载全部订单
		return view;
	}


    private void firstGetOrder()
    {
        judgeUserid();
        mGetOrders.getOrders(mUserId, "1", "10", null);
    }

    private void judgeUserid() {
        if (mUserId == null || mUserId.equals(""))
        {
            NotLogin.gotoLogin(getActivity());
        }
    }

    class GetOrders implements HttpReqCallback
    {

        @Override
        public void onFail(int errorCode, String message) {
            if (errorCode == 100)
            {
                NotLogin.gotoLogin(getActivity());
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
            {
                toastMgr.builder.display(message, 1);
            }
            else
            {
                toastMgr.builder.display(message,1);
            }
        }

        @Override
        public void onSuccess(Object object) {
            Json2MyOrderBean orderBean = (Json2MyOrderBean) object;
            mMyOrderList = orderBean.getRows();
            mAdapter = new OrderListAdapter(getActivity(),mMyOrderList, mMyOrderList.size());
            my_order_listview.setAdapter(mAdapter);

        }
    }



    class OrderListAdapter extends BaseAdapter
    {

        private List<MyOrderBean> myOrderBeans;
        int count;
        private FragmentActivity mcontext;
        LayoutInflater inflater;

        public OrderListAdapter(FragmentActivity context, List<MyOrderBean> list, int count)
        {
            this.myOrderBeans = list;
            this.count = count;
            this.mcontext = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void refresh(List<MyOrderBean> list, int count)
        {
            this.myOrderBeans = list;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return myOrderBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return myOrderBeans.get(position);
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
                view = inflater.inflate(R.layout.item_order_list_item, null);
                holder.order_item_Text = (TextView) view.findViewById(R.id.order_item_Text);
                holder.order_item_totalPrice = (TextView) view.findViewById(R.id.order_item_totalPrice);
                holder.order_item_subtime = (TextView) view.findViewById(R.id.order_item_subtime);
                holder.order_item_name = (TextView) view.findViewById(R.id.order_item_name);
                holder.order_item_status = (TextView) view.findViewById(R.id.order_item_status);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }

            holder.order_item_Text.setText(myOrderBeans.get(position).getOrderNumber());
            holder.order_item_totalPrice.setText(myOrderBeans.get(position).getOrderMoney());
            holder.order_item_status.setText(myOrderBeans.get(position).getOrderStatusName());
            holder.order_item_name.setText(myOrderBeans.get(position).getOrderName());
            holder.order_item_subtime.setText(myOrderBeans.get(position).getOrderTime());

            return view;
        }

        class ViewHolder
        {
            TextView        order_item_Text;//订单号
            TextView        order_item_totalPrice;//定案金额
            TextView        order_item_subtime;//下单时间
            TextView        order_item_name;//名单种类
            TextView        order_item_status;//状态

        }
    }


    private class GetDataTask extends AsyncTask<Void, Void, String>
    {

        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result)
        {

            mAdapter.notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            my_order_listview.onRefreshComplete();
        }
    }
}
