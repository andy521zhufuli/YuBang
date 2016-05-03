package com.car.yubangapk.ui.firstpagefragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2MyOrderBean;
import com.car.yubangapk.json.bean.MyOrderBean;
import com.car.yubangapk.network.myHttpReq.HttpGetMyOrders;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.swipetoloadlayout.SwipeToLoadLayout;
import com.car.yubangapk.ui.myordersfragment.MyOrdersActivity;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class FirstPageFragment extends Fragment {

    HttpGetMyOrders mGetOrders;


    SwipeToLoadLayout swipeToLoadLayout;
    PullToRefreshListView my_order_listview;
    private LinearLayout    nothing_layout;//什么都没有
    private TextView        text_empty;//文本显示

    List<MyOrderBean> mMyOrderList = new ArrayList<>();


    private String mType;
    private boolean mIsPullUp = false;//是不是上啦加载
    private int     mCurrentPage = 1;

    String mUserId ;

    Context mContext;
    public FirstPageFragment()
    {

    }


	public FirstPageFragment(String type){
        this.mType = type;
        mGetOrders = new HttpGetMyOrders();


	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
        this.mContext = context;
        mUserId = Configs.getLoginedInfo(mContext).getUserid();
        L.e("TAG " + mType, "onAttach");
	}

	@Override
	public void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        L.e("TAG " + mType, "onCreate");


    }

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {

        L.e("TAG " + mType, "onCreateView");
        View view = inflater.inflate(R.layout.lay4, container, false);

        my_order_listview = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        nothing_layout = (LinearLayout) view.findViewById(R.id.nothing_layout);
        text_empty = (TextView) view.findViewById(R.id.text_empty);



        if (isFirstVisibleToUser == false)
        {
            //这里是被销毁了 重新createView  再去拿数据  显示

        }

		return view;
	}


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.e("TAG " + mType, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        L.e("TAG " + mType, "onStart 是不是此时才可见");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.e("TAG-- " + mType, "onResume 是不是此时才可见");
        MobclickAgent.onPageStart("myOrderFragment" + mType);
    }


    @Override
    public void onPause() {
        super.onPause();
        L.e("TAG-- " + mType, "onPause ");
        MobclickAgent.onPageEnd("myOrderFragment" + mType);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.e("TAG-- " + mType, "onDestroyView ");
        isFirstVisibleToUser = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.e("TAG-- " + mType, "onDestroy ");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            L.e("TAG " + mType, "setUserVisibleHint ");
            //当前对用户是可见的
            if (isFirstVisibleToUser)
            {

                L.e("TAG " + mType, "这里是第一次可见 ");
                isFirstVisibleToUser = true;

            }
            else
            {
                //不是第一次可见  已经过时返回来之后
                L.e("TAG " + mType, "不是第一次可见  已经过时返回来之后 ");
            }

        }
        else
        {
            //当前对用户不可见
            L.e("TAG " + mType, "当前对用户不可见 ");
        }
    }


    private boolean isFirstVisibleToUser = true;




    private String mRequestStatus;
    private String getRequestType()
    {

        if(mType.equals(MyOrdersActivity.ALL_ORDER))
        {
            mRequestStatus = null;
        }
        else if (mType.equals(MyOrdersActivity.WAIT_SIGN))
        {
            mRequestStatus = "1";
        }
        else if (mType.equals(MyOrdersActivity.WAIT_BUYER))
        {
            mRequestStatus = "2";
        }
        else if (mType.equals(MyOrdersActivity.WAIT_SHOP_INSTALL))
        {
            mRequestStatus = "3";
        }
        else if (mType.equals(MyOrdersActivity.INSTALLED))
        {
            mRequestStatus = "4";
        }
        else if (mType.equals(MyOrdersActivity.WAIT_EVALUATE))
        {
            mRequestStatus = "5";
        }
        else if (mType.equals(MyOrdersActivity.DEAL_SUCCESS))
        {
            mRequestStatus = "6";
        }
        else if (mType.equals(MyOrdersActivity.DEAL_FAIL))
        {
            mRequestStatus = "7";
        }
        return mRequestStatus;
    }
}
