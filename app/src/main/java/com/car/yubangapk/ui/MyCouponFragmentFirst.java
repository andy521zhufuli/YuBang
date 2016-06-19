package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.utils.L;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class MyCouponFragmentFirst extends Fragment implements PullToRefreshBase.OnRefreshListener2, HttpReqCallback{
    PullToRefreshListView listview;
    private Context mContext;
    private LinearLayout nothing_layout;//什么都没有
    private TextView    text_empty;//什么都没有的内容原因
    String mUserId;
    String mType;

    MyCouponListAdapter mCouponAdapter;


    public MyCouponFragmentFirst() {
    }


    public MyCouponFragmentFirst(String type) {
        this.mType = type;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mUserId = Configs.getLoginedInfo(mContext).getUserid();
        L.e("TAG ", " 1 onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();
        L.e("TAG ", " 1 onCreateView");
        View view = inflater.inflate(R.layout.activity_my_coupon_fragment_first, container, false);
        listview = (PullToRefreshListView) view.findViewById(R.id.my_coupon_refresh_listview);
        nothing_layout = (LinearLayout) view.findViewById(R.id.nothing_layout);
        text_empty  = (TextView) view.findViewById(R.id.text_empty);
        listview.onRefreshComplete();
        listview.setOnRefreshListener(this);
        List<String> couponList = new ArrayList<>();
        couponList.add("11");
        couponList.add("11");
        couponList.add("11");
        couponList.add("11");
        couponList.add("11");
        mCouponAdapter = new MyCouponListAdapter(couponList, couponList.size());
        listview.setAdapter(mCouponAdapter);
        return view;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }


    @Override
    public void onFail(int errorCode, String message) {

    }

    @Override
    public void onSuccess(Object object) {

    }


    class MyCouponListAdapter extends BaseAdapter
    {

        private List<String> myOrderBeans;
        int count;
        private FragmentActivity mcontext;
        LayoutInflater inflater;

        public MyCouponListAdapter(List<String> list, int count)
        {
            this.myOrderBeans = list;
            this.count = count;

            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void refresh(List<String> list, int count)
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
                view = inflater.inflate(R.layout.item_wallet_coupon, null);
                holder.coupon_item_layout = (RelativeLayout) view.findViewById(R.id.coupon_item_layout);
                holder.coupon_item_icon = (ImageView) view.findViewById(R.id.coupon_item_icon);
                holder.coupon_item_value = (TextView) view.findViewById(R.id.coupon_item_value);
                holder.coupon_item_quota = (TextView) view.findViewById(R.id.coupon_item_quota);
                holder.coupon_item_limit = (TextView) view.findViewById(R.id.coupon_item_limit);
                holder.coupon_item_date = (TextView) view.findViewById(R.id.coupon_item_date);
                holder.coupon_item_typeicon = (ImageView) view.findViewById(R.id.coupon_item_typeicon);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }



            //这里图片就默认了
//            String url = Configs.IP_ADDRESS
//                    + Configs.IP_ADDRESS_ACTION_GETFILE
//                    + "?fileReq.pathCode=" + myOrderBeans.get(position).getPathCode()
//                    + "&fileReq.fileName=" + myOrderBeans.get(position).getShopPhoto();
//            ImageLoaderTools.getInstance(mContext).displayImage(url, holder.coupon_item_icon);
            return view;
        }

        class ViewHolder
        {
            RelativeLayout  coupon_item_layout;//整个优惠券的布局
            ImageView       coupon_item_icon;//优惠券的图标
            TextView        coupon_item_value;//优惠券的金额
            TextView        coupon_item_quota;//优惠券满减条件
            TextView        coupon_item_limit;//优惠券显示条件, 比如哪个店铺
            TextView        coupon_item_date;//优惠券使用时间  起止时间
            ImageView       coupon_item_typeicon;//优惠券的类型  比如是否过期
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        L.e("TAG ", " 1 onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        L.e("TAG ", " 1 onStop");
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.e("TAG ", " 1 onActivityCreated");
    }


    @Override
    public void onResume() {
        super.onResume();
        L.e("TAG ", " 1 onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.e("TAG ", " 1 onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.e("TAG ", " 1 onDestroy");

    }



}
