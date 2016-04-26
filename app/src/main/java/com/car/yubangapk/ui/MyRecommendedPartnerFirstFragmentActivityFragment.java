package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2MyRecommendPartnersBean;
import com.car.yubangapk.json.bean.MyOrderBean;
import com.car.yubangapk.json.bean.MyRecommendPartnersBean;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqGetMyRecommendPartners;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyRecommendedPartnerFirstFragmentActivityFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2{

    PullToRefreshListView listview;
    private Context mContext;
    private LinearLayout nothing_layout;//什么都没有
    String mUserId;

    RecommendPartnerListAdapter mAdapter;

    HttpReqGetMyRecommendPartners mGetMyRecommendPartners;

    public MyRecommendedPartnerFirstFragmentActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mUserId = Configs.getLoginedInfo(mContext).getUserid();
        mGetMyRecommendPartners = new HttpReqGetMyRecommendPartners();
        mGetMyRecommendPartners.setCallback(new GetRecommendsHttp());
        L.e("TAG ", " 1 onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();
        L.e("TAG ", " 1 onCreateView");
        View view = inflater.inflate(R.layout.fragment_my_recommended_partner_first, container, false);
        listview = (PullToRefreshListView) view.findViewById(R.id.my_recommended_partner_pull_refresh_list);
        nothing_layout = (LinearLayout) view.findViewById(R.id.nothing_layout);
        listview.onRefreshComplete();
        listview.setOnRefreshListener(this);
        listview.setRefreshing(true);
        mGetMyRecommendPartners.getRecommendPartners(mUserId, "1", "10", null);

        return view;
    }


    // 定义图片的资源

    private void judgeUserid() {
        if (mUserId == null || mUserId.equals(""))
        {
            NotLogin.gotoLogin(getActivity());
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        //每次下拉就去拿第一页
        judgeUserid();
        mGetMyRecommendPartners.getRecommendPartners(mUserId, "1", "10", null);
    }



    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }

    class GetRecommendsHttp implements HttpReqCallback
    {

        @Override
        public void onFail(int errorCode, String message) {
            if (errorCode == 100) {
                NotLogin.gotoLogin(getActivity());
            }
            else
            {
                nothing_layout.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
                toastMgr.builder.display(message, 1);
            }


        }

        @Override
        public void onSuccess(Object object) {
            nothing_layout.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
            Json2MyRecommendPartnersBean recommendPartnersBean = (Json2MyRecommendPartnersBean) object;
            mAdapter = new RecommendPartnerListAdapter(recommendPartnersBean.getRows(), recommendPartnersBean.getRows().size());
            listview.setAdapter(mAdapter);
        }
    }

    class RecommendPartnerListAdapter extends BaseAdapter
    {

        private List<MyRecommendPartnersBean> myOrderBeans;
        int count;
        private FragmentActivity mcontext;
        LayoutInflater inflater;

        public RecommendPartnerListAdapter(List<MyRecommendPartnersBean> list, int count)
        {
            this.myOrderBeans = list;
            this.count = count;

            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void refresh(List<MyRecommendPartnersBean> list, int count)
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
                view = inflater.inflate(R.layout.item_my_recommend_partners_item, null);
                holder.my_recommended_partners_shop_name = (TextView) view.findViewById(R.id.my_recommended_partners_shop_name);
                holder.my_recommended_partners_shop_address = (TextView) view.findViewById(R.id.my_recommended_partners_shop_address);
                holder.my_recommended_partners_phone_num = (TextView) view.findViewById(R.id.my_recommended_partners_phone_num);

                holder.my_recommended_partners_shop_image = (ImageView) view.findViewById(R.id.my_recommended_partners_shop_image);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }

            holder.my_recommended_partners_shop_name.setText(myOrderBeans.get(position).getShopName());
            holder.my_recommended_partners_shop_address.setText(myOrderBeans.get(position).getShopAddress());
            holder.my_recommended_partners_phone_num.setText(myOrderBeans.get(position).getPhoneNum());

            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + myOrderBeans.get(position).getPathCode() + "&fileReq.fileName=" + myOrderBeans.get(position).getShopPhoto();
            ImageLoaderTools.getInstance(mContext).displayImage(url, holder.my_recommended_partners_shop_image);
            return view;
        }

        class ViewHolder
        {
            TextView        my_recommended_partners_shop_name;//门店名字
            TextView        my_recommended_partners_shop_address;//地址
            TextView        my_recommended_partners_phone_num;//电话

            ImageView       my_recommended_partners_shop_image;//照片
        }
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



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            L.e("TAG ", " 1 setUserVisibleHint");
        }
        else
        {
            L.e("TAG ", " 1 invisible");
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


}
