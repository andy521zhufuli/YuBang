package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqGetMyRecommendPartners;
import com.car.yubangapk.utils.L;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MyCouponFragmentFirst extends Fragment implements PullToRefreshBase.OnRefreshListener2, HttpReqCallback{
    PullToRefreshListView listview;
    private Context mContext;
    private LinearLayout nothing_layout;//什么都没有
    private TextView    text_empty;//什么都没有的内容原因
    String mUserId;
    String mType;

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
