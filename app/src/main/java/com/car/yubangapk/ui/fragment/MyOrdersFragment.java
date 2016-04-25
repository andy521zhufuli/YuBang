package com.car.yubangapk.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.json.bean.AddressBean;
import com.car.yubangapk.swipetoloadlayout.OnLoadMoreListener;
import com.car.yubangapk.swipetoloadlayout.OnRefreshListener;
import com.car.yubangapk.swipetoloadlayout.SwipeToLoadLayout;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyOrdersFragment extends ListFragment {
	TextView tv;
	String text;


	private OrderListAdapter mAdapter;
    SwipeToLoadLayout swipeToLoadLayout;
    ListView          my_order_listview;

    List<String> list = new ArrayList<>();

    Context mContext;
    public MyOrdersFragment()
    {
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
    }


	public MyOrdersFragment(String text){
		this.text=text;
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
        this.mContext = context;
	}

	@Override
	public void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mAdapter = new OrderListAdapter(getActivity(), list, list.size());
        setListAdapter(mAdapter);

    }

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.my_order_fragment, container, false);


//        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
//        my_order_listview = (ListView) view.findViewById(android.R.id.list);
//        mAdapter = new OrderListAdapter(list, list.size());
//
//        my_order_listview.setAdapter(mAdapter);
//
//        my_order_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    if (view.getLastVisiblePosition() == view.getCount() - 1 && !ViewCompat.canScrollVertically(view, 1)) {
//                        swipeToLoadLayout.setLoadingMore(true);
//                    }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//
//        });
//
//        swipeToLoadLayout.setOnRefreshListener(this);
//        swipeToLoadLayout.setOnLoadMoreListener(this);
		return view;
	}

	@Override
	public void onViewCreated(View view,  Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);


	}


    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        swipeToLoadLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeToLoadLayout.setRefreshing(true);
//            }
//        });
    }

    @Override
	public void onResume() {
		super.onResume();
        toastMgr.builder.display("onResume", 1);
	}





    class OrderListAdapter extends BaseAdapter
    {

        private List<String> peopleList;
        int count;
        private Context mcontext;

        public OrderListAdapter(Context context, List<String> list, int count)
        {
            this.peopleList = list;
            this.count = count;
            this.mcontext = context;
        }

        public void refresh(List<String> list, int count)
        {
            this.peopleList = list;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return peopleList.get(position);
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
                view = View.inflate(mcontext, R.layout.item_address_item, null);
                holder.tv_address_item_name = (TextView) view.findViewById(R.id.tv_address_item_name);
                holder.tv_address_item_phone = (TextView) view.findViewById(R.id.tv_address_item_phone);
                holder.tv_address_item_default = (TextView) view.findViewById(R.id.tv_address_item_default);
                holder.modify_address_layout = (LinearLayout) view.findViewById(R.id.modify_address_layout);
                holder.relatalayout_address_item = (RelativeLayout) view.findViewById(R.id.relatalayout_address_item);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }





            return view;
        }

        class ViewHolder
        {
            TextView        tv_address_item_name;//名字
            TextView        tv_address_item_phone;//电话
            TextView        tv_address_item_default;//是否默认
            RelativeLayout  relatalayout_address_item;//item
            LinearLayout    modify_address_layout;//编辑地址
        }
    }
}
