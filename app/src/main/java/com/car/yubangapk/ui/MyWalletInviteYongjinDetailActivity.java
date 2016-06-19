package com.car.yubangapk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.view.CustomProgressDialog;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

public class MyWalletInviteYongjinDetailActivity extends BaseActivity implements View.OnClickListener,HttpReqCallback
{


    private Context                 mContext;
    private ImageView               img_back;
    private PullToRefreshListView   my_wallet_invite_yongjin_detail_refresh_listview;
    private LinearLayout            nothing_layout;
    private TextView                text_empty;

    private CustomProgressDialog    mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_wallet_invite_yongjin_detail);
        mContext = this;
        mProgress = new CustomProgressDialog(mContext);
        findViews();
        //第一次请求
        firstRequestYongjinDetail();
    }

    /**
     * 进入界面第一次请求佣金明细
     */
    private void firstRequestYongjinDetail() {
        mProgress = mProgress.show(mContext, "正在加载...",false, null);
    }

    private void findViews() {
        img_back = (ImageView) findViewById(R.id.img_back);

        my_wallet_invite_yongjin_detail_refresh_listview = (PullToRefreshListView) findViewById(R.id.my_wallet_invite_yongjin_detail_refresh_listview);
        nothing_layout = (LinearLayout) findViewById(R.id.nothing_layout);
        text_empty = (TextView) findViewById(R.id.text_empty);

        //监听器

        img_back.setOnClickListener(this);
        text_empty.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.text_empty:
                //这里应该是点击重新加载
                break;
        }
    }

    /**
     * 请求网络返回错误
     * @param errorCode
     * @param message
     */
    @Override
    public void onFail(int errorCode, String message) {
        mProgress.dismiss();
    }

    /**
     * 请求网络返回成功
     * 成功之后加载信息, 显示列表
     * @param object
     */
    @Override
    public void onSuccess(Object object) {
        mProgress.dismiss();
    }


    /**
     * 加载列表的 adapter
     */
    class MyWalletInviteYongjinDetailListAdapter extends BaseAdapter
    {

        private List<String> myInviteYongjinDetail;
        int count;
        private FragmentActivity mcontext;
        LayoutInflater inflater;

        public MyWalletInviteYongjinDetailListAdapter(List<String> list, int count)
        {
            this.myInviteYongjinDetail = list;
            this.count = count;

            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void refresh(List<String> list, int count)
        {
            this.myInviteYongjinDetail = list;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return myInviteYongjinDetail.size();
        }

        @Override
        public Object getItem(int position) {
            return myInviteYongjinDetail.get(position);
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
                view = inflater.inflate(R.layout.item_wallet_invite_yongjin_detail, null);
                holder.intive_yongjin_item_layout = (RelativeLayout) view.findViewById(R.id.coupon_item_layout);
                holder.intive_yongjin_item_icon   = (ImageView) view.findViewById(R.id.intive_yongjin_item_icon);
                holder.intive_yongjin_item_time = (TextView) view.findViewById(R.id.intive_yongjin_item_time);
                holder.intive_yongjin_item_money = (TextView) view.findViewById(R.id.intive_yongjin_item_money);
                holder.intive_yongjin_item_xiaxian = (TextView) view.findViewById(R.id.intive_yongjin_item_xiaxian);
                holder.intive_yongjin_item_order = (TextView) view.findViewById(R.id.intive_yongjin_item_order);

                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }



            //这里图片就默认了
//            String url = Configs.IP_ADDRESS
//                    + Configs.IP_ADDRESS_ACTION_GETFILE
//                    + "?fileReq.pathCode=" + myInviteYongjinDetail.get(position).getPathCode()
//                    + "&fileReq.fileName=" + myInviteYongjinDetail.get(position).getShopPhoto();
//            ImageLoaderTools.getInstance(mContext).displayImage(url, holder.coupon_item_icon);
            return view;
        }

        class ViewHolder
        {
            RelativeLayout  intive_yongjin_item_layout;//整个优惠券的布局
            ImageView       intive_yongjin_item_icon;//图片
            TextView        intive_yongjin_item_time;//时间
            TextView        intive_yongjin_item_money;//金额
            TextView        intive_yongjin_item_xiaxian;//下线
            TextView        intive_yongjin_item_order;//顺序
        }
    }
}
