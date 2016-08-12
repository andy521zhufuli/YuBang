package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompatSideChannelService;
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
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.wallet.CouponRow;
import com.car.yubangapk.json.bean.wallet.Json2MyCouponBean;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.wallet.HttpReqGetMyCoupon;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.view.CustomProgressDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCouponFragmentFirst extends Fragment implements PullToRefreshBase.OnRefreshListener2, HttpReqCallback{
    PullToRefreshListView listview;
    private Context mContext;
    private LinearLayout nothing_layout;//什么都没有
    private TextView    text_empty;//什么都没有的内容原因
    String mUserId;
    String mType;

    private int mCurrentRequestCouponState = Configs.COUPON_STATE_NOT_USED;


    MyCouponListAdapter mCouponAdapter = null;

    HttpReqGetMyCoupon mGetCouponHttpReq;

    private int mCurrentPage = 1;
    private int row = 10;


    CustomProgressDialog mProgress;

    public MyCouponFragmentFirst() {
    }


    public MyCouponFragmentFirst(String type)
    {
        this.mType = type;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mUserId = Configs.getLoginedInfo(mContext).getUserid();
        mProgress = new CustomProgressDialog(mContext);
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
        text_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickToReload();
            }
        });
        listview.onRefreshComplete();
        listview.setOnRefreshListener(this);
        List<String> couponList = new ArrayList<>();
        couponList.add("11");
        couponList.add("11");
        couponList.add("11");
        couponList.add("11");
        couponList.add("11");
        //这是网络请求的参数
        mGetCouponHttpReq = new HttpReqGetMyCoupon();
        mGetCouponHttpReq.setCallback(this);
        //在这里去请求网络
        firstRequestMyCoupon("未使用");
        mProgress = mProgress.show(mContext, "正在加载...", false, null);


        return view;
    }

    /**
     * 点击重新加载
     */
    private void clickToReload()
    {
        firstRequestMyCoupon("未使用");
        mProgress = mProgress.show(mContext, "正在加载...", false, null);
        listview.setVisibility(View.VISIBLE);
        nothing_layout.setVisibility(View.GONE);
    }

    private int isFirstLoadOrPullUpLoad = 0;
    private int FIRSTLOAD = 0X11;//首次加载
    private int PULL_UP_LOAD = 0X22;//上拉加载

    /**
     * 首次请求 我的优惠券  全部
     */
    private void firstRequestMyCoupon(String couponType) {
        isFirstLoadOrPullUpLoad = FIRSTLOAD;
        if (couponType.equals("未使用"))
        {
            mCurrentRequestCouponState = Configs.COUPON_STATE_NOT_USED;
        }
        else if (couponType.equals("已使用"))
        {
            mCurrentRequestCouponState = Configs.COUPON_STATE_USED;
        }
        else if (couponType.equals("已过期"))
        {
            mCurrentRequestCouponState = Configs.COUPON_STATE_OUT_DATE;
        }

        String userid = Configs.getLoginedInfo(mContext).getUserid();
        mGetCouponHttpReq.getMyCoupon(userid,mCurrentPage, row, mType, couponType, getCurrentScrop(mType));
    }


    /**
     * 上拉加载的时候  调用这个
     * @param couponType
     */
    private void pullUpToLoadMyCoupon(String couponType)
    {
        isFirstLoadOrPullUpLoad = PULL_UP_LOAD;
        if (couponType.equals("未使用"))
        {
            mCurrentRequestCouponState = Configs.COUPON_STATE_NOT_USED;
        }
        else if (couponType.equals("已使用"))
        {
            mCurrentRequestCouponState = Configs.COUPON_STATE_USED;
        }
        else if (couponType.equals("已过期"))
        {
            mCurrentRequestCouponState = Configs.COUPON_STATE_OUT_DATE;
        }

        String userid = Configs.getLoginedInfo(mContext).getUserid();
        mCurrentPage += 1;
        mGetCouponHttpReq.getMyCoupon(userid,mCurrentPage, row, mType, couponType, getCurrentScrop(mType));
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView)
    {
        mProgress = mProgress.show(mContext, "正在加载...", false, null);
        pullUpToLoadMyCoupon("未使用");
    }


    @Override
    public void onFail(int errorCode, String message) {

        if (isFirstLoadOrPullUpLoad == FIRSTLOAD)
        {
            //没有数据
            if (mCurrentRequestCouponState == Configs.COUPON_STATE_NOT_USED)
            {
                //未使用没有数据
                mCouponData.put(Configs.COUPON_STATE_NOT_USED, null);
                firstRequestMyCoupon("已使用");

            }
            else if (mCurrentRequestCouponState == Configs.COUPON_STATE_USED)
            {
                //已使用  没有数据
                mCouponData.put(Configs.COUPON_STATE_USED, null);
                firstRequestMyCoupon("已过期");
            }
            else
            {
                //已过期 没有数据
                mCouponData.put(Configs.COUPON_STATE_OUT_DATE, null);
                //判断mCouponData是否有数据,  如果其中一个有数据, 就显示  如果都没有数据  那就显示什么都没有
                List<CouponRow> notUsed = mCouponData.get(Configs.COUPON_STATE_NOT_USED);
                List<CouponRow> used = mCouponData.get(Configs.COUPON_STATE_USED);
                List<CouponRow> outDate = mCouponData.get(Configs.COUPON_STATE_OUT_DATE);
                if (notUsed == null && used == null && outDate == null)
                {
                    nothing_layout.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.GONE);
                    text_empty.setText("什么都没有呢!点击这里加载.");
                }
                else
                {
                    //这里, 有可能第三次请求是失败的  但是前两次某一次是成功的,  所以有可能在这里new adapter
                    if (mCouponAdapter == null)
                    {
                        mCouponAdapter = new MyCouponListAdapter(mCouponData, mCouponData.size());
                        listview.setAdapter(mCouponAdapter);
                    }
                    else
                    {
                        listview.setAdapter(mCouponAdapter);
                    }
                }
                mProgress.dismiss();
            }
        }
        else//上拉加载
        {
            //没有数据
            if (mCurrentRequestCouponState == Configs.COUPON_STATE_NOT_USED)
            {
                //未使用没有数据

                mCouponData.put(Configs.COUPON_STATE_NOT_USED, null);

            }
            else if (mCurrentRequestCouponState == Configs.COUPON_STATE_USED)
            {
                //已使用  没有数据
                mCouponData.put(Configs.COUPON_STATE_USED, null);
            }
            else
            {
                //已过期 没有数据
                mCouponData.put(Configs.COUPON_STATE_OUT_DATE, null);
            }
        }






    }

    private Map<Integer, List<CouponRow>> mCouponData = new HashMap<>();
    @Override
    public void onSuccess(Object object) {
        Json2MyCouponBean couponBean = (Json2MyCouponBean) object;
        if (mCurrentRequestCouponState == Configs.COUPON_STATE_NOT_USED)
        {
            mCouponData.put(Configs.COUPON_STATE_NOT_USED, couponBean.getRows());
            //去获取已使用的优惠券
            firstRequestMyCoupon("已使用");
        }
        else if (mCurrentRequestCouponState == Configs.COUPON_STATE_USED)
        {
            mCouponData.put(Configs.COUPON_STATE_USED, couponBean.getRows());
            //去获取已过期的优惠券
            firstRequestMyCoupon("已过期");
        }
        else
        {
            mCouponData.put(Configs.COUPON_STATE_OUT_DATE, couponBean.getRows());
            //所有类型的优惠券都已经获取完毕
            if (mCouponAdapter == null)
            {
                mCouponAdapter = new MyCouponListAdapter(mCouponData, mCouponData.size());
                listview.setAdapter(mCouponAdapter);
            }
            else
            {
                listview.setAdapter(mCouponAdapter);
            }
            mProgress.dismiss();
        }

    }

    /**
     * 优惠券列表的适配器
     */
    class MyCouponListAdapter extends BaseAdapter
    {

        private Map<Integer, List<CouponRow>> myOrderBeans;
        int count;
        private FragmentActivity mcontext;
        LayoutInflater inflater;

        public MyCouponListAdapter(Map<Integer, List<CouponRow>> list, int count)
        {
            this.myOrderBeans = list;
            this.count = count;

            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void refresh(Map<Integer, List<CouponRow>> list, int count)
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

    private int getCurrentScrop(String type)
    {
        if (type.equals("全部"))
        {
            return -1;
        }
        else if (type.equals("总价"))
        {
            return 0;
        }
        else if (type.equals("商铺"))
        {
            return 1;
        }
        else if (type.equals("产品包"))
        {
            return 2;
        }
        else if (type.equals("产品"))
        {
            return 3;
        }
        return -2;
    }

}
